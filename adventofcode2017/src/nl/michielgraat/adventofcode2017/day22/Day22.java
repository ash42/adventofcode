package nl.michielgraat.adventofcode2017.day22;

import java.util.List;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day22 {

    private static final String FILENAME = "day22.txt";

    private static final int DIM = 450;

    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;

    private int getVirusStart(final List<String> lines) {
        final int posInInitialGrid = (lines.size() / 2);
        return ((DIM - lines.size()) / 2) + posInInitialGrid;
    }

    private char[][] getGrid(final List<String> lines) {
        final char[][] grid = new char[DIM][DIM];
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                grid[i][j] = '.';
            }
        }

        final int initialSize = lines.size();
        final int startX = (DIM - initialSize) / 2;
        final int startY = startX;

        for (int y = startY; y < startY + initialSize; y++) {
            final String line = lines.get(y - startY);
            for (int x = startX; x < startX + initialSize; x++) {
                grid[y][x] = line.charAt(x - startX);
            }
        }

        return grid;
    }

    private int turnRight(final int d) {
        return (d + 1) % 4;
    }

    private int turnLeft(final int d) {
        return (d == UP) ? LEFT : (d - 1) % 4;
    }

    private int revert(final int d) {
        return (d + 2) % 4;
    }

    public int runPart2(final List<String> lines) {
        final char[][] grid = getGrid(lines);
        int x = getVirusStart(lines);
        int y = x;
        int dir = UP;
        int infected = 0;

        for (int i = 0; i < 10000000; i++) {
            if (grid[y][x] == '.') {
                dir = turnLeft(dir);
                grid[y][x] = 'W';
            } else if (grid[y][x] == 'W') {
                grid[y][x] = '#';
                infected++;
            } else if (grid[y][x] == '#') {
                dir = turnRight(dir);
                grid[y][x] = 'F';
            } else {
                dir = revert(dir);
                grid[y][x] = '.';
            }
            if (dir == UP) {
                y--;
            } else if (dir == LEFT) {
                x--;
            } else if (dir == DOWN) {
                y++;
            } else if (dir == RIGHT) {
                x++;
            } else {
                throw new IllegalArgumentException("Unknown direction " + dir);
            }
        }
        return infected;
    }

    public int runPart1(final List<String> lines) {
        final char[][] grid = getGrid(lines);
        int x = getVirusStart(lines);
        int y = x;
        int dir = UP;
        int infected = 0;

        for (int i = 0; i < 10000; i++) {
            if (grid[y][x] == '#') {
                dir = turnRight(dir);
                grid[y][x] = '.';
            } else {
                dir = turnLeft(dir);
                grid[y][x] = '#';
                infected++;
            }
            if (dir == UP) {
                y--;
            } else if (dir == LEFT) {
                x--;
            } else if (dir == DOWN) {
                y++;
            } else if (dir == RIGHT) {
                x++;
            } else {
                throw new IllegalArgumentException("Unknown direction " + dir);
            }
        }
        return infected;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day22().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day22().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
