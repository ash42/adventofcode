package nl.michielgraat.adventofcode2025.day02;

import java.util.List;

import nl.michielgraat.adventofcode2025.AocSolver;

public class Day02 extends AocSolver {

    protected Day02(final String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final String[] sequences = input.get(0).split(",");
        long total = 0;
        for (final String sequence : sequences) {
            final String[] boundaries = sequence.split("-");

            for (long id = Long.parseLong(boundaries[0]); id <= Long.parseLong(boundaries[1]); id++) {
                final String sValue = String.valueOf(id);
                for (int i = 1; i < (sValue.length() / 2) + 1; i++) {
                    // Just cut off increasingly greater parts of this ID to use as a pattern. The
                    // longest pattern has obviously a length of half of the length of the ID.
                    final String pattern = sValue.substring(0, i);
                    boolean invalidId = true;

                    for (int j = 0; j < sValue.length(); j += pattern.length()) {
                        // "Walk" along the value and constantly cut off the next part of the value
                        // which could be equal to the pattern.
                        final int end = j + pattern.length();
                        final String partToCheck = (end < sValue.length()) ? sValue.substring(j, j + pattern.length())
                                : sValue.substring(j);
                        if (!partToCheck.equals(pattern)) {
                            // We found a part of the ID that is not equals to the pattern, so this is not
                            // an invalid ID (yet).
                            invalidId = false;
                            break;
                        }
                    }
                    if (invalidId) {
                        total += id;
                        break;
                    }
                }
            }
        }
        return String.valueOf(total);
    }

    @Override
    protected String runPart1(final List<String> input) {
        final String[] sequences = input.get(0).split(",");
        long total = 0;
        for (final String sequence : sequences) {
            final String[] boundaries = sequence.split("-");

            for (long id = Long.parseLong(boundaries[0]); id <= Long.parseLong(boundaries[1]); id++) {
                final int length = (int) (Math.log10(id) + 1);
                final long div = (long) Math.pow(10, length / 2);
                final long first = id / div;
                final long second = id % div;
                if (first == second) {
                    total += id;
                }
            }
        }
        return String.valueOf(total);
    }

    public static void main(final String... args) {
        new Day02("day02.txt");
    }
}
