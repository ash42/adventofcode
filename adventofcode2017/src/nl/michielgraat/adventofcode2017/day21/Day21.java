package nl.michielgraat.adventofcode2017.day21;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day21 {

    private static final String FILENAME = "day21.txt";
    private final String initial = ".#./..#/###";

    private List<Rule> getRules(final List<String> lines) {
        final List<Rule> rules = new ArrayList<>();
        for (final String line : lines) {
            rules.add(new Rule(line));
        }
        return rules;
    }

    private long getNrOn(final String input) {
        return input.chars().filter(ch -> ch == '#').count();
    }

    private long solve(String input, final List<Rule> rules, final int iterations) {
        int width = 4;

        for (int iter = 0; iter < iterations; iter++) {
            final String[] elements = input.split("/");
            final List<String> squares = new ArrayList<>();
            final int size = elements[0].length();
            if (size % 2 == 0) {
                for (int i = 0; i < size; i += 2) {
                    for (int j = 0; j < size; j += 2) {
                        squares.add(elements[i].substring(j, j + 2) + "/" + elements[i + 1].substring(j, j + 2));
                    }
                }
                width = (width / 2) * 3;
            } else {
                for (int i = 0; i < size; i += 3) {
                    for (int j = 0; j < size; j += 3) {
                        squares.add(elements[i].substring(j, j + 3) + "/" + elements[i + 1].substring(j, j + 3) + "/"
                                + elements[i + 2].substring(j, j + 3));
                    }
                }
                width = (width / 3) * 4;
            }

            int i = 0;
            int j = 0;
            final String[] outputRows = new String[width];
            for (final String square : squares) {
                for (final Rule rule : rules) {
                    if (rule.matches(square)) {
                        final String pattern = rule.output;
                        final String[] patternRows = pattern.split("/");
                        final int squareWidth = width / patternRows.length;
                        for (int k = 0; k < patternRows.length; k++) {
                            if (outputRows[k + i] == null) {
                                outputRows[k + i] = patternRows[k];
                            } else {
                                outputRows[k + i] = outputRows[k + i] + patternRows[k];
                            }
                        }
                        j++;
                        if (squareWidth > 0 && j % squareWidth == 0) {
                            i += patternRows.length;
                        }
                    }
                }
            }
            for (int k = 0; k < width; k++) {
                input = (k == 0) ? outputRows[k] : input + "/" + outputRows[k];
            }
        }
        return getNrOn(input);
    }

    private long runPart2(final List<String> lines) {
        return solve(initial, getRules(lines), 18);
    }

    private long runPart1(final List<String> lines) {
        return solve(initial, getRules(lines), 5);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day21().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day21().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}