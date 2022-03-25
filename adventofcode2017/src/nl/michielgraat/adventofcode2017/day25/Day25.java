package nl.michielgraat.adventofcode2017.day25;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day25 {

    private static final String FILENAME = "day25.txt";

    private Map<Character, StateRule> getStates(final List<String> lines) {
        final Map<Character, StateRule> states = new HashMap<>();
        for (int i = 3; i < lines.size(); i += 10) {
            final char curState = lines.get(i).charAt(lines.get(i).indexOf(":") - 1);
            final boolean zeroWriteZero = Character
                    .getNumericValue(lines.get(i + 2).charAt(lines.get(i + 2).indexOf(".") - 1)) == 0;
            final Direction zeroDirection = (lines.get(i + 3)
                    .substring(lines.get(i + 3).lastIndexOf(" ") + 1, lines.get(i + 3).length() - 1).equals("left"))
                            ? Direction.LEFT
                            : Direction.RIGHT;
            final char zeroNextState = lines.get(i + 4).charAt(lines.get(i + 4).length() - 2);
            final boolean oneWriteZero = Character
                    .getNumericValue(lines.get(i + 6).charAt(lines.get(i + 6).indexOf(".") - 1)) == 0;
            final Direction oneDirection = (lines.get(i + 7)
                    .substring(lines.get(i + 7).lastIndexOf(" ") + 1, lines.get(i + 7).length() - 1).equals("left"))
                            ? Direction.LEFT
                            : Direction.RIGHT;
            final char oneNextState = lines.get(i + 8).charAt(lines.get(i + 8).length() - 2);

            states.put(curState, new StateRule(zeroWriteZero, zeroDirection, zeroNextState, oneWriteZero, oneDirection,
                    oneNextState));
        }
        return states;
    }

    private long runProgram(char state, final int steps, final Map<Character, StateRule> states) {

        // Used a LinkedList here before, but that is a lot slower. No shame ;)
        final int[] tape = new int[8000];
        int cursor = 4000;

        for (int i = 0; i < steps; i++) {
            final StateRule rule = states.get(state);
            if (tape[cursor] == 0) {
                tape[cursor] = rule.zeroWriteZero ? 0 : 1;
                cursor += rule.zeroDirection == Direction.LEFT ? -1 : 1;
                state = rule.zeroNextState;
            } else {
                tape[cursor] = rule.oneWriteZero ? 0 : 1;
                cursor += rule.oneDirection == Direction.LEFT ? -1 : 1;
                state = rule.oneNextState;
            }
        }

        return Arrays.stream(tape).filter(i -> i == 1).count();
    }

    public long runPart1(final List<String> lines) {
        final char state = lines.get(0).charAt(lines.get(0).length() - 2);
        final int steps = Integer
                .parseInt(lines.get(1).substring(lines.get(1).indexOf("after ") + 6, lines.get(1).indexOf(" steps")));
        final Map<Character, StateRule> states = getStates(lines);

        return runProgram(state, steps, states);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        final long start = System.nanoTime();
        System.out.println("Answer: " + new Day25().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}