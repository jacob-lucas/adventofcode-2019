package com.jacoblucas.adventofcode2019.day2;

import com.jacoblucas.adventofcode2019.utils.InputReader;
import io.vavr.collection.Array;
import io.vavr.collection.HashMap;
import io.vavr.collection.Stream;

public class Day2 {
    public static void main(String[] args) {
        final Array<Integer> input = InputReader.read("day2-input.txt")
                .map(str -> str.split(","))
                .flatMap(Stream::of)
                .map(Integer::valueOf)
                .toArray();

        IntcodeComputer computer = new IntcodeComputer(input);
        computer.update(HashMap.of(1, 12, 2, 2));
        computer.execute();
        System.out.println(computer.getOutput());

        final int desiredOutput = 19690720;
        boolean found = false;
        for (int noun = 0; noun < 100 && !found; noun++) {
            for (int verb = 0; verb < 100 && !found; verb++) {
                computer = new IntcodeComputer(input);
                computer.update(HashMap.of(1, noun, 2, verb));
                computer.execute();
                final int output = computer.getOutput();
                if (output == desiredOutput) {
                    System.out.println(String.format("noun[%d] verb[%d] = %d", noun, verb, output));
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("noun/verb combination not found");
        }
    }
}
