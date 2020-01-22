package com.jacoblucas.adventofcode2019.day12;

import io.vavr.collection.List;
import io.vavr.control.Try;

import java.math.BigInteger;

import static com.jacoblucas.adventofcode2019.utils.InputReader.read;

public class Day12 {
    public static void main(String[] args) {
        Planet jupiter = ImmutablePlanet.builder()
                .moons(loadMoons())
                .build();

        MoonMotionSimulator.simulate(1000, jupiter);

        System.out.println(jupiter.getTotalEnergy());

        jupiter = ImmutablePlanet.builder()
                .moons(loadMoons())
                .build();

        final BigInteger steps = MoonMotionSimulator.simulateLoop(jupiter);
        System.out.println(steps);
    }

    private static List<Moon> loadMoons() {
        return read("day12-input.txt")
                .map(Moon::parse)
                .map(Try::get)
                .toList();
    }
}
