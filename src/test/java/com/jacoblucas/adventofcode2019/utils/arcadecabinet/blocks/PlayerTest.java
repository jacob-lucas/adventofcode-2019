package com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks;

import com.jacoblucas.adventofcode2019.utils.arcadecabinet.Joystick;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class PlayerTest {

    private Grid grid;

    @Before
    public void setUp() {
        grid = new Grid();
        grid.receive(BigInteger.valueOf(1));
        grid.receive(BigInteger.valueOf(2));
        grid.receive(BigInteger.valueOf(3));
        grid.receive(BigInteger.valueOf(6));
        grid.receive(BigInteger.valueOf(5));
        grid.receive(BigInteger.valueOf(4));
    }

    @Test
    public void updatesScoreAfterScoreSignal() {
        final Player player = new Player(grid, mock(Joystick.class));
        assertThat(player.getScore(), is(0));

        player.receive(BigInteger.valueOf(-1));
        player.receive(BigInteger.valueOf(0));
        player.receive(BigInteger.valueOf(12345));

        assertThat(player.getBuffer().isEmpty(), is(true));
        assertThat(player.getScore(), is(12345));
    }

}
