package com.jacoblucas.adventofcode2019.day12;

import com.jacoblucas.adventofcode2019.utils.coordinates.VectorCoordinate;
import io.vavr.Tuple3;
import io.vavr.collection.Array;
import io.vavr.control.Try;

import java.util.Objects;

public class Moon {

    private Tuple3<VectorCoordinate, VectorCoordinate, VectorCoordinate> location;

    public static Try<Moon> parse(final String raw) {
        return Try.of(() -> {
            final Array<Integer> parts = Array.of(raw.split(","))
                    .map(str -> str.split("=")[1])
                    .map(str -> str.replaceAll(">", ""))
                    .map(String::trim)
                    .map(Integer::valueOf);

            return new Moon(parts.get(0), parts.get(1), parts.get(2), 0, 0, 0);
        });
    }

    public Moon(
            final int x, final int y, final int z,
            final int i, final int j, final int k
    ) {
        this.location = new Tuple3<>(
                new VectorCoordinate(x, i),
                new VectorCoordinate(y, j),
                new VectorCoordinate(z, k));
    }

    public Tuple3<VectorCoordinate, VectorCoordinate, VectorCoordinate> getLocation() {
        return location;
    }

    public int getPotentialEnergy() {
        return Math.abs(location._1.getPosition()) + Math.abs(location._2.getPosition()) + Math.abs(location._3.getPosition());
    }

    public int getKineticEnergy() {
        return Math.abs(location._1.getVelocity()) + Math.abs(location._2.getVelocity()) + Math.abs(location._3.getVelocity());
    }

    public int getTotalEnergy() {
        return getPotentialEnergy() * getKineticEnergy();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Moon moon = (Moon) o;
        return location.equals(moon.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return String.format("pos=(%d,%d,%d), vel=(%d,%d,%d)",
                location._1.getPosition(), location._2.getPosition(), location._3.getPosition(),
                location._1.getVelocity(), location._2.getVelocity(), location._3.getVelocity());
    }
}
