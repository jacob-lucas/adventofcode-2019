package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.google.common.base.Preconditions;
import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import org.immutables.value.Value;

@Value.Immutable
public abstract class MemoryUpdateInstruction extends Instruction<Array<Integer>> {
    @Override
    public Array<Integer> execute(final Array<Integer> program) {
        final List<Parameter> parameters = getParameters();
        final Parameter p1 = getParameters().get(0);
        final Parameter p2 = getParameters().get(1);
        final int a = p1.getMode() == ParameterMode.POSITION ? program.get(p1.getValue()) : p1.getValue();
        final int b = p2.getMode() == ParameterMode.POSITION ? program.get(p2.getValue()) : p2.getValue();
        final int c = parameters.get(2).getValue();

        final Opcode opcode = getOpcode();
        return program.update(c, opcode.apply(a, b));
    }

    @Value.Check
    public void check() {
        Preconditions.checkState(List.of(Opcode.ADD, Opcode.MULTIPLY, Opcode.LESS_THAN, Opcode.EQUALS).contains(getOpcode()), "MemoryUpdateInstruction must have be either opcode ADD, MULTIPLY, LESS_THAN, or EQUALS");
        Preconditions.checkState(getParameters().size() == 3, "MemoryUpdateInstruction must contain only three parameters");
    }
}
