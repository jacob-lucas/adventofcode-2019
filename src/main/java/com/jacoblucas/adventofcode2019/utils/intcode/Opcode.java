package com.jacoblucas.adventofcode2019.utils.intcode;

import io.vavr.Function2;
import io.vavr.collection.Stream;
import io.vavr.control.Option;

public enum Opcode {
    ADD(1, Integer::sum),
    MULTIPLY(2, (a, b) -> a * b),
    SAVE(3, null),
    OUTPUT(4, null),
    JUMP_IF_TRUE(5, (a, b) -> a != 0 ? b : -1),
    JUMP_IF_FALSE(6, (a, b) -> a == 0 ? b : -1),
    LESS_THAN(7, (a, b) -> a < b ? 1 : 0),
    EQUALS(8, (a, b) -> a.equals(b) ? 1 : 0),
    HALT(99, null);

    private final int code;
    private final Function2<Integer, Integer, Integer> func;

    Opcode(final int code, final Function2<Integer, Integer, Integer> func) {
        this.code = code;
        this.func = func;
    }

    public int apply(final int a, final int b) {
        return func.apply(a, b);
    }

    public static Option<Opcode> of(final int code) {
        return Stream.of(Opcode.values()).filter(o -> o.code == code).headOption();
    }
}
