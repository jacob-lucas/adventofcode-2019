package com.jacoblucas.adventofcode2019.utils;

import com.jacoblucas.adventofcode2019.day3.Coordinate;

public final class Calculator {
    public static int manhattanDistance(final Coordinate p, final Coordinate q) {
        return Math.abs(p.x() - q.x()) + Math.abs(p.y() - q.y()) + Math.abs(p.z() - q.z());
    }
}
