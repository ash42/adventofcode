package nl.michielgraat.adventofcode2024.day02;

import java.util.Arrays;
import java.util.List;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day02 extends AocSolver {

    protected Day02(final String filename) {
        super(filename);
    }

    private boolean isSafe(final int[] numbers) {
        boolean descending = false;
        for (int i = 1; i < numbers.length; i++) {
            final int prev = numbers[i - 1];
            final int cur = numbers[i];
            final int diff = Math.abs(cur - prev);
            if (i == 1) {
                descending = cur < prev;
            }
            if (descending && prev <= cur || !descending && prev >= cur) {
                return false;
            } else if (diff < 1 || diff > 3) {
                return false;
            }
        }
        return true;
    }

    private int[][] getReportsWithOneLevelRemoved(final int[] numbers) {
        final int[][] reports = new int[numbers.length][numbers.length - 1];
        for (int i = 0; i < numbers.length; i++) {
            final int[] report = new int[numbers.length - 1];
            int idx = 0;
            for (int j = 0; j < numbers.length; j++) {
                if (j != i) {
                    report[idx] = numbers[j];
                    idx++;
                }
            }
            reports[i] = report;
        }
        return reports;
    }

    private boolean isSafeWithMaxOneLevelRemoved(final int[] report) {
        if (isSafe(report)) {
            return true;
        } else {
            for (final int[] r : getReportsWithOneLevelRemoved(report)) {
                if (isSafe(r)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(input.stream()
                .filter(l -> isSafeWithMaxOneLevelRemoved(Arrays.stream(l.split(" ")).mapToInt(Integer::parseInt).toArray()))
                .count());
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(input.stream()
                .filter(l -> isSafe(Arrays.stream(l.split(" ")).mapToInt(Integer::parseInt).toArray())).count());
    }

    public static void main(final String... args) {
        new Day02("day02.txt");
    }
}
