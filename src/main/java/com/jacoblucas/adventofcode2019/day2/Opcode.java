package com.jacoblucas.adventofcode2019.day2;

public enum Opcode {
    ADD(1),
    MULTIPLY(2),
    HALT(99);

    private final int code;

    Opcode(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
