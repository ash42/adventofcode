package nl.michielgraat.adventofcode2018.day01;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day01 {

    private static final String FILENAME = "day01.txt";

    public int runPart2(final List<Integer> values) {
        final Set<Integer> freqs = new HashSet<>();
        int total = 0;
        while (true) {
            for (final int v : values) {
                total += v;
                if (!freqs.add(total)) {
                    return total;
                }
            }
        }
    }

    public int runPart1(final List<Integer> values) {
        int total = 0;
        for (final int v : values) {
            total += v;
        }
        return total;
    }

    public static void main(final String[] args) {
        final List<Integer> lines = FileReader.getCompleteIntegerList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day01().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day01().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
