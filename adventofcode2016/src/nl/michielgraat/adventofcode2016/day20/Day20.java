package nl.michielgraat.adventofcode2016.day20;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day20 {

    private static final String FILENAME = "day20.txt";

    private Map<Long, Long> getRanges(final List<String> lines) {
        final Map<Long, Long> ranges = new TreeMap<>();
        for (final String line : lines) {
            final long first = Long.parseLong(line.substring(0, line.indexOf("-")));
            final long second = Long.parseLong(line.substring(line.indexOf("-") + 1));
            ranges.put(first, second);
        }
        return ranges;
    }

    private long runPart2(final List<String> lines) {
        final Map<Long, Long> ranges = getRanges(lines);
        final Long[] keys = ranges.keySet().toArray(new Long[ranges.keySet().size()]);
        long biggest = -1L;
        long total = 0;
        for (int i = 0; i < keys.length - 1; i++) {
            final long key = keys[i];
            final long value = ranges.get(key);
            if (value > biggest) {
                biggest = value;
            }
            final long next = keys[i + 1];
            if (next > biggest + 1) {
                total += (next - (biggest + 1));
            }

        }
        return total;
    }

    private long runPart1(final List<String> lines) {
        final Map<Long, Long> ranges = getRanges(lines);

        final Long[] keys = ranges.keySet().toArray(new Long[ranges.keySet().size()]);
        long biggest = -1L;
        for (int i = 0; i < keys.length - 1; i++) {
            final long value = ranges.get(keys[i]);
            if (value > biggest) {
                biggest = value;
            }
            final long next = keys[i + 1];
            if (next > biggest + 1) {
                return biggest + 1;
            }
        }
        return -1;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day20().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day20().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}