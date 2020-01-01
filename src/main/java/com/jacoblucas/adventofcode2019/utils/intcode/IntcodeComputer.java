package com.jacoblucas.adventofcode2019.utils.intcode;

import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import io.vavr.control.Try;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.isIn;

public class IntcodeComputer {
    private static final int CONTINUE = 0;
    private static final int BREAK = 1;

    static Instruction PROGRAM_HALT = ImmutableInstruction.builder()
            .memoryAddress(-1)
            .opcode(Opcode.HALT)
            .build();

    private Array<Integer> memory;
    private int instructionPointer;
    private int input;
    private int output;

    public IntcodeComputer(final Array<Integer> program) {
        this(program, 0);
    }

    public IntcodeComputer(final Array<Integer> program, final int input) {
        memory = program;
        instructionPointer = 0;
        this.input = input;
        this.output = Integer.MIN_VALUE;
    }

    public Array<Integer> getMemory() {
        return this.memory;
    }

    public int getOutput() {
        if (output == Integer.MIN_VALUE) {
            return getMemory().get(0);
        } else {
            return output;
        }
    }

    public IntcodeComputer update(final Map<Integer, Integer> addressValueMap) {
        if (Stream.range(0, memory.length()).containsAll(addressValueMap.keySet())) {
            addressValueMap.forEach((k, v) -> memory = memory.update(k, v));
        }
        return this;
    }

    public IntcodeComputer execute() {
        int result = CONTINUE;
        while (result == CONTINUE) {
            final Option<Instruction> instruction = at(instructionPointer);
            result = instruction.map(this::execute).getOrElse(BREAK);
        }
        return this;
    }

    private int execute(final Instruction instruction) {
        if (instruction.getOpcode() == Opcode.HALT) {
            return BREAK;
        }

        final int currentInstructionPointer = instructionPointer;

//        System.out.println(String.format("[pos=%d] Before: %s", instructionPointer, memory));

        final Opcode opcode = instruction.getOpcode();
        if (opcode == Opcode.SAVE) {
            memory = instruction.execute(memory, input);
        } else if (opcode == Opcode.OUTPUT) {
            output = instruction.getParameterValue(0, memory);
            System.out.println(String.format("%s INPUT=%d OUTPUT=%d", instruction, input, output));
        } else if (List.of(Opcode.JUMP_IF_TRUE, Opcode.JUMP_IF_FALSE).contains(opcode)) {
            final int jumpToIndex = instruction.calculateInstructionPointer(memory);
            if (jumpToIndex > 0) {
                instructionPointer = jumpToIndex;
            }
        } else {
            memory = instruction.execute(memory);
        }

        if (instructionPointer == currentInstructionPointer) {
            // only increment if an instruction has not adjusted the instruction pointer
            instructionPointer += instruction.getIncrement();
        }

//        System.out.println(String.format("[pos=%d] After: %s", instructionPointer, memory));

        return CONTINUE;
    }

    Option<Instruction> at(final int address) {
        if (address < 0 || address >= memory.size()) {
            return Option.none();
        }

        final Option<Integer> integerOption = valueAt(address);
        if (integerOption.isEmpty()) {
            return Option.none();
        }

        final Integer instruction = integerOption.get();
        final Option<Opcode> opcodeOption = getOpcode(instruction);
        if (opcodeOption.isEmpty()) {
            return Option.none();
        }

        final Opcode opcode = opcodeOption.get();

        return Option.of(getInstruction(address, instruction, opcode));
    }

    private Instruction getInstruction(
            final int address,
            final int instruction,
            final Opcode opcode
    ) {
        final int numExpectedParameters = Match(opcode).of(
                Case($(isIn(Opcode.ADD, Opcode.MULTIPLY, Opcode.LESS_THAN, Opcode.EQUALS)), 3),
                Case($(isIn(Opcode.JUMP_IF_TRUE, Opcode.JUMP_IF_FALSE)), 2),
                Case($(isIn(Opcode.SAVE, Opcode.OUTPUT, Opcode.JUMP_IF_TRUE, Opcode.JUMP_IF_FALSE)), 1),
                Case($(Opcode.HALT), 0));

        final Seq<Option<Parameter>> params = Stream.range(1, numExpectedParameters + 1)
                .map(n -> getParameter(instruction, address, n));
        if (params.contains(Option.none())) {
            return PROGRAM_HALT;
        }

        return ImmutableInstruction.builder()
                .memoryAddress(address)
                .opcode(opcode)
                .parameters(params.map(Option::get).toList())
                .build();
    }

    Option<Opcode> getOpcode(final int instruction) {
        return Opcode.of(instruction % 100);
    }

    Option<Parameter> getParameter(final int instruction, final int address, final int parameterNumber) {
        if (!List.of(1,2,3).contains(parameterNumber)) {
            System.out.println(String.format("Unsupported parameterNumber[%d]", parameterNumber));
            return Option.none();
        }

        final int parameterAddress = address + parameterNumber;
        final Option<Integer> parameterValue = valueAt(parameterAddress);
        if (parameterValue.isEmpty()) {
            return Option.none();
        }

        final int mode = getMode(instruction, parameterNumber);

        final Option<ParameterMode> modeValue = ParameterMode.of(mode);
        if (modeValue.isEmpty()) {
            return Option.none();
        }

        return Option.of(ImmutableParameter.builder()
                .value(parameterValue.get())
                .mode(modeValue.get())
                .build());
    }

    private Option<Integer> valueAt(final int address) {
        final Try<Integer> value = Try.of(() -> memory.get(address));
        if (value.isFailure()) {
            System.out.println(String.format("Unable to access memory at address [%d] (capacity = %d)", address, memory.length()));
            return Option.none();
        }

        return Option.of(value.get());
    }

    private int getMode(final int instruction, final int parameterNumber) {
        final char[] mode = String.format("%05d", instruction).toCharArray();
        return Character.getNumericValue(Match(parameterNumber).of(
                Case($(1), mode[2]),
                Case($(2), mode[1]),
//                Case($(3), mode[0]),
                Case($(), '0')));
    }
}
