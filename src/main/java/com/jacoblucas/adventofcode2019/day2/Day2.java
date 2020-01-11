package com.jacoblucas.adventofcode2019.day2;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;
import io.vavr.collection.Map;

import java.math.BigInteger;

import static com.jacoblucas.adventofcode2019.utils.InputReader.loadInput;

public class Day2 {
    public static void main(String[] args) {
        Map<BigInteger, BigInteger> input = loadInput("day2-input.txt");
        input = input.put(BigInteger.valueOf(1), BigInteger.valueOf(12));
        input = input.put(BigInteger.valueOf(2), BigInteger.valueOf(2));

        IntcodeComputer computer = new IntcodeComputer();
        computer.feed(input);
        computer.execute();
        System.out.println(computer.getOutput());

        final BigInteger desiredOutput = BigInteger.valueOf(19690720);
        boolean found = false;
        for (int noun = 0; noun < 100 && !found; noun++) {
            for (int verb = 0; verb < 100 && !found; verb++) {
                input = input.put(BigInteger.valueOf(1), BigInteger.valueOf(noun));
                input = input.put(BigInteger.valueOf(2), BigInteger.valueOf(verb));
                computer.feed(input);
                computer.execute();
                final BigInteger output = computer.getOutput();
                if (output.equals(desiredOutput)) {
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
