package com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerOutputReceiver;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.control.Option;

import java.math.BigInteger;

public class Grid implements IntcodeComputerOutputReceiver {
    private Array<Integer> buffer;
    private List<Tile> tiles;

    public Grid() {
        this.buffer = Array.empty();
        this.tiles = List.empty();
    }

    public Array<Integer> getBuffer() {
        return buffer;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    @Override
    public String id() {
        return "BlocksGrid";
    }

    @Override
    public void receive(BigInteger input) {
        buffer = buffer.append(input.intValue());
        if (buffer.size() == 3) {
            final int x = buffer.get(0);
            final int y = buffer.get(1);
            if (!(x == -1 && y == 0)) {
                final Tile tile = ImmutableTile.builder()
                        .id(TileId.of(buffer.get(2)).get())
                        .x(x)
                        .y(y)
                        .build();

                Option<Tile> existingTile = tiles.find(t -> t.x() == x && t.y() == y);
                if (existingTile.isEmpty()) {
                    tiles = tiles.append(tile);
                } else {
                    tiles = tiles.replace(existingTile.get(), tile);
                }
            }
            buffer = Array.empty();
        }
    }

    public List<String> print() {
        final int minX = 0;
        final int minY = 0;
        final int maxX = tiles.map(Tile::x).sorted().last();
        final int maxY = tiles.map(Tile::y).sorted().last();

        List<String> grid = List.of("GRID");
        for (int y=minY; y<=maxY; y++) {
            final StringBuilder sb = new StringBuilder();
            for (int x=minX; x<=maxX; x++) {
                int finalX = x;
                int finalY = y;
                sb.append(tiles.find(t -> t.x() == finalX && t.y() == finalY).get().id());
            }
            grid = grid.append(sb.toString());
        }

        grid.forEach(System.out::println);

        return grid;
    }
}
