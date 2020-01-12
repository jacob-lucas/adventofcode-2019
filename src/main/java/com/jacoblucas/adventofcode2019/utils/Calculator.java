package com.jacoblucas.adventofcode2019.utils;

import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinate;
import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinate2D;


public final class Calculator {
    public static int manhattanDistance(final Coordinate p, final Coordinate q) {
        return Math.abs(p.x() - q.x()) + Math.abs(p.y() - q.y()) + Math.abs(p.z() - q.z());
    }

    public static double gradient(final Coordinate2D p, final Coordinate2D q) {
        return ((double) q.y() - p.y()) / (q.x() - p.x());
    }

    public static double angleFromZero(final Coordinate2D p, final Coordinate2D q) {
        final int opp = p.x() - q.x();
        final int adj = p.y() - q.y();
        final int positive = opp >= 0 ? -1 : 1;
        return positive * Math.tan((double)opp / adj);
    }
}
