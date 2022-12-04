package nl.michielgraat.adventofcode2022.day04;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day04 extends AocSolver {

    protected Day04(final String filename) {
        super(filename);
    }

    private int getNextInteger(final Matcher m) {
        m.find();
        return Integer.parseInt(m.group());
    }

    private int getResult(final List<String> input, final boolean part1) {
        int total = 0;
        final Pattern p = Pattern.compile("\\d+");
        for (final String line : input) {
            final Matcher m = p.matcher(line);
            final int first = getNextInteger(m);
            final int second = getNextInteger(m);
            final int third = getNextInteger(m);
            final int fourth = getNextInteger(m);
            if (part1) {
                if ((first >= third && second <= fourth) || (third >= first && fourth <= second)) {
                    total++;
                }
            } else {
                if (first <= fourth && third <= second) {
                    total++;
                }
            }
        }
        return total;
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
        new Day04("day04.txt");
    }
}
