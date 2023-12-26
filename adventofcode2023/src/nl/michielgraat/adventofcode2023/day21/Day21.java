package nl.michielgraat.adventofcode2023.day21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

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

    private Set<Coordinate> getCoordinatesWithStepLength(Coordinate current, int curDist, int maxDist, final List<Coordinate> coordinates, Set<CoordinateDistance> visited) {
        Set<Coordinate> result = new HashSet<>();
        if (!visited.contains(new CoordinateDistance(current, curDist))) {
            if (curDist == maxDist) {
                result.add(current);
            } else {
                visited.add(new CoordinateDistance(current, curDist));
                
                List<Coordinate> neighbours = current.getNeighbours(coordinates);
                for (Coordinate neighbour : neighbours) {
                    result.addAll(getCoordinatesWithStepLength(neighbour, curDist+1, maxDist, coordinates, visited));
                }
            }
        }
        return result;
    }

    @Override
    protected String runPart2(final List<String> input) {
        //Credit where credit is due, this is based on https://github.com/villuna/aoc23/wiki/A-Geometric-solution-to-advent-of-code-2023,-day-21
        Map<Coordinate,Integer> paths = dijkstra(getStart(input), readCoordinates(input));
        long nrEvenCorners = paths.entrySet().stream().filter(e -> e.getValue() > 65 && e.getValue() % 2 == 0).count();
        long nrOddCorners = paths.entrySet().stream().filter(e -> e.getValue() > 65 && e.getValue() % 2 == 1).count();

        long nrEvenPlots = paths.entrySet().stream().filter(e -> e.getValue() % 2 == 0).count();
        long nrOddPlots = paths.entrySet().stream().filter(e -> e.getValue() % 2 == 1).count();

        long n = (26501365-65)/131;

        long totalNrOddPlotsInSingleMap = (n+1)*(n+1) * nrOddPlots;

        long totalNrEvenPlotsInSingleMap = n*n*nrEvenPlots;

        long nrForOddCorners = (n+1) * nrOddCorners;
        long nrForEvenCorners = n * nrEvenCorners;

        return String.valueOf(totalNrOddPlotsInSingleMap + totalNrEvenPlotsInSingleMap - nrForOddCorners + nrForEvenCorners);
    }

    @Override
    protected String runPart1(final List<String> input) {
        Map<Coordinate,Integer> paths = dijkstra(getStart(input), readCoordinates(input));
        return String.valueOf(paths.entrySet().stream().filter(e -> e.getValue() <= 64 && e.getValue() % 2 == 0).count());
    }

    public static void main(String... args) {
        new Day21("day21.txt");
    }
}
