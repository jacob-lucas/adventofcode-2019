package com.jacoblucas.adventofcode2019.day8;

import com.google.common.base.Splitter;
import io.vavr.collection.Array;
import io.vavr.collection.Stream;
import io.vavr.control.Try;

public class SpaceImageFormat {
    public static Array<Array<String>> layers(final String encoded, final int width, final int height) throws IllegalArgumentException {
        final int pixelCount = encoded.length();
        final int pixelsPerLayer = width * height;
        if (pixelCount % pixelsPerLayer != 0) {
            throw new IllegalArgumentException(String.format("Cannot extract layers from provided string - width[%d] height[%d] are not correct for encoded string length [%d]", width, height, pixelCount));
        }

        return Stream.ofAll(Splitter.fixedLength(pixelsPerLayer).split(encoded))
                .map(layer -> parseLayer(layer, width, height))
                .toArray();
    }

    static Array<String> parseLayer(final String encodedLayer, final int width, final int height) {
        final int pixelCount = encodedLayer.length();
        if (pixelCount / width != height) {
            throw new IllegalArgumentException(String.format("Cannot parse provided layer - width[%d] height[%d] are not correct for encoded layer length [%d]", width, height, pixelCount));
        }

        return Stream.ofAll(Splitter.fixedLength(width).split(encodedLayer)).toArray();
    }

    static int countDigit(final Array<String> layer, final int digit) {
        return layer
                .map(str -> str.chars().filter(ch -> ch == (char)(digit+'0')).count())
                .sum()
                .intValue();
    }

    static Array<String> decode(final String encoded, final int width, final int height) {
        final Array<Array<String>> layers = layers(encoded, width, height);

        Array<String> decoded = Array.empty();
        for (int h=0; h<height; h++) {
            final StringBuilder sb = new StringBuilder();
            for (int w=0; w<width; w++) {
                sb.append(pixelAt(layers, h, w).ordinal());
            }
            decoded = decoded.append(sb.toString());
        }
        return decoded;
    }

    static PixelColour pixelAt(final Array<Array<String>> layers, final int row, final int col) {
        final Array<Try<PixelColour>> flattenedAtRowCol = layers
                .map(layer -> layer.get(row))
                .map(r -> ""+r.charAt(col))
                .map(Integer::valueOf)
                .map(PixelColour::of);
        if (!flattenedAtRowCol.filter(Try::isFailure).isEmpty()) {
            throw new IllegalArgumentException(String.format("Unable to determine pixel colour at row[%d] col[%d] - unsupported pixel colour detected", row, col));
        }

        return flattenedAtRowCol
                .map(Try::get)
                .filter(pc -> pc != PixelColour.TRANSPARENT)
                .head();
    }
}
