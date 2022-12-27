package nl.michielgraat.adventofcode2022.day23;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record Position(int x, int y) {
    private static final int N = 0;
    private static final int S = 1;
    private static final int W = 2;
    private static final int E = 3;

    public boolean isGoingToMove(final List<Position> grove) {
        for (final Position p : getAllSurrounding()) {
            if (grove.indexOf(p) != -1) {
                return true;
            }
        }
        return false;
    }

    public Position getNextPosition(final int direction) {
        switch (direction) {
            case N:
                return new Position(x, y - 1);
            case S:
                return new Position(x, y + 1);
            case W:
                return new Position(x - 1, y);
            default:
                return new Position(x + 1, y);
        }
    }

    public Set<Position> getAllSurrounding() {
        final Set<Position> positions = new HashSet<>();
        positions.addAll(getNorthPositions());
        positions.addAll(getSouthPositions());
        positions.addAll(getWestPositions());
        positions.addAll(getEastPositions());
        return positions;
    }

    public Set<Position> getNorthPositions() {
        final Set<Position> positions = new HashSet<>();
        positions.add(new Position(x - 1, y - 1));
        positions.add(new Position(x, y - 1));
        positions.add(new Position(x + 1, y - 1));
        return positions;
    }

    public Set<Position> getEastPositions() {
        final Set<Position> positions = new HashSet<>();
        positions.add(new Position(x + 1, y - 1));
        positions.add(new Position(x + 1, y));
        positions.add(new Position(x + 1, y + 1));
        return positions;
    }

    public Set<Position> getSouthPositions() {
        final Set<Position> positions = new HashSet<>();
        positions.add(new Position(x + 1, y + 1));
        positions.add(new Position(x, y + 1));
        positions.add(new Position(x - 1, y + 1));
        return positions;
    }

    public Set<Position> getWestPositions() {
        final Set<Position> positions = new HashSet<>();
        positions.add(new Position(x - 1, y - 1));
        positions.add(new Position(x - 1, y));
        positions.add(new Position(x - 1, y + 1));
        return positions;
    }

    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + "]";
    }

}
