package com.jacoblucas.adventofcode2019.utils.intcode;

import io.vavr.collection.Array;
import io.vavr.collection.List;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InstructionTest {

    @Test
    public void testAdd() {
        final Instruction add = ImmutableInstruction.builder()
                .opcode(Opcode.ADD)
                .parameters(List.of(9,10,3))
                .build();

        assertThat(add.execute(Array.of(1,9,10,3,2,3,11,0,99,30,40,50)), is(Array.of(1,9,10,70,2,3,11,0,99,30,40,50)));
    }

    @Test
    public void testMultiply() {
        final Instruction multiply = ImmutableInstruction.builder()
                .opcode(Opcode.MULTIPLY)
                .parameters(List.of(3,11,0))
                .build();

        assertThat(multiply.execute(Array.of(1,9,10,70,2,3,11,0,99,30,40,50)), is(Array.of(3500,9,10,70,2,3,11,0,99,30,40,50)));
    }

    @Test
    public void testHalt() {
        final Array<Integer> memory = Array.of(1,9,10,3,2,3,11,0,99,30,40,50);
        final Instruction halt = ImmutableInstruction.builder()
                .opcode(Opcode.HALT)
                .build();

        assertThat(halt.execute(memory), is(memory));
    }

}
