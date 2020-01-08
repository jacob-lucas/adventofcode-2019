package com.jacoblucas.adventofcode2019.day7;

import com.jacoblucas.adventofcode2019.utils.InputReader;
import io.vavr.collection.Array;
import io.vavr.collection.Stream;

import java.math.BigInteger;

public class Day7 {
    public static void main(String[] args) {
        final Array<BigInteger> program = InputReader.read("day7-input.txt")
                .map(str -> str.split(","))
                .flatMap(Stream::of)
                .map(BigInteger::new)
                .toArray();

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
