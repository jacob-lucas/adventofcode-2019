package com.jacoblucas.adventofcode2019.utils.intcode;

import com.google.common.base.Preconditions;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import org.immutables.value.Value;

import static com.jacoblucas.adventofcode2019.utils.intcode.Opcode.ADD;
import static com.jacoblucas.adventofcode2019.utils.intcode.Opcode.HALT;
import static com.jacoblucas.adventofcode2019.utils.intcode.Opcode.MULTIPLY;
import static com.jacoblucas.adventofcode2019.utils.intcode.Opcode.OUTPUT;
import static com.jacoblucas.adventofcode2019.utils.intcode.Opcode.SAVE;
import static com.jacoblucas.adventofcode2019.utils.intcode.ParameterMode.IMMEDIATE;
import static com.jacoblucas.adventofcode2019.utils.intcode.ParameterMode.POSITION;

@Value.Immutable
public abstract class Instruction {
    public abstract int getMemoryAddress();

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

        if (opcode == SAVE) {
            Preconditions.checkArgument(size == 1, String.format("%s must be constructed with 1 parameter, currently has [%d]", opcode.name(), size));
            Preconditions.checkArgument(parameters.get(0).getMode() != IMMEDIATE, "Parameter must not be set to IMMEDIATE");
        }
    }

    public Array<Integer> execute(final Array<Integer> memory, final int input) {
        final Opcode opcode = getOpcode();
        final List<Parameter> parameters = getParameters();
        if (opcode == HALT || parameters.size() != 1) {
            return memory;
        }

        final int value = parameters.get(0).getValue();

        if (opcode == OUTPUT) {
            System.out.println(String.format("%s INPUT=%d OUTPUT=%d", this, input, memory.get(value)));
        } else if (opcode == SAVE) {
            return memory.update(value, input);
        }

        return memory;
    }

    public Array<Integer> execute(final Array<Integer> memory) {
        final Opcode opcode = getOpcode();
        if (opcode == HALT || getParameters().size() != 3) {
            return memory;
        }

        final int a = getParameterValue(0, memory);
        final int b = getParameterValue(1, memory);
        final int c = getParameters().get(2).getValue();

        return memory.update(c, opcode.apply(a, b));
    }

    public int calculateInstructionPointer(final Array<Integer> memory) {
        final Opcode opcode = getOpcode();
        final List<Parameter> parameters = getParameters();
        if (parameters.size() != 2) {
            return 0;
        }

        final int a = getParameterValue(0, memory);
        final int b = getParameterValue(1, memory);

        return opcode.apply(a, b);
    }

    public int getParameterValue(int n, final Array<Integer> memory) {
        final Parameter parameter = getParameters().get(n);
        return parameter.getMode() == POSITION ? memory.get(parameter.getValue()) : parameter.getValue();
    }
}
