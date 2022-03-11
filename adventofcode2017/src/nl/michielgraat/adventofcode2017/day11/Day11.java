package nl.michielgraat.adventofcode2017.day11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day11 {

    private static final String FILENAME = "day11.txt";

    public int getDistance(final HexCoordinate i, final int q, final int r, final int s) {
        return (Math.abs(i.q - q) + Math.abs(i.r - r) + Math.abs(i.s - s)) / 2;
    }

    private int getMax(final String input, final HexCoordinate start) {
        final String[] steps = input.split(",");
        int q = start.q;
        int r = start.r;
        int s = start.s;
        int max = 0;
        for (final String step : steps) {
            switch (step) {
                case "nw":
                    q--;
                    s++;
                    break;
                case "n":
                    r--;
                    s++;
                    break;
                case "ne":
                    q++;
                    r--;
                    break;
                case "sw":
                    q--;
                    r++;
                    break;
                case "s":
                    r++;
                    s--;
                    break;
                case "se":
                    q++;
                    s--;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown direction '" + step + "'");
            }
            final int distance = getDistance(start, q, r, s);
            if (distance > max) {
                max = distance;
            }
        }
        return max;
    }

    private HexCoordinate getChildPos(final String input) {
        final String[] steps = input.split(",");
        int q = 0;
        int r = 0;
        int s = 0;
        for (final String step : steps) {
            switch (step) {
                case "nw":
                    q--;
                    s++;
                    break;
                case "n":
                    r--;
                    s++;
                    break;
                case "ne":
                    q++;
                    r--;
                    break;
                case "sw":
                    q--;
                    r++;
                    break;
                case "s":
                    r++;
                    s--;
                    break;
                case "se":
                    q++;
                    s--;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown direction '" + step + "'");
            }
        }
        return new HexCoordinate(q, r, s);
    }

    public int runPart2(final List<String> lines) {
        return getMax(lines.get(0), new HexCoordinate(0, 0, 0));
    }

    public int runPart1(final List<String> lines) {
        final HexCoordinate end = getChildPos(lines.get(0));
        return getDistance(end, 0, 0, 0);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day11().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day11().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
