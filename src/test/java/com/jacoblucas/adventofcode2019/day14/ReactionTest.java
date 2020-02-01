package com.jacoblucas.adventofcode2019.day14;

import io.vavr.control.Try;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class ReactionTest {

    @Test
    public void testParse() {
        final Try<Reaction> reaction = Reaction.parse("1 A, 2 B, 3 C => 2 D");
        assertThat(reaction.isSuccess(), is(true));
        assertThat(reaction.get().getInputs(), containsInAnyOrder(
                ImmutableChemical.of("A", 1),
                ImmutableChemical.of("B", 2),
                ImmutableChemical.of("C", 3)));
        assertThat(reaction.get().getOutput(), is(ImmutableChemical.of("D", 2)));
    }

    @Test
    public void testParseFailure() {
        final Try<Reaction> reaction = Reaction.parse("acbde");
        assertThat(reaction.isFailure(), is(true));
    }

    @Test
    public void testParseFailureOnPart() {
        final Try<Reaction> reaction = Reaction.parse("1 A, 2B, 3 C => 2 D");
        assertThat(reaction.isFailure(), is(true));
    }

}
