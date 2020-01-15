package com.jacoblucas.adventofcode2019.day11;

import io.vavr.collection.Stream;

public enum Direction {
    UP(0),
    DOWN(180),
    LEFT(270),
    RIGHT(90);

    private final int bearing;

    Direction(final int bearing) {
        this.bearing = bearing;
    }

    static Direction of(final int bearing) {
        return Stream.of(values())
                .filter(d -> d.bearing == bearing)
                .head();
    }

    Direction left() {
        return of(bearing > 0 ? bearing - 90 : 270);
    }

    Direction right() {
        return of(bearing < 270 ? bearing + 90 : 0);
    }
}