package nl.michielgraat.adventofcode2018.day11;

import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

/**
 * Apparently this can be done quicker with summed-area tables, but
 * this brute force solution is quick enough (takes about a minute on
 * my machine).
 */
public class Day11 {
    private static final int DIM = 300;
    private static final String FILENAME = "day11.txt";

    private int[][] getGrid(int serialNr) {
        int[][] grid = new int[DIM][DIM];
        for (int y = 0; y < DIM; y++) {
            for (int x = 0; x < DIM; x++) {
                int power = 0;
                int rackId = x + 1 + 10;
                power = rackId * (y + 1);
                power += serialNr;
                power *= rackId;
                if (power < 100) {
                    power = 0;
                } else {
                    power = (power / 100) % 10;
                }
                power -= 5;
                grid[x][y] = power;
            }
        }
        return grid;
    }

    private String getCoordinate(int[][] grid, int size) {
        int maxX = 0;
        int maxY = 0;
        int max = 0;
        for (int y = 0; y <= (DIM - size); y++) {
            for (int x = 0; x <= (DIM - size); x++) {
                int total = 0;
                for (int j = y; j < y + size; j++) {
                    for (int i = x; i < x + size; i++) {
                        total += grid[i][j];
                    }
                }
                if (total > max) {
                    max = total;
                    maxX = x + 1;
                    maxY = y + 1;
                }
            }
        }
        return maxX + "," + maxY + "," + size + ": " + max;
    }

    private String runPart2(List<String> lines) {
        int serialNr = Integer.parseInt(lines.get(0));
        int[][] grid = getGrid(serialNr);
        int max = 0;
        String maxResult = "";
        for (int size = 1; size <= 300; size++) {
            String result = getCoordinate(grid, size);
            int total = Integer.parseInt(result.substring(result.indexOf(":") + 2));
            if (total > max) {
                max = total;
                maxResult = result.substring(0, result.indexOf(":"));
            }
        }
        return maxResult;
    }

    private String runPart1(List<String> lines) {
        int serialNr = Integer.parseInt(lines.get(0));
        int[][] grid = getGrid(serialNr);
        String result = getCoordinate(grid, 3);
        return result.substring(0, result.lastIndexOf(","));
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day11().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day11().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
