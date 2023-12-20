package nl.michielgraat.adventofcode2023.day19;

public record Part (int x, int m, int a, int s) {
    
    public long getRating() {
        return x + m + a + s;
    }
}
