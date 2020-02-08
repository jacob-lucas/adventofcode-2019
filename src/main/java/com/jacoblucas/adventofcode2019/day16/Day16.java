package com.jacoblucas.adventofcode2019.day16;

import com.jacoblucas.adventofcode2019.utils.InputReader;
import io.vavr.collection.List;
import io.vavr.collection.Stream;

public class Day16 {
    public static void main(String[] args) {
        final String inputStr = InputReader.read("day16-input.txt").head();
        final List<Integer> input = Stream.range(0, inputStr.length())
                .map(inputStr::charAt)
                .map(Character::getNumericValue)
                .toList();

        final FlawedFrequencyTransmission fft = new FlawedFrequencyTransmission();
        final List<Integer> result = fft.process(input, 100);
        System.out.println(result.take(8));
    }
}
