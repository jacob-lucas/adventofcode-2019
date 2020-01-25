package com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks;

import com.jacoblucas.adventofcode2019.utils.arcadecabinet.Game;
import com.jacoblucas.adventofcode2019.utils.arcadecabinet.Joystick;
import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerOutputReceiver;
import io.vavr.collection.List;

public class Blocks implements Game {
    private Grid grid;
    private Player player;

    @Override
    public void initialize(Joystick joystick) {
        grid = new Grid();
        player = new Player(grid, joystick);
    }

    @Override
    public List<IntcodeComputerOutputReceiver> receivers() {
        return List.of(grid, player);
    }

    public Grid getGrid() {
        return grid;
    }

    public Player getPlayer() {
        return player;
    }
}
