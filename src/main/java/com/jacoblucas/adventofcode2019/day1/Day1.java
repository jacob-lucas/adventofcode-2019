package com.jacoblucas.adventofcode2019.day1;

import com.jacoblucas.adventofcode2019.utils.InputReader;
import io.vavr.collection.Stream;

public class Day1 {
    public static void main(String[] args) {
        final Stream<String> input = InputReader.read("day1-input.txt");
        final int requiredFuel = input.map(Module::of)
                .map(Module::getRequiredFuel)
                .sum()
                .intValue();
        System.out.println(requiredFuel);
    }
}
