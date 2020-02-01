package com.jacoblucas.adventofcode2019.day14;

import io.vavr.control.Try;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChemicalTest {

    @Test
    public void testParse() {
        final Try<Chemical> chemical = Chemical.parse("2 D");
        assertThat(chemical.isSuccess(), is(true));
        assertThat(chemical.get().getId(), is("D"));
        assertThat(chemical.get().getQuantity(), is(2));
    }

    @Test
    public void testParseWithSpaces() {
        final Try<Chemical> chemical = Chemical.parse("   2 D   ");
        assertThat(chemical.isSuccess(), is(true));
        assertThat(chemical.get().getId(), is("D"));
        assertThat(chemical.get().getQuantity(), is(2));
    }

    @Test
    public void testParseFailure() {
        final Try<Chemical> chemical = Chemical.parse("abcde");
        assertThat(chemical.isFailure(), is(true));
    }
}
