package com.jacoblucas.adventofcode2019.day13;

import io.vavr.collection.Array;
import io.vavr.control.Try;

public enum TileId {
    EMPTY,
    WALL,
    BLOCK,
    HORIZONTAL_PADDLE,
    BALL;

    public static Try<TileId> of(final int id) {
        return Try.of(() -> Array.of(values())
                .filter(t -> t.ordinal() == id)
                .head());
    }
}
