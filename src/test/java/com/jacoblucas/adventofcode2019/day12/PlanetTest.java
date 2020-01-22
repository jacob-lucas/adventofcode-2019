package com.jacoblucas.adventofcode2019.day12;

import io.vavr.collection.List;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PlanetTest {

    @Test
    public void testGetTotalEnergy() {
        final Moon m1 = Moon.parse("<x=-1, y=0, z=2>").get();
        final Moon m2 = Moon.parse("<x=2, y=-10, z=-7>").get();
        final Moon m3 = Moon.parse("<x=4, y=-8, z=8>").get();
        final Moon m4 = Moon.parse("<x=3, y=5, z=-1>").get();
        final Planet planet = ImmutablePlanet.builder()
                .moons(List.of(m1, m2, m3, m4))
                .build();

        MoonMotionSimulator.simulate(10, planet);

        assertThat(planet.getTotalEnergy(), is(179));
    }

}
