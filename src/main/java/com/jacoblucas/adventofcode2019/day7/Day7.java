package com.jacoblucas.adventofcode2019.day7;

import io.vavr.collection.Array;
import io.vavr.collection.Map;

import java.math.BigInteger;

import static com.jacoblucas.adventofcode2019.utils.InputReader.loadInput;

public class Day7 {
    public static void main(String[] args) {
        final Map<BigInteger, BigInteger> program = loadInput("day7-input.txt");

        BigInteger[] max = {BigInteger.valueOf(Integer.MIN_VALUE)};
        Array.of(0,1,2,3,4).permutations().forEach(phaseSetting -> {
            final Circuit circuit = new Circuit(5, program, CircuitMode.RUN_ONCE);
            final BigInteger result = circuit.run(phaseSetting.toJavaArray(Integer.class));
            if (result.compareTo(max[0]) > 0) {
                max[0] = result;
            }
        });

        System.out.println(max[0]);

        max[0] = BigInteger.valueOf(Long.MIN_VALUE);
        Array.of(5,6,7,8,9).permutations().forEach(phaseSetting -> {
            final Circuit circuit = new Circuit(5, program, CircuitMode.LOOP);
            final BigInteger result = circuit.run(phaseSetting.toJavaArray(Integer.class));
            if (result.compareTo(max[0]) > 0) {
                max[0] = result;
            }
        });

        System.out.println(max[0]);
    }
}
