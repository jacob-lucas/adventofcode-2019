package com.jacoblucas.adventofcode2019.day2;

import com.jacoblucas.adventofcode2019.utils.InputReader;
import io.vavr.collection.Stream;

public class Day2 {
    public static void main(String[] args) {
        final Stream<String> input = InputReader.read("day2-input.txt")
                .map(str -> str.split(","))
                .flatMap(Stream::of);

        final Intcode intcode = new Intcode(input.map(Integer::valueOf));
        intcode.update(1, 12);
        intcode.update(2, 2);

        final Intcode result = intcode.execute();
        System.out.println(result.underlying().get(0));
    }
}
