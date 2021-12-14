package nl.michielgraat.adventofcode2021.day14;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day14 {

    private static final String FILENAME = "day14.txt";

    private Map<String, String> getInstructions(final List<String> lines) {
        final Map<String, String> instructions = new HashMap<>();
        for (int i = 2; i < lines.size(); i++) {
            final String[] line = lines.get(i).split(" -> ");
            instructions.put(line[0], line[1]);
        }
        return instructions;
    }

    private String buildPolymer(String template, final int steps, final Map<String, String> instructions) {
        for (int i = 1; i <= steps; i++) {
            final StringBuilder result = new StringBuilder();
            for (int j = 0; j < template.length() - 1; j++) {
                final String input = template.substring(j, j + 2);
                result.append(input.substring(0, 1) + instructions.get(input));
            }
            result.append(template.substring(template.length() - 1));
            template = result.toString();
        }
        return template;
    }

    private long getResultBruteForce(final int steps, final List<String> lines) {
        final String result = buildPolymer(lines.get(0), steps, getInstructions(lines));
        final Map<Character, Long> counts = result.chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return Collections.max(counts.values()) - Collections.min(counts.values());
    }

    private Map<String, Long> getInitialCounts(final String template, final Map<String, String> instructions) {
        final Map<String, Long> counts = new HashMap<>();
        instructions.keySet().forEach(i -> counts.put(i, 0L));
        for (int j = 0; j < template.length() - 1; j++) {
            final String val = template.substring(j, j + 2);
            counts.put(val, counts.get(val) + 1);
        }
        return counts;
    }

    private Map<String, Long> getPairCounts(final int steps, Map<String, Long> initialCounts,
            final Map<String, String> instructions) {
        for (int i = 1; i <= steps; i++) {
            final Map<String, Long> newCounts = new HashMap<>();
            for (final Entry<String, Long> entry : initialCounts.entrySet()) {
                if (entry.getValue() > 0) {
                    final String input = entry.getKey();
                    final long count = entry.getValue();
                    final String middle = instructions.get(input);
                    final String output1 = input.substring(0, 1) + middle;
                    final String output2 = middle + input.substring(1, 2);
                    final long count1 = (newCounts.get(output1) == null) ? count : newCounts.get(output1) + count;
                    final long count2 = (newCounts.get(output2) == null) ? count : newCounts.get(output2) + count;
                    newCounts.put(output1, count1);
                    newCounts.put(output2, count2);
                }
            }
            initialCounts = newCounts;
        }
        return initialCounts;
    }

    private Map<String, Long> countTotalLetters(final Map<String, Long> pairCounts, final String template) {
        final Map<String, Long> letterCounts = new HashMap<>();
        for (final Entry<String, Long> entry : pairCounts.entrySet()) {
            final String combi = entry.getKey();
            final long count = entry.getValue();
            final String letter1 = combi.substring(0, 1);
            final long count1 = (letterCounts.get(letter1) == null) ? count : letterCounts.get(letter1) + count;
            letterCounts.put(letter1, count1);
        }
        final String lastLetter = template.substring(template.length() - 1);
        letterCounts.put(lastLetter, letterCounts.get(lastLetter) + 1);
        return letterCounts;
    }

    private long getResultSmarter(final int steps, final List<String> lines) {
        final String template = lines.get(0);
        final Map<String, String> instructions = getInstructions(lines);
        final Map<String, Long> pairCounts = getPairCounts(steps, getInitialCounts(template, instructions),
                getInstructions(lines));
        final Map<String, Long> letterCounts = countTotalLetters(pairCounts, template);
        return Collections.max(letterCounts.values()) - Collections.min(letterCounts.values());
    }

    private long runPart2(final List<String> lines) {
        return getResultSmarter(40, lines);
    }

    private long runPart1(final List<String> lines) {
        return getResultBruteForce(10, lines);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day14().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day14().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}