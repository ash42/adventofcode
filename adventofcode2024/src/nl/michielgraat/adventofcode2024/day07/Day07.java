package nl.michielgraat.adventofcode2024.day07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day07 extends AocSolver {

    protected Day07(final String filename) {
        super(filename);
    }

    private Map<Long, List<Long>> getTestValueToOperandsMap(final List<String> input) {
        final Map<Long, List<Long>> testValueToOperands = new HashMap<>();
        for (final String line : input) {
            final String[] lineArr = line.split(": ");
            testValueToOperands.put(Long.valueOf(lineArr[0]),
                    Arrays.stream(lineArr[1].split(" ")).map(Long::valueOf).collect(Collectors.toList()));
        }
        return testValueToOperands;
    }

    private void getOperatorCombos(final int idx, final int length, String current, final List<String> combos, final boolean part1) {
        if (idx == length) {
            combos.add(current);
            return;
        } else {
            current += "+";
            getOperatorCombos(idx + 1, length, current, combos, part1);
            current = current.substring(0, current.length() - 1);
            current += "*";
            getOperatorCombos(idx + 1, length, current, combos, part1);
            if (!part1) {
                current = current.substring(0, current.length() - 1);
                current += "|";
                getOperatorCombos(idx + 1, length, current, combos, part1);
            }
        }
    }

    private boolean matchesTestValue(final long testValue, final List<Long> operands, final char[] operators, final boolean part1) {
        long total = operands.get(0);
        int j = 0;
        for (int i = 1; i < operands.size(); i++) {
            final long val = operands.get(i);
            final char operator = operators[j++];
            total = operator == '+' ? total + val
                    : part1 || operator == '*' ? total * val
                            : Long.parseLong(String.valueOf(total) + String.valueOf(val));
            if (total > testValue) {
                return false;
            }
        }
        return total == testValue;
    }

    private long getTotalCalibrationResult(final List<String> input, final boolean part1) {
        long total = 0;
        for (final Entry<Long, List<Long>> entry : getTestValueToOperandsMap(input).entrySet()) {
            final List<String> operators = new ArrayList<>();
            getOperatorCombos(0, entry.getValue().size() - 1, "", operators, part1);
            for (final String o : operators) {
                if (matchesTestValue(entry.getKey(), entry.getValue(), o.toCharArray(), part1)) {
                    total += entry.getKey();
                    break;
                }
            }
        }
        return total;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(getTotalCalibrationResult(input, false));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getTotalCalibrationResult(input, true));
    }

    public static void main(final String... args) {
        new Day07("day07.txt");
    }
}
