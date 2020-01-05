package com.jacoblucas.adventofcode2019.utils.intcode;

public interface IntcodeComputerOutputReceiver {
    String id();

    void receive(final int output);
}
