package com.jacoblucas.adventofcode2019.day8;

import io.vavr.collection.Array;
import io.vavr.collection.List;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SpaceImageFormatTest {

    @Test(expected = IllegalArgumentException.class)
    public void testImageSizeValidation() {
        SpaceImageFormat.decode("1234", 3, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLayerSizeValidation() {
        SpaceImageFormat.decodeLayer("1234", 3, 2);
    }

    @Test
    public void testDecodeLayer() {
        final Array<String> layer = SpaceImageFormat.decodeLayer("123456", 3, 2);
        assertThat(layer, is(List.of("123", "456")));
    }

    @Test
    public void testDecode() {
        final Array<Array<String>> layers = SpaceImageFormat.decode("123456789012", 3, 2);
        assertThat(layers.get(0), is(List.of("123", "456")));
        assertThat(layers.get(1), is(List.of("789", "012")));
    }

    @Test
    public void testCountZeroes() {
        final Array<Array<String>> layers = SpaceImageFormat.decode("123456789012004303", 3, 2);
        assertThat(SpaceImageFormat.countDigit(layers.get(0), 0), is(0));
        assertThat(SpaceImageFormat.countDigit(layers.get(1), 0), is(1));
        assertThat(SpaceImageFormat.countDigit(layers.get(2), 0), is(3));
    }

}
