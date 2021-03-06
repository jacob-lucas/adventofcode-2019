package com.jacoblucas.adventofcode2019.utils.coordinates;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Coordinates2D implements Coordinates {
    @Value.Parameter
    public abstract int x();

    @Value.Parameter
    public abstract int y();

    @Value.Default
    public int z() {
        return 0;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x(), y());
    }
}
