package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InputInstructionTest {

    private static final InputInstruction SAVE = ImmutableInputInstruction.builder()
            .address(0)
            .opcode(Opcode.SAVE)
            .input(Option.of(5))
            .parameters(List.of(
                    ImmutableParameter.builder()
                            .value(0)
                            .mode(ParameterMode.POSITION)
                            .build()))
            .build();

    @Test
    public void testExecute() {
        assertThat(SAVE.execute(Array.of(3, 0, 4, 0, 99)), is(Array.of(5, 0, 4, 0, 99)));
    }

    @Test
    public void testGetIncrement() {
        assertThat(SAVE.getIncrement(), is(2));
    }
}