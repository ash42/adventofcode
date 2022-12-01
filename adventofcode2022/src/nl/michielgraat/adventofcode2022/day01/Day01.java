package nl.michielgraat.adventofcode2022.day01;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day01 extends AocSolver {

    protected Day01(final String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final Map<Integer, Integer> elfToCal = new HashMap<>();
        int currentElf = 1;
        int currentTotal = 0;
        for (int i = 0; i < input.size(); i++) {
            final String line = input.get(i);
            if (line.isEmpty() || i == input.size() - 1) {
                elfToCal.put(currentElf, currentTotal);
                currentElf++;
                currentTotal = 0;
            } else {
                currentTotal += Integer.parseInt(line);
            }
        }
        return String.valueOf(elfToCal.values().stream().sorted(Comparator.reverseOrder()).limit(3)
                .mapToInt(Integer::intValue).sum());

    }

    @Override
    protected String runPart1(final List<String> input) {
        int max = 0;
        int currentTotal = 0;
        for (int i = 0; i < input.size(); i++) {
            final String line = input.get(i);
            if (line.isEmpty() || i == input.size() - 1) {
                if (currentTotal > max) {
                    max = currentTotal;
                }
                currentTotal = 0;
            } else {
                currentTotal += Integer.parseInt(line);
            }
        }
        return String.valueOf(max);
    }

    public static void main(final String... args) {
        new Day01("day01.txt");
    }
}
