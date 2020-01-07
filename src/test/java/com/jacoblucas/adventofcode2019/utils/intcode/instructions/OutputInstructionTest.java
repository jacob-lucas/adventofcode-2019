package com.jacoblucas.adventofcode2019.utils.intcode.instructions;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData;
import com.jacoblucas.adventofcode2019.utils.intcode.Opcode;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import org.junit.Test;

import static com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerData.MEMORY_KEY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OutputInstructionTest {

    private static final OutputInstruction OUTPUT = ImmutableOutputInstruction.builder()
            .address(9)
            .opcode(Opcode.OUTPUT)
            .parameters(List.of(ImmutableParameter.builder()
                    .value(13)
                    .mode(ParameterMode.POSITION)
                    .build()))
            .build();

    @Test
    public void testExecute() {
        final IntcodeComputerData data = new IntcodeComputerData();
        data.put(MEMORY_KEY, Array.of(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 12, 1, 9));
        assertThat(OUTPUT.execute(data), is(12));
    }

    @Test
    public void testGetIncrement() {
        assertThat(OUTPUT.getIncrement(), is(2));
    }

}
