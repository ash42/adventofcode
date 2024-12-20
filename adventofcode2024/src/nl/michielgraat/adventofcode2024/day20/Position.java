package nl.michielgraat.adventofcode2024.day20;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record Position(int x, int y) implements Comparable<Position> {

    private boolean isInBounds(final int x, final int y, final char[][] grid) {
        return x >= 0 && y >= 0 && y < grid.length && x < grid[0].length;
    }

    private boolean isReachablePosition(final int x, final int y, final char[][] grid) {
        return isInBounds(x, y, grid) && grid[y][x] != '#';
    }

    public List<Position> getNeighbours(final char[][] grid) {
        final List<Position> neighbours = new ArrayList<>();

        if (isReachablePosition(x - 1, y, grid)) {
            neighbours.add(new Position(x - 1, y));
        }
        if (isReachablePosition(x + 1, y, grid)) {
            neighbours.add(new Position(x + 1, y));
        }
        if (isReachablePosition(x, y - 1, grid)) {
            neighbours.add(new Position(x, y - 1));
        }
        if (isReachablePosition(x, y + 1, grid)) {
            neighbours.add(new Position(x, y + 1));
        }
        return neighbours;
    }

    public List<Position> getShortCuts(final char[][] grid) {
        final List<Position> shortCuts = new ArrayList<>();

        if (this.isReachablePosition(x, y, grid)) {
            if (grid[y][x - 1] == '#' && isReachablePosition(x - 2, y, grid)) {
                shortCuts.add(new Position(x - 2, y));
            }
            if (grid[y][x + 1] == '#' && isReachablePosition(x + 2, y, grid)) {
                shortCuts.add(new Position(x + 2, y));
            }
            if (grid[y - 1][x] == '#' && isReachablePosition(x, y - 2, grid)) {
                shortCuts.add(new Position(x, y - 2));
            }
            if (grid[y + 1][x] == '#' && isReachablePosition(x, y + 2, grid)) {
                shortCuts.add(new Position(x, y + 2));
            }
        }

        return shortCuts;
    }

    public int getManhattanDistance(final int x, final int y) {
        return Math.abs(this.x - x) + Math.abs(this.y - y);
    }

    public List<Position> getAllShortCutsWithMaxDistance(final int maxDistance, final char[][] grid,
            final Map<Position, Integer> distances) {
        final List<Position> neighbours = new ArrayList<>();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                // Add all end positions for which the following is true:
                // 1. The manhattan distance to the end position should be less than the max
                // distance, for part 1 this is 2, for part 2 this 20.
                // 2. The distance should be more than 1 (otherwise it is not a short cut).
                // 3. The end position should be on the path.
                // 4. The manhattan distance should be less than the distance to the end
                // position on the "normal" path (the path without cheating).
                if (getManhattanDistance(x, y) <= maxDistance && getManhattanDistance(x, y) > 1
                        && distances.containsKey(new Position(x, y))
                        && getManhattanDistance(x, y) < distances.get(new Position(x, y))
                                - distances.get(new Position(this.x, this.y))) {
                    neighbours.add(new Position(x, y));
                }
            }
        }
        return neighbours;
    }

    @Override
    public int compareTo(final Position o) {
        if (this.y != o.y()) {
            return Integer.compare(this.y, o.y());
        } else {
            return Integer.compare(this.x, o.x());
        }
    }
}
