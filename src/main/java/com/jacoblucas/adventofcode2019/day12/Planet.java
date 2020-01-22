package com.jacoblucas.adventofcode2019.day12;

import io.vavr.collection.List;
import org.immutables.value.Value;

@Value.Immutable
public abstract class Planet {
    public abstract List<Moon> getMoons();

    public int getTotalEnergy() {
        return getMoons()
                .map(Moon::getTotalEnergy)
                .sum()
                .intValue();
    }
}
