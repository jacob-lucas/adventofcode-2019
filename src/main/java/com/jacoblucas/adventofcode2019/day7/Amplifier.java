package com.jacoblucas.adventofcode2019.day7;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;
import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerOutputReceiver;
import io.vavr.collection.Array;
import io.vavr.collection.Stream;
import io.vavr.control.Option;

public class Amplifier implements IntcodeComputerOutputReceiver {
    private final int id;
    private final IntcodeComputer computer;
    private Option<Amplifier> connection;
    private Thread thread;

    public Amplifier(final Integer id, final Array<Integer> program) {
        this.id = id;
        this.connection = Option.none();
        this.computer = new IntcodeComputer();

        computer.feed(program);
    }

    public void connect(final Amplifier other) {
        this.connection = Option.of(other);
        computer.subscribe(other);
    }

    public Option<Amplifier> getConnection() {
        return connection;
    }

    @Override
    public String id() {
        return String.format("Amp(%d)", id);
    }

    @Override
    public void receive(final int input) {
        computer.receiveInput(input);
    }

    public void amplify(final Integer... inputs) {
        Stream.of(inputs).forEach(computer::receiveInput);
        thread = new Thread(computer::execute, id());
        thread.start();
    }

    public int result() {
        return computer.getOutput();
    }

    public boolean completed() {
        return !thread.isAlive();
    }
}
