package com.jacoblucas.adventofcode2019.day5;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;

public class Day5 {
    public static void main(String[] args) {
        final IntcodeComputer computer = new IntcodeComputer("day5-input.txt", 1);
        computer.execute();
    }
}
