package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.google.common.base.Preconditions;
import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import org.immutables.value.Value;

@Value.Immutable
public abstract class InputInstruction extends Instruction<Array<Integer>> {
    @Override
    public Array<Integer> execute(final Array<Integer> program) {
        final int a = getParameters().get(0).getValue();
        return program.update(a, getInput().get());
    }

    @Value.Check
    public void check()  {
        Preconditions.checkState(getOpcode() == Opcode.SAVE, "InputInstruction must have INPUT opcode");
        Preconditions.checkState(getParameters().size() == 1, "InputInstruction must contain only one parameter");
    }
}
