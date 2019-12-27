package com.jacoblucas.adventofcode2019.day3;

import com.jacoblucas.adventofcode2019.utils.Calculator;
import com.jacoblucas.adventofcode2019.utils.InputReader;
import io.vavr.Tuple2;
import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

import java.util.Comparator;

import static com.jacoblucas.adventofcode2019.day3.Grid.CENTRAL_PORT;

public class Day3 {
    public static void main(String[] args) {
        listIntersections(
                List.of("R75","D30","R83","U83","L12","D49","R71","U7","L72"),
                List.of("U62","R66","U55","R34","D71","R55","D58","R83"));

        listIntersections(
                List.of("R98","U47","R26","D63","R33","U87","L62","D20","R33","U53","R51"),
                List.of("U98","R91","D20","R16","D67","R40","U7","R15","U6","R7"));

        final Iterator<List<String>> input = InputReader.read("day3-input.txt")
                .map(str -> List.of(str.split(",")))
                .iterator();
        listIntersections(input.next(), input.next());
    }

    private static void listIntersections(final List<String> wireOneTrace, final List<String> wireTwoTrace) {
        final Grid grid = new Grid();
        grid.trace("a", CENTRAL_PORT, wireOneTrace);
        grid.trace("b", CENTRAL_PORT, wireTwoTrace);

        final Seq<Tuple2<Coordinate2D, GridEntry>> intersections = grid.getWhere(
                gridEntry -> gridEntry.intersections() > 0,
                Comparator.comparingInt(t -> Calculator.manhattanDistance(CENTRAL_PORT, t._1)));

//        grid.entries().forEach(System.out::println);

        intersections.forEach(i -> {
            int manhattanDistance = Calculator.manhattanDistance(CENTRAL_PORT, i._1);
            System.out.println(String.format("[MD=%d] %s", manhattanDistance, i));
        });
        System.out.println("-----");
    }
}
