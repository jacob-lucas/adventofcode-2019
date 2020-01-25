package com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks;

import io.vavr.collection.Array;
import io.vavr.control.Try;

public enum TileId {
    EMPTY(" "),
    WALL("#"),
    BLOCK("■"),
    HORIZONTAL_PADDLE("▬"),
    BALL("●");

    final String view;

    TileId(final String view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return view;
    }

    public static Try<TileId> of(final int id) {
        return Try.of(() -> Array.of(values())
                .filter(t -> t.ordinal() == id)
                .head());
    }
}
