package com.jacoblucas.adventofcode2019.day8;

import com.jacoblucas.adventofcode2019.utils.InputReader;
import io.vavr.collection.Array;

public class Day8 {
    public static void main(String[] args) {
        final String input = InputReader.read("day8-input.txt").head();

        final Array<Array<String>> layers = SpaceImageFormat.layers(input, 25, 6);
        final Array<String> fewestZeroes = layers
                .sortBy(layer -> SpaceImageFormat.countDigit(layer, 0))
                .head();

        final int ones = SpaceImageFormat.countDigit(fewestZeroes, 1);
        final int twos = SpaceImageFormat.countDigit(fewestZeroes, 2);
        System.out.println(ones * twos);

        SpaceImageFormat.decode(input, 25, 6).forEach(System.out::println);
    }
}
