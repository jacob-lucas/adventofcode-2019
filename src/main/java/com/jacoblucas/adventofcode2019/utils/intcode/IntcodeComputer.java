package com.jacoblucas.adventofcode2019.utils.intcode;

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

    static Instruction HALT = ImmutableInstruction.builder()
            .opcode(Opcode.HALT)
            .build();

    private Array<Integer> memory;
    private int instructionPointer;

    public IntcodeComputer(final Array<Integer> input) {
        memory = input;
        instructionPointer = 0;
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
        if (instruction == HALT) {
            return BREAK;
        }

//        System.out.println(String.format("[pos=%d] Before: %s", instructionPointer, memory));
        memory = instruction.execute(memory);
        instructionPointer += instruction.getIncrement();
//        System.out.println(String.format("[pos=%d] After: %s", instructionPointer, memory));

        return CONTINUE;
    }

    Option<Instruction> at(final int address) {
        if (address < 0 || address >= memory.size()) {
            return Option.none();
        }

        final Option<Integer> instruction = valueAt(address);
        if (instruction.isEmpty()) {
            return Option.none();
        }

        return getOpcode(instruction.get())
                .map(o -> Match(o).of(
                        Case($(isIn(Opcode.ADD, Opcode.MULTIPLY)), () -> {
                            final List<Option<Parameter>> params = List.of(
                                    getParameter(instruction.get(), address, 1),
                                    getParameter(instruction.get(), address, 2),
                                    getParameter(instruction.get(), address, 3));
                            if (params.contains(Option.none())) {
                                return HALT;
                            }

                            return ImmutableInstruction.builder()
                                    .opcode(o)
                                    .parameters(params.map(Option::get))
                                    .build();
                        }),
                        Case($(), HALT)
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

        final int mode = Match(parameterNumber).of(
                Case($(1), instruction / 100),
                Case($(2), instruction / 1000),
                Case($(3), instruction / 10000),
                Case($(), -1)); // should never happen based on check above

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
}
