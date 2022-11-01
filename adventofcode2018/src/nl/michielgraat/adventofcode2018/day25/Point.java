package nl.michielgraat.adventofcode2018.day25;

public class Point {
    private final int x;
    private final int y;
    private final int z;
    private final int w;

    public Point(final int x, final int y, final int z, final int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    private int manhattanDistance(final Point o) {
        return Math.abs(this.x - o.x) + Math.abs(this.y - o.y) + Math.abs(this.z - o.z) + Math.abs(this.w - o.w);
    }

    public boolean inSameConstellation(final Point o) {
        return manhattanDistance(o) <= 3;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + "," + w + ")";
    }

}
