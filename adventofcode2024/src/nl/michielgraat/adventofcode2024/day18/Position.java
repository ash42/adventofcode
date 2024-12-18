package nl.michielgraat.adventofcode2024.day18;

import java.util.ArrayList;
import java.util.List;

public record Position(int x, int y) implements Comparable<Position> {

    private boolean isInBounds(final int x, final int y, final int[][] grid) {
        return x >= 0 && y >= 0 && y < grid.length && x < grid[0].length;
    }

    private boolean isReachablePosition(final int x, final int y, final int[][] grid) {
        return isInBounds(x, y, grid) && grid[y][x] != 1;
    }

    public List<Position> getNeighbours(final int[][] grid) {
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

    @Override
    public int compareTo(final Position o) {
        if (this.y != o.y()) {
            return Integer.compare(this.y, o.y());
        } else {
            return Integer.compare(this.x, o.x());
        }
    }
}
