package com.jacoblucas.adventofcode2019.day10;

import com.jacoblucas.adventofcode2019.utils.InputReader;
import io.vavr.collection.List;
import io.vavr.control.Option;

public class Day10 {
    public static void main(String[] args) {
        final List<String> input = InputReader.read("day10-input.txt").toList();
        final AsteroidMap asteroidMap = new AsteroidMap(input);

        final Option<Integer> max = asteroidMap.getAsteroids()
                .map(a -> a.countVisibleAsteroids(asteroidMap))
                .max();
        System.out.println(max.get());
    }
}
