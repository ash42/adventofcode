package nl.michielgraat.adventofcode2025.day09;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2025.AocSolver;

public class Day09 extends AocSolver {

    protected Day09(final String filename) {
        super(filename);
    }

    private List<RedTile> parseRedTiles(final List<String> input) {
        final List<RedTile> result = new ArrayList<>();
        for (final String line : input) {
            final String[] splitLine = line.split(",");
            result.add(new RedTile(Long.valueOf(splitLine[0]), Long.valueOf(splitLine[1])));
        }
        return result;
    }

    private List<Edge> getEdgesOfPolygon(final List<RedTile> redTiles) {
        final List<Edge> result = new ArrayList<>();
        for (int i = 0; i < redTiles.size(); i++) {
            final RedTile current = redTiles.get(i);
            final RedTile next = redTiles.get(i < redTiles.size() - 1 ? i + 1 : 0);
            result.add(new Edge(current, next));
        }
        result.add(new Edge(redTiles.get(redTiles.size() - 1), redTiles.get(0)));
        return result;
    }

    private long findBiggestArea(final List<RedTile> redTiles) {
        long maxArea = 0;
        while (redTiles.size() > 2) {
            final RedTile r = redTiles.get(0);
            for (int i = 1; i < redTiles.size(); i++) {
                maxArea = Math.max(maxArea, new Rectangle(r, redTiles.get(i)).getAreaSize());
            }
            redTiles.remove(0);
        }
        return maxArea;
    }

    private long findBiggestAreaInPolygon(List<RedTile> redTiles, List<Edge> polygon) {
        long maxArea = 0;
        for (int i = 0; i < redTiles.size() - 1; i++) {
            final RedTile r1 = redTiles.get(i);
            for (int j = i; j < redTiles.size(); j++) {
                final RedTile r2 = redTiles.get(j);
                final Rectangle area = new Rectangle(r1, r2);
                if (!area.intersects(polygon)) {
                    maxArea = Math.max(maxArea, area.getAreaSize());
                }
            }
        }
        return maxArea;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<RedTile> redTiles = parseRedTiles(input);
        return String.valueOf(findBiggestAreaInPolygon(redTiles, getEdgesOfPolygon(redTiles)));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(findBiggestArea(parseRedTiles(input)));
    }

    public static void main(final String... args) {
        new Day09("day09.txt");
    }
}
