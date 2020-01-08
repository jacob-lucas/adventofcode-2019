package com.jacoblucas.adventofcode2019.day5;

import com.jacoblucas.adventofcode2019.utils.InputReader;
import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;
import io.vavr.collection.Array;
import io.vavr.collection.Queue;
import io.vavr.collection.Stream;

import java.math.BigInteger;

public class Day5 {
    public static void main(String[] args) {
        final Array<BigInteger> input = InputReader.read("day5-input.txt")
                .map(str -> str.split(","))
                .flatMap(Stream::of)
                .map(BigInteger::new)
                .toArray();

        IntcodeComputer computer = new IntcodeComputer();
        computer.feed(input, Queue.of(BigInteger.ONE));
        computer.execute();
        System.out.println(computer.getOutput());

        computer.feed(input, Queue.of(BigInteger.valueOf(5)));
        computer.execute();
        System.out.println(computer.getOutput());
    }
}
