package nl.michielgraat.adventofcode2019.day04;

import java.util.List;

import nl.michielgraat.adventofcode2019.AocSolver;

public class Day04 extends AocSolver {

    protected Day04(final String filename) {
        super(filename);
    }

    private boolean twoAdjacentNoGroup(final int d1, final int d2, final int d3, final int d4, final int d5,
            final int d6) {
        return (d1 == d2 && d2 != d3) || (d1 != d2 && d2 == d3 && d3 != d4) || (d2 != d3 && d3 == d4 && d4 != d5)
                || (d3 != d4 && d4 == d5 && d5 != d6) || (d4 != d5 && d5 == d6);
    }

    private boolean twoAdjacent(final int d1, final int d2, final int d3, final int d4, final int d5, final int d6) {
        return d1 == d2 || d2 == d3 || d3 == d4 || d4 == d5 || d5 == d6;
    }

    private boolean notDecreasing(final int d1, final int d2, final int d3, final int d4, final int d5, final int d6) {
        return d1 <= d2 && d2 <= d3 && d3 <= d4 && d4 <= d5 && d5 <= d6;
    }

    private boolean meetsCriteria(final int number, final boolean part1) {
        final int d1 = (number / 100000) % 10;
        final int d2 = (number / 10000) % 10;
        final int d3 = (number / 1000) % 10;
        final int d4 = (number / 100) % 10;
        final int d5 = (number / 10) % 10;
        final int d6 = number % 10;

        return part1 ? twoAdjacent(d1, d2, d3, d4, d5, d6) && notDecreasing(d1, d2, d3, d4, d5, d6)
                : twoAdjacentNoGroup(d1, d2, d3, d4, d5, d6) && notDecreasing(d1, d2, d3, d4, d5, d6);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final int start = Integer.parseInt(input.get(0).split("-")[0]);
        final int end = Integer.parseInt(input.get(0).split("-")[1]);
        int total = 0;
        for (int number = start; number <= end; number++) {
            if (meetsCriteria(number, false))
                total++;
        }
        return String.valueOf(total);
    }

    @Override
    protected String runPart1(final List<String> input) {
        final int start = Integer.parseInt(input.get(0).split("-")[0]);
        final int end = Integer.parseInt(input.get(0).split("-")[1]);
        int total = 0;
        for (int number = start; number <= end; number++) {
            if (meetsCriteria(number, true))
                total++;
        }
        return String.valueOf(total);
    }

    public static void main(final String... args) {
        new Day04("day04.txt");
    }
}
