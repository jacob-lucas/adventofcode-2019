package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import io.vavr.collection.Array;
import org.immutables.value.Value;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@Value.Immutable
public abstract class Parameter {
    @Value.Parameter
    public abstract int getValue();

    @Value.Parameter
    public abstract ParameterMode getMode();

    public int resolve(final Array<Integer> memory) {
        return Match(getMode()).of(
                Case($(ParameterMode.POSITION), () -> memory.get(getValue())),
                Case($(), this::getValue));
    }
}
