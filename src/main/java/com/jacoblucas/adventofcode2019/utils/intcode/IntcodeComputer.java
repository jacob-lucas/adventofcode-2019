package com.jacoblucas.adventofcode2019.utils.intcode;

import com.jacoblucas.adventofcode2019.utils.InputReader;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Map;
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

    public IntcodeComputer(final String filename, final int input) {
        memory = InputReader.read(filename)
                .map(str -> str.split(","))
                .flatMap(Stream::of)
                .map(Integer::valueOf)
                .toArray();

        instructionPointer = 0;
        this.input = input;
    }

    public IntcodeComputer(final Array<Integer> program) {
        this(program, 0);
    }

    public IntcodeComputer(final Array<Integer> program, final int input) {
        memory = program;
        instructionPointer = 0;
        this.input = input;
    }

    public Array<Integer> getMemory() {
        return this.memory;
    }

    public int getOutput() {
        return getMemory().get(0);
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

//        System.out.println(String.format("[pos=%d] Before: %s", instructionPointer, memory));
        final Opcode opcode = instruction.getOpcode();
        if (List.of(Opcode.SAVE, Opcode.OUTPUT).contains(opcode)) {
            memory = instruction.execute(memory, input);
        } else {
            memory = instruction.execute(memory);
        }

        instructionPointer += instruction.getIncrement();
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
        return getOpcode(instruction)
                .map(o -> Match(o).of(
                        Case($(isIn(Opcode.ADD, Opcode.MULTIPLY)), () -> {
                            final List<Option<Parameter>> params = List.of(
                                    getParameter(instruction, address, 1),
                                    getParameter(instruction, address, 2),
                                    getParameter(instruction, address, 3));
                            if (params.contains(Option.none())) {
                                return PROGRAM_HALT;
                            }

                            return ImmutableInstruction.builder()
                                    .memoryAddress(address)
                                    .opcode(o)
                                    .parameters(params.map(Option::get))
                                    .build();
                        }),
                        Case($(isIn(Opcode.SAVE, Opcode.OUTPUT)), () -> {
                            final Option<Parameter> param = getParameter(instruction, address, 1);
                            if (param.isEmpty()) {
                                return PROGRAM_HALT;
                            }

                            return ImmutableInstruction.builder()
                                    .memoryAddress(address)
                                    .opcode(o)
                                    .parameters(List.of(param.get()))
                                    .build();
                        }),
                        Case($(), ImmutableInstruction.builder()
                                .memoryAddress(address)
                                .opcode(Opcode.HALT)
                                .build())
                ));
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
