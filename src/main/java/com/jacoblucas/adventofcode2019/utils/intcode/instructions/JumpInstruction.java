package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.google.common.base.Preconditions;
import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import org.immutables.value.Value;

@Value.Immutable
public abstract class JumpInstruction extends Instruction<Integer> {
    @Override
    public Integer execute(final Array<Integer> program) {
        final List<Parameter> parameters = getParameters();
        final Parameter p1 = getParameters().get(0);
        final Parameter p2 = getParameters().get(1);
        final int a = p1.getMode() == ParameterMode.POSITION ? program.get(p1.getValue()) : p1.getValue();
        final int b = p2.getMode() == ParameterMode.POSITION ? program.get(p2.getValue()) : p2.getValue();
        return getOpcode().apply(a, b);
    }

    @Value.Check
    public void check() {
        Preconditions.checkState(List.of(Opcode.JUMP_IF_TRUE, Opcode.JUMP_IF_FALSE).contains(getOpcode()), "JumpInstruction must have be either opcode JUMP_IF_TRUE or JUMP_IF_FALSE");
        Preconditions.checkState(getParameters().size() == 2, "JumpInstruction must contain only two parameters");
    }
}
