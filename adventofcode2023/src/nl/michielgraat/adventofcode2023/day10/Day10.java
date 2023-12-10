package nl.michielgraat.adventofcode2023.day10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day10 extends AocSolver {

    protected Day10(String filename) {
        super(filename);
    }

    private List<Coordinate> readGrid(final List<String> input) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                coordinates.add(new Coordinate(x, y, line.charAt(x)));
            }
        }
        return coordinates;
    }

    private Map<Coordinate, Integer> dijkstra(final Coordinate start, final List<Coordinate> positions) {
        final PriorityQueue<Coordinate> queue = new PriorityQueue<>();
        queue.offer(start);
        final Map<Coordinate, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        while (!queue.isEmpty()) {
            final Coordinate current = queue.poll();
            final int dist = distances.get(current);
            final List<Coordinate> neighbours = current.getNeighbours(positions);
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

    private List<Coordinate> getPolygon(Coordinate start, Map<Coordinate, Integer> distances) {
        // To get all corners of the polygon in the right order we start at 'S', and
        // from there get one of the potentially two corners which are closest (we are
        // on a loop), and continue from there. Rinse and repeat. Prepare for some ugly
        // code!
        List<Coordinate> corners = new ArrayList<>();
        corners.add(start);
        int distance = 1;
        int max = distances.values().stream().mapToInt(i -> i).max().getAsInt();
        boolean goingUp = true;
        Coordinate current = distances.entrySet().stream().filter(e -> e.getValue() == 1).map(e -> e.getKey()).toList()
                .get(1);
        while (!(goingUp == false && distance == 1)) {
            if (current.tile() != '|' && current.tile() != '-') {
                // This must be a corner, so add it.
                corners.add(current);
            }
            // Check if we are still working towards the point which is the furthest away
            // from 'S'. If so keep adding 1 to the distance, otherwise count down (as we
            // are now going from the point which is the furthest away to 'S').
            if (goingUp) {
                distance++;
                goingUp = distance <= max;
                if (!goingUp) {
                    distance = max - 1;
                }
            } else {
                distance--;
            }
            // Make copies so we can use them in the lambda below.
            int curDistance = distance;
            Coordinate toCheck = new Coordinate(current.x(), current.y(), current.tile());

            // Get the next coordinate to check. This should have the correct distance, not
            // be already a part of the list of corners and it should be a neighbour to the
            // current coordinate.
            current = distances.entrySet().stream().filter(e -> e.getValue() == curDistance)
                    .filter(e -> !corners.contains(e.getKey()) && toCheck.isNeighbour(e.getKey())).findFirst().get()
                    .getKey();
        }
        if (current.tile() != '|' && current.tile() != '-') {
            corners.add(current);
        }
        corners.add(start);
        return corners;
    }

    @Override
    protected String runPart2(final List<String> input) {
        List<Coordinate> coordinates = readGrid(input);
        Coordinate start = coordinates.stream().filter(Coordinate::isStart).findFirst().get();
        Map<Coordinate, Integer> distances = dijkstra(start, coordinates);

        List<Coordinate> polygon = getPolygon(start, distances);

        // The coordinates which are possibly enclosed are all those which are not on
        // the loop (i.e. have a distance to 'S').
        List<Coordinate> coordinatesToCheck = coordinates.stream().filter(c -> !distances.keySet().contains(c))
                .toList();
        
        return String.valueOf(coordinatesToCheck.stream().filter(c -> c.isInsidePolygon(polygon)).count());
    }

    @Override
    protected String runPart1(final List<String> input) {
        List<Coordinate> coordinates = readGrid(input);
        Map<Coordinate, Integer> distances = dijkstra(
                coordinates.stream().filter(Coordinate::isStart).findFirst().get(), coordinates);

        return String.valueOf(distances.values().stream().mapToInt(i -> i).max().getAsInt());
    }

    public static void main(String... args) {
        new Day10("day10.txt");
    }
}
