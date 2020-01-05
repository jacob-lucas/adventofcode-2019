package com.jacoblucas.adventofcode2019.day8;

import io.vavr.collection.Array;
import io.vavr.collection.List;
import org.junit.Test;

import static com.jacoblucas.adventofcode2019.day8.PixelColour.BLACK;
import static com.jacoblucas.adventofcode2019.day8.PixelColour.WHITE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SpaceImageFormatTest {

    @Test(expected = IllegalArgumentException.class)
    public void testImageSizeValidation() {
        SpaceImageFormat.layers("1234", 3, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLayerSizeValidation() {
        SpaceImageFormat.parseLayer("1234", 3, 2);
    }

    @Test
    public void testParseLayer() {
        final Array<String> layer = SpaceImageFormat.parseLayer("123456", 3, 2);
        assertThat(layer, is(List.of("123", "456")));
    }

    @Test
    public void testLayers() {
        final Array<Array<String>> layers = SpaceImageFormat.layers("123456789012", 3, 2);
        assertThat(layers.get(0), is(List.of("123", "456")));
        assertThat(layers.get(1), is(List.of("789", "012")));
    }

    @Test
    public void testCountZeroes() {
        final Array<Array<String>> layers = SpaceImageFormat.layers("123456789012004303", 3, 2);
        assertThat(SpaceImageFormat.countDigit(layers.get(0), 0), is(0));
        assertThat(SpaceImageFormat.countDigit(layers.get(1), 0), is(1));
        assertThat(SpaceImageFormat.countDigit(layers.get(2), 0), is(3));
    }

    @Test
    public void testPixelAt() {
        final Array<Array<String>> layers = SpaceImageFormat.layers("0222112222120000", 2, 2);

        final PixelColour pixelColour1 = SpaceImageFormat.pixelAt(layers, 0, 0);
        assertThat(pixelColour1, is(BLACK));
        final PixelColour pixelColour2 = SpaceImageFormat.pixelAt(layers, 0, 1);
        assertThat(pixelColour2, is(WHITE));
        final PixelColour pixelColour3 = SpaceImageFormat.pixelAt(layers, 1, 0);
        assertThat(pixelColour3, is(WHITE));
        final PixelColour pixelColour4 = SpaceImageFormat.pixelAt(layers, 1, 1);
        assertThat(pixelColour4, is(BLACK));
    }

    @Test
    public void testDecode() {
        final Array<String> decoded = SpaceImageFormat.decode("0222112222120000", 2, 2);
        decoded.forEach(System.out::println);
    }

}
