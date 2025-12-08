package nl.michielgraat.adventofcode2025.day08;

public record JunctionBox(long x, long y, long z) {

    public double getDistance(final JunctionBox other) {
        return Math.sqrt(Math.pow(this.x() - other.x(), 2) + Math.pow(this.y() - other.y(), 2)
                + Math.pow(this.z() - other.z(), 2));
    }
}
