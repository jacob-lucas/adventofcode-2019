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
    public void testDistance() {
        double distance = Calculator.distance(ImmutableCoordinate2D.of(4, 3), ImmutableCoordinate2D.of(3, -2));
        assertThat(String.format("%.3f", distance), is("5.099"));

        distance = Calculator.distance(ImmutableCoordinate2D.of(-1, -1), ImmutableCoordinate2D.of(-3, -3));
        assertThat(String.format("%.3f", distance), is("2.828"));

        distance = Calculator.distance(ImmutableCoordinate2D.of(0, 0), ImmutableCoordinate2D.of(5, 5));
        assertThat(String.format("%.3f", distance), is("7.071"));
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
    public void testRelativeAngleFromZero() {
        final Coordinate2D a = ImmutableCoordinate2D.of(2, 2);
        final Coordinate2D b = ImmutableCoordinate2D.of(1, 0);
        final Coordinate2D c = ImmutableCoordinate2D.of(3, 4);

        assertThat(String.format("%.3f", Calculator.relativeAngleFromZero(a, b)), is("-26.565"));
        assertThat(String.format("%.3f", Calculator.relativeAngleFromZero(a, c)), is("26.565"));
    }

    @Test
    public void testAbsoluteAngleFromZero() {
        final Coordinate2D p = ImmutableCoordinate2D.of(2, 2);
        final Coordinate2D q1 = ImmutableCoordinate2D.of(2, 0);
        final Coordinate2D q2 = ImmutableCoordinate2D.of(3, 0);
        final Coordinate2D q3 = ImmutableCoordinate2D.of(2, 3);
        final Coordinate2D q4 = ImmutableCoordinate2D.of(3, 2);
        final Coordinate2D q5 = ImmutableCoordinate2D.of(1, 4);
        final Coordinate2D q6 = ImmutableCoordinate2D.of(0, 2);
        final Coordinate2D q7 = ImmutableCoordinate2D.of(1, 0);
        final Coordinate2D q8 = ImmutableCoordinate2D.of(3, 4);

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
