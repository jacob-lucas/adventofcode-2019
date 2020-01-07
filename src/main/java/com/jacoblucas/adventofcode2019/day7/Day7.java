package com.jacoblucas.adventofcode2019.day7;

import com.jacoblucas.adventofcode2019.utils.InputReader;
import io.vavr.collection.Array;
import io.vavr.collection.Stream;

public class Day7 {
    public static void main(String[] args) {
        final Array<Integer> program = InputReader.read("day7-input.txt")
                .map(str -> str.split(","))
                .flatMap(Stream::of)
                .map(Integer::valueOf)
                .toArray();

        int[] max = {Integer.MIN_VALUE};
        Array.of(0,1,2,3,4).permutations().forEach(phaseSetting -> {
            final Circuit circuit = new Circuit(5, program, CircuitMode.RUN_ONCE);
            final int result = circuit.run(phaseSetting.toJavaArray(Integer.class));
            if (result > max[0]) {
                max[0] = result;
            }
        });

        System.out.println(max[0]);

        max[0] = Integer.MIN_VALUE;
        Array.of(5,6,7,8,9).permutations().forEach(phaseSetting -> {
            final Circuit circuit = new Circuit(5, program, CircuitMode.LOOP);
            final int result = circuit.run(phaseSetting.toJavaArray(Integer.class));
            if (result > max[0]) {
                max[0] = result;
            }
        });

        System.out.println(max[0]);
    }
}