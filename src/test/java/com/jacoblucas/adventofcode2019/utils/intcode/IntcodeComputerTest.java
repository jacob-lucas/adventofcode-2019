package com.jacoblucas.adventofcode2019.utils.intcode;

import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.control.Option;
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
        computer.feed(Array.of(3,0,4,0,99));
        final Option<Instruction> instruction = computer.at(0);
        assertThat(instruction.isDefined(), is(true));
        assertThat(instruction.get().getMemoryAddress(), is(0));
        assertThat(instruction.get().getOpcode(), is(Opcode.SAVE));
        assertThat(instruction.get().getParameters(), is(List.of(
                ImmutableParameter.of(0, ParameterMode.POSITION))));
    }

    @Test
    public void atReturnsOutputInstruction() {
        computer.feed(Array.of(3,0,4,0,99));
        final Option<Instruction> instruction = computer.at(2);
        assertThat(instruction.isDefined(), is(true));
        assertThat(instruction.get().getMemoryAddress(), is(2));
        assertThat(instruction.get().getOpcode(), is(Opcode.OUTPUT));
        assertThat(instruction.get().getParameters(), is(List.of(
                ImmutableParameter.of(0, ParameterMode.POSITION))));
    }

    @Test
    public void atReturnsJumpIfTrueInstruction() {
        computer.feed(Array.of(3,3,1105,-1,9,1101,0,0,12,4,12,99,1));
        final Option<Instruction> instruction = computer.at(2);
        assertThat(instruction.get().getMemoryAddress(), is(2));
        assertThat(instruction.get().getOpcode(), is(Opcode.JUMP_IF_TRUE));
        assertThat(instruction.get().getParameters(), is(List.of(
                ImmutableParameter.of(-1, ParameterMode.IMMEDIATE),
                ImmutableParameter.of(9, ParameterMode.IMMEDIATE))));
    }

    @Test
    public void atReturnsJumpIfFalseInstruction() {
        computer.feed(Array.of(3,12,6,0,15,1,13,14,13,4,13,99,-1,0,1,9));
        final Option<Instruction> instruction = computer.at(2);
        assertThat(instruction.get().getMemoryAddress(), is(2));
        assertThat(instruction.get().getOpcode(), is(Opcode.JUMP_IF_FALSE));
        assertThat(instruction.get().getParameters(), is(List.of(
                ImmutableParameter.of(0, ParameterMode.POSITION),
                ImmutableParameter.of(15, ParameterMode.POSITION))));
    }

    @Test
    public void atReturnsLessThanInstruction() {
        computer.feed(Array.of(3,9,7,9,10,9,4,9,99,-1,8));
        final Option<Instruction> instruction = computer.at(2);
        assertThat(instruction.get().getMemoryAddress(), is(2));
        assertThat(instruction.get().getOpcode(), is(Opcode.LESS_THAN));
        assertThat(instruction.get().getParameters(), is(List.of(
                ImmutableParameter.of(9, ParameterMode.POSITION),
                ImmutableParameter.of(10, ParameterMode.POSITION),
                ImmutableParameter.of(9, ParameterMode.POSITION))));
    }

    @Test
    public void atReturnsEqualsInstruction() {
        computer.feed(Array.of(3,9,8,9,10,9,4,9,99,-1,8));
        final Option<Instruction> instruction = computer.at(2);
        assertThat(instruction.get().getMemoryAddress(), is(2));
        assertThat(instruction.get().getOpcode(), is(Opcode.EQUALS));
        assertThat(instruction.get().getParameters(), is(List.of(
                ImmutableParameter.of(9, ParameterMode.POSITION),
                ImmutableParameter.of(10, ParameterMode.POSITION),
                ImmutableParameter.of(9, ParameterMode.POSITION))));
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
        final Option<Instruction> instruction = computer.at(8);
        assertThat(instruction.get().getIncrement(), is(1));
    }

    @Test
    public void incrementForSave() {
        computer.feed(Array.of(3,9,8,9,10,9,4,9,99,-1,8));
        final Option<Instruction> instruction = computer.at(0);
        assertThat(instruction.get().getIncrement(), is(2));
    }

    @Test
    public void incrementForOutput() {
        computer.feed(Array.of(3,9,8,9,10,9,4,9,99,-1,8));
        final Option<Instruction> instruction = computer.at(6);
        assertThat(instruction.get().getIncrement(), is(2));
    }

    @Test
    public void incrementForJumpIfTrue() {
        computer.feed(Array.of(3,3,1105,0,9,1101,0,0,12,4,12,99,1));
        Option<Instruction> instruction = computer.at(2);
        assertThat(instruction.get().getIncrement(), is(3));
    }

    @Test
    public void incrementForJumpIfFalse() {
        computer.feed(Array.of(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9));
        Option<Instruction> instruction = computer.at(2);
        assertThat(instruction.get().getIncrement(), is(3));
    }

    @Test
    public void incrementForLessThan() {
        computer.feed(Array.of(3,9,7,9,10,9,4,9,99,-1,8));
        final Option<Instruction> instruction = computer.at(2);
        assertThat(instruction.get().getIncrement(), is(4));
    }

    @Test
    public void incrementForEquals() {
        computer.feed(Array.of(3,9,8,9,10,9,4,9,99,-1,8));
        final Option<Instruction> instruction = computer.at(2);
        assertThat(instruction.get().getIncrement(), is(4));
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
