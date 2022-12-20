package nl.michielgraat.adventofcode2022.day17;

public class Position implements Comparable<Position> {

    public Position(final int x, final long y, final char type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    private final int x;
    private long y;
    private final char type;

    public int x() {
        return x;
    }

    public long y() {
        return y;
    }

    public void setY(final long y) {
        this.y = y;
    }

    public char type() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + (int) (y ^ (y >>> 32));
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
        if (this.y != o.y) {
            return Long.compare(this.y, o.y);
        } else {
            return Integer.compare(this.x, o.x);
        }
    }

    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + ", type=" + type + "]";
    }

}