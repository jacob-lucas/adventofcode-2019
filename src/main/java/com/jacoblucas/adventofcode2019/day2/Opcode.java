package com.jacoblucas.adventofcode2019.day2;

import io.vavr.Function2;
import io.vavr.control.Option;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

public enum Opcode {
    ADD(Integer::sum),
    MULTIPLY((a, b) -> a * b),
    HALT((a, b) -> -1);

    private static final int ADD_CODE = 1;
    private static final int MULTIPLY_CODE = 2;
    private static final int HALT_CODE = 99;

    private final Function2<Integer, Integer, Integer> func;

    Opcode(final Function2<Integer, Integer, Integer> func) {
        this.func = func;
    }

    public int apply(final int a, final int b) {
        return func.apply(a, b);
    }

    public static Option<Opcode> of(final int code) {
        return Match(code).of(
                Case($(ADD_CODE), Option.some(ADD)),
                Case($(MULTIPLY_CODE), Option.some(MULTIPLY)),
                Case($(HALT_CODE), Option.some(HALT)),
                Case($(), Option.none())
        );
    }
}
