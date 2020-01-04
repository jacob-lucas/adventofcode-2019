package com.jacoblucas.adventofcode2019.day7;

import io.vavr.collection.Array;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CircuitTest {

    @Test
    public void testRunExample1() {
        final Array<Integer> program = Array.of(3, 15, 3, 16, 1002, 16, 10, 16, 1, 16, 15, 15, 4, 15, 99, 0, 0);
        final Circuit circuit = new Circuit(5, program);
        final int output = circuit.run(4,3,2,1,0);
        assertThat(output, is(43210));
    }

    @Test
    public void testRunExample2() {
        final Array<Integer> program = Array.of(3,23,3,24,1002,24,10,24,1002,23,-1,23, 101,5,23,23,1,24,23,23,4,23,99,0,0);
        final Circuit circuit = new Circuit(5, program);
        final int output = circuit.run(0,1,2,3,4);
        assertThat(output, is(54321));
    }

    @Test
    public void testRunExample3() {
        final Array<Integer> program = Array.of(3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33, 1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0);
        final Circuit circuit = new Circuit(5, program);
        final int output = circuit.run(1,0,4,3,2);
        assertThat(output, is(65210));
    }

}
