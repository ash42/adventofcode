package nl.michielgraat.adventofcode2023.day01;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day01 extends AocSolver {

    protected Day01(String filename) {
        super(filename);
    }

    private int getFirstLastCombined(String line) {
        Matcher first = Pattern.compile("\\d").matcher(line);
        // \d matches a single digit.
        // (?![^\r\n\d]*\d) is a negative lookahead to check there are no more digits
        // in the string.
        Matcher last = Pattern.compile("\\d(?![^\\r\\n\\d]*\\d)").matcher(line);
        first.find();
        last.find();
        return Integer.parseInt(first.group() + last.group());
    }

    private String normalize(String line) {
        line = line.replaceAll("one", "o1e");
        line = line.replaceAll("two", "t2o");
        line = line.replaceAll("three", "t3e");
        line = line.replaceAll("four", "f4r");
        line = line.replaceAll("five", "f5e");
        line = line.replaceAll("six", "s6x");
        line = line.replaceAll("seven", "s7e");
        line = line.replaceAll("eight", "e8t");
        line = line.replaceAll("nine", "n9e");
        return line;
    }

    @Override
    protected String runPart2(final List<String> input) {
        int total = 0;
        for (String line : input) {
            total += getFirstLastCombined(normalize(line));
        }
        return String.valueOf(total);
    }

    @Override
    protected String runPart1(final List<String> input) {
        int total = 0;
        for (String line : input) {
            total += getFirstLastCombined(line);
        }
        return String.valueOf(total);
    }

    public static void main(String... args) {
        new Day01("day01.txt");
    }
}
