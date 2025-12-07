package nl.michielgraat.adventofcode2025.day07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.michielgraat.adventofcode2025.AocSolver;

public class Day07 extends AocSolver {

    protected Day07(final String filename) {
        super(filename);
    }

    private char[][] parseGrid(final List<String> input) {
        final char[][] grid = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            final String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                grid[y][x] = line.charAt(x);
            }
        }
        return grid;
    }

    private boolean canBePartOfBeam(final int x, final int y, final char[][] grid) {
        if (y < 0 || x < 0 || x >= grid[y].length) {
            // Out of bounds.
            return false;
        } else if (grid[y][x] == 'S') {
            // Reached S, so there is a beam from the splitter to S.
            return true;
        } else if ((x - 1 >= 0 && grid[y][x - 1] == '^') || (x + 1 < grid[y].length && grid[y][x + 1] == '^')) {
            // There is a splitter left or right from the current path, this means that the
            // initial splitter can be reached from here (because the beam has split at the
            // splitter left or right), so it can be part of the beam.
            // Note: This shouldn't work for cases in which there is another splitter above
            // the left or right splitters, but it seems to work on all inputs (@see
            // https://www.reddit.com/r/adventofcode/comments/1pgpxcn/2025_day_7_part_1_is_this_just_luck_or_am_i/
            // ). However, this does not completely sit right with me, so I have another
            // implementation: findNrOfSplitsBetter :)
            return true;
        } else if (grid[y][x] == '^') {
            // Found another splitter directly above the splitter we started from, so there
            // is no path possible from the starting splitter.
            return false;
        } else {
            // Just continue going up.
            return canBePartOfBeam(x, y - 1, grid);
        }
    }

    private int findNrOfSplits(final char[][] grid) {
        // The beam can only be split at splitters, so just loop over every splitter in
        // the grid and see if there is a path possible from the splitter back up.
        int total = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == '^') {
                    if (canBePartOfBeam(x, y - 1, grid)) {
                        total++;
                    }
                }
            }
        }
        return total;
    }

    private int findNrOfSplitsBetter(final char[][] grid) {
        // Start at S, store its x-coordinate in a set (since x-coordinates represent
        // the position of the beam), if we encounter a splitter, we add the x-values of
        // the coordinates next to it to the set, and remove the current x. And of
        // course, add 1 to the counter for total number of splits.
        final Coordinate start = findStart(grid);
        final Set<Integer> xCoords = new HashSet<>();
        xCoords.add(start.x());
        int totalNrOfSplits = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == '^') {
                    if (xCoords.contains(x)) {
                        if (x - 1 >= 0 && grid[y][x - 1] != '^') {
                            xCoords.add(x - 1);
                        }
                        if (x + 1 < grid[y].length && grid[y][x + 1] != '^') {
                            xCoords.add(x + 1);
                        }
                        xCoords.remove(x);
                        totalNrOfSplits++;
                    }
                }
            }
        }
        return totalNrOfSplits;
    }

    private Coordinate findStart(final char[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 'S') {
                    return new Coordinate(x, y);
                }
            }
        }
        throw new IllegalArgumentException("Grid does not have starting coordinate.");
    }

    private List<Coordinate> findNeighbours(final Coordinate start, final char[][] grid) {
        final List<Coordinate> result = new ArrayList<>();
        final int x = start.x();
        final int y = start.y();
        if (y < 0 || y >= grid.length || x < 0 || x >= grid[y].length) {
            return result;
        }
        if (y + 1 < grid.length) {
            if (grid[y + 1][x] == '^') {
                // Split.
                if (x - 1 >= 0) {
                    result.add(new Coordinate(x - 1, y + 1));
                }
                if (x + 1 < grid[y].length) {
                    result.add(new Coordinate(x + 1, y + 1));
                }
            } else {
                // Go straight down.
                result.add(new Coordinate(x, y + 1));
            }
        }
        return result;
    }

    private long countNrPaths(final Coordinate start, final char[][] grid, final Map<Coordinate, Long> memoMap) {
        if (memoMap.containsKey(start)) {
            // We have been here before, so just return the number of paths from here to the
            // end from our cache.
            return memoMap.get(start);
        }
        final int x = start.x();
        final int y = start.y();
        long paths = 0;

        if (y < 0 || y >= grid.length || x < 0 || x >= grid[y].length) {
            // Out of bounds.
            return 0;
        } else if (y == grid.length - 1) {
            // At the bottom row, so we have finished the path.
            return 1;
        }

        // Not out of bounds or at the end, so continue building our path with the
        // neighbours of this coordinate.
        for (final Coordinate neighbour : findNeighbours(start, grid)) {
            paths += countNrPaths(neighbour, grid, memoMap);
        }

        // Do not forget to update the cache.
        memoMap.put(start, paths);

        return paths;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final char[][] grid = parseGrid(input);
        return String.valueOf(countNrPaths(findStart(grid), grid, new HashMap<>()));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(findNrOfSplitsBetter(parseGrid(input)));
    }

    public static void main(final String... args) {
        new Day07("day07.txt");
    }
}
