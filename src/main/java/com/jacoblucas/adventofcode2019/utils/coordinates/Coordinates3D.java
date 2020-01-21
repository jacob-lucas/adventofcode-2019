package com.jacoblucas.adventofcode2019.utils.coordinates;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Coordinates3D implements Coordinates {
    @Value.Parameter
    public abstract int x();

    @Value.Parameter
    public abstract int y();

    @Value.Parameter
    public abstract int z();

    @Override
    public String toString() {
        return String.format("<x=%1$3s, y=%2$4s, z=%3$4s>", x(), y(), z());
    }
}
