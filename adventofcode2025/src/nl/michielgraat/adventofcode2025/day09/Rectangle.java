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
            if (minX < eMaxX && maxX > eMinX && minY < eMaxY && maxY > eMinY) {
                return true;
            }
        }
        return false;
    }
}