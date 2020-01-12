package com.jacoblucas.adventofcode2019.day10;

import com.jacoblucas.adventofcode2019.utils.Calculator;
import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinate2D;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.immutables.value.Value;

@Value.Immutable
public abstract class Asteroid {
    @Value.Parameter
    public abstract Coordinate2D getCoordinate();

    @Override
    public String toString() {
        final Coordinate2D coordinate = getCoordinate();
        return String.format("(%d,%d)", coordinate.x(), coordinate.y());
    }

    public int countVisibleAsteroids(final AsteroidMap map) {
        final Map<Tuple2<Double, Double>, List<Asteroid>> byGradient = map.getAsteroids()
                .filter(a -> !a.equals(this))
                .groupBy(a -> {
                    final double gradient = Calculator.gradient(getCoordinate(), a.getCoordinate());
                    System.out.println(String.format("Gradient between %s and %s = %f",
                            this, a, gradient));
                    final double angle = Calculator.angleFromZero(getCoordinate(), a.getCoordinate());
                    return new Tuple2<>(gradient, angle);
                });
        return byGradient.size();
    }
}
