package nl.michielgraat.adventofcode2018.day22;

import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day22 {
    private static final String FILENAME = "day22.txt";

    private int runPart2(final List<String> lines) {
        final int depth = Integer.parseInt(lines.get(0).split(" ")[1]);
        final int targetX = Integer.parseInt(lines.get(1).split(" ")[1].split(",")[0]);
        final int targetY = Integer.parseInt(lines.get(1).split(" ")[1].split(",")[1]);
        final Cave cave = new Cave(depth, targetX, targetY);
        return cave.getShortestRoute();
    }

    private int runPart1(final List<String> lines) {
        final int depth = Integer.parseInt(lines.get(0).split(" ")[1]);
        final int targetX = Integer.parseInt(lines.get(1).split(" ")[1].split(",")[0]);
        final int targetY = Integer.parseInt(lines.get(1).split(" ")[1].split(",")[1]);
        final Cave cave = new Cave(depth, targetX, targetY);
        return cave.getRiskLevel();
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day22().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day22().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
