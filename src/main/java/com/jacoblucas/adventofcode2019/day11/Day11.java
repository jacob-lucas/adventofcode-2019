package com.jacoblucas.adventofcode2019.day11;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;

import static com.jacoblucas.adventofcode2019.utils.InputReader.read;

public class Day11 {
    public static void main(String[] args) {
        final String input = read("day11-input.txt").head();
        final IntcodeComputer computer = new IntcodeComputer();
        final EmergencyHullPaintingRobot robot = new EmergencyHullPaintingRobot(input, computer);

        robot.run();
        System.out.println(robot.hull.size());
    }
}
