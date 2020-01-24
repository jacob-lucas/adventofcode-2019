package com.jacoblucas.adventofcode2019.utils.intcode;

import io.vavr.collection.Map;
import io.vavr.collection.Queue;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static com.jacoblucas.adventofcode2019.TestUtils.bigIntegerInput;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IntcodeComputerTest {

    private IntcodeComputer computer;
    private Map<BigInteger, BigInteger> program;



    @Before
    public void setUp() {
        this.program = bigIntegerInput(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50);
        this.computer = new IntcodeComputer();
        computer.feed(program);
    }

    @Test
    public void executeExamples() {
        computer.feed(bigIntegerInput(1, 0, 0, 0, 99));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(2,0,0,0,99)));

        computer.feed(bigIntegerInput(2, 3, 0, 3, 99));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(2,3,0,6,99)));

        computer.feed(bigIntegerInput(2, 4, 4, 5, 99, 0));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(2,4,4,5,99,9801)));

        computer.feed(bigIntegerInput(1, 1, 1, 4, 99, 5, 6, 0, 99));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(30,1,1,4,2,5,6,0,99)));

        computer.feed(bigIntegerInput(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(3500,9,10,70,2,3,11,0,99,30,40,50)));
    }

    @Test
    public void executeExamplesWithModes() {
        computer.feed(bigIntegerInput(3, 0, 4, 0, 99), Queue.of(BigInteger.valueOf(5)));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(5,0,4,0,99)));
        assertThat(computer.getOutput().intValue(), is(5));

        computer.feed(bigIntegerInput(1002,4,3,4,33));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(1002,4,3,4,99)));
        assertThat(computer.getOutput().intValue(), is(1002));

        computer.feed(bigIntegerInput(109,1,204,-1,209,14,1001,15,2,16,1202,6,2,18,99,9,10,3,12));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(109,1,204,-1,209,14,1001,15,2,16,1202,6,2,18,99,9,11,3,22)));
        assertThat(computer.getOutput().intValue(), is(109));
    }

    @Test
    public void executeExamplesWithEquals() {
        computer.feed(bigIntegerInput(3,9,8,9,10,9,4,9,99,-1,8), Queue.of(BigInteger.valueOf(5)));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(3,9,8,9,10,9,4,9,99,0,8)));
        assertThat(computer.getOutput().intValue(), is(0));

        computer.feed(bigIntegerInput(3,9,8,9,10,9,4,9,99,-1,8), Queue.of(BigInteger.valueOf(8)));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(3,9,8,9,10,9,4,9,99,1,8)));
        assertThat(computer.getOutput().intValue(), is(1));

        computer.feed(bigIntegerInput(3,3,1108,-1,8,3,4,3,99), Queue.of(BigInteger.valueOf(8)));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(3,3,1108,1,8,3,4,3,99)));
        assertThat(computer.getOutput().intValue(), is(1));

        computer.feed(bigIntegerInput(3,3,1108,-1,8,3,4,3,99), Queue.of(BigInteger.valueOf(-1)));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(3,3,1108,0,8,3,4,3,99)));
        assertThat(computer.getOutput().intValue(), is(0));
    }

    @Test
    public void executeExamplesWithLessThan() {
        computer.feed(bigIntegerInput(3,9,7,9,10,9,4,9,99,-1,8), Queue.of(BigInteger.valueOf(5)));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(3,9,7,9,10,9,4,9,99,1,8)));
        assertThat(computer.getOutput().intValue(), is(1));

        computer.feed(bigIntegerInput(3,9,7,9,10,9,4,9,99,-1,8), Queue.of(BigInteger.valueOf(18)));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(3,9,7,9,10,9,4,9,99,0,8)));
        assertThat(computer.getOutput().intValue(), is(0));

        computer.feed(bigIntegerInput(3,3,1107,-1,8,3,4,3,99), Queue.of(BigInteger.valueOf(-3)));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(3,3,1107,1,8,3,4,3,99)));
        assertThat(computer.getOutput().intValue(), is(1));

        computer.feed(bigIntegerInput(3,3,1107,-1,8,3,4,3,99), Queue.of(BigInteger.valueOf(10)));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(3,3,1107,0,8,3,4,3,99)));
        assertThat(computer.getOutput().intValue(), is(0));
    }

    @Test
    public void executeExamplesWithJumpIfTrue() {
        computer.feed(bigIntegerInput(3,3,1105,-1,9,1101,0,0,12,4,12,99,1), Queue.of(BigInteger.valueOf(5)));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(3,3,1105,5,9,1101,0,0,12,4,12,99,1)));
        assertThat(computer.getOutput().intValue(), is(1));

        computer.feed(bigIntegerInput(3,3,1105,-1,9,1101,0,0,12,4,12,99,1), Queue.of(BigInteger.ZERO));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(3,3,1105,0,9,1101,0,0,12,4,12,99,0)));
        assertThat(computer.getOutput().intValue(), is(0));
    }

    @Test
    public void executeExamplesWithJumpIfFalse() {
        computer.feed(bigIntegerInput(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9), Queue.of(BigInteger.valueOf(5)));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(3,12,6,12,15,1,13,14,13,4,13,99,5,1,1,9)));
        assertThat(computer.getOutput().intValue(), is(1));

        computer.feed(bigIntegerInput(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9), Queue.of(BigInteger.ZERO));
        assertThat(computer.execute().getMemory(), is(bigIntegerInput(3,12,6,12,15,1,13,14,13,4,13,99,0,0,1,9)));
        assertThat(computer.getOutput().intValue(), is(0));
    }

    @Test
    public void executeLongerExampleBelowEight() {
        final Map<BigInteger, BigInteger> memory = bigIntegerInput(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99);
        computer.feed(memory, Queue.of(BigInteger.valueOf(5)));
        computer.execute();

        assertThat(computer.getOutput().intValue(), is(999));
    }

    @Test
    public void executeLongerExampleEqualEight() {
        final Map<BigInteger, BigInteger> memory = bigIntegerInput(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99);
        computer.feed(memory, Queue.of(BigInteger.valueOf(8)));
        computer.execute();

        assertThat(computer.getOutput().intValue(), is(1000));
    }

    @Test
    public void executeLongerExampleAboveEight() {
        final Map<BigInteger, BigInteger> memory = bigIntegerInput(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99);
        computer.feed(memory, Queue.of(BigInteger.valueOf(100)));
        computer.execute();

        assertThat(computer.getOutput().intValue(), is(1001));
    }

    @Test
    public void testBlocksDoubleSubscription() {
        final TestReceiver receiver = new TestReceiver();
        computer.subscribe(receiver);
        computer.subscribe(receiver);

        assertThat(computer.getReceivers().size(), is(1));
    }

    @Test
    public void testOutputSubscription() {
        final Map<BigInteger, BigInteger> memory = bigIntegerInput(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99);
        computer.feed(memory, Queue.of(BigInteger.valueOf(100)));

        final TestReceiver receiver = new TestReceiver();
        computer.subscribe(receiver);

        computer.execute();

        assertThat(receiver.getReceived().intValue(), is(1001));
    }

    @Test(timeout = 500)
    public void testAwaitsInput() {
        final Map<BigInteger, BigInteger> memory = bigIntegerInput(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99);
        computer.feed(memory); // no input

        Runnable t1 = () -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // do nothing
            }
            computer.receiveInput(BigInteger.valueOf(100));
        };
        t1.run();

        computer.execute();

        assertThat(computer.getOutput().intValue(), is(1001));
    }

    @Test
    public void supportsLargerNumbers() {
        final BigInteger a = BigInteger.valueOf(34915192);
        final BigInteger b = BigInteger.valueOf(34915192);
        final Map<BigInteger, BigInteger> program = bigIntegerInput(1102,34915192,34915192,7,4,7,99,0);
        computer.feed(program); // no input
        computer.execute();

        assertThat(String.valueOf(computer.getOutput()).length(), is(16));
        assertThat(computer.getOutput(), is(a.multiply(b)));
    }

    @Test
    public void outputsLargerNumbers() {
        computer.feed("104,1125899906842624,99"); // no input
        computer.execute();

        assertThat(computer.getOutput(), is(new BigInteger("1125899906842624")));
    }

    @Test
    public void supportsLargerThanInitialMemory() {
        computer.feed("1001,21,3,20,4,20,99");
        computer.execute();

        assertThat(computer.getOutput().intValue(), is(3));
    }

}
