package com.jacoblucas.adventofcode2019.day8;

import com.google.common.base.Splitter;
import io.vavr.collection.Array;
import io.vavr.collection.Stream;

public class SpaceImageFormat {
    public static Array<Array<String>> decode(final String encoded, final int width, final int height) throws IllegalArgumentException {
        final int pixelCount = encoded.length();
        final int pixelsPerLayer = width * height;
        if (pixelCount % pixelsPerLayer != 0) {
            throw new IllegalArgumentException(String.format("Cannot decode provided string - width[%d] height[%d] are not correct for encoded string length [%d]", width, height, pixelCount));
        }

        return Stream.ofAll(Splitter.fixedLength(pixelsPerLayer).split(encoded))
                .map(layer -> decodeLayer(layer, width, height))
                .toArray();
    }

    static Array<String> decodeLayer(final String encodedLayer, final int width, final int height) {
        final int pixelCount = encodedLayer.length();
        if (pixelCount / width != height) {
            throw new IllegalArgumentException(String.format("Cannot decode provided layer - width[%d] height[%d] are not correct for encoded layer length [%d]", width, height, pixelCount));
        }

        return Stream.ofAll(Splitter.fixedLength(width).split(encodedLayer)).toArray();
    }

    static int countDigit(final Array<String> layer, final int digit) {
        return layer
                .map(str -> str.chars().filter(ch -> ch == (char)(digit+'0')).count())
                .sum()
                .intValue();
    }
}
