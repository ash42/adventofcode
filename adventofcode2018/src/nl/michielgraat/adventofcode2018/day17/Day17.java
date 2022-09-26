package nl.michielgraat.adventofcode2018.day17;

import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day17 {
    private static final String FILENAME = "day17test.txt";

    private String runPart2(List<String> lines) {
        return null;
    }

    private String runPart1(List<String> lines) {
        return null;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day17().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day17().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}