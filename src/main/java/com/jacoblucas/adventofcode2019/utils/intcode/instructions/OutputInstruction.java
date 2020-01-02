package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.google.common.base.Preconditions;
import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import org.immutables.value.Value;

@Value.Immutable
public abstract class OutputInstruction extends Instruction<Integer> {
    @Override
    public Integer execute(final Array<Integer> program) {
        final Parameter parameter = getParameters().get(0);
        return parameter.getMode() == ParameterMode.POSITION ? program.get(parameter.getValue()) : parameter.getValue();
    }

    @Value.Check
    public void check() {
        Preconditions.checkState(getOpcode() == Opcode.OUTPUT, "OutputInstruction must have OUTPUT opcode");
        Preconditions.checkState(getParameters().size() == 1, "OutputInstruction must contain only one parameter");
    }
}
