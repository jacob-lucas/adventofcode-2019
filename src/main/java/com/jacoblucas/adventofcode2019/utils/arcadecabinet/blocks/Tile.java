package com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Tile {
    public abstract TileId id();
    public abstract int x();
    public abstract int y();
}
