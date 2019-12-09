package com.jacoblucas.adventofcode2019.day2;

import io.vavr.collection.Array;
import io.vavr.collection.Stream;
import org.junit.Test;

import static com.jacoblucas.adventofcode2019.day2.Intcode.CONTINUE;
import static com.jacoblucas.adventofcode2019.day2.Intcode.HALT;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IntcodeTest {

    @Test
    public void testAdd() {
        final Intcode intcode = new Intcode(Stream.of(1,9,10,3,2,3,11,0,99,30,40,50));
        final int result = intcode.handle(0);
        assertThat(result, is(CONTINUE));
        assertThat(intcode.underlying(), is(Stream.of(1,9,10,70,2,3,11,0,99,30,40,50).toArray()));
    }

    @Test
    public void testMultiply() {
        final Intcode intcode = new Intcode(Stream.of(1,9,10,70,2,3,11,0,99,30,40,50));
        final int result = intcode.handle(4);
        assertThat(result, is(CONTINUE));
        assertThat(intcode.underlying(), is(Stream.of(3500,9,10,70,2,3,11,0,99,30,40,50).toArray()));
    }

    @Test
    public void testHalt() {
        final Stream<Integer> input = Stream.of(1, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50);
        final Intcode intcode = new Intcode(input);
        final int result = intcode.handle(8);
        assertThat(result, is(HALT));
        assertThat(intcode.underlying(), is(input));
    }

    @Test
    public void testHandlesIndexOutOfBounds() {
        final Stream<Integer> input = Stream.of(1, 999, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50);
        final Intcode intcode = new Intcode(input);
        final int result = intcode.handle(0);
        assertThat(result, is(HALT));
        assertThat(intcode.underlying(), is(input));
    }

    @Test
    public void testExamples() {
        assertThat(new Intcode(Stream.of(1,0,0,0,99)).execute().underlying(), is(Array.of(2,0,0,0,99)));
        assertThat(new Intcode(Stream.of(2,3,0,3,99)).execute().underlying(), is(Array.of(2,3,0,6,99)));
        assertThat(new Intcode(Stream.of(2,4,4,5,99,0)).execute().underlying(), is(Array.of(2,4,4,5,99,9801)));
        assertThat(new Intcode(Stream.of(1,1,1,4,99,5,6,0,99)).execute().underlying(), is(Array.of(30,1,1,4,2,5,6,0,99)));
        assertThat(new Intcode(Stream.of(1,9,10,3,2,3,11,0,99,30,40,50)).execute().underlying(), is(Array.of(3500,9,10,70,2,3,11,0,99,30,40,50)));
    }

}
