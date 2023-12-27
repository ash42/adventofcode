package nl.michielgraat.adventofcode2023.day23;

import java.util.Date;
import java.util.List;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day23 extends AocSolver {

    protected Day23(String filename) {
        super(filename);
    }

    private char[][] readGrid(List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            grid[y] = line.toCharArray();
        }
        return grid;
    }

    private Coordinate getStart(List<String> input) {
        String line = input.get(0);
        return new Coordinate(line.indexOf('.'), 0);
    }

    private Coordinate getEnd(List<String> input) {
        String line = input.get(input.size() - 1);
        return new Coordinate(line.indexOf('.'), input.size() - 1);
    }

    private int getPathLength(char[][] grid) {
        int totalO = 0;

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 'O') {
                    totalO++;
                }
            }
        }
        return totalO;
    }

    //Important: increase stack size to at least 2M (-Xss2m)
    private int getLongestPath(int startX, int startY, int endX, int endY, char[][] grid, char[][] origGrid, boolean part1) {

        if (grid[startY][startX] == 'O') {
            return 0;
        } else if (startX == endX && startY == endY) {
            return getPathLength(grid);
        } else {
            grid[startY][startX] = 'O';
            int maxLength = 0;

            if (part1 && origGrid[startY][startX] == '>') {
                if (startX + 1 < grid[startY].length && grid[startY][startX + 1] != '#') {
                    int nextLength = getLongestPath(startX + 1, startY, endX, endY, grid, origGrid,part1);
                    if (nextLength > maxLength) {
                        maxLength = nextLength;
                    }
                }
            } else if (part1 && origGrid[startY][startX] == '<') {
                if (startX - 1 >= 0 && grid[startY][startX - 1] != '#') {
                    int nextLength = getLongestPath(startX - 1, startY, endX, endY, grid,origGrid, part1);
                    if (nextLength > maxLength) {
                        maxLength = nextLength;
                    }
                }
            } else if (part1 && origGrid[startY][startX] == '^') {
                if (startY - 1 >= 0 && grid[startY - 1][startX] != '#') {
                    int nextLength = getLongestPath(startX, startY - 1, endX, endY, grid, origGrid,part1);
                    if (nextLength > maxLength) {
                        maxLength = nextLength;
                    }
                }
            } else if (part1 && origGrid[startY][startX] == 'v') {
                if (startY + 1 < grid.length
                        && grid[startY + 1][startX] != '#') {
                    int nextLength = getLongestPath(startX, startY + 1, endX, endY, grid,origGrid, part1);
                    if (nextLength > maxLength) {
                        maxLength = nextLength;
                    }
                }
            } else {
                if (startY - 1 >= 0 && grid[startY - 1][startX] != '#') {
                    int nextLength = getLongestPath(startX, startY - 1, endX, endY, grid,origGrid, part1);
                    if (nextLength > maxLength) {
                        maxLength = nextLength;
                    }
                }
                if (startX + 1 < grid[startY].length && grid[startY][startX + 1] != '#') {
                    int nextLength = getLongestPath(startX + 1, startY, endX, endY, grid,origGrid, part1);
                    if (nextLength > maxLength) {
                        maxLength = nextLength;
                    }
                }
                if (startY + 1 < grid.length && grid[startY + 1][startX] != '#') {
                    int nextLength = getLongestPath(startX, startY + 1, endX, endY, grid,origGrid, part1);
                    if (nextLength > maxLength) {
                        maxLength = nextLength;
                    }
                }
                if (startX - 1 >= 0 && grid[startY][startX - 1] != '#') {
                    int nextLength = getLongestPath(startX - 1, startY, endX, endY, grid, origGrid,part1);
                    if (nextLength > maxLength) {
                        maxLength = nextLength;
                    }
                }
            }
            grid[startY][startX] = '.';
            return maxLength;
        }
    }

    @Override
    protected String runPart2(final List<String> input) {
        System.out.println(new Date());
        return String.valueOf(getLongestPath(getStart(input).x(), getStart(input).y(), getEnd(input).x(),
                getEnd(input).y(), readGrid(input), readGrid(input),false));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getLongestPath(getStart(input).x(), getStart(input).y(), getEnd(input).x(),
                getEnd(input).y(), readGrid(input), readGrid(input),true));
    }

    public static void main(String... args) {
        new Day23("day23.txt");
    }
}
