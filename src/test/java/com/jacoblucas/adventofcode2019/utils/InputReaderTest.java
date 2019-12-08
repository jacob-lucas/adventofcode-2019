package com.jacoblucas.adventofcode2019.utils;

import io.vavr.collection.Stream;
import io.vavr.control.Try;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InputReaderTest {

    private static final String TEST_INPUT_DIR = "src/test/resources/";

    @Test
    public void failureOnFileNotFound() {
        final Try<Stream<String>> lines = InputReader.read(TEST_INPUT_DIR, "does_not_exist.txt");
        assertThat(lines.isFailure(), is(true));
    }

    @Test
    public void returnsPopulatedStreamOnReadableFile() {
        final Try<Stream<String>> lines = InputReader.read(TEST_INPUT_DIR,"test-input.txt");
        assertThat(lines.isSuccess(), is(true));
        assertThat(lines.get(), is(Stream.of("1","2","3")));
    }

}
