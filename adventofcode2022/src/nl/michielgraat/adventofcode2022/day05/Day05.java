package nl.michielgraat.adventofcode2022.day05;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day05 extends AocSolver {

    private int getNrOfStacks(final List<String> input) {
        for (String line : input) {
            if (!line.contains("[")) {
                line = line.trim();
                return Integer.parseInt(line.substring(line.lastIndexOf(" ") + 1));
            }
        }
        throw new IllegalArgumentException("Can not find number of stacks in input");
    }

    private List<Deque<Character>> getStackList(final List<String> input) {
        final int nrOfStacks = getNrOfStacks(input);
        final List<Deque<Character>> stacks = new ArrayList<>();
        for (int i = 0; i < nrOfStacks; i++) {
            stacks.add(new ArrayDeque<>());
        }
        return stacks;
    }

    private List<Deque<Character>> buildInitialStacks(final List<String> input) {
        final List<Deque<Character>> stacks = getStackList(input);
        for (final String line : input) {
            if (line.trim().startsWith("[")) {
                for (int crateIdx = 1, stackIdx = 0; crateIdx < line.length(); crateIdx += 4, stackIdx++) {
                    final char crate = line.charAt(crateIdx);
                    if (!Character.isSpaceChar(crate)) {
                        stacks.get(stackIdx).add(crate);
                    }
                }
            }
        }
        return stacks;
    }

    private int getNextInteger(final Matcher m) {
        m.find();
        return Integer.parseInt(m.group());
    }

    private void moveMultipleCrates(final List<Deque<Character>> stacks, final int nr, int from, int to) {
        final Deque<Character> fromStack = stacks.get(--from);
        final Deque<Character> toStack = stacks.get(--to);
        final StringBuilder cratesToMove = new StringBuilder();
        for (int i = 0; i < nr; i++) {
            cratesToMove.insert(0, fromStack.pop());
        }
        cratesToMove.chars().forEach(c -> toStack.push((char) c));
    }

    private void moveSingleCrates(final List<Deque<Character>> stacks, final int nr, int from, int to) {
        final Deque<Character> fromStack = stacks.get(--from);
        final Deque<Character> toStack = stacks.get(--to);
        for (int i = 0; i < nr; i++) {
            toStack.push(fromStack.pop());
        }
    }

    private List<Deque<Character>> runInstructions(final List<String> input, final List<Deque<Character>> stacks,
            final boolean part1) {
        final Pattern p = Pattern.compile("\\d+");
        for (final String line : input) {
            if (line.startsWith("move")) {
                final Matcher m = p.matcher(line);
                final int nr = getNextInteger(m);
                final int from = getNextInteger(m);
                final int to = getNextInteger(m);
                if (part1) {
                    moveSingleCrates(stacks, nr, from, to);
                } else {
                    moveMultipleCrates(stacks, nr, from, to);
                }
            }
        }
        return stacks;
    }

    private String getTopCrates(final List<Deque<Character>> stacks) {
        final StringBuilder sb = new StringBuilder();
        stacks.forEach(s -> sb.append(s.peek()));
        return sb.toString();
    }

    protected Day05(final String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        return getTopCrates(runInstructions(input, buildInitialStacks(input), false));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return getTopCrates(runInstructions(input, buildInitialStacks(input), true));
    }

    public static void main(final String... args) {
        new Day05("day05.txt");
    }
}
