package nl.michielgraat.adventofcode2018.day23;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day23 {
    private static final String FILENAME = "day23.txt";

    private List<Nanobot> getBotList(final List<String> lines) {
        final List<Nanobot> bots = new ArrayList<>();
        for (final String line : lines) {
            bots.add(new Nanobot(line));
        }
        return bots;
    }

    private int getNrInRangeOfStrongest(final List<Nanobot> bots) {
        final Nanobot strongest = Collections.max(bots, Comparator.comparing(Nanobot::getR));
        int total = 0;
        for (final Nanobot bot : bots) {
            if (strongest.inRange(bot))
                total++;
        }
        return total;
    }

    /**
     * Create an initial cube to cover all bots. Make sure to use
     * a side length which is a factor of 2 to make division easy (and
     * prevent rounding errors during division).
     * 
     * @param bots The list of bots.
     * @return The initial cube.
     */
    private Cube getInitialSearchCube(final List<Nanobot> bots) {
        final int minX = bots.stream().mapToInt(Nanobot::getX).min().getAsInt();
        final int minY = bots.stream().mapToInt(Nanobot::getY).min().getAsInt();
        final int minZ = bots.stream().mapToInt(Nanobot::getZ).min().getAsInt();
        final int maxX = bots.stream().mapToInt(Nanobot::getX).max().getAsInt();
        final int maxY = bots.stream().mapToInt(Nanobot::getY).max().getAsInt();
        final int maxZ = bots.stream().mapToInt(Nanobot::getZ).max().getAsInt();
        int length = 1;
        while (minX + length < maxX || minY + length < maxY || minZ + length < maxZ) {
            length *= 2;
        }
        return new Cube(minX, minY, minZ, length, bots);
    }

    public int runPart2(final List<String> lines) {
        // So the idea is to create a big box which covers all bots, split it into eight
        // equal sub-cubes, find out which one of those have the greatest number of bots
        // covered and continue with those. Rinse and repeat until we have a box which
        // has side length of 1, then we have a single point. Because we sort the cubes
        // first on highest number of bots covered, then distance to origin, this should
        // be the point we are looking for (see source code for Cube for sorting in
        // compareTo method).
        final List<Nanobot> bots = getBotList(lines);
        final PriorityQueue<Cube> queue = new PriorityQueue<>();
        final Cube initial = getInitialSearchCube(bots);
        queue.add(initial);
        while (!queue.isEmpty()) {
            final Cube c = queue.poll();
            if (c.sideLength == 1) {
                // Found it!
                return c.getDistanceToOrigin();
            } else {
                final List<Cube> parts = c.split();
                // Cubes with highest number of bots covered first.
                Collections.sort(parts);
                queue.addAll(parts);
            }
        }
        throw new IllegalArgumentException("There does not seem to be a result for the input.");
    }

    public int runPart1(final List<String> lines) {
        return getNrInRangeOfStrongest(getBotList(lines));
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day23().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day23().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
