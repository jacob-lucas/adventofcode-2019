package com.jacoblucas.adventofcode2019.utils;

import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinates;
import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinates2D;


public final class Calculator {
    public static int manhattanDistance(final Coordinates p, final Coordinates q) {
        return Math.abs(p.x() - q.x()) + Math.abs(p.y() - q.y()) + Math.abs(p.z() - q.z());
    }

    public static double distance(final Coordinates p, final Coordinates q) {
        return Math.sqrt(Math.pow(q.x() - p.x(), 2) + Math.pow(q.y() - p.y(), 2));
    }

    public static double gradient(final Coordinates2D p, final Coordinates2D q) {
        return ((double) q.y() - p.y()) / (q.x() - p.x());
    }

    public static double relativeAngleFromZero(final Coordinates2D p, final Coordinates2D q) {
        final int opp = p.x() - q.x();
        final int adj = p.y() - q.y();
        final int positive = opp >= 0 ? -1 : 1;
        return positive * Math.toDegrees(Math.atan((double)opp / adj));
    }

    public static double absoluteAngleFromZero(final Coordinates2D p, final Coordinates2D q) {
        final int opp = Math.abs(q.x() - p.x());
        final int adj = Math.abs(q.y() - p.y());

        int quadrant;
        if (p.x() == q.x()) {
            quadrant = q.y() <= p.y() ? 0 : 2;
        } else if (p.y() == q.y()) {
            quadrant = q.x() >= p.x() ? 0 : 2;
        } else if (q.x() > p.x()) {
            quadrant = q.y() > p.y() ? 1 : 0;
        } else if (q.y() > p.y()) {
            quadrant = q.x() < p.x() ? 2 : 1;
        } else {
            quadrant = 3;
        }

        final double degrees = Math.toDegrees(Math.atan((double) opp / adj));
        if (quadrant == 0 || quadrant == 2) {
            // 0 or 2, add
            return quadrant * 90 + degrees;
        } else {
            return (quadrant + 1) * 90 - degrees;
        }
    }
}
