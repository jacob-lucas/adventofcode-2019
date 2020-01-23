package com.jacoblucas.adventofcode2019.day13;

import static com.jacoblucas.adventofcode2019.utils.InputReader.read;

public class Day13 {
    public static void main(String[] args) {
        final String program = read("day13-input.txt").head();

        ArcadeCabinet arcadeCabinet = new ArcadeCabinet();
        arcadeCabinet.run(program);

        System.out.println(arcadeCabinet.getTiles().filter(t -> t.id() == TileId.BLOCK).size());
    }
}
