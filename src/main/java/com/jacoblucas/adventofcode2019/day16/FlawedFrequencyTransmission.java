package com.jacoblucas.adventofcode2019.day16;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Stream;

public class FlawedFrequencyTransmission {
    private static final List<Integer> BASE_REPEATING_PATTERN = List.of(0, 1, 0, -1);

    public List<Integer> process(final List<Integer> input, int phases) {
        List<Integer> result = input;
        int count = 0;
        while (count < phases) {
            result = phase(result);
            count++;
        }
        return result;
    }

    public List<Integer> phase(final List<Integer> input) {
        return Stream.range(0, input.size())
                .map(i -> {
                    final List<Integer> pattern = offset(1, getRepeatingPattern(i));
                    return Stream.range(0, input.size())
                            .map(j -> input.get(j) * pattern.get(j % pattern.size()))
                            .sum()
                            .intValue();
                })
                .map(i -> {
                    final String s = String.valueOf(i);
                    return s.charAt(s.length()-1);
                })
                .map(Character::getNumericValue)
                .toList();
    }

    public List<Integer> getRepeatingPattern(final int index) {
        final int repeatCount = index + 1;
        return BASE_REPEATING_PATTERN
                .map(n -> Stream.range(0, repeatCount)
                        .map(rc -> n))
                .flatMap(Stream::toList)
                .toList();
    }

    public List<Integer> offset(final int n, final List<Integer> input) {
        final Tuple2<List<Integer>, List<Integer>> subLists = input.splitAt(n);
        return subLists._2.appendAll(subLists._1);
    }
}
