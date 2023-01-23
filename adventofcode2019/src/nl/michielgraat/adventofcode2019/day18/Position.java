package nl.michielgraat.adventofcode2019.day18;

public record Position(int x, int y, char type) implements Comparable<Position> {

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
        if (this.y() != o.y()) {
            return Integer.compare(this.y(), o.y());
        } else {
            return Integer.compare(this.x(), o.x());
        }
    }

}
