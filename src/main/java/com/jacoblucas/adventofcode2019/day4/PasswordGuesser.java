package com.jacoblucas.adventofcode2019.day4;

import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Stream;

import java.util.function.Function;
import java.util.function.Predicate;

public class PasswordGuesser {
    private final int lower;
    private final int upper;
    private final List<Predicate<Integer>> rules;

    public PasswordGuesser(final int lower, final int upper) {
        this.lower = lower;
        this.upper = upper;

        rules = List.of(
                n -> String.valueOf(n).length() == 6,
                n -> lower <= n && n <= upper,
                n -> String.valueOf(n).matches("^(?=.*(.)\\1)[0-9]+$"),            // https://regex101.com/r/nG2xO7/78
                n -> String.valueOf(n).matches("^(?=\\d{6}$)1*2*3*4*5*6*7*8*9*$"), // https://regex101.com/r/nG2xO7/80
                n -> Array.of(String.valueOf(n).split(""))
                        .groupBy(Function.identity())
                        .count(k -> k._2.size() == 2L) > 0
        );
    }

    public boolean meetsCriteria(final int guess) {
        return rules.foldLeft(true, (bool, rule) -> bool && rule.test(guess));
    }

    public int validInRange() {
        return Stream.range(lower, upper).count(this::meetsCriteria);
    }
}
