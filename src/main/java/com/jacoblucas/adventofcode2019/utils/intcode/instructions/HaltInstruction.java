package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.google.common.base.Preconditions;
import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import org.immutables.value.Value;

@Value.Immutable
public abstract class HaltInstruction extends Instruction<Array<Integer>> {
    @Override
    public Array<Integer> execute(final Array<Integer> program) {
        // do nothing
        return program;
    }

    @Value.Check
    public void check() {
        Preconditions.checkState(getOpcode() == Opcode.HALT, "HaltInstruction must have HALT opcode");
        Preconditions.checkState(getParameters().isEmpty(), "HaltInstruction must contain zero parameters");
    }
}
