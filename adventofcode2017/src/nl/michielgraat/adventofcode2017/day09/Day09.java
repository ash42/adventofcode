package nl.michielgraat.adventofcode2017.day09;

import java.util.List;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day09 {

    private static final String FILENAME = "day09.txt";

    private static final char GROUP_START = '{';
    private static final char GROUP_END = '}';
    private static final char GARBAGE_START = '<';
    private static final char GARBAGE_END = '>';
    private static final char NEGATE = '!';

    private int parse(final String input, final boolean part2) {
        int score = 0;
        int total = 0;
        int garbage = 0;
        int negate = 0;
        int totalGarbage = 0;
        for (int i = 0; i < input.length(); i++) {
            final char c = input.charAt(i);
            if (c == GROUP_START && garbage == 0) {
                score++;
            } else if (c == GROUP_END && garbage == 0 && negate == 0) {
                total += score;
                score--;
            } else if (c == GARBAGE_START && negate == 0 && garbage == 0) {
                garbage = 1;
            } else if (c == GARBAGE_END && negate == 0 && garbage == 1) {
                garbage = 0;
            } else if (c == NEGATE) {
                if (negate == 1) {
                    negate = 0;
                } else {
                    negate = 1;
                }
            } else {
                if (negate == 0 && garbage == 1) {
                    totalGarbage++;
                }
                negate = 0;
            }
        }
        return part2 ? totalGarbage : total;
    }

    public int runPart2(final List<String> lines) {
        return parse(lines.get(0), true);
    }

    public int runPart1(final List<String> lines) {
        return parse(lines.get(0), false);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day09().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day09().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}