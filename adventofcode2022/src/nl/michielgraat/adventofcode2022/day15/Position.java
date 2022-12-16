package nl.michielgraat.adventofcode2022.day15;

public record Position(int x, int y) {
    public int getManhattanDistance(Position o) {
        return Math.abs(this.x - o.x) + Math.abs(this.y - o.y);
    }   
}