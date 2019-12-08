package com.jacoblucas.adventofcode2019.utils;

import io.vavr.collection.Stream;
import io.vavr.control.Try;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Failure;
import static io.vavr.Patterns.$Success;

public class InputReader {
    private static final String INPUT_DIR = "src/main/resources/";

    public static Stream<String> read(final String filename) {
        final Try<Stream<String>> input = readFile(INPUT_DIR, filename);
        return Match(input).of(
                Case($Success($()), Function.identity()),
                Case($Failure($()), ex -> {
                    System.out.println("Failed to read input");
                    ex.printStackTrace();
                    return Stream.empty();
                })
        );
    }

    static Try<Stream<String>> readFile(final String path, final String filename) {
        return Try.of(() -> Files.readAllLines(Paths.get(path + filename), StandardCharsets.UTF_8))
                .mapTry(Stream::ofAll);
    }
}
