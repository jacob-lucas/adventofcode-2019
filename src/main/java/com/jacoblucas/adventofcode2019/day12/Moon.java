package com.jacoblucas.adventofcode2019.day12;

import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinates;
import com.jacoblucas.adventofcode2019.utils.coordinates.ImmutableCoordinates3D;
import io.vavr.collection.Array;
import io.vavr.control.Try;

import java.util.Objects;

public class Moon {

    private Coordinates position;
    private Coordinates velocity;

    public static Try<Moon> parse(final String raw) {
        return Try.of(() -> {
            final Array<Integer> parts = Array.of(raw.split(","))
                    .map(str -> str.split("=")[1])
                    .map(str -> str.replaceAll(">", ""))
                    .map(String::trim)
                    .map(Integer::valueOf);

            return new Moon(parts.get(0), parts.get(1), parts.get(2));
        });
    }

    public Moon(final int x, final int y, final int z) {
        setPosition(x, y, z);
        setVelocity(0, 0, 0);
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(final int x, final int y, final int z) {
        this.position = ImmutableCoordinates3D.of(x, y, z);
    }

    public Coordinates getVelocity() {
        return velocity;
    }

    public void setVelocity(final int x, final int y, final int z) {
        this.velocity = ImmutableCoordinates3D.of(x, y, z);
    }

    public int getPotentialEnergy() {
        return Math.abs(position.x()) + Math.abs(position.y()) + Math.abs(position.z());
    }

    public int getKineticEnergy() {
        return Math.abs(velocity.x()) + Math.abs(velocity.y()) + Math.abs(velocity.z());
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
        return position.equals(moon.position) && velocity.equals(moon.velocity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, velocity);
    }

    @Override
    public String toString() {
        return String.format("pos=%s, vel=%s", position, velocity);
    }
}
