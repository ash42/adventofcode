package nl.michielgraat.adventofcode2018.day04;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day04 {

    private static final String FILENAME = "day04.txt";

    private Set<Record> getSortedRecords(List<String> lines) {
        Set<Record> records = new TreeSet<>();
        lines.forEach(l -> {
            try {
                records.add(new Record(l));
            } catch (ParseException e) {
                System.out.println("Illegal input " + l);
                e.printStackTrace();
            }
        });
        return records;
    }

    private void parseRecords(List<String> lines, Map<Integer,Integer> gToMin, Map<Integer,Integer[]> gToMinCount) {
        Set<Record> records = getSortedRecords(lines);
        
        int guard = 0;
        int sleepStart = 0;
        int sleepEnd = 0;

        for (Record r : records) {
            if (r.guard != -1) {
                guard = r.guard;
                gToMin.computeIfAbsent(guard, g -> 0);
                gToMinCount.computeIfAbsent(guard, g -> IntStream.of(new int[60]).boxed().toArray(Integer[]::new));
            } else if (r.fallsAsleep) {
                sleepStart = r.minute;
            } else {
                sleepEnd = r.minute-1;
                int total = sleepEnd - sleepStart;
                gToMin.put(guard, gToMin.get(guard) + total);
                int[] minutes = Arrays.stream(gToMinCount.get(guard)).mapToInt(Integer::intValue).toArray();
                for (int i = sleepStart; i <= sleepEnd; i++) {
                    minutes[i] += 1;
                }
                gToMinCount.put(guard, IntStream.of(minutes).boxed().toArray((Integer[]::new)));
            }
        }
    }

    private int calculateAnswer1(Map<Integer,Integer> gToMin, Map<Integer,Integer[]> gToMinCount) {
        Map.Entry<Integer,Integer> maxEntry = gToMin.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).orElse(null);
        int sleepiestGuard = maxEntry.getKey();
        Integer[] minArray = gToMinCount.get(sleepiestGuard);
        int max = 0;
        int min = -1;
        for (int i = 0; i<minArray.length; i++) {
            if (minArray[i] > max) {
                max = minArray[i];
                min = i;
            }
        }

        return sleepiestGuard * min;
    }

    private int calculateAnswer2(Map<Integer,Integer[]> gToMinCount) {
        int maxGuard = -1;
        int maxMin = -1;
        int maxMinsAsleep = -1;
        for (Map.Entry<Integer,Integer[]> entry : gToMinCount.entrySet()) {
            Integer[] minuteCounts = entry.getValue();
            for (int minute = 0; minute < minuteCounts.length; minute++) {
                int minsAsleep = minuteCounts[minute];
                if (minsAsleep > maxMinsAsleep) {
                    maxMin = minute;
                    maxMinsAsleep = minsAsleep;
                    maxGuard = entry.getKey();
                }
            }
        }
        return maxGuard*maxMin;
    }

    public int runPart2(List<String> lines) {
        Map<Integer,Integer> gToMin = new HashMap<>();
        Map<Integer,Integer[]> gToMinCount = new HashMap<>();
        parseRecords(lines, gToMin, gToMinCount);
        return calculateAnswer2(gToMinCount);
    }

    public int runPart1(List<String> lines) {
        Map<Integer,Integer> gToMin = new HashMap<>();
        Map<Integer,Integer[]> gToMinCount = new HashMap<>();
        parseRecords(lines, gToMin, gToMinCount);
        return calculateAnswer1(gToMin, gToMinCount);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day04().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day04().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}