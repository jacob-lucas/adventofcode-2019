package com.jacoblucas.adventofcode2019.day13;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;
import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerOutputReceiver;
import io.vavr.collection.Array;
import io.vavr.collection.List;

import java.math.BigInteger;

public class ArcadeCabinet implements IntcodeComputerOutputReceiver {

    private final IntcodeComputer computer;

    private Array<Integer> buffer;
    private List<Tile> tiles;

    public ArcadeCabinet() {
        this.computer = new IntcodeComputer();
        this.buffer = Array.empty();
        this.tiles = List.empty();
    }

    @Override
    public String id() {
        return "ArcadeCabinet";
    }

    public void run(final String program) {
        computer.feed(program);
        computer.subscribe(this);
        computer.execute();
    }

    @Override
    public void receive(BigInteger input) {
        buffer = buffer.append(input.intValue());
        if (buffer.size() == 3) {
            final Tile tile = ImmutableTile.builder()
                    .id(TileId.of(buffer.get(2)).get())
                    .x(buffer.get(0))
                    .y(buffer.get(1))
                    .build();
            System.out.println(String.format("Created %s", tile));
            tiles = tiles.append(tile);
            buffer = Array.empty();
        }
    }

    public Array<Integer> getBuffer() {
        return buffer;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}
