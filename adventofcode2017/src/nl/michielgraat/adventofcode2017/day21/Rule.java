package nl.michielgraat.adventofcode2017.day21;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    List<char[][]> input;
    String output;

    public Rule(final String line) {
        this.input = getInputPerms(line);
        this.output = convertOutputPattern(line);
    }

    private char[][] convert(final String input) {
        final String[] rows = input.split("/");
        final char[][] result = new char[rows.length][rows.length];
        for (int j = 0; j < rows.length; j++) {
            final String row = rows[j];
            for (int i = 0; i < row.length(); i++) {
                result[j][i] = row.charAt(i);
            }
        }
        return result;
    }

    private char[][] convertInputPattern(final String line) {
        return convert(line.split(" => ")[0]);
    }

    private String convertOutputPattern(final String line) {
        return line.split(" => ")[1];
    }

    private char[][] copy(final char[][] input) {
        final char[][] output = new char[input.length][input.length];
        for (int i = 0; i < input.length; i++) {
            System.arraycopy(input[i], 0, output[i], 0, output[i].length);
        }
        return output;
    }

    private char[][] rotate(final char[][] input, int times) {
        times %= 4;
        final char[][] output = copy(input);
        for (int i = 0; i < times; i++) {
            final char[][] temp = copy(output);
            for (int y = 0; y < input.length; y++) {
                for (int x = 0; x < input.length; x++) {
                    output[input.length - 1 - y][x] = temp[x][y];
                }
            }
        }
        return output;
    }

    private char[][] flipVertically(final char[][] input) {
        final char[][] output = new char[input.length][input.length];
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input.length; x++) {
                output[input.length - 1 - x][y] = input[x][y];
            }
        }
        return output;
    }

    private char[][] flipHorizontally(final char[][] input) {
        final char[][] output = new char[input.length][input.length];
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input.length; x++) {
                output[x][input.length - 1 - y] = input[x][y];
            }
        }
        return output;
    }

    private List<char[][]> getInputPerms(final String line) {
        final List<char[][]> perms = new ArrayList<>();
        final char[][] pattern = convertInputPattern(line);
        perms.add(pattern);
        final char[][] r1 = rotate(pattern, 1);
        final char[][] r2 = rotate(pattern, 2);
        final char[][] r3 = rotate(pattern, 3);
        perms.add(r1);
        perms.add(r2);
        perms.add(r3);
        if (pattern.length > 2) {
            perms.add(flipHorizontally(pattern));
            perms.add(flipVertically(pattern));
            perms.add(flipHorizontally(r1));
            perms.add(flipVertically(r1));
        }
        return perms;
    }

    public boolean matches(final char[][] o) {
        if (o.length != input.get(0).length) {
            return false;
        }
        for (final char[][] perm : input) {
            boolean equals = true;
            for (int i = 0; i < o.length; i++) {
                for (int j = 0; j < o.length; j++) {
                    if (o[i][j] != perm[i][j]) {
                        equals = false;
                    }
                }
            }
            if (equals) {
                return true;
            }
        }
        return false;
    }

    public boolean matches(final String input) {
        return matches(convert(input));
    }
}
