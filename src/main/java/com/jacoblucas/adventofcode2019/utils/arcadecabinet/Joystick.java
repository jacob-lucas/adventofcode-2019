package com.jacoblucas.adventofcode2019.utils.arcadecabinet;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;

import java.math.BigInteger;

public class Joystick {
    private final IntcodeComputer computer;

    Joystick(IntcodeComputer computer) {
        this.computer = computer;
    }

    public void tiltLeft() {
        computer.receiveInput(BigInteger.valueOf(-1));
    }

    public void tiltRight() {
        computer.receiveInput(BigInteger.valueOf(1));
    }

    public void neutral() {
        computer.receiveInput(BigInteger.ZERO);
    }
}
