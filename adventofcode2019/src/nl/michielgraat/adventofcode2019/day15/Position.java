package nl.michielgraat.adventofcode2019.day15;

import java.util.ArrayList;
import java.util.List;

public record Position(int x, int y, Status status) implements Comparable<Position> {

    public List<Position> getNeighbours(final List<Position> positions) {
        final List<Position> neighbours = new ArrayList<>();
        final Position north = new Position(this.x(), this.y() - 1, Status.EMPTY);
        final Position south = new Position(this.x(), this.y() + 1, Status.EMPTY);
        final Position west = new Position(this.x() - 1, this.y(), Status.EMPTY);
        final Position east = new Position(this.x() + 1, this.y(), Status.EMPTY);
        if (positions.contains(north) && positions.get(positions.indexOf(north)).status() != Status.WALL) {
            neighbours.add(positions.get(positions.indexOf(north)));
        }
        if (positions.contains(south) && positions.get(positions.indexOf(south)).status() != Status.WALL) {
            neighbours.add(positions.get(positions.indexOf(south)));
        }
        if (positions.contains(west) && positions.get(positions.indexOf(west)).status() != Status.WALL) {
            neighbours.add(positions.get(positions.indexOf(west)));
        }
        if (positions.contains(east) && positions.get(positions.indexOf(east)).status() != Status.WALL) {
            neighbours.add(positions.get(positions.indexOf(east)));
        }
        return neighbours;
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

    @Override
    public int compareTo(final Position o) {
        if (this.y != o.y()) {
            return Integer.compare(this.y, o.y());
        } else {
            return Integer.compare(this.x, o.x());
        }
    }

}
