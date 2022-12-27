package nl.michielgraat.adventofcode2022.day24;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day24 extends AocSolver {

    private static final char EMPTY = '.';
    private static final char WALL = '#';

    protected Day24(final String filename) {
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

    private Map<Integer, Set<Position>> getMinuteToBlizzard(final char[][] grid) {
        final Map<Integer, Set<Position>> mToB = new HashMap<>();
        for (int minute = 0; minute <= 1000; minute++) {
            final Set<Position> positions = new HashSet<>();
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[y].length; x++) {
                    if (grid[y][x] == '>') {
                        final int xPos = 1 + (x - 1 + minute) % (grid[y].length - 2);
                        positions.add(new Position(xPos, y));
                    } else if (grid[y][x] == '<') {
                        final int xPos = 1 + Math.floorMod(x - 1 - minute, grid[y].length - 2);
                        positions.add(new Position(xPos, y));
                    } else if (grid[y][x] == 'v') {
                        final int yPos = 1 + (y - 1 + minute) % (grid.length - 2);
                        positions.add(new Position(x, yPos));
                    } else if (grid[y][x] == '^') {
                        final int yPos = 1 + Math.floorMod(y - 1 - minute, grid.length - 2);
                        positions.add(new Position(x, yPos));
                    }
                }
            }
            mToB.put(minute, positions);
        }
        return mToB;
    }

    private int findQuickestRoute(final char[][] grid, final State start, final Map<Integer, Set<Position>> mToB,
            final boolean part1) {
        final Deque<State> queue = new ArrayDeque<>();
        final Set<State> cache = new HashSet<>();
        queue.push(start);
        while (!queue.isEmpty()) {
            State s = queue.removeLast();
            
            if (s.p().x() < 0 || s.p().y() < 0 || s.p().x() >= grid[0].length || s.p().y() >= grid.length
                    || grid[s.p().y()][s.p().x()] == WALL) {
                // off grid or in wall
                continue;
            }
            if (s.p().y() == grid.length - 1) {
                // Reached end
                if (part1 || s.visitedStart()) {
                    return s.minute();
                } else {
                    s = new State(s.p(), s.minute(), false, true);
                }
            }
            if (s.p().y() == 0 && s.visitedEnd()) {
                // Reached start
                s = new State(s.p(), s.minute(), true, s.visitedEnd());
            }
            if (cache.contains(s)) {
                // Already checked this state
                continue;
            }

            cache.add(s);
            final Set<Position> blizzards = mToB.get(s.minute() + 1);
            if (!blizzards.contains(s.p())) {
                // Wait
                queue.push(new State(s.p(), s.minute() + 1, s.visitedStart(), s.visitedEnd()));
            }
            final Position right = new Position(s.p().x() + 1, s.p().y());
            if (!blizzards.contains(right)) {
                queue.push(new State(right, s.minute() + 1, s.visitedStart(), s.visitedEnd()));
            }
            final Position left = new Position(s.p().x() - 1, s.p().y());
            if (!blizzards.contains(left)) {
                queue.push(new State(left, s.minute() + 1, s.visitedStart(), s.visitedEnd()));
            }
            final Position down = new Position(s.p().x(), s.p().y() + 1);
            if (!blizzards.contains(down)) {
                queue.push(new State(down, s.minute() + 1, s.visitedStart(), s.visitedEnd()));
            }
            final Position up = new Position(s.p().x(), s.p().y() - 1);
            if (!blizzards.contains(up)) {
                queue.push(new State(up, s.minute() + 1, s.visitedStart(), s.visitedEnd()));
            }

        }
        return 0;
    }

    private int getStartX(final char[][] grid) {
        for (int x = 0; x < grid.length; x++) {
            if (grid[0][x] == EMPTY) {
                return x;
            }
        }
        return -1;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final char[][] grid = getGrid(input);
        final Map<Integer, Set<Position>> mToB = getMinuteToBlizzard(grid);
        final State start = new State(new Position(getStartX(grid), 0), 0, false, false);
        return String.valueOf(findQuickestRoute(grid, start, mToB, false));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final char[][] grid = getGrid(input);
        final Map<Integer, Set<Position>> mToB = getMinuteToBlizzard(grid);
        final State start = new State(new Position(getStartX(grid), 0), 0, false, false);
        return String.valueOf(findQuickestRoute(grid, start, mToB, true));
    }

    public static void main(final String... args) {
        new Day24("day24.txt");
    }
}
