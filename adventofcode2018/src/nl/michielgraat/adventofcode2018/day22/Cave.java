package nl.michielgraat.adventofcode2018.day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Cave {
    public static final int ROCKY = 0;
    public static final int WET = 1;
    public static final int NARROW = 2;

    public static final int NEITHER = 0;
    public static final int TORCH = 1;
    public static final int CLIMBING_GEAR = 2;

    private final Map<Position, Integer> cache = new HashMap<>();

    private final int depth;
    private final int targetX;
    private final int targetY;

    public Cave(final int depth, final int targetX, final int targetY) {
        this.depth = depth;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    private int getGeologicalIdx(final int x, final int y) {
        if (x == 0 && y == 0) {
            return 0;
        } else if (x == targetX && y == targetY) {
            return 0;
        } else if (y == 0) {
            return x * 16807;
        } else if (x == 0) {
            return y * 48271;
        } else {
            return getErosionLevel(x - 1, y) * getErosionLevel(x, y - 1);
        }
    }

    private int getErosionLevel(final int x, final int y) {
        final Position p = new Position(x, y);
        if (cache.containsKey(p)) {
            return cache.get(p);
        }
        final int level = (getGeologicalIdx(x, y) + depth) % 20183;
        cache.put(p, level);
        return level;
    }

    private int getType(final int x, final int y) {
        final int start = getErosionLevel(x, y);
        if (start % 3 == 0) {
            return ROCKY;
        } else if (start % 3 == 1) {
            return WET;
        } else {
            return NARROW;
        }
    }

    public int getRiskLevel() {
        int total = 0;
        for (int y = 0; y <= targetY; y++) {
            for (int x = 0; x <= targetX; x++) {
                total += getType(x, y);
            }
        }
        return total;
    }

    private boolean isOutOfBounds(final int x, final int y) {
        // It is not necessary to go all the way till 'depth'. But how far to go?
        // In the example the cave was printed 5 rows and columns further than
        // the target, so lets use that.
        return x < 0 || y < 0 || x > (targetX + 5) || y > (targetY + 5);
    }

    private List<Position> getPossiblePositions(final int x, final int y) {
        final List<Position> possible = new ArrayList<>();
        if (!isOutOfBounds(x, y)) {
            switch (getType(x, y)) {
                case ROCKY:
                    possible.add(new Position(x, y, CLIMBING_GEAR));
                    possible.add(new Position(x, y, TORCH));
                    break;
                case WET:
                    possible.add(new Position(x, y, CLIMBING_GEAR));
                    possible.add(new Position(x, y, NEITHER));
                    break;
                case NARROW:
                    possible.add(new Position(x, y, TORCH));
                    possible.add(new Position(x, y, NEITHER));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type for (" + x + "," + y + ")");
            }
        }
        return possible;
    }

    private List<Position> getNeighbours(final Position p) {
        final List<Position> neighbours = new ArrayList<>();
        neighbours.addAll(getPossiblePositions(p.x - 1, p.y));
        neighbours.addAll(getPossiblePositions(p.x + 1, p.y));
        neighbours.addAll(getPossiblePositions(p.x, p.y - 1));
        neighbours.addAll(getPossiblePositions(p.x, p.y + 1));
        return neighbours;
    }

    private int dijkstra(final Position start, final Position end) {
        final PriorityQueue<Position> queue = new PriorityQueue<>();
        queue.offer(start);
        final Map<Position, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        while (!queue.isEmpty()) {
            final Position current = queue.poll();
            final int dist = distances.get(current);
            final List<Position> neighbours = getNeighbours(current);
            for (final Position n : neighbours) {
                int ndist = dist;
                if (current.gear == n.gear) {
                    // Gear is ok, so just move to next region
                    ndist++;
                } else {
                    // Change gear
                    ndist += 7;
                    // Move to next region
                    ndist++;
                }
                if (ndist < distances.getOrDefault(n, Integer.MAX_VALUE)) {
                    distances.put(n, ndist);
                    queue.add(n);
                }
            }
        }

        return distances.entrySet().stream().filter(e -> e.getKey().equals(end)).collect(Collectors.toList()).stream()
                .map(Entry::getValue).mapToInt(d -> d).min().orElse(Integer.MAX_VALUE);
    }

    public int getShortestRoute() {
        final Position start = new Position(0, 0, TORCH);
        final Position end = new Position(targetX, targetY, TORCH);
        return dijkstra(start, end);
    }
}

class Position implements Comparable<Position> {
    int x;
    int y;
    int gear;

    Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    Position(final int x, final int y, final int gear) {
        this.x = x;
        this.y = y;
        this.gear = gear;
    }

    @Override
    public int compareTo(final Position o) {
        if (this.y != o.y) {
            return Integer.compare(this.y, o.y);
        } else {
            return Integer.compare(this.x, o.x);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + gear;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        if (gear != other.gear)
            return false;
        return true;
    }

}