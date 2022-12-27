package nl.michielgraat.adventofcode2022.day25;

import java.util.List;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day25 extends AocSolver {

    protected Day25(final String filename) {
        super(filename);
    }

    private long parseSNAFU(final String input) {
        final char[] reverse = new StringBuilder(input).reverse().toString().toCharArray();
        long total = 0;
        for (int pwr = 0; pwr < reverse.length; pwr++) {
            final char cur = reverse[pwr];
            final long pow = (long) Math.pow(5, pwr);
            if (cur == '-') {
                total += -pow;
            } else if (cur == '=') {
                total += -2 * pow;
            } else {
                total += Character.getNumericValue(cur) * pow;
            }
        }
        return total;
    }

    private String convertToSNAFU(final long input) {
        final char[] SNAFUDigits = { '0', '1', '2', '=', '-' };
        if (input == 0) {
            return "";
        }
        final int remainder = (int) (input % 5);
        final StringBuilder sb = new StringBuilder();
        final char digit = SNAFUDigits[remainder];
        sb.append(convertToSNAFU((input + 2) / 5));
        sb.append(digit);
        return sb.toString();
    }

    @Override
    protected String runPart2(final List<String> input) {
        return "no part 2";
    }

    @Override
    protected String runPart1(final List<String> input) {
        long total = 0;
        for (final String line : input) {
            total += parseSNAFU(line);
        }
        return String.valueOf(convertToSNAFU(total));
    }

    public static void main(final String... args) {
        new Day25("day25.txt");
    }
}
