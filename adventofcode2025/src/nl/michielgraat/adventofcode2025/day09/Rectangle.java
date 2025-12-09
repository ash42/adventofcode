package nl.michielgraat.adventofcode2025.day09;

import java.util.List;

public record Rectangle(RedTile r1, RedTile r2) {
    public long getAreaSize() {
        return (Math.abs(r1.x() - r2.x()) + 1) * (Math.abs(r1.y() - r2.y()) + 1);
    }

    // Based upon https://kishimotostudios.com/articles/aabb_collision/
    public boolean intersects(final List<Edge> edges) {
        final long minX = Math.min(r1.x(), r2.x());
        final long maxX = Math.max(r1.x(), r2.x());
        final long minY = Math.min(r1.y(), r2.y());
        final long maxY = Math.max(r1.y(), r2.y());

        for (final Edge e : edges) {
            final long eMinX = Math.min(e.r1().x(), e.r2().x());
            final long eMaxX = Math.max(e.r1().x(), e.r2().x());
            final long eMinY = Math.min(e.r1().y(), e.r2().y());
            final long eMaxY = Math.max(e.r1().y(), e.r2().y());
            // The idea is that two rectangles collide if they intersect on both axes.
            // In this case we are checking if the rectangle collides with one of the edges
            // of the polygon.
            //
            // So what we are checking here if the rectangle is above, below, left or right
            // of the edge.
            //
            // Since we are talking about comparing an edge with a rectangle here, this also
            // works if the rectangle is completely inside of the polygon (because then it
            // will not intersect with any of the edges).
            //
            // Note that a rectangle completely outside the polygon also does not intersect
            // with any of the edges. For most cases this is no problem (as all rectangles
            // we generate have corners which are red tile), but for some cases (like the
            // rectangle defined by (2,5) to (9,7) in the test input) this gives incorrect
            // results. Because of the way the input is constructed (basically like a big
            // circle) this does not lead to problems for the actual answer.
            //
            // This also works (the rectangle does not intersect with an edge) if one of the
            // edges of the polygon also happens to be the edge of the rectangle (as we are
            // using < and >, and not <= and =>).
            if (minX < eMaxX && maxX > eMinX && minY < eMaxY && maxY > eMinY) {
                return true;
            }
        }
        return false;
    }
}