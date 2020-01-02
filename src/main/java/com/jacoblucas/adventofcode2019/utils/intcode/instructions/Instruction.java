package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.immutables.value.Value;

public abstract class Instruction<T> {
    public abstract int getAddress();

    public abstract Opcode getOpcode();

    public abstract List<Parameter> getParameters();

    @Value.Default
    public Option<Integer> getInput() {
        return Option.none();
    }

    public abstract T execute(final Array<Integer> program);

    public int getIncrement() {
        return 1 + getParameters().size();
    }

//    int resolve(final Parameter parameter) {
//        final int value = parameter.getValue();
//        return parameter.getMode() == ParameterMode.POSITION ? getMemory().get(value) : value;
//    }


}
