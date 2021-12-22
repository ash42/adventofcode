package nl.michielgraat.adventofcode2021.day22;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day22 {

    private static final String FILENAME = "day22.txt";

    private int parseMax(final String s) {
        return Integer.parseInt(s.substring(s.indexOf("..") + 2));
    }

    private int parseMin(final String s) {
        return Integer.parseInt(s.substring(s.indexOf("=") + 1, s.indexOf("..")));
    }

    private Cuboid getCuboid(final String line) {
        final String[] coordinates = line.split(",");
        final boolean on = coordinates[0].startsWith("on");
        final String x = coordinates[0];
        final String y = coordinates[1];
        final String z = coordinates[2];
        return new Cuboid(parseMin(x), parseMax(x), parseMin(y), parseMax(y), parseMin(z), parseMax(z), on);
    }

    private List<Cuboid> parseCuboids(final List<String> lines, final int min, final int max) {
        final List<Cuboid> cuboids = new ArrayList<>();
        for (final String line : lines) {
            final Cuboid c = getCuboid(line);
            if (c.minX >= min && c.maxX <= max && c.minY >= min && c.maxY <= max && c.minZ >= min && c.maxZ <= max) {
                cuboids.add(getCuboid(line));
            }
        }
        return cuboids;
    }

    private List<Cuboid> getCubes(final List<Cuboid> cuboids) {
        final List<Cuboid> resultingCubeList = new ArrayList<>();
        for (final Cuboid c : cuboids) {
            final List<Cuboid> intersections = new ArrayList<>();
            for (final Cuboid o : resultingCubeList) {
                if (c.overlaps(o)) {
                    final int overlapMinX = Math.max(c.minX, o.minX);
                    final int overlapMaxX = Math.min(c.maxX, o.maxX);
                    final int overlapMinY = Math.max(c.minY, o.minY);
                    final int overlapMaxY = Math.min(c.maxY, o.maxY);
                    final int overlapMinZ = Math.max(c.minZ, o.minZ);
                    final int overlapMaxZ = Math.min(c.maxZ, o.maxZ);
                    final Cuboid intersection = new Cuboid(overlapMinX, overlapMaxX, overlapMinY, overlapMaxY, overlapMinZ,
                            overlapMaxZ, !o.on);
                    intersections.add(intersection);
                }
            }
            resultingCubeList.addAll(intersections);
            if (c.on) {
                resultingCubeList.add(c);
            }
        }
        return resultingCubeList;
    }

    private long countOnCubes(final List<Cuboid> cubes) {
        long total = 0;
        for (final Cuboid c : cubes) {
            if (c.on) {
                total += c.getVolume();
            } else {
                total -= c.getVolume();
            }
        }
        return total;
    }

    private long runPart2(final List<String> lines) {
        return countOnCubes(getCubes(parseCuboids(lines, Integer.MIN_VALUE, Integer.MAX_VALUE)));
    }

    private long runPart1(final List<String> lines) {
        return countOnCubes(getCubes(parseCuboids(lines, -50, 50)));
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day22().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 100000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day22().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 100000 + " ms");
    }
}

class Cuboid {
    int minX = 0;
    int maxX = 0;
    int minY = 0;
    int maxY = 0;
    int minZ = 0;
    int maxZ = 0;
    boolean on = false;

    public Cuboid(final int minX, final int maxX, final int minY, final int maxY, final int minZ, final int maxZ, final boolean on) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
        this.on = on;
    }

    boolean overlaps(final Cuboid o) {
        return this.minX <= o.maxX && o.minX <= this.maxX && this.minY <= o.maxY && o.minY <= this.maxY
                && this.minZ <= o.maxZ && o.minZ <= this.maxZ;
    }

    long getVolume() {
        return (maxX - minX + 1L) * (maxY - minY + 1L) * (maxZ - minZ + 1L);
    }
}