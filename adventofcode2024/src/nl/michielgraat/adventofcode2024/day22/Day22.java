package nl.michielgraat.adventofcode2024.day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day22 extends AocSolver {

    protected Day22(final String filename) {
        super(filename);
    }

    private List<Long> parseInput(final List<String> input) {
        return input.stream().map(Long::parseLong).collect(Collectors.toList());
    }

    private long doStep(long secret) {
        long result = secret << 6;
        secret = result ^ secret;
        secret = secret % 16777216;
        result = secret >> 5;
        secret = result ^ secret;
        secret = secret % 16777216;
        result = secret << 11;
        secret = result ^ secret;
        secret = secret % 16777216;
        return secret;
    }

    private int getPrice(final long secret) {
        return (int) (secret % 10);
    }

    private Map<List<Integer>, Integer> getSequenceToFirstPrice(long secret) {
        final Map<List<Integer>, Integer> seqToPrice = new HashMap<>();
        List<Integer> prevDifferences = new ArrayList<>();
        int prevPrice = getPrice(secret);
        for (int i = 1; i <= 2000; i++) {
            secret = doStep(secret);
            final int price = getPrice(secret);
            final int difference = price - prevPrice;
            prevDifferences.add(difference);
            if (prevDifferences.size() == 4) {
                seqToPrice.putIfAbsent(prevDifferences, price);
                prevDifferences = new ArrayList<>(prevDifferences.subList(1, prevDifferences.size()));
            }
            prevPrice = price;
        }
        return seqToPrice;
    }

    private Map<Long, Map<List<Integer>, Integer>> getSequencesToScores(final List<Long> initialSecrets) {
        final Map<Long, Map<List<Integer>, Integer>> sequencesToScores = new HashMap<>();
        for (final long secret : initialSecrets) {
            final Map<List<Integer>, Integer> map = getSequenceToFirstPrice(secret);
            sequencesToScores.put(secret, map);
        }
        return sequencesToScores;
    }

    private Set<List<Integer>> getAllSequences(final List<Long> initialSecrets,
            final Map<Long, Map<List<Integer>, Integer>> sequencesToScores) {
        return sequencesToScores.values().stream().flatMap(s -> s.keySet().stream()).collect(Collectors.toSet());
    }

    private int getMaxBananas(final List<Long> initialSecrets,
            final Map<Long, Map<List<Integer>, Integer>> sequencesToScores,
            final Set<List<Integer>> allSequences) {
        int maxBananas = 0;
        for (final List<Integer> sequence : allSequences) {
            int bananas = 0;
            for (final long secret : initialSecrets) {
                final Map<List<Integer>, Integer> sequences = sequencesToScores.get(secret);
                bananas += sequences.containsKey(sequence) ? sequences.get(sequence) : 0;
            }
            maxBananas = Math.max(maxBananas, bananas);
        }
        return maxBananas;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Long> initialSecrets = parseInput(input);
        final Map<Long, Map<List<Integer>, Integer>> sequencesToScores = getSequencesToScores(initialSecrets);
        final Set<List<Integer>> allSequences = getAllSequences(initialSecrets, sequencesToScores);
        final int maxBananas = getMaxBananas(initialSecrets, sequencesToScores, allSequences);

        return String.valueOf(maxBananas);
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Long> initial = parseInput(input);
        long total = 0;
        for (long secret : initial) {
            for (int i = 1; i <= 2000; i++) {
                secret = doStep(secret);
            }
            total += secret;
        }
        return String.valueOf(total);
    }

    public static void main(final String... args) {
        new Day22("day22.txt");
    }
}
