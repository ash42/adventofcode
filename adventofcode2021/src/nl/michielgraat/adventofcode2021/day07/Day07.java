package nl.michielgraat.adventofcode2021.day07;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day07 {
    private static final String FILENAME = "day07.txt";

    private List<Integer> getPositions(final List<String> lines) {
        final String[] sNrs = lines.get(0).split(",");
        final List<Integer> nrs = new ArrayList<>();
        for (int i = 0; i < sNrs.length; i++) {
            nrs.add(Integer.parseInt(sNrs[i]));
        }
        return nrs;
    }

    private int getTriangularNumber(final int nr, final int goal) {
        final int steps = getDistanceTo(nr, goal);
        return (steps == 0) ? 0 : (steps * (steps + 1)) / 2;
    }

    private int getDistanceTo(final int nr, final int goal) {
        return Math.abs(goal - nr);
    }

    public int runPart2(final List<String> lines) {
        final List<Integer> positions = getPositions(lines);
        
        final int startPos = positions.stream().mapToInt(p -> p).min().orElseThrow(NoSuchElementException::new);
        final int endPos = positions.stream().mapToInt(p -> p).max().orElseThrow(NoSuchElementException::new);
        
        int min = Integer.MAX_VALUE;

        for (int pos = startPos; pos <= endPos; pos++) {
            int totalDistance = 0;

            for (final int goal : positions) {
                totalDistance += getTriangularNumber(pos, goal);
            }

            if (totalDistance < min) {
                min = totalDistance;
            }
        }

        return min;

    }

    public int runPart1(final List<String> lines) {
        final List<Integer> positions = getPositions(lines);

        final int startPos = positions.stream().mapToInt(p -> p).min().orElseThrow(NoSuchElementException::new);
        final int endPos = positions.stream().mapToInt(p -> p).max().orElseThrow(NoSuchElementException::new);

        int min = Integer.MAX_VALUE;
        for (int pos = startPos; pos <= endPos; pos++) {
            int totalDistance = 0;
            for (final int goal : positions) {
                totalDistance += getDistanceTo(pos, goal);
            }
            if (totalDistance < min) {
                min = totalDistance;
            }
        }
        return min;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day07().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day07().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}
