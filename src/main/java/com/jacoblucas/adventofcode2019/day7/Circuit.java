package com.jacoblucas.adventofcode2019.day7;

import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;

import java.math.BigInteger;

public class Circuit {
    final Array<Amplifier> amplifiers;

    public Circuit(final int n, final Map<BigInteger, BigInteger> program, final CircuitMode mode) {
        this.amplifiers = Stream.range(0, n).map(i -> new Amplifier(i, program)).toArray();
        for (int i=0; i<n-1; i++) {
            amplifiers.get(i).connect(amplifiers.get(i+1));
        }
        if (mode == CircuitMode.LOOP) {
            amplifiers.get(n-1).connect(amplifiers.get(0));
        }
    }

    public BigInteger run(final Integer... phaseSettings) {
        amplifiers.head().amplify(BigInteger.valueOf(phaseSettings[0]), BigInteger.ZERO);
        final int last = amplifiers.size() - 1;
        for (int i = 1; i<=last-1; i++) {
            amplifiers.get(i).amplify(BigInteger.valueOf(phaseSettings[i]));
        }
        amplifiers.get(last).amplify(BigInteger.valueOf(phaseSettings[last]));

        // wait for results
        do {
            try {
                Thread.sleep(250);
//                System.out.println(String.format("[%s] Alive: %s", Thread.currentThread().getName(), amplifiers.filter(a -> !a.completed()).map(Amplifier::id).toList()));
            } catch (InterruptedException e) {
                return BigInteger.valueOf(-1);
            }
        } while (amplifiers.map(Amplifier::completed).size() < amplifiers.size());

        return amplifiers.get(last).result();
    }
}
