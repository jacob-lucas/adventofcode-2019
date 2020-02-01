package com.jacoblucas.adventofcode2019.day14;

import io.vavr.control.Try;
import org.immutables.value.Value;

@Value.Immutable
public abstract class Chemical {
    @Value.Parameter
    public abstract String getId();

    @Value.Parameter
    public abstract int getQuantity();

    public static Try<Chemical> parse(final String str) {
        final String[] parts = str.trim().split(" ");
        return Try.of(() -> ImmutableChemical.of(parts[1], Integer.parseInt(parts[0])));
    }
}
