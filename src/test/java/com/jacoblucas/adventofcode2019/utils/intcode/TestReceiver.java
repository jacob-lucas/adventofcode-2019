package com.jacoblucas.adventofcode2019.utils.intcode;

class TestReceiver implements IntcodeComputerOutputReceiver {
    private Integer received;

    @Override
    public String id() {
        return "TR1";
    }

    @Override
    public void receive(int output) {
        received = output;
    }

    int getReceived() {
        return received;
    }
}
