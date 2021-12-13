package nl.michielgraat.adventofcode2021.day13;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day13 {

    private static final String FILENAME = "day13.txt";

    private void printGrid(final int[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == 1) {
                    System.out.print("#");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private int getMax(final List<String> lines, final boolean x) {
        int i = 0;
        int max = 0;
        final int index = (x) ? 0 : 1;
        while (!lines.get(i).trim().isEmpty()) {
            final int cur = Integer.parseInt(lines.get(i).split(",")[index]);
            if (cur > max) {
                max = cur;
            }
            i++;
        }
        return max + 1;
    }

    private int[][] parseGrid(final List<String> lines) {
        final int[][] grid = new int[getMax(lines, false)][getMax(lines, true)];
        int i = 0;
        while (!lines.get(i).trim().isEmpty()) {
            final String[] parts = lines.get(i).split(",");
            final int x = Integer.parseInt(parts[0]);
            final int y = Integer.parseInt(parts[1]);
            grid[y][x] = 1;
            i++;
        }
        return grid;
    }

    private int[][] foldLeft(final int[][] grid, final int line) {
        final int[][] result = new int[grid.length][line];

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < line; x++) {
                result[y][x] = grid[y][x];
            }
        }
        int sub = 2;
        for (int y = 0; y < grid.length; y++) {
            for (int x = line + 1; x < grid[0].length; x++) {
                if (result[y][x - sub] != 1) {
                    result[y][x - sub] = grid[y][x];
                }
                sub += 2;
            }
            sub = 2;
        }
        return result;
    }

    private int[][] foldUp(final int[][] grid, final int line) {
        final int[][] result = new int[line][grid[0].length];

        for (int y = 0; y < line; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                result[y][x] = grid[y][x];
            }
        }
        int sub = 2;
        for (int y = line + 1; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (result[y - sub][x] != 1) {
                    result[y - sub][x] = grid[y][x];
                }
            }
            sub += 2;
        }
        return result;
    }

    private List<String> getInstructions(final List<String> lines) {
        return lines.stream().filter(l -> l.startsWith("fold")).collect(Collectors.toList());
    }

    private int[][] runInstruction(final String instruction, final int[][] grid) {
        final int line = Integer.parseInt(instruction.substring(instruction.indexOf("=") + 1));
        if (instruction.contains("x")) {
            return foldLeft(grid, line);
        } else {
            return foldUp(grid, line);
        }
    }

    private int getVisibleDots(final int[][] grid) {
        int total = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                total += grid[y][x];
            }
        }
        return total;
    }

    private int runPart2(final List<String> lines) {
        int[][] grid = parseGrid(lines);
        for (final String in : getInstructions(lines)) {
            grid = runInstruction(in, grid);
        }
        printGrid(grid);
        return 0;
    }

    private int runPart1(final List<String> lines) {
        int[][] grid = parseGrid(lines);
        grid = runInstruction(getInstructions(lines).get(0), grid);
        return getVisibleDots(grid);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day13().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        System.out.println("Answer to part 2: ");
        start = Calendar.getInstance().getTimeInMillis();
        new Day13().runPart2(lines);
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}
