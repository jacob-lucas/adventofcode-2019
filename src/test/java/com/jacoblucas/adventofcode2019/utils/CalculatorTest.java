package com.jacoblucas.adventofcode2019.utils;

import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinates;
import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinates2D;
import com.jacoblucas.adventofcode2019.utils.coordinates.ImmutableCoordinates2D;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {

    @Test
    public void testManhattanDistance2D() {
        final Coordinates a = ImmutableCoordinates2D.of(-7, -4);
        final Coordinates b = ImmutableCoordinates2D.of(17, 6);
        final Coordinates c = ImmutableCoordinates2D.of(0, 0);
        final Coordinates d = ImmutableCoordinates2D.of(3, 3);
        final Coordinates e = ImmutableCoordinates2D.of(1, 1);
        final Coordinates f = ImmutableCoordinates2D.of(9, 9);

        assertThat(Calculator.manhattanDistance(a, b), is(34));
        assertThat(Calculator.manhattanDistance(b, a), is(34));
        assertThat(Calculator.manhattanDistance(c, d), is(6));
        assertThat(Calculator.manhattanDistance(d, c), is(6));
        assertThat(Calculator.manhattanDistance(d, d), is(0));
        assertThat(Calculator.manhattanDistance(e, f), is(16));
    }

    @Test
    public void testDistance() {
        double distance = Calculator.distance(ImmutableCoordinates2D.of(4, 3), ImmutableCoordinates2D.of(3, -2));
        assertThat(String.format("%.3f", distance), is("5.099"));

        distance = Calculator.distance(ImmutableCoordinates2D.of(-1, -1), ImmutableCoordinates2D.of(-3, -3));
        assertThat(String.format("%.3f", distance), is("2.828"));

        distance = Calculator.distance(ImmutableCoordinates2D.of(0, 0), ImmutableCoordinates2D.of(5, 5));
        assertThat(String.format("%.3f", distance), is("7.071"));
    }

    @Test
    public void testGradient() {
        final Coordinates2D a = ImmutableCoordinates2D.of(0, 1);
        final Coordinates2D c = ImmutableCoordinates2D.of(1, 2);
        final Coordinates2D d = ImmutableCoordinates2D.of(3, 2);
        final Coordinates2D e = ImmutableCoordinates2D.of(4, 3);

        assertThat(Calculator.gradient(a, c), is(1.0));
        assertThat(String.format("%.3f", Calculator.gradient(a, d)), is("0.333"));
        assertThat(String.format("%.3f", Calculator.gradient(a, e)), is("0.500"));
    }

    @Test
    public void testRelativeAngleFromZero() {
        final Coordinates2D a = ImmutableCoordinates2D.of(2, 2);
        final Coordinates2D b = ImmutableCoordinates2D.of(1, 0);
        final Coordinates2D c = ImmutableCoordinates2D.of(3, 4);

        assertThat(String.format("%.3f", Calculator.relativeAngleFromZero(a, b)), is("-26.565"));
        assertThat(String.format("%.3f", Calculator.relativeAngleFromZero(a, c)), is("26.565"));
    }

    @Test
    public void testAbsoluteAngleFromZero() {
        final Coordinates2D p = ImmutableCoordinates2D.of(2, 2);
        final Coordinates2D q1 = ImmutableCoordinates2D.of(2, 0);
        final Coordinates2D q2 = ImmutableCoordinates2D.of(3, 0);
        final Coordinates2D q3 = ImmutableCoordinates2D.of(2, 3);
        final Coordinates2D q4 = ImmutableCoordinates2D.of(3, 2);
        final Coordinates2D q5 = ImmutableCoordinates2D.of(1, 4);
        final Coordinates2D q6 = ImmutableCoordinates2D.of(0, 2);
        final Coordinates2D q7 = ImmutableCoordinates2D.of(1, 0);
        final Coordinates2D q8 = ImmutableCoordinates2D.of(3, 4);

        assertThat(String.format("%.3f", Calculator.absoluteAngleFromZero(p, q1)), is("0.000"));
        assertThat(String.format("%.3f", Calculator.absoluteAngleFromZero(p, q4)), is("90.000"));
        assertThat(String.format("%.3f", Calculator.absoluteAngleFromZero(p, q3)), is("180.000"));
        assertThat(String.format("%.3f", Calculator.absoluteAngleFromZero(p, q6)), is("270.000"));

        assertThat(String.format("%.3f", Calculator.absoluteAngleFromZero(p, q2)), is("26.565"));
        assertThat(String.format("%.3f", Calculator.absoluteAngleFromZero(p, q8)), is("153.435"));
        assertThat(String.format("%.3f", Calculator.absoluteAngleFromZero(p, q5)), is("206.565"));
        assertThat(String.format("%.3f", Calculator.absoluteAngleFromZero(p, q7)), is("333.435"));
    }

}
