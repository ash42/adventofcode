package nl.michielgraat.adventofcode2022.day01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day01 extends AocSolver {

    protected Day01(final String filename) {
        super(filename);
    }

    private int getResult(final List<String> input, final boolean part1) {
        final List<Integer> cals = new ArrayList<>();
        int currentTotal = 0;
        for (int i = 0; i < input.size(); i++) {
            final String line = input.get(i);
            if (line.isEmpty() || i == input.size() - 1) {
                cals.add(currentTotal);
                currentTotal = 0;
            } else {
                currentTotal += Integer.parseInt(line);
            }
        }
        Collections.sort(cals, Comparator.reverseOrder());
        return cals.stream().mapToInt(Integer::intValue).limit(part1 ? 1 : 3).sum();
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(getResult(input, false));

    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getResult(input, true));
    }

    public static void main(final String... args) {
        new Day01("day01.txt");
    }
}
