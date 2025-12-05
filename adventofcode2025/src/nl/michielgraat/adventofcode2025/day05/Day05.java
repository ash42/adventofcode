package nl.michielgraat.adventofcode2025.day05;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.michielgraat.adventofcode2025.AocSolver;

public class Day05 extends AocSolver {

    protected Day05(final String filename) {
        super(filename);
    }
    
    private Set<Range> parseIdRanges(final List<String> input) {
        final Set<Range> ranges = new HashSet<>();
        for (final String line : input) {
            if (line.isBlank()) {
                break;
            } else {
                final String[] splitLine = line.split("-");
                ranges.add(new Range(Long.valueOf(splitLine[0]), Long.valueOf(splitLine[1])));
            }
        }
        return ranges;
    }

    private int countFreshIds(final List<String> input, final Set<Range> ranges) {
        boolean startProcessing = false;
        int fresh = 0;
        for (final String line : input) { 
            if (startProcessing) {
                final long value = Long.valueOf(line);
                fresh += ranges.stream().anyMatch(r -> r.contains(value)) ? 1 : 0;
            } else if (line.isBlank()) {
                startProcessing = true;
            }
        }
        return fresh;
    }
    
    private Set<Range> combineIdRanges(Set<Range> ranges) {
        boolean changedRange = false;
        do {
            final Set<Range> newRanges = new HashSet<>();
            changedRange = false;
            for (final Range range : ranges) {
                for (final Range range2 : ranges) {
                    if (range.overlaps(range2)) {
                        range.adjust(range2);
                        changedRange = true;
                    }
                    newRanges.add(range);
                }
            }
            if (changedRange) {
                ranges = new HashSet<>(newRanges);
            }
        } while (changedRange);
        return ranges;
    }
    
    private long countFreshIdsFromRanges(final Set<Range> ranges) {
        return ranges.stream().mapToLong(r -> r.end - r.start + 1L).sum();
    }
    
    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(countFreshIdsFromRanges(combineIdRanges(parseIdRanges(input))));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(countFreshIds(input, parseIdRanges(input)));
    }

    public static void main(final String... args) {
        new Day05("day05.txt");
    }
}
