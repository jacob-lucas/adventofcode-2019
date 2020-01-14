package com.jacoblucas.adventofcode2019.day10;

import com.jacoblucas.adventofcode2019.utils.coordinates.ImmutableCoordinate2D;
import io.vavr.collection.List;
import io.vavr.control.Option;

import java.util.Objects;

public class AsteroidMap {
    private static final char ASTEROID = '#';

    private List<Asteroid> asteroids;

    public AsteroidMap(final List<String> rawInput) {
        List<Asteroid> asteroids = List.of();
        for (int y=0; y<rawInput.size(); y++) {
            final String row = rawInput.get(y);
            for (int x=0; x<row.length(); x++) {
                if (row.charAt(x) == ASTEROID) {
                    asteroids = asteroids.append(ImmutableAsteroid.of(ImmutableCoordinate2D.of(x, y)));
                }
            }
        }
        this.asteroids = asteroids;
    }

    public Option<Asteroid> at(final int x, final int y) {
        return asteroids.find(a -> a.getCoordinate().x() == x && a.getCoordinate().y() == y);
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsteroidMap that = (AsteroidMap) o;
        return asteroids.equals(that.asteroids);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asteroids);
    }
}
