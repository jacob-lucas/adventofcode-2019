package com.jacoblucas.adventofcode2019.day9;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;
import io.vavr.collection.Map;
import io.vavr.collection.Queue;

import java.math.BigInteger;

import static com.jacoblucas.adventofcode2019.utils.InputReader.loadInput;

public class Day9 {
    public static void main(String[] args) {
        final Map<BigInteger, BigInteger> program = loadInput("day9-input.txt");
        final IntcodeComputer computer = new IntcodeComputer();
        computer.feed(program, Queue.of(BigInteger.ONE));
        computer.execute();
        System.out.println(computer.getOutput());
    }
}
