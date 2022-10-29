package nl.michielgraat.adventofcode2018.day17;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day17 {
    private static final String FILENAME = "day17.txt";

    private static final char SAND = '.';
    private static final char CLAY = '#';
    private static final char FLOWING_WATER = '|';
    private static final char WATER = '~';

    private List<Line> parseLines(final List<String> lines) {
        final List<Line> iLines = new ArrayList<>();
        for (final String line : lines) {
            final String[] parts = line.split(", ");
            final boolean xFirst = parts[0].startsWith("x");
            final int part1Val = Integer.parseInt(parts[0].split("=")[1]);
            final int part2Val1 = Integer.parseInt(parts[1].substring(parts[1].indexOf("=") + 1, parts[1].indexOf("..")));
            final int part2Val2 = Integer.parseInt(parts[1].substring(parts[1].indexOf("..") + 2));
            final Line iLine = new Line(xFirst ? part1Val : part2Val1, xFirst ? null : part2Val2,
                    xFirst ? part2Val1 : part1Val, xFirst ? part2Val2 : null);
            iLines.add(iLine);
        }
        return iLines;
    }

    private char[][] buildGrid(final List<Line> lines, final int maxX, final int maxY) {
        final char[][] grid = new char[maxX + 1][maxY + 1];
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                grid[x][y] = SAND;
            }
        }
        for (final Line line : lines) {
            for (int y = line.y1; y <= (line.y2 == null ? line.y1 : line.y2); y++) {
                for (int x = line.x1; x <= (line.x2 == null ? line.x1 : line.x2); x++) {
                    grid[x][y] = CLAY;
                }

            }
        }
        return grid;
    }

    private int getMaxY(final List<Line> lines) {
        return Math.max(lines.stream().mapToInt(i -> i.y1).max().getAsInt(),
                lines.stream().filter(i -> i.y2 != null).mapToInt(i -> i.y2).max().getAsInt());
    }

    private int getMinY(final List<Line> lines) {
        return lines.stream().filter(Objects::nonNull).mapToInt(i -> i.y1).min().getAsInt();
    }

    private int getMaxX(final List<Line> lines) {
        return (Math.max(lines.stream().mapToInt(i -> i.x1).max().getAsInt(),
                lines.stream().filter(i -> i.x2 != null).mapToInt(i -> i.x2).max().getAsInt())) + 1;
    }

    private int getMinX(final List<Line> lines) {
        return lines.stream().filter(Objects::nonNull).mapToInt(i -> i.x1).min().getAsInt() - 1;
    }

    private void printGrid(final char[][] grid, final int minX) {
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = minX; x < grid.length; x++) {
                System.out.print(grid[x][y]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean hasSubsurface(final char[][] grid, final int x, final int y) {
        return grid[x][y] == CLAY || grid[x][y] == WATER;
    }

    private int getClayLeft(final char[][] grid, final int x, final int y, final int minX) {
        for (int x1 = x; x1 >= minX; x1--) {
            if (grid[x1][y] == CLAY) {
                return x1;
            }
        }
        return -1;
    }

    private int getClayRight(final char[][] grid, final int x, final int y, final int maxX) {
        for (int x1 = x; x1 <= maxX; x1++) {
            if (grid[x1][y] == CLAY) {
                return x1;
            }
        }
        return -1;
    }

    private boolean canSettle(final char[][] grid, final int leftX, final int rightX, final int y) {
        for (int x = leftX + 1; x < rightX; x++) {
            if (grid[x][y + 1] != CLAY && grid[x][y + 1] != WATER) {
                return false;
            }
        }
        return true;
    }

    private void flowLeft(final char[][] grid, final int x, final int y) {
        int sub = -1;
        while (true) {
            final int x1 = x + sub;
            if (grid[x1][y] == CLAY) {
                return;
            }
            grid[x1][y] = WATER;
            sub--;
        }
    }

    private void flowRight(final char[][] grid, final int x, final int y) {
        int add = 1;
        while (true) {
            final int x1 = x + add;
            if (grid[x1][y] == CLAY) {
                return;
            }
            grid[x1][y] = WATER;
            add++;
        }
    }

    private void flow(final char[][] grid, final int x, final int y, final int minX, final int maxX, final int maxY) {
        if (y >= maxY) {
            return;
        }

        if (grid[x][y + 1] == SAND) {
            if (y >= 0) {
                grid[x][y + 1] = FLOWING_WATER;
            }
            flow(grid, x, y + 1, minX, maxX, maxY);
        }

        if (hasSubsurface(grid, x, y + 1) && grid[x - 1][y] == SAND) {
            grid[x - 1][y] = FLOWING_WATER;
            flow(grid, x - 1, y, minX, maxX, maxY);
        }

        if (hasSubsurface(grid, x, y + 1) && grid[x + 1][y] == SAND) {
            grid[x + 1][y] = FLOWING_WATER;
            flow(grid, x + 1, y, minX, maxX, maxY);
        }

        final int left = getClayLeft(grid, x, y, minX);
        final int right = getClayRight(grid, x, y, maxX);
        if (left >= 0 && right >= 0 && canSettle(grid, left, right, y)) {
            flowLeft(grid, x, y);
            flowRight(grid, x, y);
            grid[x][y] = WATER;
        }
    }

    private int getAllWater(final char[][] grid, final int minY) {
        int total = 0;
        for (int y = minY; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] == WATER || grid[x][y] == FLOWING_WATER) {
                    total++;
                }
            }
        }
        return total;
    }

    private int getRetainedWater(final char[][] grid, final int minY) {
        int total = 0;
        for (int y = minY; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] == WATER) {
                    total++;
                }
            }
        }
        return total;
    }

    private int getAnswer(final List<String> lines, final boolean part1) {
        final List<Line> iLines = parseLines(lines);
        final int minX = getMinX(iLines);
        final int maxX = getMaxX(iLines);
        final int minY = getMinY(iLines);
        final int maxY = getMaxY(iLines);
        final char[][] grid = buildGrid(iLines, maxX, maxY);
        flow(grid, 500, 0, minX, maxX, maxY);
        // printGrid(grid, minX);
        return part1 ? getAllWater(grid, minY) : getRetainedWater(grid, minY);
    }

    public int runPart2(final List<String> lines) {
        return getAnswer(lines, false);
    }

    public int runPart1(final List<String> lines) {
        return getAnswer(lines, true);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day17().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day17().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}