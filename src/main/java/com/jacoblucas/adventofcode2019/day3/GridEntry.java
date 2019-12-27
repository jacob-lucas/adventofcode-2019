package com.jacoblucas.adventofcode2019.day3;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import org.immutables.value.Value;

@Value.Immutable
public abstract class GridEntry {
    @Value.Default
    public Set<String> getIds() {
        return HashSet.of();
    }

    @Value.Derived
    public int intersections() {
        final int n = getIds().size();
        return n > 1 ? n - 1 : 0;
    }

    @Value.Derived
    public String gridValue() {
        final Set<String> ids = getIds();
        final int n = ids.size();
        if (n > 1) {
            return "X";
        } else if (n == 1) {
            return ids.head();
        } else {
            return ".";
        }
    }
}
