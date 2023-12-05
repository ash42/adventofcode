package nl.michielgraat.adventofcode2023.day05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day05 extends AocSolver {

    protected Day05(String filename) {
        super(filename);
    }

    private List<Long> getSeeds(String line) {
        List<Long> seeds = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\d+").matcher(line);
        while (matcher.find()) {
            seeds.add(Long.valueOf(matcher.group()));
        }
        return seeds;
    }

    private List<Range> getSeedRanges(String line) {
        List<Range> seedRanges = new ArrayList<>();
        Matcher matcher = Pattern.compile("(\\d+)\\s+(\\d+)").matcher(line);
        while (matcher.find()) {
            long rangeStart = Long.valueOf(matcher.group(1));
            long rangeEnd = rangeStart + Long.valueOf(matcher.group(2)) - 1;
            Range seedRange = new Range(rangeStart, rangeEnd);
            seedRanges.add(seedRange);
        }
        return seedRanges;
    }

    private Map<Range, Range> getMapping(List<String> input) {
        Map<Range, Range> mapping = new HashMap<>();
        for (String line : input) {
            Matcher matcher = Pattern.compile("(\\d+)\\s+(\\d+)\\s+(\\d+)").matcher(line);
            matcher.find();
            long destStart = Long.valueOf(matcher.group(1));
            long srcStart = Long.valueOf(matcher.group(2));
            long range = Long.valueOf(matcher.group(3));
            Range source = new Range(srcStart, srcStart + range - 1);
            Range destination = new Range(destStart, destStart + range - 1);
            mapping.put(source, destination);
        }
        return mapping;
    }

    private List<Long> getDestNrs(List<Long> srcNrs, Map<Range, Range> srcToDest) {
        List<Long> destNrs = new ArrayList<>();

        for (long srcNr : srcNrs) {
            Optional<Range> optSrc = srcToDest.keySet().stream().filter(r -> r.inRange(srcNr)).findFirst();
            long dest = srcNr;
            if (optSrc.isPresent()) {
                Range src = optSrc.get();
                long offset = src.getOffSet(srcNr);
                dest = srcToDest.get(src).getStart() + offset;
            }
            destNrs.add(dest);
        }
        return destNrs;
    }

    private List<Long> getSrcNrs(List<Long> destNrs, Map<Range, Range> srcToDest) {
        List<Long> srcNrs = new ArrayList<>();

        for (long destNr : destNrs) {
            Optional<Range> destSrc = srcToDest.values().stream().filter(r -> r.inRange(destNr)).findFirst();
            long src = destNr;
            if (destSrc.isPresent()) {
                Range dest = destSrc.get();
                long offset = dest.getOffSet(destNr);
                src = srcToDest.entrySet().stream().filter(e -> dest.equals(e.getValue())).map(Map.Entry::getKey)
                        .findFirst().get().getStart() + offset;
            }
            srcNrs.add(src);
        }
        return srcNrs;
    }

    private List<List<String>> getInputBlocks(List<String> input) {
        List<List<String>> totalResult = new ArrayList<>();

        int lastEmptyLineIdx = 1;
        for (int mapNr = 1; mapNr <= 7; mapNr++) {
            List<String> result = new ArrayList<>();
            for (int i = lastEmptyLineIdx + 2; i < input.size(); i++) {
                String line = input.get(i);
                if (!line.isBlank()) {
                    result.add(line);
                } else {
                    lastEmptyLineIdx = i;
                    break;
                }
            }
            totalResult.add(result);
        }

        return totalResult;
    }

    private List<Map<Range, Range>> convertToRanges(List<List<String>> inputBlocks) {
        List<Map<Range, Range>> ranges = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            List<String> inputBlock = inputBlocks.get(i);
            Map<Range, Range> mapping = getMapping(inputBlock);
            ranges.add(mapping);
        }
        return ranges;
    }

    private boolean locationNrHasSeed(long destNr, List<Range> seedRanges, List<Map<Range, Range>> ranges) {
        List<Long> destNrs = List.of(destNr);
        for (int i = 6; i >= 0; i--) {
            Map<Range, Range> mapping = ranges.get(i);
            destNrs = getSrcNrs(destNrs, mapping);
        }
        for (Range seedRange : seedRanges) {
            for (long nr : destNrs) {
                if (seedRange.inRange(nr)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected String runPart2(final List<String> input) {
        List<Range> seedRanges = getSeedRanges(input.get(0));
        List<List<String>> inputBlocks = getInputBlocks(input);
        List<Map<Range, Range>> ranges = convertToRanges(inputBlocks);

        for (long i = 0;; i++) {
            if (locationNrHasSeed(i, seedRanges, ranges)) {
                return String.valueOf(i);
            }
        }
    }

    @Override
    protected String runPart1(final List<String> input) {
        List<Long> src = getSeeds(input.get(0));
        List<List<String>> inputBlocks = getInputBlocks(input);

        for (List<String> inputBlock : inputBlocks) {
            Map<Range, Range> mapping = getMapping(inputBlock);
            src = getDestNrs(src, mapping);
        }

        return String.valueOf(src.stream().mapToLong(l -> l).min().getAsLong());
    }

    public static void main(String... args) {
        new Day05("day05.txt");
    }
}
