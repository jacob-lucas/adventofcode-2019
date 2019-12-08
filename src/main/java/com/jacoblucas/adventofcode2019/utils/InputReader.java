package com.jacoblucas.adventofcode2019.utils;

import io.vavr.collection.Stream;
import io.vavr.control.Try;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InputReader {
    private static final String INPUT_DIR = "src/main/resources/";

    public static Try<Stream<String>> read(final String filename) {
        return read(INPUT_DIR, filename);
    }

    static Try<Stream<String>> read(final String path, final String filename) {
        return Try.of(() -> Files.readAllLines(Paths.get(path + filename), StandardCharsets.UTF_8))
                .mapTry(Stream::ofAll);
    }
}
