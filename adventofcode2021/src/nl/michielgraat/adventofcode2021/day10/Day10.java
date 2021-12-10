package nl.michielgraat.adventofcode2021.day10;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day10 {

    private static final String FILENAME = "day10.txt";

    private boolean isStartingChar(final char c) {
        return c == '(' || c == '[' || c == '{' || c == '<';
    }

    private boolean areMatchingChars(final char start, final char end) {
        if (start == '(') {
            return end == ')';
        } else if (start == '[') {
            return end == ']';
        } else if (start == '{') {
            return end == '}';
        } else {
            return end == '>';
        }
    }

    private List<Character> getIllegalCharacters(final List<String> lines) {
        final List<Character> illegalChars = new ArrayList<>();
        for (final String line : lines) {
            final Deque<Character> stack = new ArrayDeque<>();

            for (final char c : line.toCharArray()) {
                if (isStartingChar(c)) {
                    stack.push(c);
                } else {
                    final char startingChar = stack.pop();
                    if (!areMatchingChars(startingChar, c)) {
                        illegalChars.add(c);
                    }
                }
            }
        }
        return illegalChars;
    }

    private int getIllegalCharacterScore(final List<Character> illegalChars) {
        int total = 0;
        for (final char illegal : illegalChars) {
            if (illegal == ')') {
                total += 3;
            } else if (illegal == ']') {
                total += 57;
            } else if (illegal == '}') {
                total += 1197;
            } else {
                total += 25137;
            }
        }

        return total;
    }

    private List<String> getIllegalList(final List<String> lines) {
        final List<String> illegalList = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            final Deque<Character> stack = new ArrayDeque<>();

            for (final char c : line.toCharArray()) {
                if (isStartingChar(c)) {
                    stack.push(c);
                } else {
                    final char startingChar = stack.pop();
                    if (!areMatchingChars(startingChar, c)) {
                        illegalList.add(line);
                    }
                }
            }
        }
        return illegalList;
    }

    private List<String> getToFinishChars(final List<String> lines) {
        final List<String> toFinishChars = new ArrayList<>();
        for (final String line : lines) {
            final Deque<Character> stack = new ArrayDeque<>();

            for (final char c : line.toCharArray()) {
                if (isStartingChar(c)) {
                    stack.push(c);
                } else {
                    if (areMatchingChars(stack.peek(), c)) {
                        stack.pop();
                    }
                }
            }
            final StringBuilder sb = new StringBuilder();
            while (!stack.isEmpty()) {
                sb.append(stack.pop());
            }
            toFinishChars.add(sb.toString());
        }
        return toFinishChars;
    }

    private long getScore(final String line) {
        long total = 0;
        for (final char c : line.toCharArray()) {
            total *= 5;
            if (c == '(') {
                total += 1;
            } else if (c == '[') {
                total += 2;
            } else if (c == '{') {
                total += 3;
            } else {
                total += 4;
            }
        }
        return total;
    }

    private long runPart2(final List<String> lines) {
        lines.removeAll(getIllegalList(lines));
        final List<String> toFinishChars = getToFinishChars(lines);
        final List<Long> scores = new ArrayList<>();
        toFinishChars.forEach(t -> scores.add(getScore(t)));
        Collections.sort(scores);
        return scores.get(scores.size()/2);
    }

    private int runPart1(final List<String> lines) {
        final List<Character> illegalChars = getIllegalCharacters(lines);
        return getIllegalCharacterScore(illegalChars);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day10().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day10().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }

}