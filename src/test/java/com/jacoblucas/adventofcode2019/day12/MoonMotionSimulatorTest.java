package com.jacoblucas.adventofcode2019.day12;

import com.jacoblucas.adventofcode2019.utils.coordinates.ImmutableCoordinates3D;
import io.vavr.collection.List;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MoonMotionSimulatorTest {

    @Test
    public void testApplyGravity() {
        final Moon ganymede = new Moon(2, 4, 1);
        ganymede.setVelocity(2, 2, 2);

        final Moon callisto = new Moon(5, 4, -3);
        callisto.setVelocity(-1, -1, -1);

        MoonMotionSimulator.applyGravity(ganymede, callisto.getPosition());

        assertThat(ganymede.getVelocity(), is(ImmutableCoordinates3D.of(3, 2, 1)));
    }

    @Test
    public void testApplyVelocity() {
        final Moon europa = new Moon(1, 2, 3);
        europa.setVelocity(-2, 0, 3);

        MoonMotionSimulator.applyVelocity(europa);

        assertThat(europa.getPosition(), is(ImmutableCoordinates3D.of(-1, 2, 6)));
        assertThat(europa.getVelocity(), is(ImmutableCoordinates3D.of(-2, 0, 3)));
    }

    @Test
    public void testSingleStep() {
        final Moon m1 = Moon.parse("<x=-1, y=0, z=2>").get();
        final Moon m2 = Moon.parse("<x=2, y=-10, z=-7>").get();
        final Moon m3 = Moon.parse("<x=4, y=-8, z=8>").get();
        final Moon m4 = Moon.parse("<x=3, y=5, z=-1>").get();
        final List<Moon> moons = List.of(m1, m2, m3, m4);

        MoonMotionSimulator.step(moons);

        assertThat(m1.getPosition(), is(ImmutableCoordinates3D.of(2, -1, 1)));
        assertThat(m1.getVelocity(), is(ImmutableCoordinates3D.of(3, -1, -1)));

        assertThat(m2.getPosition(), is(ImmutableCoordinates3D.of(3, -7, -4)));
        assertThat(m2.getVelocity(), is(ImmutableCoordinates3D.of(1, 3, 3)));

        assertThat(m3.getPosition(), is(ImmutableCoordinates3D.of(1, -7, 5)));
        assertThat(m3.getVelocity(), is(ImmutableCoordinates3D.of(-3, 1, -3)));

        assertThat(m4.getPosition(), is(ImmutableCoordinates3D.of(2, 2, 0)));
        assertThat(m4.getVelocity(), is(ImmutableCoordinates3D.of(-1, -3, 1)));
    }

}
