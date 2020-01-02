package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JumpInstructionTest {

    private static final JumpInstruction JUMP_IF_TRUE_NON_ZERO = ImmutableJumpInstruction.builder()
            .address(3)
            .opcode(Opcode.JUMP_IF_TRUE)
            .parameters(List.of(
                    ImmutableParameter.builder()
                            .value(5)
                            .mode(ParameterMode.IMMEDIATE)
                            .build(),
                    ImmutableParameter.builder()
                            .value(9)
                            .mode(ParameterMode.IMMEDIATE)
                            .build()))
            .build();

    private static final JumpInstruction JUMP_IF_TRUE_ZERO = ImmutableJumpInstruction.builder()
            .address(3)
            .opcode(Opcode.JUMP_IF_TRUE)
            .parameters(List.of(
                    ImmutableParameter.builder()
                            .value(0)
                            .mode(ParameterMode.IMMEDIATE)
                            .build(),
                    ImmutableParameter.builder()
                            .value(9)
                            .mode(ParameterMode.POSITION)
                            .build()))
            .build();

    private static final JumpInstruction JUMP_IF_FALSE_NON_ZERO = ImmutableJumpInstruction.builder()
            .address(2)
            .opcode(Opcode.JUMP_IF_FALSE)
            .parameters(List.of(
                    ImmutableParameter.builder()
                            .value(5)
                            .mode(ParameterMode.IMMEDIATE)
                            .build(),
                    ImmutableParameter.builder()
                            .value(9)
                            .mode(ParameterMode.POSITION)
                            .build()))
            .build();

    private static final JumpInstruction JUMP_IF_FALSE_ZERO = ImmutableJumpInstruction.builder()
            .address(2)
            .opcode(Opcode.JUMP_IF_FALSE)
            .parameters(List.of(
                    ImmutableParameter.builder()
                            .value(0)
                            .mode(ParameterMode.IMMEDIATE)
                            .build(),
                    ImmutableParameter.builder()
                            .value(9)
                            .mode(ParameterMode.IMMEDIATE)
                            .build()))
            .build();

    @Test
    public void testJumpIfTrueNonZero() {
        assertThat(JUMP_IF_TRUE_NON_ZERO.execute(Array.of(3, 3, 1105, 5, 9, 1101, 0, 0, 12, 4, 12, 99, 1)), is(9));
    }

    @Test
    public void testJumpIfTrueZero() {
        assertThat(JUMP_IF_TRUE_ZERO.execute(Array.of(3,3,105,0,9,1101,0,0,12,4,12,99,1)), is(0));
    }

    @Test
    public void testJumpIfFalseNonZero() {
        assertThat(JUMP_IF_FALSE_NON_ZERO.execute(Array.of(3, 3, 1106, 5, 9, 1101, 0, 0, 12, 4, 12, 99, 1)), is(0));
    }

    @Test
    public void testJumpIfFalseZero() {
        assertThat(JUMP_IF_FALSE_ZERO.execute(Array.of(3,3,1106,0,9,1101,0,0,12,4,12,99,1)), is(9));
    }

    @Test
    public void testGetIncrement() {
        assertThat(JUMP_IF_TRUE_ZERO.getIncrement(), is(3));
        assertThat(JUMP_IF_FALSE_ZERO.getIncrement(), is(3));
    }
}
