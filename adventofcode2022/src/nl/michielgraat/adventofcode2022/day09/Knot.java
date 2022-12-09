package nl.michielgraat.adventofcode2022.day09;

public class Knot implements Comparable<Knot> {
    private final int nr;
    private Position position;

    public Knot(final int nr, final Position position) {
        this.nr = nr;
        this.position = position;
    }

    public boolean touches(final Knot o) {
        return this.getPosition().touches(o.getPosition());
    }

    @Override
    public int compareTo(final Knot o) {
        return Integer.compare(this.nr, o.nr);
    }

    public int getX() {
        return this.position.getX();
    }

    public int getY() {
        return this.position.getY();
    }

    public void setX(final int x) {
        this.position.setX(x);
    }

    public void setY(final int y) {
        this.position.setY(y);
    }

    public int getNr() {
        return nr;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(final Position position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + nr;
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
        final Knot other = (Knot) obj;
        if (nr != other.nr)
            return false;
        return true;
    }
}