package nl.michielgraat.adventofcode2024.day03;

import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day03 extends AocSolver {

    protected Day03(final String filename) {
        super(filename);
    }

    private int multiply(String match) {
        match = match.substring(4);
        match = match.substring(0, match.length() - 1);
        final int val1 = Integer.parseInt(match.split(",")[0]);
        final int val2 = Integer.parseInt(match.split(",")[1]);
        return val1 * val2;
    }

    private int getTotal(final List<String> input) {
        int total = 0;
        for (final String line : input) {
            final Matcher matcher = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)").matcher(line);
            while (matcher.find()) {
                total += multiply(matcher.group());
            }
        }
        return total;
    }

    private String getMemory(final List<String> input) {
        final StringBuilder sb = new StringBuilder();
        for (final String line : input) {
            sb.append(line);
        }
        return sb.toString();
    }

    private TreeMap<Integer, Boolean> getSortedDoDontIndexes(final String memory) {
        final Matcher m = Pattern.compile("do\\(\\)").matcher(memory);
        final Matcher m2 = Pattern.compile("don\\'t\\(\\)").matcher(memory);

        final TreeMap<Integer, Boolean> indexes = new TreeMap<>();
        while (m.find()) {
            indexes.put(m.start(), true);
        }
        while (m2.find()) {
            indexes.put(m2.start(), false);
        }
        return indexes;
    }

    private int getTotalConditionally(final String memory) {
        final TreeMap<Integer, Boolean> multIndexes = getSortedDoDontIndexes(memory);
        int total = 0;
        final Matcher matcher = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)").matcher(memory);

        while (matcher.find()) {
            final int index = matcher.start();
            final boolean multiply = multIndexes.floorKey(index) != null ? multIndexes.get(multIndexes.floorKey(index))
                    : true;
            if (multiply) {
                total += multiply(matcher.group());
            }
        }
        return total;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final String memory = getMemory(input);
        return String.valueOf(getTotalConditionally(memory));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getTotal(input));
    }

    public static void main(final String... args) {
        new Day03("day03.txt");
    }
}
