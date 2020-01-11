package com.jacoblucas.adventofcode2019.day7;

import io.vavr.collection.Map;
import org.junit.Test;

import java.math.BigInteger;

import static com.jacoblucas.adventofcode2019.TestUtils.bigIntegerInput;
import static io.vavr.control.Option.none;
import static io.vavr.control.Option.some;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CircuitTest {

    @Test
    public void testCircuitRunOnceSetup() {
        final Map<BigInteger, BigInteger> program = bigIntegerInput(3, 15, 3, 16, 1002, 16, 10, 16, 1, 16, 15, 15, 4, 15, 99, 0, 0);
        final Circuit circuit = new Circuit(5, program, CircuitMode.RUN_ONCE);
        assertThat(circuit.amplifiers.get(0).getConnection(), is(some(circuit.amplifiers.get(1))));
        assertThat(circuit.amplifiers.get(1).getConnection(), is(some(circuit.amplifiers.get(2))));
        assertThat(circuit.amplifiers.get(2).getConnection(), is(some(circuit.amplifiers.get(3))));
        assertThat(circuit.amplifiers.get(3).getConnection(), is(some(circuit.amplifiers.get(4))));
        assertThat(circuit.amplifiers.get(4).getConnection(), is(none()));
    }

    @Test
    public void testCircuitLoopSetup() {
        final Map<BigInteger, BigInteger> program = bigIntegerInput(3, 15, 3, 16, 1002, 16, 10, 16, 1, 16, 15, 15, 4, 15, 99, 0, 0);
        final Circuit circuit = new Circuit(5, program, CircuitMode.LOOP);
        assertThat(circuit.amplifiers.get(0).getConnection(), is(some(circuit.amplifiers.get(1))));
        assertThat(circuit.amplifiers.get(1).getConnection(), is(some(circuit.amplifiers.get(2))));
        assertThat(circuit.amplifiers.get(2).getConnection(), is(some(circuit.amplifiers.get(3))));
        assertThat(circuit.amplifiers.get(3).getConnection(), is(some(circuit.amplifiers.get(4))));
        assertThat(circuit.amplifiers.get(4).getConnection(), is(some(circuit.amplifiers.get(0))));
    }

    @Test
    public void testRunExample1() {
        final Map<BigInteger, BigInteger> program = bigIntegerInput(3, 15, 3, 16, 1002, 16, 10, 16, 1, 16, 15, 15, 4, 15, 99, 0, 0);
        final Circuit circuit = new Circuit(5, program, CircuitMode.RUN_ONCE);
        final BigInteger output = circuit.run(4,3,2,1,0);
        assertThat(output.intValue(), is(43210));
    }

    @Test
    public void testRunExample2() {
        final Map<BigInteger, BigInteger> program = bigIntegerInput(3,23,3,24,1002,24,10,24,1002,23,-1,23, 101,5,23,23,1,24,23,23,4,23,99,0,0);
        final Circuit circuit = new Circuit(5, program, CircuitMode.RUN_ONCE);
        final BigInteger output = circuit.run(0,1,2,3,4);
        assertThat(output.intValue(), is(54321));
    }

    @Test
    public void testRunExample3() {
        final Map<BigInteger, BigInteger> program = bigIntegerInput(3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33, 1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0);
        final Circuit circuit = new Circuit(5, program, CircuitMode.RUN_ONCE);
        final BigInteger output = circuit.run(1,0,4,3,2);
        assertThat(output.intValue(), is(65210));
    }

    @Test
    public void testExample4() {
        final Map<BigInteger, BigInteger> program = bigIntegerInput(3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5);
        final Circuit circuit = new Circuit(5, program, CircuitMode.LOOP);
        final BigInteger output = circuit.run(9,8,7,6,5);
        assertThat(output.intValue(), is(139629729));
    }

    @Test
    public void testExample5() {
        final Map<BigInteger, BigInteger> program = bigIntegerInput(3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10);
        final Circuit circuit = new Circuit(5, program, CircuitMode.LOOP);
        final BigInteger output = circuit.run(9,7,8,5,6);
        assertThat(output.intValue(), is(18216));
    }

}
