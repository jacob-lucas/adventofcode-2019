package com.jacoblucas.adventofcode2019.day5;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;
import io.vavr.collection.Map;
import io.vavr.collection.Queue;

import java.math.BigInteger;

import static com.jacoblucas.adventofcode2019.utils.InputReader.loadInput;

public class Day5 {
    public static void main(String[] args) {
        final Map<BigInteger, BigInteger> input = loadInput("day5-input.txt");

        IntcodeComputer computer = new IntcodeComputer();
        computer.feed(input, Queue.of(BigInteger.ONE));
        computer.execute();
        System.out.println(computer.getOutput());

        computer.feed(input, Queue.of(BigInteger.valueOf(5)));
        computer.execute();
        System.out.println(computer.getOutput());
    }
}
