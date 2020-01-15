package com.jacoblucas.adventofcode2019.day11;

import com.jacoblucas.adventofcode2019.utils.coordinates.ImmutableCoordinate2D;
import com.jacoblucas.adventofcode2019.utils.intcode.IntcodeComputer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigInteger;

import static com.jacoblucas.adventofcode2019.day11.Colour.WHITE;
import static com.jacoblucas.adventofcode2019.day11.Direction.DOWN;
import static com.jacoblucas.adventofcode2019.day11.Direction.LEFT;
import static com.jacoblucas.adventofcode2019.day11.Direction.RIGHT;
import static com.jacoblucas.adventofcode2019.day11.Direction.UP;
import static com.jacoblucas.adventofcode2019.day11.EmergencyHullPaintingRobot.RobotState.MOVING;
import static com.jacoblucas.adventofcode2019.day11.EmergencyHullPaintingRobot.RobotState.PAINTING;
import static com.jacoblucas.adventofcode2019.day11.EmergencyHullPaintingRobot.RobotState.TURNING;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmergencyHullPaintingRobotTest {

    @Mock private IntcodeComputer mockComputer;

    @Test
    public void runExecutesComputer() {
        new EmergencyHullPaintingRobot("1101,1,1,5,99", mockComputer).run();
        verify(mockComputer, times(1)).execute();
        verify(mockComputer, times(1)).receiveInput(BigInteger.ZERO);
    }

    @Test
    public void testStateTransitions() {
        assertThat(TURNING.next(), is(MOVING));
        assertThat(MOVING.next(), is(PAINTING));
        assertThat(PAINTING.next(), is(TURNING));
    }

    @Test
    public void signalOnMove() {
        final EmergencyHullPaintingRobot robot = new EmergencyHullPaintingRobot("1101,1,1,5,99", mockComputer);
        robot.state = TURNING;

        robot.receive(BigInteger.ONE);

        verify(mockComputer, times(1)).receiveInput(any(BigInteger.class));
    }

    @Test
    public void testTurn() {
        final EmergencyHullPaintingRobot robot = new EmergencyHullPaintingRobot("1101,1,1,5,99", mockComputer);
        robot.state = TURNING;
        assertThat(robot.direction, is(UP));

        robot.receive(BigInteger.ZERO);
        assertThat(robot.direction, is(LEFT));
        robot.state = TURNING;

        robot.receive(BigInteger.ZERO);
        assertThat(robot.direction, is(DOWN));
        robot.state = TURNING;

        robot.receive(BigInteger.ZERO);
        assertThat(robot.direction, is(RIGHT));
        robot.state = TURNING;

        robot.receive(BigInteger.ZERO);
        assertThat(robot.direction, is(UP));
        robot.state = TURNING;

        robot.receive(BigInteger.ONE);
        assertThat(robot.direction, is(RIGHT));
        robot.state = TURNING;

        robot.receive(BigInteger.ONE);
        assertThat(robot.direction, is(DOWN));
        robot.state = TURNING;

        robot.receive(BigInteger.ONE);
        assertThat(robot.direction, is(LEFT));
        robot.state = TURNING;

        robot.receive(BigInteger.ONE);
        assertThat(robot.direction, is(UP));
    }

    @Test
    public void testMoveAfterTurn() {
        final EmergencyHullPaintingRobot robot = new EmergencyHullPaintingRobot("1101,1,1,5,99", mockComputer);
        robot.state = TURNING;
        robot.receive(BigInteger.ZERO);
        assertThat(robot.coordinate, is(ImmutableCoordinate2D.of(-1, 0)));

        robot.state = TURNING;
        robot.receive(BigInteger.ZERO);
        assertThat(robot.coordinate, is(ImmutableCoordinate2D.of(-1, -1)));

        robot.state = TURNING;
        robot.receive(BigInteger.ZERO);
        assertThat(robot.coordinate, is(ImmutableCoordinate2D.of(0, -1)));

        robot.state = TURNING;
        robot.receive(BigInteger.ZERO);
        assertThat(robot.coordinate, is(ImmutableCoordinate2D.of(0, 0)));
    }

    @Test
    public void testPaint() {
        final EmergencyHullPaintingRobot robot = new EmergencyHullPaintingRobot("1101,1,1,5,99", mockComputer);

        robot.receive(BigInteger.ONE);

        assertThat(robot.hull.get("(0,0)").get(), is(WHITE));
    }

}
