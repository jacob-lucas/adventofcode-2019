package com.jacoblucas.adventofcode2019.utils.intcode;

import io.vavr.control.Option;
import org.junit.Test;

import static com.jacoblucas.adventofcode2019.utils.intcode.Opcode.ADD;
import static com.jacoblucas.adventofcode2019.utils.intcode.Opcode.HALT;
import static com.jacoblucas.adventofcode2019.utils.intcode.Opcode.MULTIPLY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OpcodeTest {

    @Test
    public void returnsOpcodeForCode() {
        assertThat(Opcode.of(1), is(Option.some(ADD)));
        assertThat(Opcode.of(2), is(Option.some(MULTIPLY)));
        assertThat(Opcode.of(99), is(Option.some(HALT)));
        assertThat(Opcode.of(3), is(Option.none()));
    }

}
