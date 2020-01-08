package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData;
import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.junit.Test;

import java.math.BigInteger;

import static com.jacoblucas.adventofcode2019.TestUtils.bigIntegerArray;
import static com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData.MEMORY_KEY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InputInstructionTest {

    private static final InputInstruction SAVE = ImmutableInputInstruction.builder()
            .address(0)
            .opcode(Opcode.SAVE)
            .input(Option.of(BigInteger.valueOf(5)))
            .parameters(List.of(
                    ImmutableParameter.builder()
                            .value(BigInteger.ZERO)
                            .mode(ParameterMode.POSITION)
                            .build()))
            .build();

    @Test
    public void testExecute() {
        final IntcodeComputerData data = new IntcodeComputerData();
        data.put(MEMORY_KEY, bigIntegerArray(3, 0, 4, 0, 99));
        assertThat(SAVE.execute(data), is(bigIntegerArray(5, 0, 4, 0, 99)));
    }

    @Test
    public void testGetIncrement() {
        assertThat(SAVE.getIncrement(), is(2));
    }
}
