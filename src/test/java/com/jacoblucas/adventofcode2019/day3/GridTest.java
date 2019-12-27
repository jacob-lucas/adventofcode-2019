package com.jacoblucas.adventofcode2019.day3;

import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import org.junit.Test;

import static com.jacoblucas.adventofcode2019.day3.Grid.CENTRAL_PORT;
import static com.jacoblucas.adventofcode2019.day3.Grid.EMPTY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class GridTest {

    @Test
    public void testEmptyGrid() {
        final Grid grid = new Grid();
        assertThat(grid.size(), is(1));
        assertThat(grid.get(0, 0).getIds().head(), is("o"));
    }

    @Test
    public void testGetOnEmptyEntry() {
        final Grid grid = new Grid();
        assertThat(grid.get(3, 4), is(EMPTY));
    }

    @Test
    public void testGetOnExistingEntry() {
        final Grid grid = new Grid();
        final String id = "1";
        grid.put(3, 4, id);
        assertThat(grid.get(3, 4), is(ImmutableGridEntry.builder().ids(HashSet.of(id)).build()));
    }

    @Test
    public void testPutOnEmptyEntry() {
        final Grid grid = new Grid();
        final String id = "1";
        final int x = 3;
        final int y = 4;

        grid.put(x, y, id);

        assertThat(grid.size(), is(2));
        assertThat(grid.get(x, y).getIds(), containsInAnyOrder(id));
    }

    @Test
    public void testPutOnExistingEntry() {
        final Grid grid = new Grid();
        final String id1 = "1";
        final String id2 = "2";
        final int x = 3;
        final int y = 4;

        grid.put(x, y, id1);
        grid.put(x, y, id2);

        assertThat(grid.size(), is(2));
        assertThat(grid.get(x, y).getIds(), containsInAnyOrder(id1, id2));
    }

    @Test
    public void testFillX() {
        final Grid grid = new Grid();
        assertThat(grid.size(), is(1));

        final String id = "1";
        final Coordinate2D p = ImmutableCoordinate2D.of(5, 0);
        final Coordinate2D q = ImmutableCoordinate2D.of(1, 0);
        grid.fillX(p, q, id);

        assertThat(grid.get(1, 0).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(2, 0).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(3, 0).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(4, 0).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(5, 0).getIds(), containsInAnyOrder(id));
    }

    @Test
    public void testFillXIgnoresCentralPort() {
        final Grid grid = new Grid();
        assertThat(grid.size(), is(1));

        final String id = "1";
        final Coordinate2D p = ImmutableCoordinate2D.of(5, 0);
        final Coordinate2D q = ImmutableCoordinate2D.of(-3, 0);
        grid.fillX(p, q, id);

        assertThat(grid.get(-3, 0).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(-2, 0).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(-1, 0).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(0, 0).getIds(), containsInAnyOrder("o"));
        assertThat(grid.get(1, 0).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(2, 0).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(3, 0).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(4, 0).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(5, 0).getIds(), containsInAnyOrder(id));
    }

    @Test
    public void testFillY() {
        final Grid grid = new Grid();
        assertThat(grid.size(), is(1));

        final String id = "2";
        final Coordinate2D p = ImmutableCoordinate2D.of(1, 5);
        final Coordinate2D q = ImmutableCoordinate2D.of(1, 1);
        grid.fillY(p, q, id);

        assertThat(grid.get(1, 1).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(1, 2).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(1, 3).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(1, 4).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(1, 5).getIds(), containsInAnyOrder(id));
    }

    @Test
    public void testFillYIgnoresCentralPort() {
        final Grid grid = new Grid();
        assertThat(grid.size(), is(1));

        final String id = "2";
        final Coordinate2D p = ImmutableCoordinate2D.of(0, -1);
        final Coordinate2D q = ImmutableCoordinate2D.of(0, 2);
        grid.fillY(p, q, id);

        assertThat(grid.get(0, -1).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(0, 0).getIds(), containsInAnyOrder("o"));
        assertThat(grid.get(0, 1).getIds(), containsInAnyOrder(id));
        assertThat(grid.get(0, 2).getIds(), containsInAnyOrder(id));
    }

    @Test
    public void testTraceExample() {
        final Grid grid = new Grid();

        grid.trace("a", CENTRAL_PORT, List.of("R8","U5","L5","D3"));
        grid.trace("b", CENTRAL_PORT, List.of("U7","R6","D4","L4"));

        final GridEntry i1 = grid.get(3, 3);
        final GridEntry i2 = grid.get(6, 5);

        assertThat(i1.getIds(), containsInAnyOrder("a", "b"));
        assertThat(i2.getIds(), containsInAnyOrder("a", "b"));
    }

    @Test
    public void testSteps() {
        final ImmutableCoordinate2D i1 = ImmutableCoordinate2D.of(3, 3);
        final ImmutableCoordinate2D i2 = ImmutableCoordinate2D.of(6, 5);
        final Grid grid = new Grid();

        final List<String> path1 = List.of("R8", "U5", "L5", "D3");
        grid.trace("a", CENTRAL_PORT, path1);
        assertThat(grid.steps(path1, i1), is(20));
        assertThat(grid.steps(path1, i2), is(15));

        final List<String> path2 = List.of("U7", "R6", "D4", "L4");
        grid.trace("b", CENTRAL_PORT, path2);
        assertThat(grid.steps(path2, i1), is(20));
        assertThat(grid.steps(path2, i2), is(15));
    }

}

