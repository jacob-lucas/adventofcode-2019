package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import io.vavr.collection.Array;
import org.immutables.value.Value;

import java.math.BigInteger;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@Value.Immutable
public abstract class Parameter {
    public abstract BigInteger getValue();

    public abstract ParameterMode getMode();

    @Value.Default
    public int getRelativeBase() {
        return 0;
    }

    public BigInteger resolve(final Array<BigInteger> memory) {
        return Match(getMode()).of(
                Case($(ParameterMode.POSITION), () -> memory.get(getValue().intValue())),
                Case($(ParameterMode.RELATIVE), () -> memory.get(getRelativeBase() + getValue().intValue())),
                Case($(), this::getValue));
    }
}
