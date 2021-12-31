package nl.michielgraat.adventofcode2021.day25;

import java.util.Arrays;
import java.util.List;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day25 {

    private static final String FILENAME = "day25.txt";

    private char[][] getGrid(List<String> lines) {
        char[][] grid = new char[lines.size()][lines.get(0).length()];
        int y = 0;
        for (String line : lines) {
            for (int x = 0; x < line.length(); x++) {
                grid[y][x] = line.charAt(x);
            }
            y++;
        }
        return grid;
    }

    private char[][] copyGrid(char[][] grid) {
        char[][] newGrid = new char[grid.length][grid[0].length];
        for (int y = 0; y < grid.length; y++) {
            newGrid[y] = Arrays.copyOf(grid[y], grid[y].length);
        }
        return newGrid;
    }

    private char[][] move(char[][] current, char cucumber) {
        char[][] newGrid = copyGrid(current);
        for (int y = 0; y < current.length; y++) {
            for (int x = 0; x < current[0].length; x++) {
                int xVal = cucumber == '>' ? (x + 1) % current[0].length : x;
                int yVal = cucumber == '>' ? y : (y + 1) % current.length;
                if (current[y][x] == cucumber && current[yVal][xVal] == '.') {
                    newGrid[y][x] = '.';
                    newGrid[yVal][xVal] = cucumber;

                }
            }
        }
        return newGrid;
    }

    private int run(List<String> lines) {
        char[][] current = getGrid(lines);
        boolean moving = true;
        int i = 1;
        while (moving) {
            // Move east
            char[][] newGrid = move(current, '>');
            moving = !Arrays.deepEquals(current, newGrid);
            current = newGrid;
            
            // Move south
            newGrid = move(current, 'v');
            moving = moving || !Arrays.deepEquals(current, newGrid);
            current = newGrid;

            if (moving) {
                i++;
            }
        }
        return i;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer: " + new Day25().run(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
