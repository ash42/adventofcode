package nl.michielgraat.adventofcode2021.day11;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day11 {

    private static final String FILENAME = "day11.txt";

    private int[][] parseGrid(final List<String> lines) {
        final int[][] grid = new int[lines.get(0).length()][lines.size()];
        for (int j = 0; j < lines.size(); j++) {
            final String line = lines.get(j);
            for (int i = 0; i < line.length(); i++) {
                grid[i][j] = Integer.parseInt(Character.toString(line.charAt(i)));
            }
        }
        return grid;
    }

    private void incrAll(final int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j]++;
            }
        }
    }

    private boolean containsFlashingOctopusses(final int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] >= 10) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateGrid(final int[][] grid, final int i, final int j) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length && grid[i][j] != 0) {
            grid[i][j]++;
        }
    }

    private int getNrOfFlashesAfterStep(final int n, final int[][] grid) {
        int nrOfFlashes = 0;

        for (int step = 1; step <= n; step++) {
            incrAll(grid);
            while (containsFlashingOctopusses(grid)) {
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[0].length; j++) {
                        if (grid[i][j] >= 10) {
                            nrOfFlashes++;
                            grid[i][j] = 0;
                            updateGrid(grid, i + 1, j);
                            updateGrid(grid, i + 1, j + 1);
                            updateGrid(grid, i + 1, j - 1);
                            updateGrid(grid, i, j + 1);
                            updateGrid(grid, i, j - 1);
                            updateGrid(grid, i - 1, j);
                            updateGrid(grid, i - 1, j + 1);
                            updateGrid(grid, i - 1, j - 1);
                        }
                    }
                }
            }
        }

        return nrOfFlashes;
    }

    private boolean allFlashed(final int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getStepOfCompleteFlash(final int[][] grid) {
        int step = 0;
        while(!allFlashed(grid)) {
            incrAll(grid);
            while (containsFlashingOctopusses(grid)) {
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[0].length; j++) {
                        if (grid[i][j] >= 10) {
                            grid[i][j] = 0;
                            updateGrid(grid, i + 1, j);
                            updateGrid(grid, i + 1, j + 1);
                            updateGrid(grid, i + 1, j - 1);
                            updateGrid(grid, i, j + 1);
                            updateGrid(grid, i, j - 1);
                            updateGrid(grid, i - 1, j);
                            updateGrid(grid, i - 1, j + 1);
                            updateGrid(grid, i - 1, j - 1);
                        }
                    }
                }
            }
            step++;
        }
        return step;
    }

    public int runPart2(final List<String> lines) {
        return getStepOfCompleteFlash(parseGrid(lines));
    }

    public int runPart1(final List<String> lines) {
        return getNrOfFlashesAfterStep(100, parseGrid(lines));
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day11().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day11().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }

}
