package com.jacoblucas.adventofcode2019.day11;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;
import io.vavr.collection.Map;
import io.vavr.collection.Set;

import static com.jacoblucas.adventofcode2019.utils.InputReader.read;

public class Day11 {
    public static void main(String[] args) {
        final String input = read("day11-input.txt").head();
        final IntcodeComputer computer = new IntcodeComputer();
        EmergencyHullPaintingRobot robot = new EmergencyHullPaintingRobot(input, computer);

        Map<String, Colour> hull = robot.run(Colour.BLACK);
        System.out.println(hull.size());

        robot = new EmergencyHullPaintingRobot(input, computer);
        hull = robot.run(Colour.WHITE);
        System.out.println(hull.size());

        final int minX = getMin(hull.keySet(), "(", ",");
        final int maxX = getMax(hull.keySet(), "(", ",");
        final int minY = getMin(hull.keySet(), ",", ")");
        final int maxY = getMax(hull.keySet(), ",", ")");

        for (int y = maxY; y >= minY; y--) {
            final StringBuilder sb = new StringBuilder();
            for (int x = minX; x <= maxX; x++) {
                final String coord = String.format("(%d,%d)", x, y);
                final Colour colour = hull.get(coord).getOrElse(Colour.BLACK);
                sb.append(colour == Colour.WHITE ? "1" : " ");
            }
            System.out.println(sb.toString());
        }
    }

    private static int getMin(final Set<String> coordStrs, final String s, final String s2) {
        return coordStrs
                .map(str -> str.substring(str.indexOf(s) + 1, str.indexOf(s2)))
                .map(Integer::valueOf)
                .min()
                .get();
    }

    private static int getMax(final Set<String> coordStrs, final String s, final String s2) {
        return coordStrs
                .map(str -> str.substring(str.indexOf(s) + 1, str.indexOf(s2)))
                .map(Integer::valueOf)
                .max()
                .get();
    }
}
