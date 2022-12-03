package nl.michielgraat.adventofcode2022.day03;

import java.util.List;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day03 extends AocSolver {

    protected Day03(final String filename) {
        super(filename);
    }

    private int getScore(final char duplicate) {
        return Character.isUpperCase(duplicate) ? duplicate - 38 : duplicate - 96;
    }

    private char findDuplicate(final String first, final String second, final String third) {
        for (final char current : first.toCharArray()) {
            if (second.indexOf(current) != -1 && (third == null || third.indexOf(current) != -1)) {
                return current;
            }
        }
        throw new IllegalArgumentException("No shared item type found for input");
    }

    @Override
    protected String runPart2(final List<String> input) {
        int total = 0;
        for (int i = 0; i < input.size(); i += 3) {
            total += getScore(findDuplicate(input.get(i), input.get(i + 1), input.get(i + 2)));
        }
        return String.valueOf(total);
    }

    @Override
    protected String runPart1(final List<String> input) {
        int total = 0;
        for (final String line : input) {
            final String firstPart = line.substring(0, line.length() / 2);
            final String secondPart = line.substring(line.length() / 2);
            total += getScore(findDuplicate(firstPart, secondPart, null));
        }
        return String.valueOf(total);
    }

    public static void main(final String... args) {
        new Day03("day03.txt");
    }
}
