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

        final IntcodeComputer computer = new IntcodeComputer(input);
        computer.update(HashMap.of(1, 12, 2, 2));
        computer.execute();
        System.out.println(computer.getOutput());
    }
}
