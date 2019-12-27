package com.jacoblucas.adventofcode2019.day3;

import com.jacoblucas.adventofcode2019.utils.Calculator;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;

import java.util.Comparator;
import java.util.function.Predicate;

public class Grid {
    static final Coordinate2D CENTRAL_PORT = ImmutableCoordinate2D.of(0, 0);
    static final GridEntry EMPTY = ImmutableGridEntry.builder().build();

    private Map<Coordinate2D, GridEntry> grid;

    public Grid() {
        this.grid = HashMap.of(CENTRAL_PORT, ImmutableGridEntry.builder().ids(HashSet.of("o")).build());
    }

    public int size() {
        return grid.keySet().size();
    }

    public GridEntry get(final int x, final int y) {
        return grid.getOrElse(ImmutableCoordinate2D.of(x, y), EMPTY);
    }

    public Seq<Tuple2<Coordinate2D, GridEntry>> getWhere(final Predicate<GridEntry> predicate, final Comparator<Tuple2<Coordinate2D, GridEntry>> comparator) {
        return grid.filter(elem -> predicate.test(elem._2))
                .map(elem -> new Tuple2<>(elem._1, elem._2))
                .sorted(comparator);
    }

    public void trace(final String id, final Coordinate2D currentCoord, final List<String> paths) {
        if (!paths.isEmpty()) {
            final String next = paths.head();
            final char direction = next.charAt(0);
            int amount = Integer.parseInt(next.substring(1));
            final Coordinate2D destinationCoord;
            if (direction == 'U' || direction == 'D') {
                final int y = currentCoord.y();
                destinationCoord = ImmutableCoordinate2D.copyOf(currentCoord)
                        .withY(direction == 'U' ? y + amount : y - amount);

                fillY(currentCoord, destinationCoord, id);
            } else {
                final int x = currentCoord.x();
                destinationCoord = ImmutableCoordinate2D.copyOf(currentCoord)
                        .withX(direction == 'R' ? x + amount : x - amount);

                fillX(currentCoord, destinationCoord, id);
            }

            trace(id, destinationCoord, paths.tail());
        }
    }

    void fillX(final Coordinate2D p, final Coordinate2D q, final String id) {
        if (p.y() == q.y()) {
            final int y = p.y();
            Stream.range(Math.min(p.x(), q.x()), Math.max(p.x(), q.x()) + 1)
                    .forEach(x -> put(x, y, id));
        }
    }

    void fillY(final Coordinate2D p, final Coordinate2D q, final String id) {
        if (p.x() == q.x()) {
            final int x = p.x();
            Stream.range(Math.min(p.y(), q.y()), Math.max(p.y(), q.y()) + 1)
                    .forEach(y -> put(x, y, id));
        }
    }

    void put(final int x, final int y, final String id) {
        final Coordinate2D coord = ImmutableCoordinate2D.of(x, y);

        if (!coord.equals(CENTRAL_PORT)) {
            final GridEntry existing = get(x, y);
            final Set<String> ids = existing.equals(EMPTY) ? HashSet.of(id) : existing.getIds().add(id);
            grid = grid.put(coord, ImmutableGridEntry.copyOf(existing).withIds(ids));
        }
    }

    List<String> entries() {
        final int dim = 300;
        List<String> entries = List.of();
        for (int y = dim; y > dim*-1; y--) {
            final StringBuilder sb = new StringBuilder();
            for (int x = dim*-1; x < dim; x++) {
                final GridEntry entry = get(x, y);
                sb.append(entry.gridValue());
            }
            entries = entries.append(sb.toString());
        }
        return entries;
    }

    public int steps(final List<String> paths, final Coordinate2D destination) {
        return steps(paths, CENTRAL_PORT, destination, 0);
    }

    private int steps(final List<String> paths, final Coordinate2D current, final Coordinate2D destination, final int stepCount) {
        if (!paths.isEmpty()) {
            final String next = paths.head();
            final char direction = next.charAt(0);
            int amount = Integer.parseInt(next.substring(1));
            final Coordinate2D updated;
            final int axisChange;
            if (direction == 'U' || direction == 'D') {
                final int y = current.y();
                axisChange = direction == 'U' ? y + amount : y - amount;
                updated = ImmutableCoordinate2D.copyOf(current).withY(axisChange);
                if (destination.x() == current.x() && Math.min(current.y(), axisChange) <= destination.y() && destination.y() <= Math.max(current.y(), axisChange)) { // dest y between current y and axisChange
                    // cross destination in this move
                    return stepCount + Calculator.manhattanDistance(current, destination);
                }
            } else {
                final int x = current.x();
                axisChange = direction == 'R' ? x + amount : x - amount;
                updated = ImmutableCoordinate2D.copyOf(current).withX(axisChange);
                if (destination.y() == current.y() && Math.min(current.x(), axisChange) <= destination.x() && destination.x() <= Math.max(current.x(), axisChange)) {
                    // cross destination in this move
                    return stepCount + Calculator.manhattanDistance(current, destination);
                }
            }

            return steps(paths.tail(), updated, destination, stepCount + amount);
        }

        return stepCount;
    }
}
