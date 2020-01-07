package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.google.common.base.Preconditions;
import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData;
import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import org.immutables.value.Value;

import static com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData.MEMORY_KEY;

@Value.Immutable
public abstract class InputInstruction extends Instruction<Array<Integer>> {
    @Override
    public Array<Integer> execute(final IntcodeComputerData data) {
        Array<Integer> memory = data.get(MEMORY_KEY, Array.<Integer>of().getClass());
        final int a = getParameters().get(0).getValue();
        memory = memory.update(a, getInput().get());
        data.put(MEMORY_KEY, memory);
        return memory;
    }

    @Value.Check
    public void check()  {
        Preconditions.checkState(getOpcode() == Opcode.SAVE, "InputInstruction must have INPUT opcode");
        Preconditions.checkState(getParameters().size() == 1, "InputInstruction must contain only one parameter");
    }
}
