package nl.michielgraat.adventofcode2018.day15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Unit implements Comparable<Unit> {
    Point p;
    Race race;
    int hitpoints = 200;
    int attackPower = 3;

    public Unit(final int x, final int y, final Race race) {
        p = new Point(x, y);
        this.race = race;
    }

    public Unit(final int x, final int y, final Race race, final int attackPower) {
        p = new Point(x, y);
        this.race = race;
        this.attackPower = attackPower;
    }

    private int dijkstra(final Point start, final Point end, final char[][] grid) {
        final PriorityQueue<Point> queue = new PriorityQueue<>();
        queue.offer(start);
        final Map<Point, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        while (!queue.isEmpty()) {
            final Point current = queue.poll();
            final int dist = distances.get(current);
            final List<Point> neighbours = current.getNeighbours(grid);
            for (final Point n : neighbours) {
                final int ndist = dist + 1;
                if (ndist < distances.getOrDefault(n, Integer.MAX_VALUE)) {
                    distances.put(n, ndist);
                    queue.add(n);
                }
            }
        }
        return distances.entrySet().stream().filter(e -> e.getKey().equals(end)).collect(Collectors.toList()).stream()
                .map(Entry::getValue).mapToInt(d -> d).min().orElse(Integer.MAX_VALUE);
    }

    private List<Unit> inRange(final List<Unit> targets) {
        final List<Unit> inRange = new ArrayList<>();
        for (final Unit target : targets) {
            if ((this.p.x == target.p.x && (this.p.y - 1 == target.p.y || this.p.y + 1 == target.p.y))
                    ||
                    (this.p.y == target.p.y && (this.p.x - 1 == target.p.x || this.p.x + 1 == target.p.x))) {
                inRange.add(target);
            }
        }
        return inRange;

    }

    private List<Point> getAdjacentSquares(final char[][] grid, final List<Unit> targets) {
        final List<Point> adjacent = new ArrayList<>();
        for (final Unit target : targets) {
            // System.out.println("Trying to find adjacent squares to " + target);
            final int x = target.p.x;
            final int y = target.p.y;
            if (x - 1 >= 0 && grid[x - 1][y] == '.') {
                // System.out.println("Found adjacent empty square at (" + (x-1) + "," + y +
                // ")");
                adjacent.add(new Point(x - 1, y));
            }
            if (x + 1 < grid.length && grid[x + 1][y] == '.') {
                // System.out.println("Found adjacent empty square at (" + (x+1) + "," + y +
                // ")");
                adjacent.add(new Point(x + 1, y));
            }
            if (y - 1 >= 0 && grid[x][y - 1] == '.') {
                // System.out.println("Found adjacent empty square at (" + x + "," + (y-1) +
                // ")");
                adjacent.add(new Point(x, y - 1));
            }
            if (y + 1 < grid[0].length && grid[x][y + 1] == '.') {
                // System.out.println("Found adjacent empty square at (" + x + "," + (y+1) +
                // ")");
                adjacent.add(new Point(x, y + 1));
            }
        }
        return adjacent;
    }

    private void move(final char[][] grid, final List<Unit> targets) {
        // System.out.println("Trying to move");
        if (inRange(targets).isEmpty()) {
            // System.out.println("No targets directly in range");
            // Get list of all adjacent points to enemies
            final List<Point> adjacent = getAdjacentSquares(grid, targets);
            final Map<Point, Integer> adjToDistance = new TreeMap<>();
            // For every of these points, calculcate the distance to it
            for (final Point adj : adjacent) {
                final int dist = dijkstra(this.p, adj, grid);
                // System.out.println("Distance from " + this.p + " to " + adj + " = " + dist);
                if (dist < Integer.MAX_VALUE) {
                    adjToDistance.put(adj, dist);
                }
            }
            if (!adjToDistance.isEmpty()) {
                // Find the nearest adjacent-to-an-enemy point
                final Point nearest = Collections.min(adjToDistance.entrySet(), Map.Entry.comparingByValue()).getKey();
                // System.out.println("Nearest adjacent square to enemy for " + this + " is at "
                // + nearest);
                // Find which neighbour point of the current unit is closest to the nearest
                // adjacent-to-an-enemy point...
                final List<Point> neighbours = this.p.getNeighbours(grid);
                int min = Integer.MAX_VALUE;
                Point minN = this.p;
                for (final Point n : neighbours) {
                    final int dist = dijkstra(n, nearest, grid);
                    if (dist < min) {
                        min = dist;
                        minN = n;
                    }
                }
                // System.out.println("Nearest adjacent square to " + nearest + " is at " + minN
                // + ", moving towards it");
                // ...and move towards it.
                this.p.x = minN.x;
                this.p.y = minN.y;
                // System.out.println("Now at " + this.p);
            }
        }
    }

    /**
     * Attacks the weakest in range target.
     * 
     * @param targets List of possible targets
     */
    private void attack(final List<Unit> targets) {
        // System.out.println("Trying to attack");
        final List<Unit> inRange = inRange(targets);
        if (!inRange.isEmpty()) {
            // System.out.println("Found " + inRange.size() + " enemies in range");
            final int minHp = (inRange.size() > 1) ? inRange.stream().filter(i -> i.hitpoints > 0).mapToInt(i -> i.hitpoints)
                    .min().orElseThrow(NoSuchElementException::new) : inRange.get(0).hitpoints;
            final List<Unit> weakest = inRange.stream().filter(i -> i.hitpoints == minHp).collect(Collectors.toList());
            // System.out.println(weakest.size() + " enemies have lowest hp of " + minHp);
            Collections.sort(weakest);
            // System.out.println("Weakest enemies: ");
            // for (Unit w : weakest) {
            // System.out.println("- " + w + ", hp: " + w.hitpoints);
            // }
            weakest.get(0).hitpoints -= this.attackPower;
            // System.out.println("Hit " + weakest.get(0) + " for " + attackPower + "
            // hitpoints, now at: " + weakest.get(0).hitpoints);
        }
    }

    /**
     * Takes a turn.
     * 
     * @param grid  The grid
     * @param units All units on the grid.
     * @return true if combat has ended (no more enemies), else false.
     */
    public boolean takeTurn(final char[][] grid, final List<Unit> units) {
        // System.out.println("----> " + this + " taking its turn...");
        final Race enemy = (this.race == Race.ELF) ? Race.GOBLIN : Race.ELF;
        final List<Unit> targets = units.stream().filter(u -> u.race == enemy).filter(u -> u.hitpoints > 0)
                .collect(Collectors.toList());
        // System.out.println("Found " + targets.size() + " targets before actions");
        if (targets.isEmpty()) {
            return true;
        }
        move(grid, targets);
        attack(targets);
        return targets.isEmpty();
    }

    @Override
    public int compareTo(final Unit o) {
        return this.p.compareTo(o.p);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((p == null) ? 0 : p.hashCode());
        result = prime * result + ((race == null) ? 0 : race.hashCode());
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
        final Unit other = (Unit) obj;
        if (p == null) {
            if (other.p != null)
                return false;
        } else if (!p.equals(other.p))
            return false;
        if (race != other.race)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return ((race == Race.ELF) ? "Elf" : "Goblin") + " at (" + p.x + "," + p.y + ")";
    }

}

class Point implements Comparable<Point> {
    int x;
    int y;

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public List<Point> getNeighbours(final char[][] grid) {
        final List<Point> neighbours = new ArrayList<>();
        if (x - 1 >= 0 && grid[x - 1][y] == '.') {
            neighbours.add(new Point(x - 1, y));
        }
        if (x + 1 < grid.length && grid[x + 1][y] == '.') {
            neighbours.add(new Point(x + 1, y));
        }
        if (y - 1 >= 0 && grid[x][y - 1] == '.') {
            neighbours.add(new Point(x, y - 1));
        }
        if (y + 1 < grid[0].length && grid[x][y + 1] == '.') {
            neighbours.add(new Point(x, y + 1));
        }
        Collections.sort(neighbours);
        return neighbours;
    }

    @Override
    public int compareTo(final Point o) {
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
        final Point other = (Point) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}