package com.jacoblucas.adventofcode2019;

import io.vavr.collection.Array;

import java.math.BigInteger;

public final class TestUtils {

    public static Array<BigInteger> bigIntegerArray(final Integer... ints) {
        return Array.of(ints).map(BigInteger::valueOf);
    }

}
