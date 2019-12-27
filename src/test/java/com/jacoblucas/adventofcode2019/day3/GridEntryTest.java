package com.jacoblucas.adventofcode2019.day3;

import io.vavr.collection.HashSet;
import org.junit.Test;

import static com.jacoblucas.adventofcode2019.day3.Grid.EMPTY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GridEntryTest {
    @Test
    public void testGridValue() {
        assertThat(EMPTY.gridValue(), is("."));
        assertThat(ImmutableGridEntry.builder().ids(HashSet.of("1")).build().gridValue(), is("1"));
        assertThat(ImmutableGridEntry.builder().ids(HashSet.of("1", "2")).build().gridValue(), is("X"));
    }
}
