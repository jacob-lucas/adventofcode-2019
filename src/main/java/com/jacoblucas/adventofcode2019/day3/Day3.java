package com.jacoblucas.adventofcode2019.day3;

import com.jacoblucas.adventofcode2019.utils.Calculator;
import com.jacoblucas.adventofcode2019.utils.InputReader;
import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinates2D;
import io.vavr.Tuple2;
import io.vavr.Tuple4;
import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

import java.util.Comparator;

import static com.jacoblucas.adventofcode2019.day3.Grid.CENTRAL_PORT;

public class Day3 {
    private static final  Grid GRID = new Grid();
    public static void main(String[] args) {
        final Iterator<List<String>> input = InputReader.read("day3-input.txt")
                .map(str -> List.of(str.split(",")))
                .iterator();

        final List<String> wire1 = input.next();
        final List<String> wire2 = input.next();
        final Seq<Tuple2<Coordinates2D, GridEntry>> intersections = listIntersections(wire1, wire2);

        intersections.forEach(i -> {
            int manhattanDistance = Calculator.manhattanDistance(CENTRAL_PORT, i._1);
            System.out.println(String.format("[MD=%d] %s", manhattanDistance, i));
        });
        System.out.println("-----");

        intersections
                .map(i -> {
                    int wire1Steps = GRID.steps(wire1, i._1);
                    int wire2Steps = GRID.steps(wire2, i._1);
                    return new Tuple4<>(i._1, wire1Steps, wire2Steps, wire1Steps + wire2Steps);
                })
                .sorted(Comparator.comparingInt(t -> t._4))
                .forEach(t -> System.out.println(String.format("Steps to %s - wire1[%d] wire2[%d] total[%d]", t._1, t._2, t._3, t._4)));
    }

    private static Seq<Tuple2<Coordinates2D, GridEntry>> listIntersections(final List<String> wireOneTrace, final List<String> wireTwoTrace) {
        GRID.trace("a", CENTRAL_PORT, wireOneTrace);
        GRID.trace("b", CENTRAL_PORT, wireTwoTrace);

        final Seq<Tuple2<Coordinates2D, GridEntry>> intersections = GRID.getWhere(
                gridEntry -> gridEntry.intersections() > 0,
                Comparator.comparingInt(t -> Calculator.manhattanDistance(CENTRAL_PORT, t._1)));

//        grid.entries().forEach(System.out::println);

        return intersections;
    }
}
