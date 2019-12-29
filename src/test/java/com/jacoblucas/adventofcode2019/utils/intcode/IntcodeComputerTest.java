package com.jacoblucas.adventofcode2019.utils.intcode;

import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.junit.Before;
import org.junit.Test;

import static com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer.PROGRAM_HALT;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IntcodeComputerTest {

    private IntcodeComputer computer;

    @Before
    public void setUp() {
        this.computer = new IntcodeComputer(Array.of(1,9,10,3,2,3,11,0,99,30,40,50));
    }

    @Test
    public void getOpcodeReturnsCorrectly() {
        assertThat(computer.getOpcode(1), is(Option.of(Opcode.ADD)));
        assertThat(computer.getOpcode(2), is(Option.of(Opcode.MULTIPLY)));
        assertThat(computer.getOpcode(99), is(Option.of(Opcode.HALT)));

        assertThat(computer.getOpcode(11101), is(Option.of(Opcode.ADD)));
        assertThat(computer.getOpcode(11102), is(Option.of(Opcode.MULTIPLY)));
        assertThat(computer.getOpcode(10199), is(Option.of(Opcode.HALT)));
    }

    @Test
    public void getParameterReturnsCorrectly() {
        assertThat(computer.getParameter(1, 0, 1), is(Option.of(ImmutableParameter.of(9, ParameterMode.POSITION))));
        assertThat(computer.getParameter(1, 0, 2), is(Option.of(ImmutableParameter.of(10, ParameterMode.POSITION))));
        assertThat(computer.getParameter(1, 0, 3), is(Option.of(ImmutableParameter.of(3, ParameterMode.POSITION))));

        assertThat(computer.getParameter(101, 0, 1), is(Option.of(ImmutableParameter.of(9, ParameterMode.IMMEDIATE))));
        assertThat(computer.getParameter(101, 0, 2), is(Option.of(ImmutableParameter.of(10, ParameterMode.POSITION))));
        assertThat(computer.getParameter(101, 0, 3), is(Option.of(ImmutableParameter.of(3, ParameterMode.POSITION))));

        assertThat(computer.getParameter(1101, 0, 1), is(Option.of(ImmutableParameter.of(9, ParameterMode.IMMEDIATE))));
        assertThat(computer.getParameter(1101, 0, 2), is(Option.of(ImmutableParameter.of(10, ParameterMode.IMMEDIATE))));
        assertThat(computer.getParameter(1101, 0, 3), is(Option.of(ImmutableParameter.of(3, ParameterMode.POSITION))));

        assertThat(computer.getParameter(11101, 0, 1), is(Option.of(ImmutableParameter.of(9, ParameterMode.IMMEDIATE))));
        assertThat(computer.getParameter(11101, 0, 2), is(Option.of(ImmutableParameter.of(10, ParameterMode.IMMEDIATE))));
        assertThat(computer.getParameter(11101, 0, 3), is(Option.of(ImmutableParameter.of(3, ParameterMode.POSITION))));

        assertThat(computer.getParameter(10001, 0, 1), is(Option.of(ImmutableParameter.of(9, ParameterMode.POSITION))));
        assertThat(computer.getParameter(10001, 0, 2), is(Option.of(ImmutableParameter.of(10, ParameterMode.POSITION))));
        assertThat(computer.getParameter(10001, 0, 3), is(Option.of(ImmutableParameter.of(3, ParameterMode.POSITION))));
    }

    @Test
    public void atReturnsAddInstruction() {
        final Option<Instruction> instruction = computer.at(0);
        assertThat(instruction.isDefined(), is(true));
        assertThat(instruction.get().getMemoryAddress(), is(0));
        assertThat(instruction.get().getOpcode(), is(Opcode.ADD));
        assertThat(instruction.get().getParameters(), is(List.of(
                ImmutableParameter.of(9, ParameterMode.POSITION),
                ImmutableParameter.of(10, ParameterMode.POSITION),
                ImmutableParameter.of(3, ParameterMode.POSITION))));
    }

    @Test
    public void atReturnsMultiplyInstruction() {
        final Option<Instruction> instruction = computer.at(4);
        assertThat(instruction.isDefined(), is(true));
        assertThat(instruction.get().getMemoryAddress(), is(4));
        assertThat(instruction.get().getOpcode(), is(Opcode.MULTIPLY));
        assertThat(instruction.get().getParameters(), is(List.of(
                ImmutableParameter.of(3, ParameterMode.POSITION),
                ImmutableParameter.of(11, ParameterMode.POSITION),
                ImmutableParameter.of(0, ParameterMode.POSITION))));
    }

    @Test
    public void atReturnsSaveInstruction() {
        final IntcodeComputer testComputer = new IntcodeComputer(Array.of(3,0,4,0,99));
        final Option<Instruction> instruction = testComputer.at(0);
        assertThat(instruction.isDefined(), is(true));
        assertThat(instruction.get().getMemoryAddress(), is(0));
        assertThat(instruction.get().getOpcode(), is(Opcode.SAVE));
        assertThat(instruction.get().getParameters(), is(List.of(
                ImmutableParameter.of(0, ParameterMode.POSITION))));
    }

    @Test
    public void atReturnsOutputInstruction() {
        final IntcodeComputer testComputer = new IntcodeComputer(Array.of(3,0,4,0,99));
        final Option<Instruction> instruction = testComputer.at(2);
        assertThat(instruction.isDefined(), is(true));
        assertThat(instruction.get().getMemoryAddress(), is(2));
        assertThat(instruction.get().getOpcode(), is(Opcode.OUTPUT));
        assertThat(instruction.get().getParameters(), is(List.of(
                ImmutableParameter.of(0, ParameterMode.POSITION))));
    }

    @Test
    public void atReturnsHaltInstruction() {
        assertThat(computer.at(8).isDefined(), is(true));
        assertThat(computer.at(8).get().getMemoryAddress(), is(8));
        assertThat(computer.at(8).get().getOpcode(), is(Opcode.HALT));
        assertThat(computer.at(8).get().getParameters(), is(List.of()));
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
        assertThat(PROGRAM_HALT.getIncrement(), is(1));
    }

    @Test
    public void executeExamples() {
        assertThat(new IntcodeComputer(Array.of(1,0,0,0,99)).execute().getMemory(), is(Array.of(2,0,0,0,99)));
        assertThat(new IntcodeComputer(Array.of(2,3,0,3,99)).execute().getMemory(), is(Array.of(2,3,0,6,99)));
        assertThat(new IntcodeComputer(Array.of(2,4,4,5,99,0)).execute().getMemory(), is(Array.of(2,4,4,5,99,9801)));
        assertThat(new IntcodeComputer(Array.of(1,1,1,4,99,5,6,0,99)).execute().getMemory(), is(Array.of(30,1,1,4,2,5,6,0,99)));
        assertThat(new IntcodeComputer(Array.of(1,9,10,3,2,3,11,0,99,30,40,50)).execute().getMemory(), is(Array.of(3500,9,10,70,2,3,11,0,99,30,40,50)));
    }

    @Test
    public void executeExamplesWithModes() {
        IntcodeComputer computer = new IntcodeComputer(Array.of(3, 0, 4, 0, 99), 5);
        assertThat(computer.execute().getMemory(), is(Array.of(5,0,4,0,99)));
        assertThat(computer.getOutput(), is(5));

        computer = new IntcodeComputer(Array.of(1002,4,3,4,33));
        assertThat(computer.execute().getMemory(), is(Array.of(1002,4,3,4,99)));
        assertThat(computer.getOutput(), is(1002));
    }

}
