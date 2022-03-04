package nl.michielgraat.adventofcode2016.day17;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

public class Day17 {

    public String dijkstra(final Position start, final String password, final boolean part2) {
        final Queue<Position> queue = new LinkedList<>();
        queue.offer(start);
        final Map<Position, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        int minPath = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            final Position current = queue.poll();
            final List<Position> neighbours = current.getNeighbours(password, minPath);
            for (final Position neighbour : neighbours) {
                final int ndist = neighbour.getPathLength();
                if (!part2 && neighbour.isEnd()) {
                    minPath = ndist;
                }
                if (ndist < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    distances.put(neighbour, ndist);
                    queue.add(neighbour);

                }
            }
        }
        if (part2) {
            return String.valueOf(distances.entrySet().stream().filter(e -> e.getKey().isEnd()).map(Entry::getValue)
                    .mapToInt(d -> d).max().orElse(0));
        }
        return distances.entrySet().stream().filter(e -> e.getKey().isEnd()).map(Entry::getKey).findFirst()
                .map(Position::getPath).orElse("does not exist");
    }

    public String runPart2(final String password) {
        final Position start = new Position(0, 0, "");

        return dijkstra(start, password, true);
    }

    public String runPart1(final String password) {
        final Position start = new Position(0, 0, "");

        return dijkstra(start, password, false);
    }

    public static void main(final String[] args) {
        final String password = "lpvhkcbi";
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day17().runPart1(password));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day17().runPart2(password));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
