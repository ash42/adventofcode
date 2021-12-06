package nl.michielgraat.adventofcode2021.day03;

import nl.michielgraat.adventofcode2021.FileReader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Day03 {
    private static final String FILENAME = "day03.txt";

    private int[] getNrOfOnes(final List<String> lines) {
        final int[] nrOf1s = new int[lines.get(0).length()];
        for (final String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                final int cur = (i < line.length() - 1) ? Integer.parseInt(line.substring(i, i + 1))
                        : Integer.parseInt(line.substring(i));
                if (cur == 1) {
                    nrOf1s[i]++;
                }
            }
        }
        return nrOf1s;
    }

    private String getGamma(final int[] nrOfOnes, final int total) {
        final StringBuilder gamma = new StringBuilder();
        for (int i = 0; i < nrOfOnes.length; i++) {
            final int nr1 = nrOfOnes[i];
            final int nr0 = total - nr1;
            if (nr0 <= nr1) {
                gamma.append("1");
            } else {
                gamma.append("0");
            }
        }
        return gamma.toString();
    }

    private String getEpsilon(final String gamma) {
        final StringBuilder epsilon = new StringBuilder();
        for (int i = 0; i < gamma.length(); i++) {
            epsilon.append((gamma.charAt(i) == '1') ? "0" : "1");
        }
        return epsilon.toString();
    }

    private List<String> getLinesToRemove(final List<String> orig, final int pos, final int expected) {
        final List<String> linesToRemove = new ArrayList<>();
        for (final String line : orig) {
            final int cur = (pos < line.length() - 1) ? Integer.parseInt(line.substring(pos, pos+1)) : Integer.parseInt(line.substring(pos));
            if (cur != expected) linesToRemove.add(line);
        }
        return linesToRemove;
    }

    private int getNrOfOnesAtPos(final List<String> lines, final int pos) {
        int total = 0;
        for (final String line : lines) {
            final int cur = (pos < line.length() - 1) ? Integer.parseInt(line.substring(pos, pos + 1))
                    : Integer.parseInt(line.substring(pos));
            if (cur == 1)
                total++;
        }
        return total;
    }

    private int getMostCommon(final List<String> lines, final int pos) {
        final int ones = getNrOfOnesAtPos(lines, pos);
        final int zeroes = lines.size() - ones;
        return (ones >= zeroes) ? 1 : 0;
    }

    private int runPart2(final List<String> lines) {
        final List<String> ox = new ArrayList<>(lines);
        final List<String> co = new ArrayList<>(lines);
        for (int i = 0; i < lines.get(0).length(); i++) {
            if (ox.size() > 1) {
                ox.removeAll(getLinesToRemove(ox, i, getMostCommon(ox, i)));
            }
            if (co.size() > 1) {
                co.removeAll(getLinesToRemove(co, i, 1 - getMostCommon(co, i)));
            }
        }
        return Integer.parseInt(ox.get(0), 2) * Integer.parseInt(co.get(0), 2);
    }

    private int runPart1(final List<String> lines) {
        final String gamma = getGamma(getNrOfOnes(lines), lines.size());
        final String epsilon = getEpsilon(gamma);
        return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day03().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day03().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

    }

}
