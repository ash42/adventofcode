package nl.michielgraat.adventofcode2021.day05;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day05 {

    private static final String FILENAME = "day05.txt";

    private int getNrOfOverlapping(final int[][] grid) {
        int total = 0;

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                if (grid[x][y] >= 2) {
                    total++;
                }
            }
        }
        return total;
    }

    private void markDiagonally(final int[][] grid, final int x1, final int y1, final int x2, final int y2) {
        if (x1 != x2 && y1 != y2) {
            int startx = Math.min(x1, x2);
            final int endx = Math.max(x1, x2);
            int starty = (x1 == startx) ? y1 : y2;
            final int endy = (x1 == endx) ? y1 : y2;

            while (startx <= endx) {
                grid[startx][starty]++;
                startx++;
                starty = (starty < endy) ? starty + 1 : starty - 1;
            }
        }
    }

    private void markVertically(final int[][] grid, final int x1, final int y1, final int x2, final int y2) {
        if (x1 == x2) {
            final int starty = Math.min(y1, y2);
            final int endy = Math.max(y1, y2);
            for (int y = starty; y <= endy; y++) {
                grid[x1][y]++;
            }
        }
    }

    private void markHorizontally(final int[][] grid, final int x1, final int y1, final int x2, final int y2) {
        if (y1 == y2) {
            final int startx = Math.min(x1, x2);
            final int endx = Math.max(x1, x2);
            for (int x = startx; x <= endx; x++) {
                grid[x][y1]++;
            }
        }
    }

    public int runPart2(final List<String> lines) {
        final int[][] grid = new int[1000][1000];

        for (final String line : lines) {
            final int x1 = Integer.parseInt(line.substring(0, line.indexOf(",")));
            final int y1 = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(" ")));
            final int x2 = Integer.parseInt(line.substring(line.lastIndexOf(" ") + 1, line.lastIndexOf(",")));
            final int y2 = Integer.parseInt(line.substring(line.lastIndexOf(",") + 1));

            markHorizontally(grid, x1, y1, x2, y2);
            markVertically(grid, x1, y1, x2, y2);
            markDiagonally(grid, x1, y1, x2, y2);
        }

        return getNrOfOverlapping(grid);
    }

    public int runPart1(final List<String> lines) {
        final int[][] grid = new int[1000][1000];

        for (final String line : lines) {
            final int x1 = Integer.parseInt(line.substring(0, line.indexOf(",")));
            final int y1 = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(" ")));
            final int x2 = Integer.parseInt(line.substring(line.lastIndexOf(" ") + 1, line.lastIndexOf(",")));
            final int y2 = Integer.parseInt(line.substring(line.lastIndexOf(",") + 1));

            markHorizontally(grid, x1, y1, x2, y2);
            markVertically(grid, x1, y1, x2, y2);
        }

        return getNrOfOverlapping(grid);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day05().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day05().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

    }
}
