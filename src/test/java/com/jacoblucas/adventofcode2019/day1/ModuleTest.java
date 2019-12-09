package com.jacoblucas.adventofcode2019.day1;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ModuleTest {

    @Test(expected = IllegalArgumentException.class)
    public void massGreaterThanZero() {
        ImmutableModule.of(-1);
    }

    @Test
    public void calculatesRequiredFuelCorrectly() {
        assertThat(ImmutableModule.of(12).getRequiredFuel(), is(2));
        assertThat(ImmutableModule.of(14).getRequiredFuel(), is(2));
        assertThat(ImmutableModule.of(1969).getRequiredFuel(), is(654));
        assertThat(ImmutableModule.of(100756).getRequiredFuel(), is(33583));
    }

    @Test
    public void calculatesTotalRequiredFuelCorrectly() {
        assertThat(ImmutableModule.of(14).getTotalRequiredFuel(), is(2));
        assertThat(ImmutableModule.of(1969).getTotalRequiredFuel(), is(966));
        assertThat(ImmutableModule.of(100756).getTotalRequiredFuel(), is(50346));
    }
}
