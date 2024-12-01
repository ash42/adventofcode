package nl.michielgraat.adventofcode2024.day01;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day01 extends AocSolver {

    protected Day01(final String filename) {
        super(filename);
    }

    private int[][] getNumberArrays(final List<String> input) {
        final int[][] nrArrays = new int[2][input.size()];

        for (int i = 0; i < input.size(); i++) {
            final String line = input.get(i);
            nrArrays[0][i] = Integer.valueOf(line.split("   ")[0]);
            nrArrays[1][i] = Integer.valueOf(line.split("   ")[1]);
        }
        return nrArrays;
    }

    private int[][] getSortedNumberArrays(final List<String> input) {
        final int[][] nrArrays = getNumberArrays(input);
        Arrays.sort(nrArrays[0]);
        Arrays.sort(nrArrays[1]);
        return nrArrays;
    }

    private int getTotalDistance(final int[][] nrArrays) {
        int total = 0;
        for (int i = 0; i < nrArrays[0].length; i++) {
            total += Math.abs(nrArrays[0][i] - nrArrays[1][i]);
        }
        return total;
    }

    private Map<Integer, Integer> getOccurenceMap(final int[][] nrArrays) {
        final Map<Integer, Integer> occurenceMap = new HashMap<>();
        for (int i = 0; i < nrArrays[1].length; i++) {
            final int key = nrArrays[1][i];
            occurenceMap.putIfAbsent(key, 0);
            occurenceMap.put(key, occurenceMap.get(key) + 1);
        }
        return occurenceMap;
    }

    private int getSimularityScore(final int[][] nrArrays) {
        final Map<Integer, Integer> occurenceMap = getOccurenceMap(nrArrays);
        int simScore = 0;
        for (int i = 0; i < nrArrays[0].length; i++) {
            final int nr = nrArrays[0][i];
            simScore += nr * occurenceMap.getOrDefault(nr, 0);
        }
        return simScore;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(getSimularityScore(getNumberArrays(input)));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getTotalDistance(getSortedNumberArrays(input)));
    }

    public static void main(final String... args) {
        new Day01("day01.txt");
    }
}
