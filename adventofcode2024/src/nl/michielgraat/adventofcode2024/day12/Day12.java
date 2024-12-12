package nl.michielgraat.adventofcode2024.day12;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day12 extends AocSolver {

    protected Day12(final String filename) {
        super(filename);
    }

    private char[][] getPlot(final List<String> input) {
        final char[][] plot = new char[input.get(0).length()][input.size()];
        for (int y = 0; y < input.size(); y++) {
            final String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                plot[y][x] = line.charAt(x);
            }
        }
        return plot;
    }

    private boolean inBounds(final int x, final int y, final char[][] plot) {
        return x >= 0 && y >= 0 && y < plot.length && x < plot[y].length;
    }

    private boolean hasLeftNeighbour(final int x, final int y, final char name, final char[][] plot) {
        return inBounds(x - 1, y, plot) && plot[y][x - 1] == name;
    }

    private boolean hasRightNeighbour(final int x, final int y, final char name, final char[][] plot) {
        return inBounds(x + 1, y, plot) && plot[y][x + 1] == name;
    }

    private boolean hasUpperNeighbour(final int x, final int y, final char name, final char[][] plot) {
        return inBounds(x, y - 1, plot) && plot[y - 1][x] == name;
    }

    private boolean hasLowerNeighbour(final int x, final int y, final char name, final char[][] plot) {
        return inBounds(x, y + 1, plot) && plot[y + 1][x] == name;
    }

    private boolean hasUnvisitedLeftNeighbour(final int x, final int y, final char name, final char[][] plot,
            final boolean[][] visited) {
        return hasLeftNeighbour(x, y, name, plot) && !visited[y][x - 1];
    }

    private boolean hasUnvisitedRightNeighbour(final int x, final int y, final char name, final char[][] plot,
            final boolean[][] visited) {
        return hasRightNeighbour(x, y, name, plot) && !visited[y][x + 1];
    }

    private boolean hasUnvisitedUpperNeighbour(final int x, final int y, final char name, final char[][] plot,
            final boolean[][] visited) {
        return hasUpperNeighbour(x, y, name, plot) && !visited[y - 1][x];
    }

    private boolean hasUnvisitedLowerNeighbour(final int x, final int y, final char name, final char[][] plot,
            final boolean[][] visited) {
        return hasLowerNeighbour(x, y, name, plot) && !visited[y + 1][x];
    }

    private boolean hasUpperLeftNeighbour(final Position p, final Set<Position> positions) {
        return positions.contains(new Position(p.x() - 1, p.y() - 1));
    }

    private boolean hasUpperRightNeighbour(final Position p, final Set<Position> positions) {
        return positions.contains(new Position(p.x() + 1, p.y() - 1));
    }

    private boolean hasLowerRightNeighbour(final Position p, final Set<Position> positions) {
        return positions.contains(new Position(p.x() + 1, p.y() + 1));
    }

    private boolean hasLowerLeftNeighbour(final Position p, final Set<Position> positions) {
        return positions.contains(new Position(p.x() - 1, p.y() + 1));
    }

    private boolean hasLeftNeighbour(final Position p, final Set<Position> positions) {
        return positions.contains(new Position(p.x() - 1, p.y()));
    }

    private boolean hasRightNeighbour(final Position p, final Set<Position> positions) {
        return positions.contains(new Position(p.x() + 1, p.y()));
    }

    private boolean hasUpperNeighbour(final Position p, final Set<Position> positions) {
        return positions.contains(new Position(p.x(), p.y() - 1));
    }

    private boolean hasLowerNeighbour(final Position p, final Set<Position> positions) {
        return positions.contains(new Position(p.x(), p.y() + 1));
    }

    private boolean hasUnvisitedNeighbours(final int x, final int y, final char name, final char[][] plot,
            final boolean[][] visited) {
        return hasUnvisitedLeftNeighbour(x, y, name, plot, visited)
                || hasUnvisitedRightNeighbour(x, y, name, plot, visited)
                || hasUnvisitedUpperNeighbour(x, y, name, plot, visited)
                || hasUnvisitedLowerNeighbour(x, y, name, plot, visited);
    }

    private int calculatePerimeter(final int x, final int y, final char name, final char[][] plot) {
        int perimeter = 0;
        if (!hasLeftNeighbour(x, y, name, plot)) {
            perimeter++;
        }
        if (!hasRightNeighbour(x, y, name, plot)) {
            perimeter++;
        }
        if (!hasUpperNeighbour(x, y, name, plot)) {
            perimeter++;
        }
        if (!hasLowerNeighbour(x, y, name, plot)) {
            perimeter++;
        }
        return perimeter;
    }

    private int getNumberOfSides(final Set<Position> positions) {
        int total = 0;
        // Calculating sides is just calculating corners
        for (final Position position : positions) {
            int corners = 0;
            if (!hasLeftNeighbour(position, positions) && !hasUpperNeighbour(position, positions)
                    || !hasUpperLeftNeighbour(position, positions) && hasLeftNeighbour(position, positions)
                            && hasUpperNeighbour(position, positions)) {
                corners++;
            }
            if (!hasRightNeighbour(position, positions) && !hasUpperNeighbour(position, positions)
                    || !hasUpperRightNeighbour(position, positions) && hasRightNeighbour(position, positions)
                            && hasUpperNeighbour(position, positions)) {
                corners++;
            }
            if (!hasRightNeighbour(position, positions) && !hasLowerNeighbour(position, positions)
                    || !hasLowerRightNeighbour(position, positions) && hasRightNeighbour(position, positions)
                            && hasLowerNeighbour(position, positions)) {
                corners++;
            }
            if (!hasLeftNeighbour(position, positions) && !hasLowerNeighbour(position, positions)
                    || !hasLowerLeftNeighbour(position, positions) && hasLeftNeighbour(position, positions)
                            && hasLowerNeighbour(position, positions)) {
                corners++;
            }
            total += corners;
        }

        return total;
    }

    private Set<Position> findArea(final int x, final int y, final char name, final char[][] plot,
            final boolean[][] visited) {
        final Set<Position> result = new HashSet<>();
        visited[y][x] = true;
        result.add(new Position(x, y));
        if (!hasUnvisitedNeighbours(x, y, name, plot, visited)) {
            return result;
        } else {
            if (hasUnvisitedLeftNeighbour(x, y, name, plot, visited)) {
                result.addAll(findArea(x - 1, y, name, plot, visited));
            }
            if (hasUnvisitedRightNeighbour(x, y, name, plot, visited)) {
                result.addAll(findArea(x + 1, y, name, plot, visited));
            }
            if (hasUnvisitedUpperNeighbour(x, y, name, plot, visited)) {
                result.addAll(findArea(x, y - 1, name, plot, visited));
            }
            if (hasUnvisitedLowerNeighbour(x, y, name, plot, visited)) {
                result.addAll(findArea(x, y + 1, name, plot, visited));
            }
            return result;
        }
    }

    private List<SizePerimeter> calculatePriceWithPerimeter(final int x, final int y, final char name,
            final char[][] plot, final boolean[][] visited,
            final int size, int perimeter) {
        final List<SizePerimeter> result = new ArrayList<>();
        visited[y][x] = true;
        perimeter += calculatePerimeter(x, y, name, plot);
        if (!hasUnvisitedNeighbours(x, y, name, plot, visited)) {
            result.add(new SizePerimeter(size, perimeter));
            return result;
        } else {
            boolean visitedNeighbour = false;
            if (hasUnvisitedLeftNeighbour(x, y, name, plot, visited)) {
                result.addAll(calculatePriceWithPerimeter(x - 1, y, name, plot, visited,
                        visitedNeighbour ? 1 : size + 1, visitedNeighbour ? 0 : perimeter));
                visitedNeighbour = true;
            }
            if (hasUnvisitedRightNeighbour(x, y, name, plot, visited)) {
                result.addAll(calculatePriceWithPerimeter(x + 1, y, name, plot, visited,
                        visitedNeighbour ? 1 : size + 1, visitedNeighbour ? 0 : perimeter));
                visitedNeighbour = true;
            }
            if (hasUnvisitedUpperNeighbour(x, y, name, plot, visited)) {
                result.addAll(calculatePriceWithPerimeter(x, y - 1, name, plot, visited,
                        visitedNeighbour ? 1 : size + 1, visitedNeighbour ? 0 : perimeter));
                visitedNeighbour = true;
            }
            if (hasUnvisitedLowerNeighbour(x, y, name, plot, visited)) {
                result.addAll(calculatePriceWithPerimeter(x, y + 1, name, plot, visited,
                        visitedNeighbour ? 1 : size + 1, visitedNeighbour ? 0 : perimeter));
                visitedNeighbour = true;
            }
            return result;
        }
    }

    private int calculatePriceWithSides(final char[][] plot) {
        final boolean[][] visited = new boolean[plot.length][plot[0].length];
        int totalPrice = 0;
        for (int y = 0; y < plot.length; y++) {
            for (int x = 0; x < plot[0].length; x++) {
                if (!visited[y][x]) {
                    final Set<Position> area = findArea(x, y, plot[y][x], plot, visited);
                    totalPrice += area.size() * getNumberOfSides(area);
                }
            }
        }
        return totalPrice;
    }

    private int calculatePriceWithPerimeter(final char[][] plot) {
        final boolean[][] visited = new boolean[plot.length][plot[0].length];
        int totalPrice = 0;
        for (int y = 0; y < plot.length; y++) {
            for (int x = 0; x < plot[0].length; x++) {
                if (!visited[y][x]) {
                    final List<SizePerimeter> result = calculatePriceWithPerimeter(x, y, plot[y][x], plot, visited, 1,
                            0);
                    totalPrice += result.stream().mapToInt(r -> r.size()).sum()
                            * result.stream().mapToInt(r -> r.perimeter()).sum();
                }
            }
        }
        return totalPrice;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(calculatePriceWithSides(getPlot(input)));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(calculatePriceWithPerimeter(getPlot(input)));
    }

    public static void main(final String... args) {
        new Day12("day12.txt");
    }
}

record SizePerimeter(int size, int perimeter) {
}

record Position(int x, int y) {
}