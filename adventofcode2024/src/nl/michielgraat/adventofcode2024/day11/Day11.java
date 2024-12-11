package nl.michielgraat.adventofcode2024.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day11 extends AocSolver {

    protected Day11(final String filename) {
        super(filename);
    }

    private int nrDigits(final long n) {
        return n == 0 ? 0 : (int) Math.floor(Math.log10(n)) + 1;
    }

    private boolean hasEvenNrDigits(final long n) {
        return nrDigits(n) % 2 == 0;
    }

    private long[] split(final long n) {
        final int nrDigits = nrDigits(n);
        final int splitAfter = nrDigits / 2;
        final long[] result = new long[2];
        result[0] = n / (long) Math.pow(10, splitAfter);
        result[1] = n % (long) Math.pow(10, splitAfter);
        return result;
    }

    private List<Long> blink(final long stone) {
        final List<Long> result = new ArrayList<>();
        if (stone == 0) {
            result.add(1L);
        } else if (hasEvenNrDigits(stone)) {
            final long[] splitResult = split(stone);
            result.add(splitResult[0]);
            result.add(splitResult[1]);
        } else {
            result.add(stone * 2024);
        }
        return result;
    }

    private long calculateSizeAfterBlinks(final long stone, final int times, final Map<MemoKey, Long> memoMap) {
        final MemoKey key = new MemoKey(stone, times);
        if (memoMap.containsKey(key)) {
            return memoMap.get(key);
        }
        final List<Long> result = blink(stone);
        if (times == 1) {
            memoMap.put(key, (long) result.size());
            return result.size();
        } else {
            long nrBlinks = calculateSizeAfterBlinks(result.get(0), times - 1, memoMap);
            if (result.size() == 2) {
                nrBlinks += calculateSizeAfterBlinks(result.get(1), times - 1, memoMap);
            }
            memoMap.put(key, nrBlinks);
            return nrBlinks;
        }
    }

    private long countStonesAfterBlinks(final List<Long> stones, final int times) {
        long result = 0;
        for (final long stone : stones) {
            result += calculateSizeAfterBlinks(stone, times, new HashMap<>());
        }
        return result;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Long> numbers = Arrays.stream(input.get(0).split(" ")).map(Long::valueOf)
                .collect(Collectors.toList());
        return String.valueOf(countStonesAfterBlinks(numbers, 75));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Long> stones = Arrays.stream(input.get(0).split(" ")).map(Long::valueOf)
                .collect(Collectors.toList());
        return String.valueOf(countStonesAfterBlinks(stones, 25));
    }

    public static void main(final String... args) {
        new Day11("day11.txt");
    }
}

record MemoKey(long number, long times) {
}
