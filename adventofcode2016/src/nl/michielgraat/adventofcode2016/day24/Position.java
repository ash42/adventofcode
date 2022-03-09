package nl.michielgraat.adventofcode2016.day24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Position {
    int x;
    int y;
    int val;
    Map<Position, Integer> distances = new HashMap<>();

    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
        this.val = -1;
    }

    public Position(final int x, final int y, final int val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }

    public void addDistanceTo(final Position p, final int distance) {
        distances.put(p, distance);
    }

    public Map<Position, Integer> getDistances() {
        return distances;
    }

    public List<Position> getNeighbours(final char[][] grid) {
        final List<Position> neighbours = new ArrayList<>();
        // Up
        int nx = x;
        int ny = y - 1;
        if (ny >= 0 && grid[nx][ny] != '#') {
            neighbours.add(new Position(nx, ny));
        }
        // Down
        nx = x;
        ny = y + 1;
        if (grid[nx][ny] != '#') {
            neighbours.add(new Position(nx, ny));
        }
        // Left
        nx = x - 1;
        ny = y;
        if (nx >= 0 && grid[nx][ny] != '#') {
            neighbours.add(new Position(nx, ny));
        }
        // Right
        nx = x + 1;
        ny = y;
        if (grid[nx][ny] != '#') {
            neighbours.add(new Position(nx, ny));
        }
        return neighbours;
    }

    @Override
    public String toString() {
        return (val >= 0) ? "(" + x + "," + y + "): " + val : "(" + x + "," + y + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

}
