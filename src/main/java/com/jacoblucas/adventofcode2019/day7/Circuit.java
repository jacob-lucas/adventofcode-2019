package com.jacoblucas.adventofcode2019.day7;

import io.vavr.collection.Array;
import io.vavr.collection.Stream;

public class Circuit {
    final Array<Amplifier> amplifiers;

    public Circuit(final int n, final Array<Integer> program, final CircuitMode mode) {
        this.amplifiers = Stream.range(0, n).map(i -> new Amplifier(i, program)).toArray();
        for (int i=0; i<n-1; i++) {
            amplifiers.get(i).connect(amplifiers.get(i+1));
        }
        if (mode == CircuitMode.LOOP) {
            amplifiers.get(n-1).connect(amplifiers.get(0));
        }
    }

    public int run(final Integer... phaseSettings) {
        amplifiers.head().amplify(phaseSettings[0], 0);
        final int last = amplifiers.size() - 1;
        for (int i = 1; i<=last-1; i++) {
            amplifiers.get(i).amplify(phaseSettings[i]);
        }
        amplifiers.get(last).amplify(phaseSettings[last]);

        // wait for results
        do {
            try {
                Thread.sleep(250);
//                System.out.println(String.format("[%s] Alive: %s", Thread.currentThread().getName(), amplifiers.filter(a -> !a.completed()).map(Amplifier::id).toList()));
            } catch (InterruptedException e) {
                return -1;
            }
        } while (amplifiers.map(Amplifier::completed).size() < amplifiers.size());

        return amplifiers.get(last).result();
    }
}
