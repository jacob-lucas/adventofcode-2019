package com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks;

import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class GridTest {

    @Test
    public void createsTileAfterThreeOutputs() {
        final Grid grid = new Grid();
        assertThat(grid.getTiles().isEmpty(), is(true));

        grid.receive(BigInteger.valueOf(2));
        grid.receive(BigInteger.valueOf(4));
        grid.receive(BigInteger.valueOf(1));

        final Tile expected = ImmutableTile.builder()
                .id(TileId.WALL)
                .x(2)
                .y(4)
                .build();

        assertThat(grid.getTiles(), containsInAnyOrder(expected));
        assertThat(grid.getBuffer().isEmpty(), is(true));
    }

    @Test
    public void replacesTileAfterThreeOutputs() {
        final Grid grid = new Grid();
        assertThat(grid.getTiles().isEmpty(), is(true));

        grid.receive(BigInteger.valueOf(2));
        grid.receive(BigInteger.valueOf(4));
        grid.receive(BigInteger.valueOf(1));

        Tile expected = ImmutableTile.builder()
                .id(TileId.WALL)
                .x(2)
                .y(4)
                .build();

        assertThat(grid.getTiles(), containsInAnyOrder(expected));

        grid.receive(BigInteger.valueOf(2));
        grid.receive(BigInteger.valueOf(4));
        grid.receive(BigInteger.valueOf(2));

        expected = ImmutableTile.builder()
                .id(TileId.BLOCK)
                .x(2)
                .y(4)
                .build();

        assertThat(grid.getTiles(), containsInAnyOrder(expected));
    }

}
