package nl.michielgraat.adventofcode2023.day22;

public record Brick(Coordinate start, Coordinate end) implements Comparable<Brick> {

    private boolean onSegment(Coordinate p, Coordinate q, Coordinate r) {
        return q.x() <= Math.max(p.x(), r.x()) && q.x() >= Math.min(p.x(), r.x()) &&
                q.y() <= Math.max(p.y(), r.y()) && q.y() >= Math.min(p.y(), r.y());
    }

    private int orientation(Coordinate p, Coordinate q, Coordinate r) {
        int val = (q.y() - p.y()) * (r.x() - q.x()) -
                (q.x() - p.x()) * (r.y() - q.y());

        if (val == 0)
            return 0; // collinear

        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    public boolean intersects(Brick o) {
        int o1 = orientation(this.start(), this.end(), o.start());
        int o2 = orientation(this.start(), this.end(), o.end());
        int o3 = orientation(o.start(), o.end(), this.start());
        int o4 = orientation(o.start(), o.end(), this.end());

        if (o1 != o2 && o3 != o4)
            return true;

        if (o1 == 0 && onSegment(this.start(), o.start(), this.end()))
            return true;

        if (o2 == 0 && onSegment(this.start(), o.end(), this.end()))
            return true;

        if (o3 == 0 && onSegment(o.start(), this.start(), o.end()))
            return true;

        if (o4 == 0 && onSegment(o.start(), this.end(), o.end()))
            return true;

        return false; 
    }

    @Override
    public int compareTo(Brick o) {
        return Integer.compare(this.start().z(), o.start().z());
    }

}
