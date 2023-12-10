package nl.michielgraat.adventofcode2023.day10;

import java.util.ArrayList;
import java.util.List;

public record Coordinate(int x, int y, char tile) implements Comparable<Coordinate> {

    public List<Coordinate> getNeighbours(final List<Coordinate> coordinates) {
        final List<Coordinate> neighbours = new ArrayList<>();
        final Coordinate north = new Coordinate(this.x(), this.y() - 1, ' ');
        final Coordinate south = new Coordinate(this.x(), this.y() + 1, ' ');
        final Coordinate west = new Coordinate(this.x() - 1, this.y(), ' ');
        final Coordinate east = new Coordinate(this.x() + 1, this.y(), ' ');

        if (coordinates.contains(north) && hasConnectionNorth(coordinates.get(coordinates.indexOf(north)).tile())) {
            neighbours.add(coordinates.get(coordinates.indexOf(north)));
        }
        if (coordinates.contains(south) && hasConnectionSouth(coordinates.get(coordinates.indexOf(south)).tile())) {
            neighbours.add(coordinates.get(coordinates.indexOf(south)));
        }
        if (coordinates.contains(west) && hasConnectionWest(coordinates.get(coordinates.indexOf(west)).tile())) {
            neighbours.add(coordinates.get(coordinates.indexOf(west)));
        }
        if (coordinates.contains(east) && hasConnectionEast(coordinates.get(coordinates.indexOf(east)).tile())) {
            neighbours.add(coordinates.get(coordinates.indexOf(east)));
        }
        return neighbours;
    }

    private boolean hasConnectionEast(char tile) {
        return (this.tile == '-' || this.tile=='F' || this.tile == 'L' || this.tile == 'S') && (tile == '-' || tile == '7' || tile == 'J');
    }

    private boolean hasConnectionWest(char tile) {
        return (this.tile == '-' || this.tile=='J' || this.tile == '7' || this.tile == 'S') && (tile == '-' || tile == 'F' || tile == 'L');
    }

    private boolean hasConnectionNorth(char tile) {
        return (this.tile == '|' || this.tile=='J' || this.tile == 'L' || this.tile == 'S') && (tile == '|' || tile == 'F' || tile == '7');
    }

     private boolean hasConnectionSouth(char tile) {
        return (this.tile == '|' || this.tile=='7' || this.tile == 'F' || this.tile == 'S') && (tile == '|' || tile == 'J' || tile == 'L');
    }

    public boolean isNeighbour(Coordinate c) {
        boolean north = hasConnectionNorth(c.tile()) && c.x() == this.x() && c.y() == this.y() - 1;
        boolean south = hasConnectionSouth(c.tile()) && c.x() == this.x() && c.y() == this.y() + 1;
        boolean west = hasConnectionWest(c.tile()) && c.x() == this.x() - 1 && c.y() == this.y();
        boolean east = hasConnectionEast(c.tile()) && c.x() == this.x() + 1 && c.y() == this.y();
        return north ^ south ^ west ^ east;
    }

    public boolean isInsidePolygon(List<Coordinate> polygon) {
        int[] vertx = polygon.stream().map(c -> c.x()).mapToInt(x -> x).toArray();
        int[] verty = polygon.stream().map(c -> c.y()).mapToInt(y -> y).toArray();
        int nvert = polygon.size();
        int i, j;
        boolean c = false;
        for (i = 0, j = nvert - 1; i < nvert; j = i++) {
            if (((verty[i] > y) != (verty[j] > y)) &&
                    (x < (vertx[j] - vertx[i]) * (y - verty[i]) / (verty[j] - verty[i]) + vertx[i]))
                c = !c;
        }
        return c;
    }

    public boolean isStart() {
        return tile == 'S';
    }

    @Override
    public int compareTo(Coordinate o) {
        if (this.y != o.y()) {
            return Integer.compare(this.y, o.y());
        } else {
            return Integer.compare(this.x, o.x());
        }
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Coordinate other = (Coordinate) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

}