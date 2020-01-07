package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData;
import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import org.junit.Test;

import static com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData.MEMORY_KEY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MemoryUpdateInstructionTest {

    private static final IntcodeComputerData DATA = new IntcodeComputerData();

    private static final ImmutableMemoryUpdateInstruction ADD = ImmutableMemoryUpdateInstruction.builder()
            .address(0)
            .opcode(Opcode.ADD)
            .parameters(List.of(
                    ImmutableParameter.builder()
                            .value(4)
                            .mode(ParameterMode.POSITION)
                            .build(),
                    ImmutableParameter.builder()
                            .value(3)
                            .mode(ParameterMode.POSITION)
                            .build(),
                    ImmutableParameter.builder()
                            .value(4)
                            .mode(ParameterMode.POSITION)
                            .build()))
            .build();

    private static final ImmutableMemoryUpdateInstruction MULTIPLY = ImmutableMemoryUpdateInstruction.builder()
            .address(0)
            .opcode(Opcode.MULTIPLY)
            .parameters(List.of(
                    ImmutableParameter.builder()
                            .value(4)
                            .mode(ParameterMode.POSITION)
                            .build(),
                    ImmutableParameter.builder()
                            .value(3)
                            .mode(ParameterMode.IMMEDIATE)
                            .build(),
                    ImmutableParameter.builder()
                            .value(4)
                            .mode(ParameterMode.POSITION)
                            .build()))
            .build();

    private static final ImmutableMemoryUpdateInstruction LESS_THAN = ImmutableMemoryUpdateInstruction.builder()
            .address(2)
            .opcode(Opcode.LESS_THAN)
            .parameters(List.of(
                    ImmutableParameter.builder()
                            .value(9)
                            .mode(ParameterMode.POSITION)
                            .build(),
                    ImmutableParameter.builder()
                            .value(10)
                            .mode(ParameterMode.POSITION)
                            .build(),
                    ImmutableParameter.builder()
                            .value(9)
                            .mode(ParameterMode.POSITION)
                            .build()))
            .build();

    private static final ImmutableMemoryUpdateInstruction EQUALS = ImmutableMemoryUpdateInstruction.builder()
            .address(2)
            .opcode(Opcode.EQUALS)
            .parameters(List.of(
                    ImmutableParameter.builder()
                            .value(9)
                            .mode(ParameterMode.POSITION)
                            .build(),
                    ImmutableParameter.builder()
                            .value(10)
                            .mode(ParameterMode.POSITION)
                            .build(),
                    ImmutableParameter.builder()
                            .value(9)
                            .mode(ParameterMode.POSITION)
                            .build()))
            .build();

    @Test
    public void testAdd() {
        DATA.put(MEMORY_KEY, Array.of(1, 4, 3, 4, 33));
        assertThat(ADD.execute(DATA), is(Array.of(1,4,3,4,37)));
    }

    @Test
    public void testMultiply() {
        DATA.put(MEMORY_KEY, Array.of(1002, 4, 3, 4, 33));
        assertThat(MULTIPLY.execute(DATA), is(Array.of(1002,4,3,4,99)));
    }

    @Test
    public void testLessThan() {
        DATA.put(MEMORY_KEY, Array.of(3,9,7,9,10,9,4,9,99,-1,8));
        assertThat(LESS_THAN.execute(DATA), is(Array.of(3,9,7,9,10,9,4,9,99,1,8)));
    }

    @Test
    public void testEquals() {
        DATA.put(MEMORY_KEY, Array.of(3,9,8,9,10,9,4,9,99,-1,8));
        assertThat(EQUALS.execute(DATA), is(Array.of(3,9,8,9,10,9,4,9,99,0,8)));
    }

    @Test
    public void testGetIncrement() {
        assertThat(ADD.getIncrement(), is(4));
        assertThat(MULTIPLY.getIncrement(), is(4));
        assertThat(LESS_THAN.getIncrement(), is(4));
        assertThat(EQUALS.getIncrement(), is(4));
    }
}
