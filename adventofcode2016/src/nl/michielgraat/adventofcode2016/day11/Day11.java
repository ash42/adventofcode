package nl.michielgraat.adventofcode2016.day11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day11 {

    private static final String FILENAME = "day11.txt";
    private static final String FILENAME2 = "day11-2.txt";

    public int dijkstra(final Node start, final Node end) {
        final PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.offer(start);
        final Map<Node, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        while (!queue.isEmpty()) {
            final Node current = queue.poll();
            final int dist = current.steps;
            final List<Node> neighbours = current.getNeighbours();
            for (final Node neighbour : neighbours) {
                final int ndist = dist + 1;
                if (ndist < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    distances.put(neighbour, ndist);
                    queue.remove(neighbour);
                    neighbour.steps = ndist;
                    queue.add(neighbour);

                }
            }
        }
        return distances.entrySet().stream().filter(e -> e.getKey().equals(end)).collect(Collectors.toList()).stream()
                .map(Entry::getValue).mapToInt(d -> d).min().orElseThrow(NoSuchElementException::new);
    }

    Node getEnd(final Node start) {
        final Node end = new Node();
        end.floors[0] = new Floor();
        end.floors[1] = new Floor();
        end.floors[2] = new Floor();
        final Floor top = new Floor();
        top.getChips().addAll(start.floors[0].getChips());
        top.getChips().addAll(start.floors[1].getChips());
        top.getChips().addAll(start.floors[2].getChips());
        top.getChips().addAll(start.floors[3].getChips());
        top.getGenerators().addAll(start.floors[0].getGenerators());
        top.getGenerators().addAll(start.floors[1].getGenerators());
        top.getGenerators().addAll(start.floors[2].getGenerators());
        top.getGenerators().addAll(start.floors[3].getGenerators());
        end.floors[3] = top;
        end.elevator = 3;
        return end;
    }

    public int runPart2(final List<String> lines) {
        final Node start = new Node().initialize(lines);
        return dijkstra(start, getEnd(start));
    }

    public int runPart1(final List<String> lines) {
        final Node start = new Node().initialize(lines);
        return dijkstra(start, getEnd(start));
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day11().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day11().runPart2(FileReader.getStringList(FILENAME2)));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
