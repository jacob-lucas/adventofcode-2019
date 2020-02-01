package com.jacoblucas.adventofcode2019.day14;

import com.jacoblucas.adventofcode2019.utils.InputReader;
import io.vavr.collection.List;
import io.vavr.control.Try;

public class Day14 {
    public static void main(String[] args) {
        final List<Reaction> reactions = InputReader.read("day14-input.txt")
                .map(Reaction::parse)
                .filter(Try::isSuccess)
                .map(Try::get)
                .toList();

        final Nanofactory nanofactory = new Nanofactory(reactions);
        final int ore = nanofactory.produce(Nanofactory.FUEL, 1);
        System.out.println(ore);
    }
}
