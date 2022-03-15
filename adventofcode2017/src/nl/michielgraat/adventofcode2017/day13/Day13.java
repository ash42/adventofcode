package nl.michielgraat.adventofcode2017.day13;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day13 {
    private static final String FILENAME = "day13.txt";

    private List<Layer> getLayers(final List<String> lines) {
        final List<Layer> layers = new ArrayList<>();
        final int size = Integer.parseInt(lines.get(lines.size() - 1).split(": ")[0]) + 1;
        for (int i = 0; i < size; i++) {
            layers.add(new Layer(i, 0));
        }
        for (final String line : lines) {
            final String[] parts = line.split(": ");
            final int name = Integer.parseInt(parts[0]);
            final int depth = Integer.parseInt(parts[1]);
            final Layer l = new Layer(name, depth);
            layers.set(layers.indexOf(l), l);
        }
        return layers;
    }

    private boolean caught(final int start, final List<Layer> layers) {
        for (int pos = 0; pos < layers.size(); pos++) {
            final Layer l = layers.get(pos);
            if (l.isAtTop(pos + start)) {
                return true;
            }
        }
        return false;
    }

    private int getSeverity(final int start, final List<Layer> layers) {
        int total = 0;
        for (int pos = 0; pos < layers.size(); pos++) {
            final Layer l = layers.get(pos);
            if (l.isAtTop(pos + start)) {
                total += l.getDepth() * l.getName();
            }
        }
        return total;
    }

    private int runPart2(final List<String> lines) {
        final List<Layer> layers = getLayers(lines);
        int ps = 0;
        while (caught(ps, layers)) {
            ps++;
        }
        return ps;
    }

    private int runPart1(final List<String> lines) {
        final List<Layer> layers = getLayers(lines);
        return getSeverity(0, layers);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day13().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day13().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
