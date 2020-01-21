package com.jacoblucas.adventofcode2019.day12;

import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinates;
import io.vavr.collection.List;

class MoonMotionSimulator {

    static void applyGravity(final Moon m1, final Coordinates otherMoonPosition) {
        final Coordinates m1Pos = m1.getPosition();
        final Coordinates m1Vel = m1.getVelocity();

        m1.setVelocity(
                m1Vel.x() + Integer.compare(otherMoonPosition.x(), m1Pos.x()),
                m1Vel.y() + Integer.compare(otherMoonPosition.y(), m1Pos.y()),
                m1Vel.z() + Integer.compare(otherMoonPosition.z(), m1Pos.z()));
    }

    static void applyVelocity(final Moon moon) {
        final Coordinates position = moon.getPosition();
        final Coordinates velocity = moon.getVelocity();
        moon.setPosition(
                position.x() + velocity.x(),
                position.y() + velocity.y(),
                position.z() + velocity.z());
    }

    static void step(final List<Moon> moons) {
        for (final Moon m1 : moons) {
            for (final Moon m2 : moons.filter(m -> !m.equals(m1))) {
                applyGravity(m1, m2.getPosition());
            }
        }

        moons.forEach(MoonMotionSimulator::applyVelocity);
    }
}
