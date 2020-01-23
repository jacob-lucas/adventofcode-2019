package com.jacoblucas.adventofcode2019.day13;

import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class ArcadeCabinetTest {

    @Test
    public void createsTileAfterThreeOutputs() {
        final ArcadeCabinet arcadeCabinet = new ArcadeCabinet();
        assertThat(arcadeCabinet.getTiles().isEmpty(), is(true));

        arcadeCabinet.receive(BigInteger.valueOf(2));
        arcadeCabinet.receive(BigInteger.valueOf(4));
        arcadeCabinet.receive(BigInteger.valueOf(1));

        final Tile expected = ImmutableTile.builder()
                .id(TileId.WALL)
                .x(2)
                .y(4)
                .build();

        assertThat(arcadeCabinet.getTiles(), containsInAnyOrder(expected));
        assertThat(arcadeCabinet.getBuffer().isEmpty(), is(true));
    }

    @Test
    public void testExampleProgram() {
        final ArcadeCabinet arcadeCabinet = new ArcadeCabinet();
        arcadeCabinet.run("104,1,104,2,104,3,104,6,104,5,104,4,99");

        final Tile t1 = ImmutableTile.builder()
                .id(TileId.HORIZONTAL_PADDLE)
                .x(1)
                .y(2)
                .build();
        final Tile t2 = ImmutableTile.builder()
                .id(TileId.BALL)
                .x(6)
                .y(5)
                .build();

        assertThat(arcadeCabinet.getTiles(), containsInAnyOrder(t1, t2));
    }

}
