package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HaltInstructionTest {

    private static final ImmutableHaltInstruction HALT = ImmutableHaltInstruction.builder()
            .address(5)
            .opcode(Opcode.HALT)
            .parameters(List.of())
            .build();

    @Test
    public void testExecute() {
        final Array<Integer> program = Array.of(1, 4, 3, 4, 33, 99);
        assertThat(HALT.execute(program), is(program));
    }

    @Test
    public void testGetIncrement() {
        assertThat(HALT.getIncrement(), is(1));
    }

}
