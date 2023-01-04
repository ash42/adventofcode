package nl.michielgraat.adventofcode2019.day08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import nl.michielgraat.adventofcode2019.AocSolver;

public class Day08 extends AocSolver {

    private static final int WIDTH = 25;
    private static final int HEIGHT = 6;
    private static final int IMAGE_SIZE = WIDTH * HEIGHT;
    private static final int TRANSPARENT = 2;

    protected Day08(final String filename) {
        super(filename);
    }

    private List<Layer> getLayers(final List<String> input) {
        final List<Integer> layers = input.get(0).chars().map(Character::getNumericValue).boxed().toList();
        final List<Layer> result = new ArrayList<>();
        for (int layer = 0; layer < layers.size(); layer += IMAGE_SIZE) {
            result.add(new Layer(layers.stream().skip(layer).limit(IMAGE_SIZE).toList(), WIDTH, HEIGHT));
        }
        return result;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Layer> layers = getLayers(input);
        final List<Integer> pixels = new ArrayList<>(Collections.nCopies(IMAGE_SIZE, TRANSPARENT));
        for (int pixelNr = 0; pixelNr < IMAGE_SIZE; pixelNr++) {
            final int color = TRANSPARENT;
            pixels.set(pixelNr, color);
            for (final Layer layer : layers) {
                if (layer.pixels().get(pixelNr) != TRANSPARENT) {
                    pixels.set(pixelNr, layer.pixels().get(pixelNr));
                    break;
                }
            }
        }
        final Layer image = new Layer(pixels, WIDTH, HEIGHT);
        return image.getImage();
    }

    @Override
    protected String runPart1(final List<String> input) {
        final Layer minLayer = getLayers(input).stream().reduce((a, b) -> a.nr0s() < b.nr0s() ? a : b)
                .orElseThrow(NoSuchElementException::new);
        return String.valueOf(minLayer.nr1s() * minLayer.nr2s());
    }

    public static void main(final String... args) {
        new Day08("day08.txt");
    }
}
