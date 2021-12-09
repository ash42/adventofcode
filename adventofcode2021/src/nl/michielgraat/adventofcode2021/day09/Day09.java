package nl.michielgraat.adventofcode2021.day09;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day09 {

    private static final String FILENAME = "day09.txt";

    private int[][] parseHeightmap(final List<String> lines) {
        final int[][] heightmap = new int[lines.get(0).length()][lines.size()];
        for (int j = 0; j < lines.size(); j++) {
            final String line = lines.get(j);
            for (int i = 0; i < line.length(); i++) {
                heightmap[i][j] = Integer.parseInt(Character.toString(line.charAt(i)));
            }
        }
        return heightmap;
    }

    private List<Point> getLowPoints(final int[][] heightmap) {
        final List<Point> lowPoints = new ArrayList<>();
        final int dim1 = heightmap.length;
        final int dim2 = heightmap[0].length;
        for (int j = 0; j < dim2; j++) {
            for (int i = 0; i < dim1; i++) {
                final int current = heightmap[i][j];
                final int left = (i > 0) ? heightmap[i - 1][j] : Integer.MAX_VALUE;
                final int right = (i < dim1 - 1) ? heightmap[i + 1][j] : Integer.MAX_VALUE;
                final int up = (j > 0) ? heightmap[i][j - 1] : Integer.MAX_VALUE;
                final int down = (j < dim2 - 1) ? heightmap[i][j + 1] : Integer.MAX_VALUE;
                if (current < left && current < right && current < up && current < down) {
                    lowPoints.add(new Point(i,j));
                }
            }
        }
        return lowPoints;
    }

    private int getBasinSize(final int[][] heightmap, final boolean[][] visited, final int i, final int j, final int dim1, final int dim2) {
        if (i < 0 || i >= dim1 || j < 0 || j >= dim2 || visited[i][j] || heightmap[i][j] == 9) {
            return 0;
        } else {
            visited[i][j] = true;
            final int left = getBasinSize(heightmap, visited, i - 1, j, dim1, dim2);
            final int right = getBasinSize(heightmap, visited, i + 1, j, dim1, dim2);
            final int up = getBasinSize(heightmap, visited, i, j - 1, dim1, dim2);
            final int down = getBasinSize(heightmap, visited, i, j + 1, dim1, dim2);
            return 1 + left + right + up + down;
        }
    }

    private List<Integer> getBasinSizes(final int[][] heightmap) {
        final List<Point> lowPoints = getLowPoints(heightmap);
        final int dim1 = heightmap.length;
        final int dim2 = heightmap[0].length;
        final List<Integer> sizes = new ArrayList<>();
        for (final Point lowPoint : lowPoints) {
            final boolean[][] visited = new boolean[dim1][dim2];
            sizes.add(getBasinSize(heightmap, visited, lowPoint.x, lowPoint.y, dim1, dim2));
        }
        return sizes;
    }

    private int runPart2(final List<String> lines) {
        final int[][] heightmap = parseHeightmap(lines);
        final List<Integer> sizes = getBasinSizes(heightmap);
        Collections.sort(sizes, Collections.reverseOrder());
        return sizes.get(0) * sizes.get(1) * sizes.get(2);
    }

    private int runPart1(final List<String> lines) {
        final int[][] heightmap = parseHeightmap(lines);
        final List<Point> lowPoints = getLowPoints(heightmap);
        int total = 0;
        for (final Point lowPoint : lowPoints) {
            total += heightmap[lowPoint.x][lowPoint.y] + 1;
        }
        return total;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day09().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day09().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}

class Point {
    int x;
    int y;

    Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
}