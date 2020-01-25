package com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks;

import org.junit.Test;

import static com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks.TileId.BALL;
import static com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks.TileId.BLOCK;
import static com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks.TileId.EMPTY;
import static com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks.TileId.HORIZONTAL_PADDLE;
import static com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks.TileId.WALL;
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
