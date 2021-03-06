package com.jacoblucas.adventofcode2019.day12;

import com.jacoblucas.adventofcode2019.utils.coordinates.VectorCoordinate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MoonTest {

    @Test
    public void testParse() {
        final Moon moon = Moon.parse("<x=2, y=-10, z=-7>").get();
        assertThat(moon.getLocation()._1, is(new VectorCoordinate(2, 0)));
        assertThat(moon.getLocation()._2, is(new VectorCoordinate(-10, 0)));
        assertThat(moon.getLocation()._3, is(new VectorCoordinate(-7, 0)));
    }

    @Test
    public void testGetPotentialEnergy() {
        final Moon moon = Moon.parse("<x= 3, y=-6, z= 1>").get();
        assertThat(moon.getPotentialEnergy(), is(10));
    }

    @Test
    public void testGetKineticEnergy() {
        final Moon moon = Moon.parse("<x= 3, y=-6, z= 1>").get();
        moon.getLocation()._1.setVelocity(3);
        moon.getLocation()._2.setVelocity(2);
        moon.getLocation()._3.setVelocity(-3);
        assertThat(moon.getKineticEnergy(), is(8));
    }

    @Test
    public void testGetTotalEnergy() {
        final Moon moon = Moon.parse("<x= 3, y=-6, z= 1>").get();
        moon.getLocation()._1.setVelocity(3);
        moon.getLocation()._2.setVelocity(2);
        moon.getLocation()._3.setVelocity(-3);
        assertThat(moon.getTotalEnergy(), is(80));
    }

}
