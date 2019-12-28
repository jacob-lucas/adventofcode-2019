package com.jacoblucas.adventofcode2019.utils.intcode;

import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.junit.Before;
import org.junit.Test;

import static com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer.HALT;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IntcodeComputerTest {

    private IntcodeComputer computer;

    @Before
    public void setUp() {
        this.computer = new IntcodeComputer(Array.of(1,9,10,3,2,3,11,0,99,30,40,50));
    }

    @Test
    public void atReturnsAddInstruction() {
        final Option<Instruction> instruction = computer.at(0);
        assertThat(instruction.isDefined(), is(true));
        assertThat(instruction.get().getOpcode(), is(Opcode.ADD));
        assertThat(instruction.get().getParameters(), is(List.of(9,10,3)));
    }

    @Test
    public void atReturnsMultiplyInstruction() {
        final Option<Instruction> instruction = computer.at(4);
        assertThat(instruction.isDefined(), is(true));
        assertThat(instruction.get().getOpcode(), is(Opcode.MULTIPLY));
        assertThat(instruction.get().getParameters(), is(List.of(3,11,0)));
    }

    @Test
    public void atReturnsHaltInstruction() {
        assertThat(computer.at(8).isDefined(), is(true));
        assertThat(computer.at(8).get(), is(HALT));
    }

    @Test
    public void incrementForAdd() {
        final Option<Instruction> instruction = computer.at(0);
        assertThat(instruction.get().getIncrement(), is(4));
    }

    @Test
    public void incrementForMultiply() {
        final Option<Instruction> instruction = computer.at(4);
        assertThat(instruction.get().getIncrement(), is(4));
    }

    @Test
    public void incrementForHalt() {
        assertThat(HALT.getIncrement(), is(1));
    }

    @Test
    public void executeExamples() {
        assertThat(new IntcodeComputer(Array.of(1,0,0,0,99)).execute().getMemory(), is(Array.of(2,0,0,0,99)));
        assertThat(new IntcodeComputer(Array.of(2,3,0,3,99)).execute().getMemory(), is(Array.of(2,3,0,6,99)));
        assertThat(new IntcodeComputer(Array.of(2,4,4,5,99,0)).execute().getMemory(), is(Array.of(2,4,4,5,99,9801)));
        assertThat(new IntcodeComputer(Array.of(1,1,1,4,99,5,6,0,99)).execute().getMemory(), is(Array.of(30,1,1,4,2,5,6,0,99)));
        assertThat(new IntcodeComputer(Array.of(1,9,10,3,2,3,11,0,99,30,40,50)).execute().getMemory(), is(Array.of(3500,9,10,70,2,3,11,0,99,30,40,50)));
    }

}
