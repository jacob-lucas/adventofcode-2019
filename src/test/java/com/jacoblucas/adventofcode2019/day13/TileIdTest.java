package com.jacoblucas.adventofcode2019.day13;

import org.junit.Test;

import static com.jacoblucas.adventofcode2019.day13.TileId.BALL;
import static com.jacoblucas.adventofcode2019.day13.TileId.BLOCK;
import static com.jacoblucas.adventofcode2019.day13.TileId.EMPTY;
import static com.jacoblucas.adventofcode2019.day13.TileId.HORIZONTAL_PADDLE;
import static com.jacoblucas.adventofcode2019.day13.TileId.WALL;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TileIdTest {

    @Test
    public void testTileId() {
        assertThat(TileId.of(0).get(), is(EMPTY));
        assertThat(TileId.of(1).get(), is(WALL));
        assertThat(TileId.of(2).get(), is(BLOCK));
        assertThat(TileId.of(3).get(), is(HORIZONTAL_PADDLE));
        assertThat(TileId.of(4).get(), is(BALL));
    }

}
