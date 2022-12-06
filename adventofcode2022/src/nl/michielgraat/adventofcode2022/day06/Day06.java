package nl.michielgraat.adventofcode2022.day06;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day06 extends AocSolver {

    protected Day06(final String filename) {
        super(filename);
    }

    private int getMarkerPosition(final String buffer, final boolean part1) {
        final int markerSize = part1 ? 4 : 14;
        for (int i = 0; i < buffer.length() - markerSize; i++) {
            final Set<Character> letters = new HashSet<>();
            for (int j = i; j < i + markerSize; j++) {
                letters.add(buffer.charAt(j));
            }
            if (letters.size() == markerSize) {
                return i + markerSize;
            }
        }
        throw new IllegalArgumentException("No marker found");
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(getMarkerPosition(input.get(0), false));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getMarkerPosition(input.get(0), true));
    }

    public static void main(final String... args) {
        new Day06("day06.txt");
    }
}
