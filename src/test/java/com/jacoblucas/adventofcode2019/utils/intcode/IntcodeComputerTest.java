package com.jacoblucas.adventofcode2019.utils.intcode;

import io.vavr.collection.Array;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IntcodeComputerTest {

    private IntcodeComputer computer;
    private Array<Integer> program;

    @Before
    public void setUp() {
        this.program = Array.of(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50);
        this.computer = new IntcodeComputer();
        computer.feed(program);
    }

    @Test
    public void executeExamples() {
        computer.feed(Array.of(1, 0, 0, 0, 99));
        assertThat(computer.execute().getMemory(), is(Array.of(2,0,0,0,99)));

        computer.feed(Array.of(2, 3, 0, 3, 99));
        assertThat(computer.execute().getMemory(), is(Array.of(2,3,0,6,99)));

        computer.feed(Array.of(2, 4, 4, 5, 99, 0));
        assertThat(computer.execute().getMemory(), is(Array.of(2,4,4,5,99,9801)));

        computer.feed(Array.of(1, 1, 1, 4, 99, 5, 6, 0, 99));
        assertThat(computer.execute().getMemory(), is(Array.of(30,1,1,4,2,5,6,0,99)));

        computer.feed(Array.of(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50));
        assertThat(computer.execute().getMemory(), is(Array.of(3500,9,10,70,2,3,11,0,99,30,40,50)));
    }

    @Test
    public void executeExamplesWithModes() {
        computer.feed(Array.of(3, 0, 4, 0, 99), 5);
        assertThat(computer.execute().getMemory(), is(Array.of(5,0,4,0,99)));
        assertThat(computer.getOutput(), is(5));

        computer.feed(Array.of(1002,4,3,4,33));
        assertThat(computer.execute().getMemory(), is(Array.of(1002,4,3,4,99)));
        assertThat(computer.getOutput(), is(1002));
    }

    @Test
    public void executeExamplesWithEquals() {
        computer.feed(Array.of(3,9,8,9,10,9,4,9,99,-1,8), 5);
        assertThat(computer.execute().getMemory(), is(Array.of(3,9,8,9,10,9,4,9,99,0,8)));
        assertThat(computer.getOutput(), is(0));

        computer.feed(Array.of(3,9,8,9,10,9,4,9,99,-1,8), 8);
        assertThat(computer.execute().getMemory(), is(Array.of(3,9,8,9,10,9,4,9,99,1,8)));
        assertThat(computer.getOutput(), is(1));

        computer.feed(Array.of(3,3,1108,-1,8,3,4,3,99), 8);
        assertThat(computer.execute().getMemory(), is(Array.of(3,3,1108,1,8,3,4,3,99)));
        assertThat(computer.getOutput(), is(1));

        computer.feed(Array.of(3,3,1108,-1,8,3,4,3,99), -1);
        assertThat(computer.execute().getMemory(), is(Array.of(3,3,1108,0,8,3,4,3,99)));
        assertThat(computer.getOutput(), is(0));
    }

    @Test
    public void executeExamplesWithLessThan() {
        computer.feed(Array.of(3,9,7,9,10,9,4,9,99,-1,8), 5);
        assertThat(computer.execute().getMemory(), is(Array.of(3,9,7,9,10,9,4,9,99,1,8)));
        assertThat(computer.getOutput(), is(1));

        computer.feed(Array.of(3,9,7,9,10,9,4,9,99,-1,8), 18);
        assertThat(computer.execute().getMemory(), is(Array.of(3,9,7,9,10,9,4,9,99,0,8)));
        assertThat(computer.getOutput(), is(0));

        computer.feed(Array.of(3,3,1107,-1,8,3,4,3,99), -3);
        assertThat(computer.execute().getMemory(), is(Array.of(3,3,1107,1,8,3,4,3,99)));
        assertThat(computer.getOutput(), is(1));

        computer.feed(Array.of(3,3,1107,-1,8,3,4,3,99), 10);
        assertThat(computer.execute().getMemory(), is(Array.of(3,3,1107,0,8,3,4,3,99)));
        assertThat(computer.getOutput(), is(0));
    }

    @Test
    public void executeExamplesWithJumpIfTrue() {
        computer.feed(Array.of(3,3,1105,-1,9,1101,0,0,12,4,12,99,1), 5);
        assertThat(computer.execute().getMemory(), is(Array.of(3,3,1105,5,9,1101,0,0,12,4,12,99,1)));
        assertThat(computer.getOutput(), is(1));

        computer.feed(Array.of(3,3,1105,-1,9,1101,0,0,12,4,12,99,1), 0);
        assertThat(computer.execute().getMemory(), is(Array.of(3,3,1105,0,9,1101,0,0,12,4,12,99,0)));
        assertThat(computer.getOutput(), is(0));
    }

    @Test
    public void executeExamplesWithJumpIfFalse() {
        computer.feed(Array.of(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9), 5);
        assertThat(computer.execute().getMemory(), is(Array.of(3,12,6,12,15,1,13,14,13,4,13,99,5,1,1,9)));
        assertThat(computer.getOutput(), is(1));

        computer.feed(Array.of(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9), 0);
        assertThat(computer.execute().getMemory(), is(Array.of(3,12,6,12,15,1,13,14,13,4,13,99,0,0,1,9)));
        assertThat(computer.getOutput(), is(0));
    }

    @Test
    public void executeLongerExampleBelowEight() {
        final Array<Integer> memory = Array.of(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99);
        computer.feed(memory, 5);
        computer.execute();

        assertThat(computer.getOutput(), is(999));
    }

    @Test
    public void executeLongerExampleEqualEight() {
        final Array<Integer> memory = Array.of(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99);
        computer.feed(memory, 8);
        computer.execute();

        assertThat(computer.getOutput(), is(1000));
    }

    @Test
    public void executeLongerExampleAboveEight() {
        final Array<Integer> memory = Array.of(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99);
        computer.feed(memory, 100);
        computer.execute();

        assertThat(computer.getOutput(), is(1001));
    }
}
