package nl.michielgraat.adventofcode2019.day06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public record PlanetarySystem(Map<CelestialBody, List<CelestialBody>> adjBodies) {

    public void addCelestialBody(final String name) {
        adjBodies.putIfAbsent(new CelestialBody(name), new ArrayList<>());
    }

    public void addOrbit(final String name1, final String name2) {
        final CelestialBody c1 = new CelestialBody(name1);
        final CelestialBody c2 = new CelestialBody(name2);
        adjBodies.get(c1).add(c2);
        adjBodies.get(c2).add(c1);
    }

    public List<CelestialBody> getAdjBodies(final String name) {
        return adjBodies.get(new CelestialBody(name));
    }

    public Set<CelestialBody> getAllBodies() {
        return adjBodies.keySet();
    }

    private int dijkstra(final CelestialBody start, final CelestialBody end) {
        final PriorityQueue<CelestialBody> queue = new PriorityQueue<>();
        queue.add(start);
        final Map<CelestialBody, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        while (!queue.isEmpty()) {
            final CelestialBody current = queue.poll();
            final int dist = distances.get(current);
            final List<CelestialBody> neighbours = getAdjBodies(current.name());
            for (final CelestialBody n : neighbours) {
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

    public int getOrbitalTransfersToSanta() {
        return dijkstra(new CelestialBody("YOU"), new CelestialBody("SAN")) - 2;
    }

    private int getNrOrbits(final CelestialBody src, final int dist, final List<CelestialBody> visited) {
        visited.add(src);
        return dist + getAdjBodies(src.name()).stream().filter(b -> !visited.contains(b))
                .mapToInt(b -> getNrOrbits(b, dist + 1, visited)).sum();
    }

    public int getNrOrbits() {
        return getNrOrbits(new CelestialBody("COM"), 0, new ArrayList<>());
    }
}
