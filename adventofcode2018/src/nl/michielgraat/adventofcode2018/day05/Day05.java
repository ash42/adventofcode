package nl.michielgraat.adventofcode2018.day05;

import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day05 {
    
    private static final String FILENAME = "day05.txt";

    private String react(String input, Character charToRemove) {
        String result = "";
        for (int i = 0; i < input.length(); i++) {
            char cur = input.charAt(i);
            char lcCur = Character.toLowerCase(cur);
            if (charToRemove == null || Character.toLowerCase(charToRemove.charValue()) != lcCur) {
                result += cur;
                if (result.length() >= 2) {
                    char prev = result.charAt(result.length() - 2);
                    char lcPrev = Character.toLowerCase(prev);
                    if (prev != cur && lcPrev == lcCur) {
                        result = result.substring(0, result.length() - 2);
                    }
                }
            }
        }
        return result;
    }

    public int runPart2(List<String> lines) {
        int min = Integer.MAX_VALUE;
        for (char c = 'a'; c <= 'z'; c++) {
            int length = react(lines.get(0), c).length();
            if (length < min) {
                min = length;
            }
        }
        return min;
    }

    public int runPart1(List<String> lines) {
        return react(lines.get(0), null).length();
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day05().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day05().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}