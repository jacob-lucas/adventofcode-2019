package com.jacoblucas.adventofcode2019.day12;

import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MoonMotionSimulatorTest {

    private Moon m1;
    private Moon m2;
    private Moon m3;
    private Moon m4;

    @Before
    public void setUp() {
        m1 = Moon.parse("<x=-1, y=0, z=2>").get();
        m2 = Moon.parse("<x=2, y=-10, z=-7>").get();
        m3 = Moon.parse("<x=4, y=-8, z=8>").get();
        m4 = Moon.parse("<x=3, y=5, z=-1>").get();
    }
    @Test
    public void testSingleStep() {
        final Planet planet = ImmutablePlanet.builder()
                .moons(List.of(m1, m2, m3, m4))
                .build();

        MoonMotionSimulator.simulate(1, planet);

        assertThat(m1, is(new Moon(2, -1, 1, 3, -1, -1)));
        assertThat(m2, is(new Moon(3, -7, -4, 1, 3, 3)));
        assertThat(m3, is(new Moon(1, -7, 5, -3, 1, -3)));
        assertThat(m4, is(new Moon(2, 2, 0, -1, -3, 1)));
    }

    @Test
    public void testSimulate() {
        final Planet planet = ImmutablePlanet.builder()
                .moons(List.of(m1, m2, m3, m4))
                .build();

        MoonMotionSimulator.simulate(10, planet);

        assertThat(m1, is(new Moon(2, 1, -3, -3, -2, 1)));
        assertThat(m2, is(new Moon(1, -8, 0, -1, 1, 3)));
        assertThat(m3, is(new Moon(3, -6, 1, 3, 2, -3)));
        assertThat(m4, is(new Moon(2, 0, 4, 1, -1, -1)));
    }

    @Test
    public void testSimulateLoop() {
        final Planet planet = ImmutablePlanet.builder()
                .moons(List.of(m1, m2, m3, m4))
                .build();

        assertThat(MoonMotionSimulator.simulateLoop(planet), is(BigInteger.valueOf(2772)));
    }

}
