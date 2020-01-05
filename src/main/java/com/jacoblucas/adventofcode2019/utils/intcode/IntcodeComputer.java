package com.jacoblucas.adventofcode2019.utils.intcode;

import com.jacoblucas.adventofcode2019.utils.intcode.instructions.ImmutableInputInstruction;
import com.jacoblucas.adventofcode2019.utils.intcode.instructions.InputInstruction;
import com.jacoblucas.adventofcode2019.utils.intcode.instructions.Instruction;
import com.jacoblucas.adventofcode2019.utils.intcode.instructions.InstructionFactory;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Queue;
import io.vavr.control.Try;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static io.vavr.control.Option.none;
import static io.vavr.control.Option.some;

public class IntcodeComputer {
    private static final int CONTINUE = 0;
    private static final int BREAK = 1;

    private Array<Integer> memory;
    private int instructionPointer;
    private BlockingQueue<Integer> input;
    private int output;
    private List<IntcodeComputerOutputReceiver> receivers = List.empty();

    public void feed(final Array<Integer> program) {
        feed(program, Queue.empty());
    }

    public void feed(Array<Integer> program, final Queue<Integer> input) {
        this.instructionPointer = 0;
        this.memory = program;
        this.output = Integer.MIN_VALUE;

        this.input = new LinkedBlockingQueue<>();
        this.input.addAll(input.toJavaList());
    }

    public void subscribe(final IntcodeComputerOutputReceiver receiver) {
        receivers = receivers.append(receiver);
//        System.out.println(String.format("[%s] Added subscriber [%s]", Thread.currentThread().getName(), receiver.id()));
    }

    private void publish(final int output) {
        receivers.forEach(r -> {
            r.receive(output);
//            System.out.println(String.format("[%s] Published output [%d] to subscriber [%s]", Thread.currentThread().getName(), output, r.id()));
        });
    }

    public int getOutput() {
        if (output == Integer.MIN_VALUE) {
            return memory.get(0);
        } else {
            return output;
        }
    }

    public IntcodeComputer execute() {
//        System.out.println(String.format("[%s] begin - queue:%s", Thread.currentThread().getName(), input));
        int result = CONTINUE;
        while (result == CONTINUE) {
            final Try<Instruction> instruction = InstructionFactory.at(instructionPointer, memory, none());
            result = instruction.map(this::execute).getOrElse(BREAK);
        }
//        System.out.println(String.format("[%s] end", Thread.currentThread().getName()));
        return this;
    }

    public void receiveInput(final int input) {
        this.input.add(input);
    }

    private int execute(Instruction instruction) {
        if (instruction.getOpcode() == Opcode.HALT) {
            return BREAK;
        } else if (instruction instanceof InputInstruction && instruction.getInput().isEmpty()) {
//            System.out.println(String.format("[%s] Awaiting input for instruction (ptr=%d) %s", Thread.currentThread().getName(), instructionPointer, instruction));
            try {
                instruction = ImmutableInputInstruction.copyOf((InputInstruction)instruction).withInput(some(input.take()));
            } catch (InterruptedException e) {
                return BREAK;
            }
//            System.out.println(String.format("[%s] Input received = %d", Thread.currentThread().getName(), instruction.getInput().get()));
        }

        final int currentInstructionPointer = instructionPointer;

//        System.out.println(String.format("[pos=%d] Before: %s", instructionPointer, memory));

        final Object result = instruction.execute(memory);

        final Opcode opcode = instruction.getOpcode();
        if (opcode == Opcode.OUTPUT) {
            output = (Integer) result;
            publish(output);
//            System.out.println(String.format("%s OUTPUT=%d", instruction, output));
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
