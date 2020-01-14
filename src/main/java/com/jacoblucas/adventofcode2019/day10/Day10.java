package com.jacoblucas.adventofcode2019.day10;

import com.jacoblucas.adventofcode2019.utils.InputReader;
import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinate2D;
import io.vavr.collection.List;

public class Day10 {
    public static void main(String[] args) {
        final List<String> input = InputReader.read("day10-input.txt").toList();
        final AsteroidMap asteroidMap = new AsteroidMap(input);

        final Asteroid bestLocation = asteroidMap.getAsteroids()
                .sortBy(a -> a.countVisibleAsteroids(asteroidMap))
                .last();
        final int visibleCount = bestLocation.countVisibleAsteroids(asteroidMap);

        System.out.println(String.format("Asteroid at %s has line of sight visibility to %d other asteroids", bestLocation, visibleCount));
        final MonitoringStation monitoringStation = ImmutableMonitoringStation.builder()
                .asteroid(bestLocation)
                .build();

        final int n = 200;
        final Asteroid asteroid = monitoringStation.vaporize(asteroidMap, n);
        System.out.println(String.format("Asteroid #%d is at %s", n, asteroid));

        final Coordinate2D coordinate = asteroid.getCoordinate();
        final int x = coordinate.x();
        final int y = coordinate.y();
        System.out.println(String.format("%d * 100 + %d = %d", x, y, x * 100 + y));
    }
}
