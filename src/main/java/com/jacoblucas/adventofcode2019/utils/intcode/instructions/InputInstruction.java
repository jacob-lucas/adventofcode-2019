package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.google.common.base.Preconditions;
import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData;
import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import org.immutables.value.Value;

import java.math.BigInteger;

import static com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData.MEMORY_KEY;

@Value.Immutable
public abstract class InputInstruction extends Instruction<Array<BigInteger>> {
    @Override
    public Array<BigInteger> execute(final IntcodeComputerData data) {
        Array<BigInteger> memory = data.get(MEMORY_KEY, Array.class);
        final int a = getParameters().get(0).getValue().intValue();
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
