package com.jacoblucas.adventofcode2019.day10;

import com.jacoblucas.adventofcode2019.utils.Calculator;
import io.vavr.collection.List;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class AsteroidMapTest {

    private List<String> toString(final Asteroid... asteroids) {
        return List.of(asteroids).map(Asteroid::getCoordinate)
                .map(c -> String.format("(%s,%s)", c.x(), c.y()));
    }

    @Test
    public void testRawInputParsing() {
        final List<String> input = List.of(
            ".#..#",
            ".....",
            "#####",
            "....#",
            "...##");

        final AsteroidMap map = new AsteroidMap(input);
        final List<Asteroid> asteroids = map.getAsteroids();

        assertThat(toString(asteroids.toJavaArray(Asteroid.class)), containsInAnyOrder(
                "(1,0)", "(4,0)",
                "(0,2)","(1,2)","(2,2)","(3,2)","(4,2)",
                "(4,3)",
                "(3,4)", "(4,4)"));
    }

    @Test
    public void testGradientSolution() {
        final List<String> input = List.of(
                ".#..#",
                ".....",
                "#####",
                "....#",
                "...##");

        final AsteroidMap map = new AsteroidMap(input);
        final List<Asteroid> asteroids = map.getAsteroids();

        final Asteroid a = asteroids.get(0);

        final Asteroid b = asteroids.get(5);
        final Asteroid c = asteroids.get(7);

        final Asteroid d = asteroids.get(4);
        final Asteroid e = asteroids.get(8);

        final double d1 = Calculator.gradient(a.getCoordinate(), b.getCoordinate());
        final double d2 = Calculator.gradient(a.getCoordinate(), c.getCoordinate());
        assertThat(d1, is(d2));

        final double d3 = Calculator.gradient(a.getCoordinate(), d.getCoordinate());
        final double d4 = Calculator.gradient(a.getCoordinate(), e.getCoordinate());
        assertThat(d3, is(d4));
    }
}
