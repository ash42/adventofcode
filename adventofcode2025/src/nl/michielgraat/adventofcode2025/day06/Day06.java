package nl.michielgraat.adventofcode2025.day06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.michielgraat.adventofcode2025.AocSolver;

public class Day06 extends AocSolver {

    protected Day06(final String filename) {
        super(filename);
    }

    private List<List<Integer>> parseNumberLists(final List<String> input) {
        final List<List<Integer>> numbers = new ArrayList<>();
        for (int i = 0; i < input.size() - 1; i++) {
            numbers.add(Arrays.stream(input.get(i).trim().split("\\s+")).mapToInt(Integer::parseInt).boxed().toList());
        }
        return numbers;
    }

    private long calculateGrandTotalPart1(final String[] operators, final List<List<Integer>> numberLists) {
        long grandTotal = 0;
        for (int i = 0; i < operators.length; i++) {
            final boolean isAddition = operators[i].equals("+");
            long total = isAddition ? 0 : 1;
            for (final List<Integer> numbers : numberLists) {
                total = (isAddition) ? total + numbers.get(i) : total * numbers.get(i);
            }
            grandTotal += total;
        }
        return grandTotal;
    }

    private List<Integer> findColumnsWidths(final String operatorLine) {
        final List<Integer> result = new ArrayList<>();

        int currentCount = 1;
        for (int i = 1; i < operatorLine.length(); i++) {
            final char c = operatorLine.charAt(i);
            if (c == '+' || c == '*') {
                result.add(currentCount - 1);
                currentCount = 1;
            } else {
                currentCount++;
            }

        }
        result.add(currentCount);
        return result;
    }

    private long calculateGrandTotalPart2(final String operatorLine, final List<Integer> columnWidths, final List<String> input) {
        long grandTotal = 0;
        int startIdx = 0;
        for (final int columnWidth : columnWidths) {

            // Calculate the end index to prevent overshooting at the end of the line of
            // operators.
            int end = startIdx + columnWidth + 1;
            if (end > operatorLine.length()) {
                end = operatorLine.length();
            }
            final boolean isAddition = operatorLine.substring(startIdx, end).trim().equals("+");

            long columnTotal = isAddition ? 0 : 1;

            // Loop over all digits in every single position within this column, starting at
            // the end.
            for (int idxWithinColumn = startIdx + columnWidth - 1; idxWithinColumn >= startIdx; idxWithinColumn--) {
                final StringBuilder currentNumber = new StringBuilder();
                for (int nrLineIdx = 0; nrLineIdx < input.size() - 1; nrLineIdx++) {
                    // Get the character on the current line of number at column index, it does not
                    // matter if the character is a space, because we will filter that out with
                    // .trim()
                    currentNumber.append(input.get(nrLineIdx).charAt(idxWithinColumn));
                }
                final long val = Long.parseLong(currentNumber.toString().trim());
                columnTotal = isAddition ? columnTotal + val : columnTotal * val;
            }

            startIdx += columnWidth + 1;
            grandTotal += columnTotal;
        }
        return grandTotal;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final String operatorLine = input.get(input.size() - 1);
        final List<Integer> columnWidths = findColumnsWidths(operatorLine);
        return String.valueOf(calculateGrandTotalPart2(operatorLine, columnWidths, input));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final String[] operators = input.get(input.size() - 1).trim().split("\\s+");
        final List<List<Integer>> numberLists = parseNumberLists(input);
        return String.valueOf(calculateGrandTotalPart1(operators, numberLists));
    }

    public static void main(final String... args) {
        new Day06("day06.txt");
    }
}
