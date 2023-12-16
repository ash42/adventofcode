package nl.michielgraat.adventofcode2023.day16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day16 extends AocSolver {

    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private static Map<LoopDetectionKey, Integer> memoMap = new HashMap<>();

    protected Day16(String filename) {
        super(filename);
    }

    private char[][] readGrid(final List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                grid[y][x] = line.charAt(x);
            }
        }
        return grid;
    }

    /**
     * Gets the number of energized tile if we start at (x,y) and go in a specific
     * direction. This number includes duplicates, that is, if we visit a coordinate
     * more than once, all those visits are counted.
     * 
     * @param x           The x to start at.
     * @param y           The y to start at.
     * @param grid        The grid we are walking.
     * @param direction   The direction we are going in.
     * @param loopSet     A set which contains all pair of (x,y) coordinate &
     *                    direction. This is used for loop detection: if we are at
     *                    an (x,y) and going in a direction which we have seen
     *                    before, we stop (return 0).
     * @param visitedList A list of all coordinates we have visited. This is easy
     *                    for checking which coordinates we have visited more than
     *                    once.
     * @return The nr of energized tiles (with duplicates)
     */
    private int getNrEnergizedTiles(int x, int y, char[][] grid, int direction, Set<LoopDetectionKey> loopSet,
            List<Coordinate> visitedList) {
        // Make sure we haven't visited this space before while going the same
        // direction, if so, we are in a loop, so stop.
        LoopDetectionKey key = new LoopDetectionKey(x, y, direction);
        Coordinate c = new Coordinate(x, y);
        if (x < 0 || y < 0 || y >= grid.length || x >= grid[y].length || loopSet.contains(key)) {
            memoMap.put(key, 0);
            return 0;
        }

        if (memoMap.containsKey(key)) {
            return memoMap.get(key);
        }

        loopSet.add(key);
        visitedList.add(c);

        if (grid[y][x] == '.' || (grid[y][x] == '|' && (direction == UP || direction == DOWN))
                || (grid[y][x] == '-' && (direction == LEFT || direction == RIGHT))) {
            int newX = (direction == UP || direction == DOWN) ? x : (direction == LEFT) ? x - 1 : x + 1;
            int newY = (direction == LEFT || direction == RIGHT) ? y : (direction == UP) ? y - 1 : y + 1;
            int nr = 1 + getNrEnergizedTiles(newX, newY, grid, direction, loopSet, visitedList);
            memoMap.put(key, nr);
            return nr;
        } else if (grid[y][x] == '/') {
            int newX = (direction == LEFT || direction == RIGHT) ? x : (direction == UP) ? x + 1 : x - 1;
            int newY = (direction == UP || direction == DOWN) ? y : (direction == LEFT) ? y + 1 : y - 1;
            int newDirection = direction == LEFT ? DOWN : direction == RIGHT ? UP : direction == UP ? RIGHT : LEFT;
            int nr = 1 + getNrEnergizedTiles(newX, newY, grid, newDirection, loopSet, visitedList);
            memoMap.put(key, nr);
            return nr;
        } else if (grid[y][x] == '\\') {
            int newX = (direction == LEFT || direction == RIGHT) ? x : (direction == UP) ? x - 1 : x + 1;
            int newY = (direction == UP || direction == DOWN) ? y : (direction == LEFT) ? y - 1 : y + 1;
            int newDirection = direction == LEFT ? UP : direction == RIGHT ? DOWN : direction == UP ? LEFT : RIGHT;
            int nr = 1 + getNrEnergizedTiles(newX, newY, grid, newDirection, loopSet, visitedList);
            memoMap.put(key, nr);
            return nr;
        } else if (grid[y][x] == '-') {
            // Direction should always be up or down, since the other cases have already
            // been covered by the first if.
            int nr = 1 + getNrEnergizedTiles(x - 1, y, grid, LEFT, loopSet, visitedList)
                    + getNrEnergizedTiles(x + 1, y, grid, RIGHT, loopSet, visitedList);
            memoMap.put(key, nr);
            return nr;
        } else {
            // Current space is |. Direction should always be left or right, since the other
            // cases have already been covered by the first if.
            int nr = 1 + getNrEnergizedTiles(x, y - 1, grid, UP, loopSet, visitedList)
                    + getNrEnergizedTiles(x, y + 1, grid, DOWN, loopSet, visitedList);
            memoMap.put(key, nr);
            return nr;
        }
    }

    private int getNrUniqueEnergizedTiles(int x, int y, int direction, char[][] grid) {
        List<Coordinate> visited = new ArrayList<>();
        memoMap = new HashMap<>();
        int total = getNrEnergizedTiles(x, y, grid, direction, new HashSet<>(), visited);
        Set<Coordinate> unique = new HashSet<>(visited);
        return total - (visited.size() - unique.size());
    }

    private int getMaxEnergizedTiles(char[][] grid) {
        int max = 0;
        // Top & bottom edge
        for (int x = 1; x < grid[0].length - 1; x++) {
            max = Math.max(max, getNrUniqueEnergizedTiles(x, 0, DOWN, grid));
            max = Math.max(max, getNrUniqueEnergizedTiles(x, grid.length - 1, UP, grid));
        }
        // Left & right edge
        for (int y = 1; y < grid.length - 1; y++) {
            max = Math.max(max, getNrUniqueEnergizedTiles(0, y, RIGHT, grid));
            max = Math.max(max, getNrUniqueEnergizedTiles(grid[y].length - 1, y, LEFT, grid));
        }
        // Corners
        max = Math.max(max, getNrUniqueEnergizedTiles(0, 0, DOWN, grid));
        max = Math.max(max, getNrUniqueEnergizedTiles(0, 0, RIGHT, grid));

        max = Math.max(max, getNrUniqueEnergizedTiles(grid[0].length - 1, 0, DOWN, grid));
        max = Math.max(max, getNrUniqueEnergizedTiles(grid[0].length - 1, 0, LEFT, grid));

        max = Math.max(max, getNrUniqueEnergizedTiles(0, grid.length - 1, UP, grid));
        max = Math.max(max, getNrUniqueEnergizedTiles(0, grid.length - 1, RIGHT, grid));

        max = Math.max(max, getNrUniqueEnergizedTiles(grid[0].length - 1, grid.length - 1, UP, grid));
        max = Math.max(max, getNrUniqueEnergizedTiles(grid[0].length - 1, grid.length - 1, LEFT, grid));
        return max;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(getMaxEnergizedTiles(readGrid(input)));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getNrUniqueEnergizedTiles(0, 0, RIGHT, readGrid(input)));
    }

    public static void main(String... args) {
        new Day16("day16.txt");
    }
}