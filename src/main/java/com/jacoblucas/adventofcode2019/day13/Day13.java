package com.jacoblucas.adventofcode2019.day13;

import com.jacoblucas.adventofcode2019.utils.arcadecabinet.ArcadeCabinet;
import com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks.Blocks;
import com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks.TileId;

import static com.jacoblucas.adventofcode2019.utils.InputReader.read;

public class Day13 {
    public static void main(String[] args) {
        final ArcadeCabinet arcadeCabinet = new ArcadeCabinet();

        final String program = read("day13-input.txt").head();
        Blocks blocks = new Blocks();
        arcadeCabinet.run(blocks, program);

        System.out.println(blocks.getGrid().getTiles().filter(t -> t.id() == TileId.BLOCK).size());

        blocks = new Blocks();
        arcadeCabinet.run(blocks, program, 2);

        blocks.getGrid().print();
        System.out.println(blocks.getPlayer().getScore());
    }
}
