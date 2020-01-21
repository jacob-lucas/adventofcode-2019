package com.jacoblucas.adventofcode2019.day12;

import com.jacoblucas.adventofcode2019.utils.coordinates.ImmutableCoordinates3D;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MoonTest {

    @Test
    public void testParse() {
        final Moon moon = Moon.parse("<x=2, y=-10, z=-7>").get();
        assertThat(moon.getPosition(), is(ImmutableCoordinates3D.of(2,-10,-7)));
        assertThat(moon.getVelocity(), is(ImmutableCoordinates3D.of(0,0,0)));
    }

    @Test
    public void testGetPotentialEnergy() {
        final Moon moon = Moon.parse("<x= 3, y=-6, z= 1>").get();
        assertThat(moon.getPotentialEnergy(), is(10));
    }

    @Test
    public void testGetKineticEnergy() {
        final Moon moon = Moon.parse("<x= 3, y=-6, z= 1>").get();
        moon.setVelocity(3, 2, -3);
        assertThat(moon.getKineticEnergy(), is(8));
    }

    @Test
    public void testGetTotalEnergy() {
        final Moon moon = Moon.parse("<x= 3, y=-6, z= 1>").get();
        moon.setVelocity(3, 2, -3);
        assertThat(moon.getTotalEnergy(), is(80));
    }

}
