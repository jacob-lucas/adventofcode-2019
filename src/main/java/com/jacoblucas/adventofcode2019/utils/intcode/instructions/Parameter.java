package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Parameter {
    @Value.Parameter
    public abstract int getValue();

    @Value.Parameter
    public abstract ParameterMode getMode();
}
