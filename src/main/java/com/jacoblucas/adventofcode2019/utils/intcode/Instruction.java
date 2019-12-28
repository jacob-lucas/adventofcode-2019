package com.jacoblucas.adventofcode2019.utils.intcode;

import com.google.common.base.Preconditions;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import org.immutables.value.Value;

import static com.jacoblucas.adventofcode2019.utils.intcode.Opcode.ADD;
import static com.jacoblucas.adventofcode2019.utils.intcode.Opcode.HALT;
import static com.jacoblucas.adventofcode2019.utils.intcode.Opcode.MULTIPLY;
import static com.jacoblucas.adventofcode2019.utils.intcode.ParameterMode.IMMEDIATE;
import static com.jacoblucas.adventofcode2019.utils.intcode.ParameterMode.POSITION;

@Value.Immutable
public abstract class Instruction {
    public abstract Opcode getOpcode();

    @Value.Default
    public List<Parameter> getParameters() {
        return List.of();
    }

    @Value.Derived
    public int getIncrement() {
        return 1 + getParameters().size();
    }

    @Value.Check
    void check() {
        final Opcode opcode = getOpcode();
        final List<Parameter> parameters = getParameters();
        final int size = parameters.size();
        if (opcode == HALT) {
            Preconditions.checkArgument(size == 0, String.format("HALT must be constructed with zero parameters, currently has [%d]", size));
        }

        if (opcode == ADD || opcode == MULTIPLY) {
            Preconditions.checkArgument(size == 3, String.format("%s must be constructed with 3 parameters, currently has [%d]", opcode.name(), size));
            Preconditions.checkArgument(parameters.get(2).getMode() != IMMEDIATE, "Output parameter must not be set to IMMEDIATE");
        }
    }

    public Array<Integer> execute(final Array<Integer> memory) {
        final Opcode opcode = getOpcode();
        if (opcode == HALT) {
            return memory;
        }

        final List<Parameter> parameters = getParameters();
        final int a = parameters.get(0).getMode() == POSITION ? memory.get(parameters.get(0).getValue()) : parameters.get(0).getValue();
        final int b = parameters.get(1).getMode() == POSITION ? memory.get(parameters.get(1).getValue()) : parameters.get(1).getValue();
        final int c = parameters.get(2).getValue();

        return memory.update(c, opcode.apply(a, b));
    }
}
