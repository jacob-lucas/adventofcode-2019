package com.jacoblucas.adventofcode2019.utils.intcode;

import com.jacoblucas.adventofcode2019.utils.intcode.instructions.Instruction;
import com.jacoblucas.adventofcode2019.utils.intcode.instructions.InstructionFactory;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;

public class IntcodeComputer {
    private static final int CONTINUE = 0;
    private static final int BREAK = 1;

    private Array<Integer> memory;
    private int instructionPointer;
    private int input;
    private int output;

    public void feed(final Array<Integer> program) {
        feed(program, Integer.MIN_VALUE);
    }

    public void feed(Array<Integer> program, int input) {
        this.instructionPointer = 0;
        this.memory = program;
        this.input = input;
        this.output = Integer.MIN_VALUE;
    }

    public int getOutput() {
        if (output == Integer.MIN_VALUE) {
            return memory.get(0);
        } else {
            return output;
        }
    }

    public IntcodeComputer execute() {
        int result = CONTINUE;
        while (result == CONTINUE) {
            final Try<Instruction> instruction = InstructionFactory.at(instructionPointer, memory, input == Integer.MIN_VALUE ? Option.none() : Option.of(input));
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

        final Object result = instruction.execute(memory);

        final Opcode opcode = instruction.getOpcode();
        if (opcode == Opcode.OUTPUT) {
            output = (Integer) result;
            System.out.println(String.format("%s INPUT=%d OUTPUT=%d", instruction, input, output));
        } else if (List.of(Opcode.JUMP_IF_TRUE, Opcode.JUMP_IF_FALSE).contains(opcode)) {
            final int jumpToIndex = (Integer) result;
            if (jumpToIndex > 0) {
                instructionPointer = jumpToIndex;
            }
        } else {
            memory = (Array<Integer>) result;
        }

        if (instructionPointer == currentInstructionPointer) {
            // only increment if an instruction has not adjusted the instruction pointer
            instructionPointer += instruction.getIncrement();
        }

//        System.out.println(String.format("[pos=%d] After: %s", instructionPointer, memory));

        return CONTINUE;
    }

    Array<Integer> getMemory() {
        return this.memory;
    }
}
