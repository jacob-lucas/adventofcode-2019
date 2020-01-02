package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import io.vavr.control.Option;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

public enum ParameterMode {
    POSITION(0),
    IMMEDIATE(1);

    private final int mode;

    ParameterMode(int mode) {
        this.mode = mode;
    }

    public static Option<ParameterMode> of(final int mode) {
        return Match(mode).of(
                Case($(POSITION.mode), Option.some(POSITION)),
                Case($(IMMEDIATE.mode), Option.some(IMMEDIATE)),
                Case($(), Option.none())
        );
    }
}
