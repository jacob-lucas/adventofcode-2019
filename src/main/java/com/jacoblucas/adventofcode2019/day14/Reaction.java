package com.jacoblucas.adventofcode2019.day14;

import io.vavr.collection.List;
import io.vavr.control.Try;
import org.immutables.value.Value;

@Value.Immutable
public abstract class Reaction {
    public abstract List<Chemical> getInputs();

    public abstract Chemical getOutput();

    // 1 A, 2 B, 3 C => 2 D
    public static Try<Reaction> parse(final String str) {
        final String[] parts = str.split("=");
        return Try.of(() -> {
            final Try<Chemical> output = Chemical.parse(parts[1].substring(1));
            final List<Try<Chemical>> inputs = List.of(parts[0].split(","))
                    .map(Chemical::parse);

            return ImmutableReaction.builder()
                    .inputs(inputs.map(Try::get))
                    .output(output.get())
                    .build();
        });
    }
}
