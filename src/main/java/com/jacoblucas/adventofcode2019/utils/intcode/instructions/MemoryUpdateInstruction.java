package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.google.common.base.Preconditions;
import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData;
import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import org.immutables.value.Value;

import static com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData.MEMORY_KEY;

@Value.Immutable
public abstract class MemoryUpdateInstruction extends Instruction<Array<Integer>> {
    @Override
    public Array<Integer> execute(final IntcodeComputerData data) {
        Array<Integer> memory = data.get(MEMORY_KEY, Array.<Integer>of().getClass());
        final List<Parameter> parameters = getParameters();
        final Parameter p1 = parameters.get(0);
        final Parameter p2 = parameters.get(1);
        final int c = parameters.get(2).getValue();

        memory = memory.update(c, getOpcode().apply(p1.resolve(memory), p2.resolve(memory)));
        data.put(MEMORY_KEY, memory);
        return memory;
    }

    @Value.Check
    public void check() {
        Preconditions.checkState(List.of(Opcode.ADD, Opcode.MULTIPLY, Opcode.LESS_THAN, Opcode.EQUALS).contains(getOpcode()), "MemoryUpdateInstruction must have be either opcode ADD, MULTIPLY, LESS_THAN, or EQUALS");
        Preconditions.checkState(getParameters().size() == 3, "MemoryUpdateInstruction must contain only three parameters");
    }
}
