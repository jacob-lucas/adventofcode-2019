package com.jacoblucas.adventofcode2019.day7;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;
import io.vavr.collection.Array;
import io.vavr.collection.Queue;

public class Amplifier {
    private final int id;
    private final Array<Integer> program;

    public Amplifier(final Integer id, final Array<Integer> program) {
        this.id = id;
        this.program = program;
    }

    public int amplify(final int phaseSetting, final int inputSignal) {
        final IntcodeComputer computer = new IntcodeComputer();
        computer.feed(program, Queue.of(phaseSetting, inputSignal));
        computer.execute();
        final int output = computer.getOutput();
        System.out.println(String.format("%d -> Amp[%d(ps=%d)] -> %d", inputSignal, id, phaseSetting, output));
        return output;
    }
}
