package nl.michielgraat.adventofcode2025.day09;

/**
 * Represents an edge of a polygon. In theory the Rectangle class could also be
 * used for this, but that is slightly confusing looking from a naming
 * perspective.
 */
public record Edge(RedTile r1, RedTile r2) {
}