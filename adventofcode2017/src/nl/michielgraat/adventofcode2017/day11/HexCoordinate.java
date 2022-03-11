package nl.michielgraat.adventofcode2017.day11;

import java.util.ArrayList;
import java.util.List;

public class HexCoordinate {
    int q;
    int r;
    int s;

    public HexCoordinate(final int q, final int r, final int s) {
        this.q = q;
        this.r = r;
        this.s = s;
    }

    public List<HexCoordinate> getNeighbours() {
        final List<HexCoordinate> neighbours = new ArrayList<>();
        // nw
        neighbours.add(new HexCoordinate(q - 1, r, s + 1));
        // n
        neighbours.add(new HexCoordinate(q, r - 1, s + 1));
        // ne
        neighbours.add(new HexCoordinate(q + 1, r - 1, s));
        // sw
        neighbours.add(new HexCoordinate(q - 1, r + 1, s));
        // s
        neighbours.add(new HexCoordinate(q, r + 1, s - 1));
        // se
        neighbours.add(new HexCoordinate(q + 1, r, s - 1));
        return neighbours;
    }

    @Override
    public String toString() {
        return "(" + q + "," + r + "," + s + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + q;
        result = prime * result + r;
        result = prime * result + s;
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
        final HexCoordinate other = (HexCoordinate) obj;
        if (q != other.q)
            return false;
        if (r != other.r)
            return false;
        if (s != other.s)
            return false;
        return true;
    }

}
