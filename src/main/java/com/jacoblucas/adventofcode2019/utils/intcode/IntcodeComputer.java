package com.jacoblucas.adventofcode2019.utils.intcode;

import com.jacoblucas.adventofcode2019.utils.intcode.instructions.ImmutableInputInstruction;
import com.jacoblucas.adventofcode2019.utils.intcode.instructions.InputInstruction;
import com.jacoblucas.adventofcode2019.utils.intcode.instructions.Instruction;
import com.jacoblucas.adventofcode2019.utils.intcode.instructions.InstructionFactory;
import com.jacoblucas.adventofcode2019.utils.intcode.instructions.OutputInstruction;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Queue;
import io.vavr.control.Try;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData.INSTRUCTION_POINTER_KEY;
import static com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData.MEMORY_KEY;
import static io.vavr.control.Option.none;
import static io.vavr.control.Option.some;

public class IntcodeComputer {
    private static final int CONTINUE = 0;
    private static final int BREAK = 1;

    private BlockingQueue<Integer> input;
    private int output;
    private List<IntcodeComputerOutputReceiver> receivers = List.empty();
    private IntcodeComputerData data = new IntcodeComputerData();

    public void feed(final Array<Integer> program) {
        feed(program, Queue.empty());
    }

    public void feed(Array<Integer> program, final Queue<Integer> input) {
        this.output = Integer.MIN_VALUE;
        this.input = new LinkedBlockingQueue<>();
        this.input.addAll(input.toJavaList());

        data.put(INSTRUCTION_POINTER_KEY, 0);
        data.put(MEMORY_KEY, program);
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

    public void receiveInput(final int input) {
        this.input.add(input);
    }

    public int getOutput() {
        return output == Integer.MIN_VALUE ? getMemory().get(0) : output;
    }

    public IntcodeComputer execute() {
//        System.out.println(String.format("[%s] begin - queue:%s", Thread.currentThread().getName(), input));
        int result = CONTINUE;
        while (result == CONTINUE) {
            final int instructionPointer = getInstructionPointer();
            final Try<Instruction> instruction = InstructionFactory.at(instructionPointer, getMemory(), none());
            result = instruction.map(this::execute).getOrElse(BREAK);
        }
//        System.out.println(String.format("[%s] end", Thread.currentThread().getName()));
        return this;
    }

    private int execute(Instruction instruction) {
        final int currentInstructionPointer = getInstructionPointer();
//        System.out.println(String.format("[pos=%d] Before: %s", currentInstructionPointer, memory));

        if (instruction.getOpcode() == Opcode.HALT) {
            return BREAK;
        } else if (instruction instanceof InputInstruction && instruction.getInput().isEmpty()) {
//            System.out.println(String.format("[%s] Awaiting input for instruction (ptr=%d) %s", Thread.currentThread().getName(), currentInstructionPointer, instruction));
            try {
                instruction = ImmutableInputInstruction.copyOf((InputInstruction)instruction).withInput(some(input.take()));
            } catch (InterruptedException e) {
                return BREAK;
            }
//            System.out.println(String.format("[%s] Input received = %d", Thread.currentThread().getName(), instruction.getInput().get()));
        }

        final Object result = instruction.execute(data);

        if (instruction instanceof OutputInstruction) {
            output = (Integer) result;
            publish(output);
//            System.out.println(String.format("%s OUTPUT=%d", instruction, output));
        }

        final int instructionPointer = getInstructionPointer();
        if (instructionPointer == currentInstructionPointer) {
            // only increment if an instruction has not adjusted the instruction pointer
            data.put(INSTRUCTION_POINTER_KEY, instructionPointer + instruction.getIncrement());
        }

//        System.out.println(String.format("[pos=%d] After: %s", currentInstructionPointer, memory));

        return CONTINUE;
    }

    int getInstructionPointer() {
        return data.get(INSTRUCTION_POINTER_KEY, Integer.class);
    }

    Array<Integer> getMemory() {
        return data.get(MEMORY_KEY, Array.class);
    }
}
