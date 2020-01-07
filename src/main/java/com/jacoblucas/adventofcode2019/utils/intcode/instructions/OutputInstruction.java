package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.google.common.base.Preconditions;
import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData;
import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import org.immutables.value.Value;

import static com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData.MEMORY_KEY;

@Value.Immutable
public abstract class OutputInstruction extends Instruction<Integer> {
    @Override
    public Integer execute(final IntcodeComputerData data) {
        final Array<Integer> memory = data.get(MEMORY_KEY, Array.<Integer>of().getClass());
        return getParameters().get(0).resolve(memory);
    }

    @Value.Check
    public void check() {
        Preconditions.checkState(getOpcode() == Opcode.OUTPUT, "OutputInstruction must have OUTPUT opcode");
        Preconditions.checkState(getParameters().size() == 1, "OutputInstruction must contain only one parameter");
    }
}
