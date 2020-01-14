package com.jacoblucas.adventofcode2019.day10;

import com.jacoblucas.adventofcode2019.utils.Calculator;
import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinate2D;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.immutables.value.Value;

@Value.Immutable
public abstract class MonitoringStation {
    public abstract Asteroid getAsteroid();

    @Value.Derived
    public Coordinate2D getLocation() {
        return getAsteroid().getCoordinate();
    }

    public Asteroid vaporize(final AsteroidMap map) {
        return vaporize(map, map.getAsteroids().size() - 1);
    }

    public Asteroid vaporize(final AsteroidMap map, final int n) {
        final Coordinate2D location = getLocation();
        final Asteroid asteroid = getAsteroid();
        Map<Double, List<Asteroid>> asteroidsByDegree = map.getAsteroids()
                .filter(a -> !a.equals(asteroid))
                .groupBy(a -> Calculator.absoluteAngleFromZero(location, a.getCoordinate()))
                .mapValues(as -> as.sortBy(a -> Calculator.distance(location, a.getCoordinate())));

        final List<Double> order = asteroidsByDegree.keySet().toSortedSet().toList();

        int i = 0;
        int j = 0;
        Asteroid vaporized = null;
        while (i < n) {
            final double degree = order.get(j % order.size());
            final List<Asteroid> atDegree = asteroidsByDegree.getOrElse(degree, List.empty());
            if (!atDegree.isEmpty()) {
                vaporized = atDegree.head();
                asteroidsByDegree = asteroidsByDegree.put(degree, atDegree.tail());
                i++;
//                System.out.println(String.format("(#%d): Vaporized %s at %.3f deg from %s", i, vaporized, degree, asteroid));
            }
            j++;
        }

        return vaporized;
    }
}