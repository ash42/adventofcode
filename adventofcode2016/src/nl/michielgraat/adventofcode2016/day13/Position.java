package nl.michielgraat.adventofcode2016.day13;

import java.util.ArrayList;
import java.util.List;

public class Position {
    int x;
    int y;

    public Position (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isOpen(int nr) {
        int result = x*x + 3*x + 2*x*y + y + y*y + nr;
        String binary = Integer.toBinaryString(result);
        long ones = binary.chars().filter(c -> c == '1').count();
        return ones % 2 == 0;
    }

    public List<Position> getNeighbours(int nr) {
        List<Position> neighbours = new ArrayList<>();
        //Up
        int nx = x;
        int ny = y - 1;
        if (ny >= 0) {
            Position p = new Position(nx, ny);
            if (p.isOpen(nr)) {
                neighbours.add(p);
            }
        }
        //Down
        nx = x;
        ny = y + 1;
        Position p = new Position(nx, ny);
        if (p.isOpen(nr)) {
            neighbours.add(p);
        }
        //Left
        nx = x - 1;
        ny = y;
        if (nx >= 0) {
            p = new Position(nx, ny);
            if (p.isOpen(nr)) {
                neighbours.add(p);
            }
        }
        //Right
        nx = x + 1;
        ny = y;
        p = new Position(nx, ny);
        if (p.isOpen(nr)) {
            neighbours.add(p);
        }
        return neighbours;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
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
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    
}
