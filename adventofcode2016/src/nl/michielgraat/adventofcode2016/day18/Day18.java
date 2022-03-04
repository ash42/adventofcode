package nl.michielgraat.adventofcode2016.day18;

import java.util.List;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day18 {

    private static final String FILENAME = "day18.txt";
    private static final Character SAFE = '.';
    private static final Character TRAP = '^';

    private boolean isTrap(final int i, final String input) {
        final char left = (i >= 1) ? input.charAt(i - 1) : '.';
        final char center = input.charAt(i);
        final char right = (i < input.length() - 1) ? input.charAt(i + 1) : SAFE;
        return (left == TRAP && center == TRAP && right == SAFE)
                || (left == SAFE && center == TRAP && right == TRAP)
                || (left == TRAP && center == SAFE && right == SAFE)
                || (left == SAFE && center == SAFE && right == TRAP);
    }

    private String generateNextLine(final String input) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            sb.append(isTrap(i, input) ? TRAP : SAFE);
        }
        return sb.toString();
    }

    private long solve(String input, final int rows) {
        long nrSafe = input.chars().filter(c -> c == SAFE).count();
        int nrRows = 1;
        while (nrRows < rows) {
            input = generateNextLine(input);
            nrSafe += input.chars().filter(c -> c == SAFE).count();
            nrRows++;
        }
        return nrSafe;
    }

    public long runPart2(final List<String> lines, final int rows) {
        return solve(lines.get(0), rows);
    }

    public long runPart1(final List<String> lines, final int rows) {
        return solve(lines.get(0), rows);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day18().runPart1(lines, 40));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day18().runPart2(lines, 400000));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
