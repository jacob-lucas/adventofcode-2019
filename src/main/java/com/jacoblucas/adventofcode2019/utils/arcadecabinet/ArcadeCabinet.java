package com.jacoblucas.adventofcode2019.utils.arcadecabinet;

import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;

public class ArcadeCabinet {

    public void run(final Game game, final String program) {
        run(game, program, 0);
    }

    public void run(final Game game, final String program, final int quarters) {
        IntcodeComputer computer = new IntcodeComputer();
        Joystick joystick = new Joystick(computer);

        game.initialize(joystick);

        String prog = program;
        if (quarters > 0) {
            final String[] parts = prog.split(",");
            parts[0] = String.valueOf(quarters);
            prog = String.join(",", parts);
        }

        computer.feed(prog);
        game.receivers().forEach(computer::subscribe);

        computer.execute();
    }

}
