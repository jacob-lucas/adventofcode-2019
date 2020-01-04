package com.jacoblucas.adventofcode2019.day5;

import com.jacoblucas.adventofcode2019.utils.InputReader;
import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;
import io.vavr.collection.Array;
import io.vavr.collection.Queue;
import io.vavr.collection.Stream;

public class Day5 {
    public static void main(String[] args) {
        final Array<Integer> input = InputReader.read("day5-input.txt")
                .map(str -> str.split(","))
                .flatMap(Stream::of)
                .map(Integer::valueOf)
                .toArray();

        IntcodeComputer computer = new IntcodeComputer();
        computer.feed(input, Queue.of(1));
        computer.execute();
        System.out.println(computer.getOutput());

        computer.feed(input, Queue.of(5));
        computer.execute();
        System.out.println(computer.getOutput());
    }
}
