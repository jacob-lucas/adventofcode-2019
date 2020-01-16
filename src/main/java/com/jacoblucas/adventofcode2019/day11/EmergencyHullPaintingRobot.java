package com.jacoblucas.adventofcode2019.day11;

import com.jacoblucas.adventofcode2019.utils.coordinates.Coordinates2D;
import com.jacoblucas.adventofcode2019.utils.coordinates.ImmutableCoordinates2D;
import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;
import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputerOutputReceiver;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;

import java.math.BigInteger;

import static com.jacoblucas.adventofcode2019.day11.EmergencyHullPaintingRobot.RobotState.PAINTING;
import static com.jacoblucas.adventofcode2019.day11.EmergencyHullPaintingRobot.RobotState.TURNING;

public class EmergencyHullPaintingRobot implements IntcodeComputerOutputReceiver {

    enum RobotState {
        TURNING,
        MOVING,
        PAINTING;

        RobotState next() {
            final int ordinal = ordinal();
            final int numStates = values().length;
            return values()[(ordinal + 1) % numStates];
        }
    }

    private final IntcodeComputer computer;

    Direction direction;

    RobotState state;

    Coordinates2D coordinate;

    Map<String, Colour> hull;

    public EmergencyHullPaintingRobot(final String program, final IntcodeComputer computer) {
        this.state = RobotState.PAINTING;
        this.computer = computer;
        this.direction = Direction.UP;

        this.coordinate = ImmutableCoordinates2D.of(0, 0);
        this.hull = HashMap.of(coordinate.toString(), Colour.BLACK);

        computer.subscribe(this);
        computer.feed(program);
    }

    @Override
    public String id() {
        return "EHPR";
    }

    public Map<String, Colour> run(final Colour colour) {
        signal(colour);
        computer.execute();
        return hull;
    }

    // step 1 - signal current panel colour
    private void signal(final Colour colour) {
//        System.out.println(String.format("-----\n[SIGNAL] %s @ %s", colour.name(), coordinate.toString()));
        final BigInteger input = BigInteger.valueOf(colour.ordinal());
        computer.receiveInput(input);
    }

    @Override
    public void receive(final BigInteger input) {
//        System.out.println(String.format("[state=%s RECEIVE] %d", state.name(), input.intValue()));
        if (state == TURNING) {
            turn(input.equals(BigInteger.ZERO) ? Direction.LEFT : Direction.RIGHT);
        } else if (state == PAINTING) {
            final int colour = input.intValue();
            paint(Colour.values()[colour]);
        }
    }

    // step 2 - paint current panel
    private void paint(final Colour colour) {
        // save colour against coordinate
//        System.out.println(String.format("[PAINT] %s @ %s", colour.name(), coordinate.toString()));
        hull = hull.put(coordinate.toString(), colour);
        state = state.next();
    }

    // step 3 - turn
    private void turn(final Direction dir) {
        // turn in the direction relative to current orientation
        direction = dir == Direction.LEFT ? direction.left() : direction.right();
//        System.out.println(String.format("[TURN] %s", direction.name()));
        state = state.next();
        move(1);
        state = state.next();
    }

    // step 4 - move to new panel
    private void move(int n) {
        // move forward n panels
        String current = coordinate.toString();
        if (direction == Direction.LEFT) {
            coordinate = ImmutableCoordinates2D.copyOf(this.coordinate).withX(this.coordinate.x() - n);
        } else if (direction == Direction.RIGHT) {
            coordinate = ImmutableCoordinates2D.copyOf(coordinate).withX(coordinate.x() + n);
        } else if (direction == Direction.UP) {
            coordinate = ImmutableCoordinates2D.copyOf(coordinate).withY(coordinate.y() + n);
        } else {
            coordinate = ImmutableCoordinates2D.copyOf(coordinate).withY(coordinate.y() - n);
        }

//        System.out.println(String.format("[MOVE] %s -> %s", current, coordinate.toString()));
        final Colour discovered = hull.getOrElse(coordinate.toString(), Colour.BLACK);
        hull = hull.put(coordinate.toString(), discovered);
        signal(discovered);
    }
}
