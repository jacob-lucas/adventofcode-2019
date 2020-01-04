package com.jacoblucas.adventofcode2019.day7;

import io.vavr.collection.Array;
import io.vavr.collection.Queue;
import io.vavr.collection.Stream;

public class Circuit {
    private final Stream<Amplifier> amplifiers;

    public Circuit(final int n, final Array<Integer> program) {
        this.amplifiers = Stream.range(0, n).map(i -> new Amplifier(i, program));
    }

    public int run(final Integer... phaseSettings) {
        int signal = 0;
        final Queue<Amplifier> amplifiers = this.amplifiers.toQueue();
        return run(amplifiers, Queue.of(phaseSettings), signal);
    }

    private int run(Queue<Amplifier> amplifiers, Queue<Integer> phaseSettings, int signal) {
        if (amplifiers.isEmpty()) {
            return signal;
        } else {
            final int output = amplifiers.head().amplify(phaseSettings.head(), signal);
            return run(amplifiers.tail(), phaseSettings.tail(), output);
        }
    }
}
