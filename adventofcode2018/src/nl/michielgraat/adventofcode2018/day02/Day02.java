package nl.michielgraat.adventofcode2018.day02;

import java.util.List;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day02 {

    private static final String FILENAME = "day02.txt";

    private int levenshtein(final String s1, final String s2) {
        int distance = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    private String getSame(final String s1, final String s2) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                sb.append(s1.charAt(i));
            }
        }
        return sb.toString();
    }

    public String runPart2(final List<String> lines) {
        for (int i = 0; i < lines.size() - 1; i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                final String s1 = lines.get(i);
                final String s2 = lines.get(j);
                if (levenshtein(s1, s2) == 1) {
                    return getSame(s1, s2);
                }
            }
        }
        return "";
    }

    public int runPart1(final List<String> lines) {
        int two = 0;
        int three = 0;
        for (final String line : lines) {
            final List<Character> chars = line.chars().distinct().mapToObj(c -> (char) c).collect(Collectors.toList());
            boolean foundTwo = false;
            boolean foundThree = false;
            for (final Character c : chars) {
                final long count = line.chars().filter(i -> i == c).count();
                if (count == 2)
                    foundTwo = true;
                if (count == 3)
                    foundThree = true;
            }
            if (foundTwo)
                two++;
            if (foundThree)
                three++;

        }
        System.out.println(two + " " + three);
        return two * three;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day02().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day02().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
