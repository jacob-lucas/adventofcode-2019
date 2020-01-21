package com.jacoblucas.adventofcode2019.day12;

import io.vavr.collection.List;
import io.vavr.control.Try;

import static com.jacoblucas.adventofcode2019.utils.InputReader.read;

public class Day12 {
    public static void main(String[] args) {
        final List<Moon> moons = read("day12-input.txt")
                .map(Moon::parse)
                .map(Try::get)
                .toList();

        final Planet jupiter = ImmutablePlanet.builder()
                .moons(moons)
                .build();

        jupiter.simulate(1000);

        System.out.println(jupiter.getTotalEnergy());
    }
}
