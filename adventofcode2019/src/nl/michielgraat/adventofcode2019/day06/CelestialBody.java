package nl.michielgraat.adventofcode2019.day06;

public record CelestialBody(String name) implements Comparable<CelestialBody> {

    @Override
    public int compareTo(final CelestialBody o) {
        return this.name().compareTo(o.name());
    }
}
