package nl.michielgraat.adventofcode2023.day21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day21 extends AocSolver {

    protected Day21(String filename) {
        super(filename);
    }

    private Map<Coordinate, Integer> dijkstra(final Coordinate start, final List<Coordinate> coordinates) {
        final PriorityQueue<Coordinate> queue = new PriorityQueue<>();
        queue.offer(start);
        final Map<Coordinate, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        while (!queue.isEmpty()) {
            final Coordinate current = queue.poll();
            final int dist = distances.get(current);
            final List<Coordinate> neighbours = current.getNeighbours(coordinates);
            for (final Coordinate n : neighbours) {
                int ndist = dist;
                ndist++;
                if (ndist < distances.getOrDefault(n, Integer.MAX_VALUE)) {
                    distances.put(n, ndist);
                    queue.add(n);
                }
            }
        }

        return distances;
    }

    private Coordinate getStart(List<String> input) {
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == 'S') {
                    return new Coordinate(x, y, '.');
                }
            }
        }
        throw new IllegalArgumentException("No starting coordinate found in input");
    }

    private List<Coordinate> readCoordinates(List<String> input) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                coordinates.add(new Coordinate(x, y, line.charAt(x) == 'S' ? '.' : line.charAt(x)));
            }
        }
        return coordinates;
    }

    @Override
    protected String runPart2(final List<String> input) {
        // Credit where credit is due, this is based on
        // https://github.com/villuna/aoc23/wiki/A-Geometric-solution-to-advent-of-code-2023,-day-21
        Map<Coordinate, Integer> paths = dijkstra(getStart(input), readCoordinates(input));

        long nrEvenPlotsInSingleMap = paths.entrySet().stream().filter(e -> e.getValue() % 2 == 0).count();
        long nrOddPlotsInSingleMap = paths.entrySet().stream().filter(e -> e.getValue() % 2 == 1).count();

        long nrEvenPlotsInCorner = paths.entrySet().stream().filter(e -> e.getValue() > 65 && e.getValue() % 2 == 0)
                .count();
        long nrOddPlotsInCorner = paths.entrySet().stream().filter(e -> e.getValue() > 65 && e.getValue() % 2 == 1)
                .count();

        long nrHorizontalSquares = (26501365 - 65) / 131;

        long totalNrOddPlots = (nrHorizontalSquares + 1) * (nrHorizontalSquares + 1) * nrOddPlotsInSingleMap;
        long totalNrEvenPlots = nrHorizontalSquares * nrHorizontalSquares * nrEvenPlotsInSingleMap;
        long totalNrForOddCorners = (nrHorizontalSquares + 1) * nrOddPlotsInCorner;
        long totalNrForEvenCorners = nrHorizontalSquares * nrEvenPlotsInCorner;

        return String.valueOf(totalNrOddPlots + totalNrEvenPlots - totalNrForOddCorners + totalNrForEvenCorners);
    }

    @Override
    protected String runPart1(final List<String> input) {
        Map<Coordinate, Integer> paths = dijkstra(getStart(input), readCoordinates(input));
        return String
                .valueOf(paths.entrySet().stream().filter(e -> e.getValue() <= 64 && e.getValue() % 2 == 0).count());
    }

    public static void main(String... args) {
        new Day21("day21.txt");
    }
}
