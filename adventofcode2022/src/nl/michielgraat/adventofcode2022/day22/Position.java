package nl.michielgraat.adventofcode2022.day22;

import java.util.HashMap;
import java.util.Map;

public class Position {

    private final int x;
    private final int y;
    private final Position[] neighbours = new Position[4];
    private final Map<Integer, Integer> adjustments = new HashMap<>();

    Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public Position[] neighbours() {
        return neighbours;
    }

    public int getAdjustment(final int face) {
        return adjustments.get(face) != null ? adjustments.get(face) : 0;
    }

    public void setNeighbour(final Position neighbour, final int direction) {
        neighbours[direction] = neighbour;
    }

    public void setAdjustment(final int adjustment, final int face) {
        adjustments.put(face, adjustment);
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
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Position [x=" + x + ", y=" + y + ", neighbours: ");
        sb.append("[RIGHT=(" + (neighbours[0] != null ? neighbours[0].x() + "," + neighbours[0].y() : "null") + ")],");
        sb.append("[DOWN=(" + (neighbours[1] != null ? neighbours[1].x() + "," + neighbours[1].y() : "null") + ")],");
        sb.append("[LEFT=(" + (neighbours[2] != null ? neighbours[2].x() + "," + neighbours[2].y() : "null") + ")],");
        sb.append("[UP=(" + (neighbours[3] != null ? neighbours[3].x() + "," + neighbours[3].y() : "null") + ")]]");
        return sb.toString();
    }

}
