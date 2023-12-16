package nl.michielgraat.adventofcode2023.day16;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day16 extends AocSolver {

    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

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
     * Gets the number of unique energized tiles if we start at (x,y) and go in a
     * specific direction.
     * 
     * @param x          The x to start at.
     * @param y          The y to start at.
     * @param grid       The grid we are walking.
     * @param direction  The direction we are going in.
     * @param loopSet    A set which contains all pair of (x,y) coordinate &
     *                   direction. This is used for loop detection: if we are at
     *                   an (x,y) and going in a direction which we have seen
     *                   before, we stop (return 0).
     * @param visitedSet A set which contains all pairs of (x,y) coordinates. Used
     *                   to check if we have visited this coordinate before, to
     *                   prevent counting energized tiles more than once.
     * @return The number of unique energized tiles.
     */
    private int getNrEnergizedTiles(int x, int y, char[][] grid, int direction, Set<LoopDetectionKey> loopSet,
            Set<Coordinate> visitedSet) {
        // Make sure we haven't visited this space before while going the same
        // direction, if so, we are in a loop, so stop.
        LoopDetectionKey key = new LoopDetectionKey(x, y, direction);
        if (x < 0 || y < 0 || y >= grid.length || x >= grid[y].length || loopSet.contains(key)) {
            return 0;
        }

        // If we have not visited this tile before we will add 1 to the total of tiles,
        // otherwise 0. We could also do this on the basis of the loopSet, but than we
        // would need to check all direction (except the direction we are currently
        // going in as we have already that checked to see if we are not in a loop), so
        // this is IMHO slightly more readable.
        Coordinate coordinate = new Coordinate(x, y);
        int nrToAdd = visitedSet.contains(coordinate) ? 0 : 1;

        loopSet.add(key);
        visitedSet.add(coordinate);

        if (grid[y][x] == '.' || (grid[y][x] == '|' && (direction == UP || direction == DOWN))
                || (grid[y][x] == '-' && (direction == LEFT || direction == RIGHT))) {
            // Tile is empty or contains a splitter, but we are approaching it from the pointy end.
            int newX = (direction == UP || direction == DOWN) ? x : (direction == LEFT) ? x - 1 : x + 1;
            int newY = (direction == LEFT || direction == RIGHT) ? y : (direction == UP) ? y - 1 : y + 1;
            int nr = nrToAdd + getNrEnergizedTiles(newX, newY, grid, direction, loopSet, visitedSet);
            return nr;
        } else if (grid[y][x] == '/') {
            int newX = (direction == LEFT || direction == RIGHT) ? x : (direction == UP) ? x + 1 : x - 1;
            int newY = (direction == UP || direction == DOWN) ? y : (direction == LEFT) ? y + 1 : y - 1;
            int newDirection = direction == LEFT ? DOWN : direction == RIGHT ? UP : direction == UP ? RIGHT : LEFT;
            int nr = nrToAdd + getNrEnergizedTiles(newX, newY, grid, newDirection, loopSet, visitedSet);
            return nr;
        } else if (grid[y][x] == '\\') {
            int newX = (direction == LEFT || direction == RIGHT) ? x : (direction == UP) ? x - 1 : x + 1;
            int newY = (direction == UP || direction == DOWN) ? y : (direction == LEFT) ? y - 1 : y + 1;
            int newDirection = direction == LEFT ? UP : direction == RIGHT ? DOWN : direction == UP ? LEFT : RIGHT;
            int nr = nrToAdd + getNrEnergizedTiles(newX, newY, grid, newDirection, loopSet, visitedSet);
            return nr;
        } else if (grid[y][x] == '-') {
            // Direction should always be up or down, since the other cases have already
            // been covered by the first if.
            int nr = nrToAdd + getNrEnergizedTiles(x - 1, y, grid, LEFT, loopSet, visitedSet)
                    + getNrEnergizedTiles(x + 1, y, grid, RIGHT, loopSet, visitedSet);
            return nr;
        } else {
            // Current space is |. Direction should always be left or right, since the other
            // cases have already been covered by the first if.
            int nr = nrToAdd + getNrEnergizedTiles(x, y - 1, grid, UP, loopSet, visitedSet)
                    + getNrEnergizedTiles(x, y + 1, grid, DOWN, loopSet, visitedSet);
            return nr;
        }
    }

    private int getNrUniqueEnergizedTiles(int x, int y, int direction, char[][] grid) {
        int total = getNrEnergizedTiles(x, y, grid, direction, new HashSet<>(), new HashSet<>());
        return total;
    }

    private int getMaxEnergizedTiles(char[][] grid) {
        int max = 0;
        // Top & bottom edge
        for (int x = 0; x < grid[0].length; x++) {
            max = Math.max(max, getNrUniqueEnergizedTiles(x, 0, DOWN, grid));
            max = Math.max(max, getNrUniqueEnergizedTiles(x, grid.length - 1, UP, grid));
        }
        // Left & right edge
        for (int y = 0; y < grid.length; y++) {
            max = Math.max(max, getNrUniqueEnergizedTiles(0, y, RIGHT, grid));
            max = Math.max(max, getNrUniqueEnergizedTiles(grid[y].length - 1, y, LEFT, grid));
        }
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