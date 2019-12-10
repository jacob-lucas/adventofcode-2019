package com.jacoblucas.adventofcode2019.day2;

import com.google.common.base.Preconditions;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import org.immutables.value.Value;

import static com.jacoblucas.adventofcode2019.day2.Opcode.HALT;

@Value.Immutable
public abstract class Instruction {
    public abstract Opcode getOpcode();

    @Value.Default
    public List<Integer> getParameters() {
        return List.of();
    }

    @Value.Derived
    public int getIncrement() {
        return 1 + getParameters().size();
    }

    @Value.Check
    void check() {
        final Opcode opcode = getOpcode();
        final List<Integer> parameters = getParameters();
        final int size = parameters.size();
        if (opcode == HALT) {
            Preconditions.checkArgument(size == 0, String.format("HALT must be constructed with zero parameters, currently has [%d]", size));
        } else {
            Preconditions.checkArgument(size == 3, String.format("%s must be constructed with 3 parameters, currently has [%d]", opcode.name(), size));
        }
    }

    public Array<Integer> execute(final Array<Integer> memory) {
        final Opcode opcode = getOpcode();
        if (opcode == HALT) {
            return memory;
        }

        final List<Integer> parameters = getParameters();
        final int a = memory.get(parameters.get(0));
        final int b = memory.get(parameters.get(1));
        final int c = parameters.get(2);

        return memory.update(c, opcode.apply(a, b));
    }
}
