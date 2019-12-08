package com.jacoblucas.adventofcode2019.day1;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(allParameters = true)
public abstract class Module {
    public abstract int getMass();

    @Value.Derived
    public int getRequiredFuel() {
        return (getMass() / 3) - 2;
    }

    @Value.Check
    void check() {
        if (getMass() < 0) {
            throw new IllegalArgumentException("mass must be > 0");
        }
    }

    public static Module of(final String mass) {
        return ImmutableModule.of(Integer.parseInt(mass));
    }
}
