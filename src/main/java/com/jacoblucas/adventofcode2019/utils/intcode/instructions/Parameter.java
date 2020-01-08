package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import io.vavr.collection.Array;
import org.immutables.value.Value;

import java.math.BigInteger;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@Value.Immutable
public abstract class Parameter {
    @Value.Parameter
    public abstract BigInteger getValue();

    @Value.Parameter
    public abstract ParameterMode getMode();

    public BigInteger resolve(final Array<BigInteger> memory) {
        return Match(getMode()).of(
                Case($(ParameterMode.POSITION), () -> memory.get(getValue().intValue())),
                Case($(), this::getValue));
    }
}
