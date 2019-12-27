package com.jacoblucas.adventofcode2019.utils;

import com.jacoblucas.adventofcode2019.day3.Coordinate;
import com.jacoblucas.adventofcode2019.day3.ImmutableCoordinate2D;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {

    @Test
    public void testManhattanDistance2D() {
        final Coordinate a = ImmutableCoordinate2D.of(-7, -4);
        final Coordinate b = ImmutableCoordinate2D.of(17, 6);
        final Coordinate c = ImmutableCoordinate2D.of(0, 0);
        final Coordinate d = ImmutableCoordinate2D.of(3, 3);
        final Coordinate e = ImmutableCoordinate2D.of(1, 1);
        final Coordinate f = ImmutableCoordinate2D.of(9, 9);

        assertThat(Calculator.manhattanDistance(a, b), is(34));
        assertThat(Calculator.manhattanDistance(b, a), is(34));
        assertThat(Calculator.manhattanDistance(c, d), is(6));
        assertThat(Calculator.manhattanDistance(d, c), is(6));
        assertThat(Calculator.manhattanDistance(d, d), is(0));
        assertThat(Calculator.manhattanDistance(e, f), is(16));
    }

}
