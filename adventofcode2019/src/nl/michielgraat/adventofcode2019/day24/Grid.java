package nl.michielgraat.adventofcode2019.day24;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Grid {
    private List<Position> bugs;
    private int size = 5;
    private int depth = 0;

    public Grid(final List<String> input) {
        init(input);
    }

    public Grid(final int depth) {
        this.depth = depth;
        bugs = new ArrayList<>();
    }

    public Grid(final int depth, final List<Position> bugs) {
        this.depth = depth;
        this.bugs = bugs.stream().toList();
    }

    private void init(final List<String> input) {
        bugs = new ArrayList<>();
        size = input.size();
        for (int y = 0; y < size; y++) {
            final String line = input.get(y);
            for (int x = 0; x < size; x++) {
                if (line.charAt(x) == '#') {
                    bugs.add(new Position(x, y));
                }
            }
        }
    }

    private boolean bordersCenter(final Position p) {
        final int x = p.x();
        final int y = p.y();
        return (x == 2 && y == 1) || (x == 1 && y == 2) || (x == 2 && y == 3) || (x == 3 && y == 2);
    }

    private int getNrForCenter(final Position p, final Map<Integer, Grid> depthToGrid, final int x, final int y) {
        int total = 0;
        if (depthToGrid.containsKey(depth + 1) && bordersCenter(p)) {
            final List<Position> centerBugs = depthToGrid.get(depth + 1).getBugs();
            if (x == 2 && y == 1) {
                total += centerBugs.contains(new Position(0, 0)) ? 1 : 0;
                total += centerBugs.contains(new Position(1, 0)) ? 1 : 0;
                total += centerBugs.contains(new Position(2, 0)) ? 1 : 0;
                total += centerBugs.contains(new Position(3, 0)) ? 1 : 0;
                total += centerBugs.contains(new Position(4, 0)) ? 1 : 0;
            } else if (x == 1 && y == 2) {
                total += centerBugs.contains(new Position(0, 0)) ? 1 : 0;
                total += centerBugs.contains(new Position(0, 1)) ? 1 : 0;
                total += centerBugs.contains(new Position(0, 2)) ? 1 : 0;
                total += centerBugs.contains(new Position(0, 3)) ? 1 : 0;
                total += centerBugs.contains(new Position(0, 4)) ? 1 : 0;
            } else if (x == 2 && y == 3) {
                total += centerBugs.contains(new Position(0, 4)) ? 1 : 0;
                total += centerBugs.contains(new Position(1, 4)) ? 1 : 0;
                total += centerBugs.contains(new Position(2, 4)) ? 1 : 0;
                total += centerBugs.contains(new Position(3, 4)) ? 1 : 0;
                total += centerBugs.contains(new Position(4, 4)) ? 1 : 0;
            } else if (x == 3 && y == 2) {
                total += centerBugs.contains(new Position(4, 0)) ? 1 : 0;
                total += centerBugs.contains(new Position(4, 1)) ? 1 : 0;
                total += centerBugs.contains(new Position(4, 2)) ? 1 : 0;
                total += centerBugs.contains(new Position(4, 3)) ? 1 : 0;
                total += centerBugs.contains(new Position(4, 4)) ? 1 : 0;
            }
        }
        return total;
    }

    private int getNrForOuterBorders(final Map<Integer, Grid> depthToGrid, final int x, final int y) {
        int total = 0;
        if (depthToGrid.containsKey(depth - 1)) {
            final List<Position> outerBugs = depthToGrid.get(depth - 1).getBugs();
            if (y == 0 || y == 4) {
                total += outerBugs.contains(y == 0 ? new Position(2, 1) : new Position(2, 3)) ? 1
                        : 0;
            }
            if (x == 0 || x == 4) {
                total += outerBugs.contains(x == 0 ? new Position(1, 2) : new Position(3, 2)) ? 1
                        : 0;
            }
        }
        return total;
    }

    private int getNrOfNeighbourBugs(final Position p) {
        final int x = p.x();
        final int y = p.y();
        int total = 0;
        if (bugs.contains(new Position(x - 1, y)))
            total++;
        if (bugs.contains(new Position(x + 1, y)))
            total++;
        if (bugs.contains(new Position(x, y - 1)))
            total++;
        if (bugs.contains(new Position(x, y + 1)))
            total++;
        return total;
    }

    private int getNrOfNeighbourBugs(final Position p, final Map<Integer, Grid> depthToGrid) {
        final int x = p.x();
        final int y = p.y();
        return getNrOfNeighbourBugs(p) + getNrForOuterBorders(depthToGrid, x, y) + getNrForCenter(p, depthToGrid, x, y);
    }

    public void update() {
        final List<Position> result = new ArrayList<>();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                final Position current = new Position(x, y);
                final int nrNeighbours = getNrOfNeighbourBugs(current);
                if ((bugs.contains(current) && nrNeighbours == 1)
                        || (!bugs.contains(current) && (nrNeighbours == 1 || nrNeighbours == 2))) {
                    result.add(current);
                }
            }
        }
        bugs = result;
    }

    public void updateRecursive(final Map<Integer, Grid> depthToGrid) {
        final List<Position> result = new ArrayList<>();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (!(x == 2 && y == 2)) {
                    final Position current = new Position(x, y);
                    final int nrNeighbours = getNrOfNeighbourBugs(current, depthToGrid);
                    if ((bugs.contains(current) && nrNeighbours == 1)
                            || (!bugs.contains(current) && (nrNeighbours == 1 || nrNeighbours == 2))) {
                        result.add(current);
                    }
                }
            }
        }
        bugs = result;
    }

    public long getBiodiversityRating() {
        return bugs.stream().mapToLong(b -> (long) Math.pow(2, b.x() + b.y() * size)).sum();
    }

    public List<Position> getBugs() {
        return this.bugs;
    }

    public int getDepth() {
        return this.depth;
    }
}
