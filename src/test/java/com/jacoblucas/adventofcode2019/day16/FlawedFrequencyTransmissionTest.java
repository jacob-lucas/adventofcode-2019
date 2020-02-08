package com.jacoblucas.adventofcode2019.day16;

import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FlawedFrequencyTransmissionTest {

    private FlawedFrequencyTransmission fft;

    @Before
    public void setUp() {
        fft = new FlawedFrequencyTransmission();
    }

    @Test
    public void testGetRepeatingPattern() {
        assertThat(fft.getRepeatingPattern(0), is(
                List.of(0, 1, 0, -1)));
        assertThat(fft.getRepeatingPattern(1), is(
                List.of(0, 0, 1, 1, 0, 0, -1, -1)));
        assertThat(fft.getRepeatingPattern(2), is(
                List.of(0, 0, 0, 1, 1, 1, 0, 0, 0, -1, -1, -1)));
        assertThat(fft.getRepeatingPattern(3), is(
                List.of(0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, -1, -1, -1, -1)));
    }

    @Test
    public void testOffset() {
        assertThat(fft.offset(1, List.of(0,1,0,-1)), is(List.of(1,0,-1,0)));
        assertThat(fft.offset(2, List.of(0,1,0,-1)), is(List.of(0,-1,0,1)));
    }

    @Test
    public void testPhase() {
        assertThat(fft.phase(List.of(1,2,3,4,5,6,7,8)), is(List.of(4,8,2,2,6,1,5,8)));
        assertThat(fft.phase(List.of(4,8,2,2,6,1,5,8)), is(List.of(3,4,0,4,0,4,3,8)));
        assertThat(fft.phase(List.of(3,4,0,4,0,4,3,8)), is(List.of(0,3,4,1,5,5,1,8)));
    }

    @Test
    public void testProcess() {
        assertThat(fft.process(List.of(1,2,3,4,5,6,7,8), 4), is(List.of(0,1,0,2,9,4,9,8)));
        assertThat(fft.process(List.of(8,0,8,7,1,2,2,4,5,8,5,9,1,4,5,4,6,6,1,9,0,8,3,2,1,8,6,4,5,5,9,5),  100).take(8), is(
                List.of(2,4,1,7,6,1,7,6)));
        assertThat(fft.process(List.of(1,9,6,1,7,8,0,4,2,0,7,2,0,2,2,0,9,1,4,4,9,1,6,0,4,4,1,8,9,9,1,7),  100).take(8), is(
                List.of(7,3,7,4,5,4,1,8)));
        assertThat(fft.process(List.of(6,9,3,1,7,1,6,3,4,9,2,9,4,8,6,0,6,3,3,5,9,9,5,9,2,4,3,1,9,8,7,3),  100).take(8), is(
                List.of(5,2,4,3,2,1,3,3)));
    }
}
