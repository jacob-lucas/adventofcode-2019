package com.jacoblucas.adventofcode2019.day8;

import io.vavr.collection.Stream;
import io.vavr.control.Try;

public enum PixelColour {
    BLACK,
    WHITE,
    TRANSPARENT;

    static Try<PixelColour> of(final int code) {
        return Try.of(() -> Stream.of(values())
                .filter(pc -> pc.ordinal() == code)
                .head());
    }
}
