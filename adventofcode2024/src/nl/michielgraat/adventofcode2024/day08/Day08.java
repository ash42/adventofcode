package nl.michielgraat.adventofcode2024.day08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day08 extends AocSolver {

    protected Day08(final String filename) {
        super(filename);
    }

    private char[][] getGrid(final List<String> input) {
        final char[][] grid = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            final String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                grid[y][x] = line.charAt(x);
            }
        }
        return grid;
    }

    private Map<Character, List<Position>> getAntennaToPositionMap(final char[][] grid) {
        final Map<Character, List<Position>> map = new HashMap<>();

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                final char cur = grid[y][x];
                if (cur != '.') {
                    map.putIfAbsent(cur, new ArrayList<>());
                    map.get(cur).add(new Position(x, y));
                }
            }
        }

        return map;
    }

    private void markAntiNodesFromPosition(final Position p1, final List<Position> positions,
            final int[][] antiNodeGrid, final int positionIdx, final int maxLengthX, final int maxLengthY,
            final boolean part1) {
        if (!part1 && positions.size() > 1) {
            // Mark current antenna as an antinode if there are more than 1 of its
            // frequency.
            antiNodeGrid[p1.y()][p1.x()] = antiNodeGrid[p1.y()][p1.x()] + 1;
        }
        for (int j = positionIdx + 1; j < positions.size(); j++) {
            final Position p2 = positions.get(j);
            if (p1.x() != p2.x() && p1.y() != p2.y()) {
                final int x = p1.x();
                final int y = p1.y();
                final int x2 = p2.x();
                final int y2 = p2.y();

                // Mark closest to p1
                int newX = x + (x - x2);
                int newY = y + (y - y2);
                if (part1) {
                    if (newX >= 0 && newY >= 0 && newX < maxLengthX && newY < maxLengthY) {
                        antiNodeGrid[newY][newX] = antiNodeGrid[newY][newX] + 1;
                    }
                } else {
                    while (newX >= 0 && newY >= 0 && newX < maxLengthX && newY < maxLengthY) {
                        antiNodeGrid[newY][newX] = antiNodeGrid[newY][newX] + 1;
                        newX += (x - x2);
                        newY += (y - y2);
                    }
                }

                // Mark closest to p2
                newX = x2 + (x2 - x);
                newY = y2 + (y2 - y);
                if (part1) {
                    if (newX >= 0 && newY >= 0 && newX < maxLengthX && newY < maxLengthY) {
                        antiNodeGrid[newY][newX] = antiNodeGrid[newY][newX] + 1;
                    }
                } else {
                    while (newX >= 0 && newY >= 0 && newX < maxLengthX && newY < maxLengthY) {
                        antiNodeGrid[newY][newX] = antiNodeGrid[newY][newX] + 1;
                        newX += (x2 - x);
                        newY += (y2 - y);
                    }
                }
            }
        }
    }

    private int[][] markAntiNodes(final Map<Character, List<Position>> map, final char[][] grid, final boolean part1) {
        final int[][] antiNodeGrid = new int[grid.length][grid[0].length];

        for (final Entry<Character, List<Position>> entry : map.entrySet()) {
            final List<Position> positions = entry.getValue();
            for (int i = 0; i < positions.size(); i++) {
                final Position p = positions.get(i);
                markAntiNodesFromPosition(p, positions, antiNodeGrid, i, grid[0].length, grid.length, part1);
            }
        }

        return antiNodeGrid;
    }

    private int countUniqueAntennas(final int[][] grid) {
        int total = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] > 0) {
                    total++;
                }
            }
        }
        return total;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final char[][] grid = getGrid(input);
        final Map<Character, List<Position>> map = getAntennaToPositionMap(grid);
        return String.valueOf(countUniqueAntennas(markAntiNodes(map, grid, false)));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final char[][] grid = getGrid(input);
        final Map<Character, List<Position>> map = getAntennaToPositionMap(grid);
        return String.valueOf(countUniqueAntennas(markAntiNodes(map, grid, true)));
    }

    public static void main(final String... args) {
        new Day08("day08.txt");
    }
}

record Position(int x, int y) {
}