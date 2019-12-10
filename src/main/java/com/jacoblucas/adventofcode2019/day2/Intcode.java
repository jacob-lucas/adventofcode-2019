package com.jacoblucas.adventofcode2019.day2;

import io.vavr.collection.Array;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import io.vavr.control.Try;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;

public class Intcode {
    static final int CONTINUE = 0;
    static final int HALT = 1;

    private Array<Integer> codes;

    public Intcode(Stream<Integer> codes) {
        this.codes = codes.toArray();
    }

    public Array<Integer> underlying() {
        return codes;
    }

    public Array<Integer> update(final int pos, final int value) {
        codes = codes.update(pos, value);
        return codes;
    }

    public Intcode execute() {
        int result = CONTINUE;
        int pos = 0;
        while (result == CONTINUE) {
            System.out.println(String.format("[pos=%d] Before: %s", pos, codes));
            result = handle(pos);
            System.out.println(String.format("[pos=%d] After: %s", pos, codes));
            pos = pos + 4;
        }
        System.out.println(underlying());
        return this;
    }

    int handle(final int pos) {
        final Option<Opcode> opcode = Opcode.of(codes.get(pos));
        return Match(opcode).of(
                Case($None(), ex -> CONTINUE),
                Case($Some($(Opcode.HALT)), o -> HALT),
                Case($Some($()), o -> update(pos, o))
        );
    }

    private int update(final int pos, final Opcode opcode) {
        final Try<Integer> a = Try.of(() -> codes.get(codes.get(pos + 1)));
        final Try<Integer> b = Try.of(() -> codes.get(codes.get(pos + 2)));
        final Try<Integer> c = Try.of(() -> codes.get(pos + 3));

        if (a.isFailure() || b.isFailure() || c.isFailure()) {
            System.out.println(String.format("Unable to access codes at positions [%d, %d, %d] (num codes = %d)", pos + 1, pos + 2, pos + 3, codes.length()));
            return HALT;
        }

        codes = update(c.get(), opcode.apply(a.get(), b.get()));
        return CONTINUE;
    }
}
