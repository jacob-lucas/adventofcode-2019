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

        return Opcode.of(memory.get(address))
                .map(o -> Match(o).of(
                        Case($(Opcode.HALT), HALT),
                        Case($(), () -> {
                            final Try<Integer> a = Try.of(() -> memory.get(address + 1));
                            final Try<Integer> b = Try.of(() -> memory.get(address + 2));
                            final Try<Integer> c = Try.of(() -> memory.get(address + 3));

                            if (a.isFailure() || b.isFailure() || c.isFailure()) {
                                System.out.println(String.format("Unable to access codes at positions [%d, %d, %d] (num codes = %d)", address + 1, address + 2, address + 3, memory.length()));
                                return HALT;
                            }

                            final List<Integer> parameters = List.of(a.get(), b.get(), c.get());
                            return ImmutableInstruction.builder()
                                    .opcode(o)
                                    .parameters(parameters)
                                    .build();
                        })
                ));
    }

}
