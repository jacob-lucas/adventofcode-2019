package com.jacoblucas.adventofcode2019.day3;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Coordinate2D implements Coordinate {
    @Value.Parameter
    public abstract int x();

    @Value.Parameter
    public abstract int y();

    @Value.Default
    public int z() {
        return 0;
    }
}
