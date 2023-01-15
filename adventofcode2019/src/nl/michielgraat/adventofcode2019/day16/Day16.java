package nl.michielgraat.adventofcode2019.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2019.AocSolver;

public class Day16 extends AocSolver {

    protected Day16(final String filename) {
        super(filename);
    }

    private Map<Integer, List<Integer>> getOutputToPattern(final int maxSize) {
        final Map<Integer, List<Integer>> result = new HashMap<>();
        for (int i = 1; i <= maxSize; i++) {
            List<Integer> pattern = new ArrayList<>();
            for (int j = 0; j < i - 1; j++) {
                pattern.add(0);
            }
            while (pattern.size() < maxSize) {
                for (int j = 1; j <= i; j++) {
                    pattern.add(1);
                }
                for (int j = 1; j <= i; j++) {
                    pattern.add(0);
                }
                for (int j = 1; j <= i; j++) {
                    pattern.add(-1);
                }
                for (int j = 1; j <= i; j++) {
                    pattern.add(0);
                }
            }
            if (pattern.size() > maxSize) {
                pattern = pattern.subList(0, maxSize);
            }
            result.put(i, pattern);
        }
        return result;
    }

    private List<Integer> runPhase(final List<Integer> input, final Map<Integer, List<Integer>> patternMap) {
        final List<Integer> output = new ArrayList<>();
        for (int outputDigit = 0; outputDigit < input.size(); outputDigit++) {
            final List<Integer> pattern = patternMap.get(outputDigit + 1);
            int result = 0;
            for (int idx = 0; idx < input.size(); idx++) {
                final int digit = input.get(idx);
                final int multiplier = pattern.get(idx);
                result += digit * multiplier;
            }
            result = Math.abs(result %= 10);
            output.add(outputDigit, result);
        }
        return output;
    }

    private List<Integer> runPhases(final int nr, List<Integer> input) {
        final Map<Integer, List<Integer>> map = getOutputToPattern(input.size());
        for (int i = 1; i <= nr; i++) {
            final List<Integer> output = runPhase(input, map);
            input = output.stream().toList();
        }
        return input;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final int offset = Integer.parseInt(input.get(0).substring(0, 7));
        final List<Integer> signal = input.get(0).chars().mapToObj(Character::getNumericValue)
                .collect(Collectors.toList());
        for (int i = 2; i <= 10000; i++) {
            signal.addAll(input.get(0).chars().mapToObj(Character::getNumericValue).collect(Collectors.toList()));
        }

        // After some hints and analysis of patterns, we can see that the result for
        // every position is the sum of the result of the positions after it. This, as
        // is described in the puzzle itself, modulo 10.
        // Or - easier - the result for a position is the sum of itself and the result
        // of the position after it.
        // As we are only interested in the the 8 digits after the offset, only
        // calculate the result from the end of the signal backwards until the offset is
        // reached.

        // Convert to an array, this seems to make getting the result about 1.5 secs
        // faster.
        final int[] signalArray = signal.stream().mapToInt(Integer::intValue).toArray();
        for (int i = 0; i < 100; i++) {
            for (int j = signalArray.length - 1; j >= offset; j--) {
                signalArray[j - 1] += signalArray[j];
                signalArray[j - 1] = signalArray[j - 1] % 10;
            }
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(signalArray[offset + i]);
        }
        return Arrays.stream(signalArray).boxed().skip(offset).limit(8).map(String::valueOf)
                .collect(Collectors.joining());
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Integer> result = runPhases(100,
                input.get(0).chars().mapToObj(Character::getNumericValue).collect(Collectors.toList()));
        return result.stream().limit(8).map(String::valueOf).collect(Collectors.joining());
    }

    public static void main(final String... args) {
        new Day16("day16.txt");
    }
}
