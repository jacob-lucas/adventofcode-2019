package com.jacoblucas.adventofcode2019.utils;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;
import io.vavr.control.Try;

import java.math.BigInteger;
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

    public static Map<BigInteger, BigInteger> loadInput(final String filename) {
        final List<BigInteger> ints = InputReader.read(filename)
                .map(str -> str.split(","))
                .flatMap(Stream::of)
                .map(BigInteger::new)
                .toList();
        Map<BigInteger, BigInteger> input = HashMap.empty();
        for (int i=0; i<ints.size(); i++) {
            input = input.put(BigInteger.valueOf(i), ints.get(i));
        }
        return input;
    }

    static Try<Stream<String>> readFile(final String path, final String filename) {
        return Try.of(() -> Files.readAllLines(Paths.get(path + filename), StandardCharsets.UTF_8))
                .mapTry(Stream::ofAll);
    }
}
