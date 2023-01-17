package nl.michielgraat.adventofcode2019.day17;

import java.util.ArrayList;
import java.util.List;

public record Position(int x, int y) {

    public List<Position> getNeighbours() {
        final List<Position> neighbours = new ArrayList<>();
        neighbours.add(getNeighbour(0));
        neighbours.add(getNeighbour(1));
        neighbours.add(getNeighbour(2));
        neighbours.add(getNeighbour(3));
        return neighbours;
    }

    public Position getNeighbour(final int dir) {
        switch (dir) {
            case 0:
                return new Position(x + 1, y);
            case 1:
                return new Position(x, y + 1);
            case 2:
                return new Position(x - 1, y);
            case 3:
                return new Position(x, y - 1);
            default:
                throw new IllegalArgumentException("Unknown direction [" + dir + "]");
        }
    }
}
