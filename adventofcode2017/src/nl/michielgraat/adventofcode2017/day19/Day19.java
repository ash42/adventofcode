package nl.michielgraat.adventofcode2017.day19;

import java.util.List;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day19 {

    private static final String FILENAME = "day19.txt";

    private int getInitialX(final char[][] grid) {
        for (int x = 0; x < grid.length; x++) {
            if (grid[x][0] == '|') {
                return x;
            }
        }
        return 0;
    }

    private char[][] getGrid(final List<String> lines) {
        final char[][] grid = new char[lines.get(0).length()][lines.size()];
        for (int l = 0; l < lines.size(); l++) {
            final String line = lines.get(l);
            for (int i = 0; i < line.length(); i++) {
                grid[i][l] = line.charAt(i);
            }
        }
        return grid;
    }

    private String findRoute(int x, final char[][] grid, final boolean part1) {
        int y = 0;
        Direction d = Direction.DOWN;
        final StringBuilder sb = new StringBuilder();
        boolean finished = false;
        int steps = 0;
        while (!finished) {
            final char c = grid[x][y];
            if (c == ' ') {
                finished = true;
            } else if (c != '+') {
                steps++;
                if (c != '|' && c != '-') {
                    sb.append(c);
                }
                if (d == Direction.DOWN && (y + 1) < grid[0].length) {
                    y++;
                } else if (d == Direction.UP && (y - 1) >= 0) {
                    y--;
                } else if (d == Direction.LEFT && (x - 1) >= 0) {
                    x--;
                } else if (d == Direction.RIGHT && (x + 1) < grid.length) {
                    x++;
                }
            } else {
                if (d != Direction.DOWN && (y - 1) >= 0 && grid[x][y - 1] != ' ') {
                    d = Direction.UP;
                    y--;
                    steps++;
                } else if (d != Direction.UP && (y + 1) < grid[0].length && grid[x][y + 1] != ' ') {
                    d = Direction.DOWN;
                    y++;
                    steps++;
                } else if (d != Direction.LEFT && (x + 1) < grid.length && grid[x + 1][y] != ' ') {
                    d = Direction.RIGHT;
                    x++;
                    steps++;
                } else if (d != Direction.RIGHT && (x - 1) >= 0 && grid[x - 1][y] != ' ') {
                    d = Direction.LEFT;
                    x--;
                    steps++;
                } else {
                    finished = true;
                }
            }
        }

        return part1 ? sb.toString() : String.valueOf(steps);

    }

    public String runPart2(final List<String> lines) {
        final char[][] grid = getGrid(lines);
        return findRoute(getInitialX(grid), getGrid(lines), false);
    }

    public String runPart1(final List<String> lines) {
        final char[][] grid = getGrid(lines);
        return findRoute(getInitialX(grid), getGrid(lines), true);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day19().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day19().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}

enum Direction {
    UP, DOWN, LEFT, RIGHT;
}
