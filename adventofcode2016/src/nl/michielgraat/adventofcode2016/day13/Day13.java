package nl.michielgraat.adventofcode2016.day13;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Day13 {

    public int dijkstra(final Position start, final Position end, final int nr) {
        final Queue<Position> queue = new LinkedList<>();
        queue.offer(start);
        final Map<Position, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        while (!queue.isEmpty()) {
            final Position current = queue.poll();
            final int dist = distances.get(current);
            final List<Position> neighbours = current.getNeighbours(nr);
            for (final Position neighbour : neighbours) {
                final int ndist = dist + 1;
                if (ndist < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    distances.put(neighbour, ndist);
                    queue.add(neighbour);

                }
            }
        }
        return distances.entrySet().stream().filter(e -> e.getKey().equals(end)).collect(Collectors.toList()).stream()
                .map(Entry::getValue).mapToInt(d -> d).min().orElse(Integer.MAX_VALUE);
    }

    public int runPart2(final int nr) {
        int total = 0;
        final Position start = new Position(1, 1);
        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 50; y++) {
                final Position end = new Position(x, y);
                if (dijkstra(start, end, nr) <= 50) {
                    total++;
                }
            }
        }
        return total;
    }

    public int runPart1(final int nr) {
        final Position start = new Position(1, 1);
        final Position end = new Position(31, 39);

        return dijkstra(start, end, nr);
    }

    public static void main(final String[] args) {
        final int nr = 1362;
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day13().runPart1(nr));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day13().runPart2(nr));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}