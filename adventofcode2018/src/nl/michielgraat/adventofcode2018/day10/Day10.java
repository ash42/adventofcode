package nl.michielgraat.adventofcode2018.day10;

import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day10 {

    private static final String FILENAME = "day10.txt";

    private int runPart2(final List<String> lines) {
        final Sky sky = new Sky(lines);
        return sky.moveUntilMessageFound();
    }

    private String runPart1(final List<String> lines) {
        final Sky sky = new Sky(lines);
        return sky.getMessage();
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day10().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day10().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
