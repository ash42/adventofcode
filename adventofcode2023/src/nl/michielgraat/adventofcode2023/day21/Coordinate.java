package nl.michielgraat.adventofcode2023.day21;

import java.util.ArrayList;
import java.util.List;

public record Coordinate (int x, int y, char type) implements Comparable<Coordinate> {

    public List<Coordinate> getNeighbours(List<Coordinate> coordinates) {
        List<Coordinate> neighbours = new ArrayList<>();

        Coordinate north = new Coordinate(x,y-1,'.');
        Coordinate south = new Coordinate(x,y+1,'.');
        Coordinate east = new Coordinate(x+1,y,'.');
        Coordinate west = new Coordinate(x-1,y,'.');

        if (coordinates.contains(north)) {
            neighbours.add(north);
        }
        if (coordinates.contains(south)) {
            neighbours.add(south);
        }
        if (coordinates.contains(east)) {
            neighbours.add(east);
        }
        if (coordinates.contains(west)) {
            neighbours.add(west);
        }

        return neighbours;
    }

    @Override
    public int compareTo(Coordinate o) {
        if (this.y != o.y()) {
            return Integer.compare(this.y, o.y());
        } else {
            return Integer.compare(this.x, o.x());
        }
    }
    
}
