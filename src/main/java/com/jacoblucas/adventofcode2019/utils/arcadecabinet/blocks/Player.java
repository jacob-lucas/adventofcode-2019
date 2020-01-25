package com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks;

import com.jacoblucas.adventofcode2019.utils.arcadecabinet.Joystick;
import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerOutputReceiver;
import io.vavr.collection.Array;

import java.math.BigInteger;

import static com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks.TileId.BALL;
import static com.jacoblucas.adventofcode2019.utils.arcadecabinet.blocks.TileId.HORIZONTAL_PADDLE;

public class Player implements IntcodeComputerOutputReceiver {
    private final Grid grid;
    private Joystick joystick;
    private Array<Integer> buffer;
    private int score;
    private boolean gameStarted;
    private Tile lastBallPosition = null;

    public Player(Grid grid, final Joystick joystick) {
        this.grid = grid;
        this.buffer = Array.empty();
        this.joystick = joystick;
        this.score = 0;
        this.gameStarted = false;
    }

    public Array<Integer> getBuffer() {
        return buffer;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String id() {
        return "BlocksPlayer";
    }

    @Override
    public void receive(BigInteger input) {
        buffer = buffer.append(input.intValue());
        if (buffer.size() == 3) {
            final int x = buffer.get(0);
            final int y = buffer.get(1);
            if (x == -1 && y == 0) {
                score = buffer.get(2);
//                System.out.println("Score: " + score);
                if (!gameStarted) {
                    lastBallPosition = find(BALL);
                    gameStarted = true;
                    grid.print();
                    move();
                }
            } else if (buffer.get(2) == BALL.ordinal()) {
                if (gameStarted) {
//                    grid.print();
                    move();
                }
            }
            buffer = Array.empty();
        }
    }

    private Tile find(final TileId tileId) {
        return ImmutableTile.copyOf(grid.getTiles().find(t -> t.id() == tileId).get());
    }

    private void move() {
        final Tile ball = find(BALL);
        final Tile paddle = find(HORIZONTAL_PADDLE);

//        System.out.println(String.format("Last=%s, Ball=%s, Paddle=%s", lastBallPosition, ball, paddle));

        if (ball.x() == paddle.x() && Math.abs(ball.y() - paddle.y()) == 1) {
            joystick.neutral();
        } else if (ball.x() > lastBallPosition.x()) {
            joystick.tiltRight();
        } else {
            joystick.tiltLeft();
        }

        lastBallPosition = ImmutableTile.copyOf(ball);
    }
}
