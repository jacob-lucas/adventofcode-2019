package com.jacoblucas.adventofcode2019.day12;

import com.jacoblucas.adventofcode2019.utils.coordinates.ImmutableCoordinates3D;
import io.vavr.collection.List;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PlanetTest {

    @Test
    public void testSimulate() {
        final Moon m1 = Moon.parse("<x=-1, y=0, z=2>").get();
        final Moon m2 = Moon.parse("<x=2, y=-10, z=-7>").get();
        final Moon m3 = Moon.parse("<x=4, y=-8, z=8>").get();
        final Moon m4 = Moon.parse("<x=3, y=5, z=-1>").get();
        final Planet planet = ImmutablePlanet.builder()
                .moons(List.of(m1, m2, m3, m4))
                .build();

        planet.simulate(10);

        assertThat(m1.getPosition(), is(ImmutableCoordinates3D.of(2, 1, -3)));
        assertThat(m1.getVelocity(), is(ImmutableCoordinates3D.of(-3, -2, 1)));

        assertThat(m2.getPosition(), is(ImmutableCoordinates3D.of(1, -8, 0)));
        assertThat(m2.getVelocity(), is(ImmutableCoordinates3D.of(-1, 1, 3)));

        assertThat(m3.getPosition(), is(ImmutableCoordinates3D.of(3, -6, 1)));
        assertThat(m3.getVelocity(), is(ImmutableCoordinates3D.of(3, 2, -3)));

        assertThat(m4.getPosition(), is(ImmutableCoordinates3D.of(2, 0, 4)));
        assertThat(m4.getVelocity(), is(ImmutableCoordinates3D.of(1, -1, -1)));
    }

    @Test
    public void testGetTotalEnergy() {
        final Moon m1 = Moon.parse("<x=-1, y=0, z=2>").get();
        final Moon m2 = Moon.parse("<x=2, y=-10, z=-7>").get();
        final Moon m3 = Moon.parse("<x=4, y=-8, z=8>").get();
        final Moon m4 = Moon.parse("<x=3, y=5, z=-1>").get();
        final Planet planet = ImmutablePlanet.builder()
                .moons(List.of(m1, m2, m3, m4))
                .build();

        planet.simulate(10);

        assertThat(planet.getTotalEnergy(), is(179));
    }

}
