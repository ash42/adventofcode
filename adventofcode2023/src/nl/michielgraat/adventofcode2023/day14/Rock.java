package nl.michielgraat.adventofcode2023.day14;

public record Rock (int x, int y, char type) implements Comparable<Rock> {

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
        Rock other = (Rock) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public int compareTo(Rock o) {
        if (this.y != o.y()) {
            return Integer.compare(this.y, o.y());
        } else {
            return Integer.compare(this.x, o.x());
        }
    }
}