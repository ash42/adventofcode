package nl.michielgraat.adventofcode2022.day12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day12 extends AocSolver {

    protected Day12(final String filename) {
        super(filename);
    }

    private int dijkstra(final Square start, final Square end, final List<Square> squares) {
        final PriorityQueue<Square> queue = new PriorityQueue<>();
        queue.offer(start);
        final Map<Square, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        while (!queue.isEmpty()) {
            final Square current = queue.poll();
            final int dist = distances.get(current);
            final List<Square> neighbours = current.getNeighbours(squares);
            for (final Square n : neighbours) {
                int ndist = dist;
                ndist++;
                if (ndist < distances.getOrDefault(n, Integer.MAX_VALUE)) {
                    distances.put(n, ndist);
                    queue.add(n);
                }
            }
        }

        return distances.entrySet().stream().filter(e -> e.getKey().equals(end)).collect(Collectors.toList()).stream()
                .map(Entry::getValue).mapToInt(d -> d).min().orElse(Integer.MAX_VALUE);
    }

    private List<Square> getSquares(final List<String> input) {
        final List<Square> squares = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            final String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                final char height = line.charAt(x);
                final Square s = new Square(x, y, height);
                if (height == 'S') {
                    s.setHeight('a');
                    s.setStart(true);
                } else if (height == 'E') {
                    s.setHeight('z');
                    s.setEnd(true);
                }
                squares.add(s);
            }

        }
        return squares;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Square> squares = getSquares(input);
        final List<Square> startSquares = squares.stream().filter(s -> s.getHeight() == 'a').toList();
        final Square end = squares.stream().filter(Square::isEnd).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No end square found"));
        int minSteps = Integer.MAX_VALUE;
        for (final Square start : startSquares) {
            final int dist = dijkstra(start, end, squares);
            minSteps = Math.min(dist, minSteps);
        }
        return String.valueOf(minSteps);
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Square> squares = getSquares(input);
        final Square start = squares.stream().filter(Square::isStart).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No start square found"));
        final Square end = squares.stream().filter(Square::isEnd).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No end square found"));
        return String.valueOf(dijkstra(start, end, squares));
    }

    public static void main(final String... args) {
        new Day12("day12.txt");
    }
}
