package com.jacoblucas.adventofcode2019.day11;

import org.junit.Test;

import static com.jacoblucas.adventofcode2019.day11.Direction.DOWN;
import static com.jacoblucas.adventofcode2019.day11.Direction.LEFT;
import static com.jacoblucas.adventofcode2019.day11.Direction.RIGHT;
import static com.jacoblucas.adventofcode2019.day11.Direction.UP;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DirectionTest {

    @Test
    public void testLeft() {
        assertThat(UP.left(), is(LEFT));
        assertThat(LEFT.left(), is(DOWN));
        assertThat(DOWN.left(), is(RIGHT));
        assertThat(RIGHT.left(), is(UP));
    }

    @Test
    public void testRight() {
        assertThat(UP.right(), is(RIGHT));
        assertThat(LEFT.right(), is(UP));
        assertThat(DOWN.right(), is(LEFT));
        assertThat(RIGHT.right(), is(DOWN));
    }

}
