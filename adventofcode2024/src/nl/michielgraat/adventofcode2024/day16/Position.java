package nl.michielgraat.adventofcode2024.day16;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Position(int x, int y, int direction) implements Comparable<Position> {

    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    private boolean isInBounds(final int x, final int y, final char[][] grid) {
        return x >= 0 && y >= 0 && y < grid.length && x < grid[0].length;
    }

    private boolean isReachablePosition(final int x, final int y, final char[][] grid) {
        return isInBounds(x, y, grid) && grid[y][x] != '#';
    }

    public Optional<Position> getNeighbours(final char[][] grid) {
        switch (this.direction()) {
            case NORTH:
                if (isReachablePosition(x, y - 1, grid)) {
                    return Optional.of(new Position(x, y - 1, NORTH));
                }
                break;
            case EAST:
                if (isReachablePosition(x + 1, y, grid)) {
                    return Optional.of(new Position(x + 1, y, EAST));
                }
                break;
            case SOUTH:
                if (isReachablePosition(x, y + 1, grid)) {
                    return Optional.of(new Position(x, y + 1, SOUTH));
                }
                break;
            default:
                if (isReachablePosition(x - 1, y, grid)) {
                    return Optional.of(new Position(x - 1, y, WEST));
                }
        }
        return Optional.empty();
    }

    public Optional<Position> getNeighbourReversed(final char[][] grid) {
        switch (this.direction) {
            case NORTH:
                if (isReachablePosition(x, y + 1, grid)) {
                    return Optional.of(new Position(x, y + 1, NORTH));
                }
                break;
            case EAST:
                if (isReachablePosition(x - 1, y, grid)) {
                    return Optional.of(new Position(x - 1, y, EAST));
                }
                break;
            case SOUTH:
                if (isReachablePosition(x, y - 1, grid)) {
                    return Optional.of(new Position(x, y - 1, SOUTH));
                }
                break;
            default:
                if (isReachablePosition(x + 1, y, grid)) {
                    return Optional.of(new Position(x + 1, y, WEST));
                }
        }
        return Optional.empty();
    }

    public List<Position> getTurns() {
        final List<Position> turns = new ArrayList<>();
        switch (direction) {
            case NORTH:
            case SOUTH:
                turns.add(new Position(x, y, EAST));
                turns.add(new Position(x, y, WEST));
                break;
            case EAST:
            case WEST:
                turns.add(new Position(x, y, NORTH));
                turns.add(new Position(x, y, SOUTH));
                break;
        }
        return turns;
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
