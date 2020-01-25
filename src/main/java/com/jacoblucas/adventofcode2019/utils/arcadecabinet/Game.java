package com.jacoblucas.adventofcode2019.utils.arcadecabinet;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerOutputReceiver;
import io.vavr.collection.List;

public interface Game {
    void initialize(final Joystick joystick);

    List<IntcodeComputerOutputReceiver> receivers();
}
