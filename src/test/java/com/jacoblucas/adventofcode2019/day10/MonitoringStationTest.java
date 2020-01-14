package com.jacoblucas.adventofcode2019.day10;

import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MonitoringStationTest {

    private AsteroidMap map;
    private MonitoringStation station;

    @Before
    public void setUp() {
        map = new AsteroidMap(List.of(
            ".#....#####...#..",
            "##...##.#####..##",
            "##...#...#.#####.",
            "..#.....#...###..",
            "..#.#.....#....##"));

        station = ImmutableMonitoringStation.builder()
                .asteroid(map.at(8, 3).get())
                .build();
    }

    @Test
    public void testVaporize() {
        assertThat(station.vaporize(map, 1), is(map.at(8,1).get()));
        assertThat(station.vaporize(map, 2), is(map.at(9,0).get()));
        assertThat(station.vaporize(map, 3), is(map.at(9,1).get()));
        assertThat(station.vaporize(map, 4), is(map.at(10,0).get()));
        assertThat(station.vaporize(map, 5), is(map.at(9,2).get()));
    }

    @Test
    public void testVaporizeAll() {
        assertThat(station.vaporize(map), is(map.at(14, 3).get()));
    }
}
