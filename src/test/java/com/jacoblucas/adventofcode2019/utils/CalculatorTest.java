package com.jacoblucas.adventofcode2019.utils;

import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinate;
import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinate2D;
import com.jacoblucas.adventofcode2019.utils.coordinates.ImmutableCoordinate2D;
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

    @Test
    public void testGradient() {
        final Coordinate2D a = ImmutableCoordinate2D.of(0, 1);
        final Coordinate2D c = ImmutableCoordinate2D.of(1, 2);
        final Coordinate2D d = ImmutableCoordinate2D.of(3, 2);
        final Coordinate2D e = ImmutableCoordinate2D.of(4, 3);

        assertThat(Calculator.gradient(a, c), is(1.0));
        assertThat(String.format("%.3f", Calculator.gradient(a, d)), is("0.333"));
        assertThat(String.format("%.3f", Calculator.gradient(a, e)), is("0.500"));
    }

    @Test
    public void testAngleFromZero() {
        final Coordinate2D a = ImmutableCoordinate2D.of(2, 2);
        final Coordinate2D b = ImmutableCoordinate2D.of(1, 0);
        final Coordinate2D c = ImmutableCoordinate2D.of(3, 4);

        assertThat(String.format("%.3f", Calculator.angleFromZero(a, b)), is("-0.546"));
        assertThat(String.format("%.3f", Calculator.angleFromZero(a, c)), is("0.546"));
    }

}
