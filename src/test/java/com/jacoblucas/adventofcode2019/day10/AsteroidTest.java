package com.jacoblucas.adventofcode2019.day10;

import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AsteroidTest {
    private static final List<String> INPUT = List.of(
            ".#..#",
            ".....",
            "#####",
            "....#",
            "...##");

    private AsteroidMap asteroidMap;

    @Before
    public void setUp() {
        asteroidMap = new AsteroidMap(INPUT);
    }

    @Test
    public void testCountVisibleAsteroids() {
        // .7..7
        // .....
        // 67775
        // ....7
        // ...87

        assertThat(asteroidMap.at(0, 2).get().countVisibleAsteroids(asteroidMap), is(6));
        assertThat(asteroidMap.at(1, 2).get().countVisibleAsteroids(asteroidMap), is(7));
        assertThat(asteroidMap.at(2, 2).get().countVisibleAsteroids(asteroidMap), is(7));
        assertThat(asteroidMap.at(1, 0).get().countVisibleAsteroids(asteroidMap), is(7));
        assertThat(asteroidMap.at(4, 0).get().countVisibleAsteroids(asteroidMap), is(7));
        assertThat(asteroidMap.at(4, 2).get().countVisibleAsteroids(asteroidMap), is(5));
        assertThat(asteroidMap.at(4, 3).get().countVisibleAsteroids(asteroidMap), is(7));
        assertThat(asteroidMap.at(3, 4).get().countVisibleAsteroids(asteroidMap), is(8));
    }

}
