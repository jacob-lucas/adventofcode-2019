package com.jacoblucas.adventofcode2019.day12;

import com.jacoblucas.adventofcode2019.utils.Calculator;
import com.jacoblucas.adventofcode2019.utils.coordinates.VectorCoordinate;
import io.vavr.Function1;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import io.vavr.collection.Stream;

import java.math.BigInteger;

class MoonMotionSimulator {

    static void simulate(final int steps, final Planet planet) {
        Stream.range(0, steps).forEach(i -> {
            step(planet.getMoons(), Tuple3::_1);
            step(planet.getMoons(), Tuple3::_2);
            step(planet.getMoons(), Tuple3::_3);
        });
    }

    static BigInteger simulateLoop(final Planet planet) {
        final int s1 = detectLoop(planet.getMoons(), Tuple3::_1);
        final int s2 = detectLoop(planet.getMoons(), Tuple3::_2);
        final int s3 = detectLoop(planet.getMoons(), Tuple3::_3);

        return Calculator.lcd(Calculator.lcd(BigInteger.valueOf(s1), BigInteger.valueOf(s2)), BigInteger.valueOf(s3));
    }

    private static int detectLoop(
            final List<Moon> moons,
            final Function1<Tuple3<VectorCoordinate, VectorCoordinate, VectorCoordinate>, VectorCoordinate> axisFunc
    ) {
        final List<VectorCoordinate> origin = moons
                .map(Moon::getLocation)
                .map(axisFunc)
                .map(vc -> new VectorCoordinate(vc.getPosition(), vc.getVelocity()));

        int steps = 0;
        while (true) {
            step(moons, axisFunc);
            steps++;
            if (moons.map(Moon::getLocation).map(axisFunc).equals(origin)) {
                return steps;
            }
        }
    }

    private static void step(
            final List<Moon> moons,
            final Function1<Tuple3<VectorCoordinate, VectorCoordinate, VectorCoordinate>, VectorCoordinate> axisFunc
    ) {
        // apply gravity on the axis
        moons.forEach(moon -> {
            final List<Moon> others = moons.filter(m -> !m.equals(moon));
            others.forEach(other -> {
                VectorCoordinate axis = axisFunc.apply(moon.getLocation());
                VectorCoordinate otherAxis = axisFunc.apply(other.getLocation());
                axis.setVelocity(axis.getVelocity() + Integer.compare(otherAxis.getPosition(), axis.getPosition()));
            });
        });

        // apply velocity on the axis
        moons.forEach(moon -> {
            VectorCoordinate moonAxis = axisFunc.apply(moon.getLocation());
            moonAxis.setPosition(moonAxis.getPosition() + moonAxis.getVelocity());
        });
    }
}
