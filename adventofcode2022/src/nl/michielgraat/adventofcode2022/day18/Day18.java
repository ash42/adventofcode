package nl.michielgraat.adventofcode2022.day18;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day18 extends AocSolver {

    private static final int AIR = 0;
    private static final int WATER = 1;
    private static final int LAVA = 2;

    protected Day18(final String filename) {
        super(filename);
    }

    private List<Point3d> getPoints(final List<String> input) {
        final List<Point3d> points = new ArrayList<>();
        for (final String line : input) {
            final List<Integer> coordinates = Arrays.stream(line.split(",")).map(Integer::parseInt)
                    .collect(Collectors.toList());
            points.add(new Point3d(coordinates.get(0), coordinates.get(1), coordinates.get(2)));
        }
        return points;
    }

    private int[][][] getCube(final List<Point3d> points) {
        final int maxX = points.stream().mapToInt(Point3d::x).max().getAsInt();
        final int maxY = points.stream().mapToInt(Point3d::y).max().getAsInt();
        final int maxZ = points.stream().mapToInt(Point3d::z).max().getAsInt();
        final int maxLength = Math.max(maxX, Math.max(maxY, maxZ));
        final int dimension = maxLength + 1;
        final int[][][] cube = new int[dimension][dimension][dimension];
        points.forEach(p -> cube[p.x()][p.y()][p.z()] = LAVA);
        return cube;
    }

    private boolean withinCube(final Point3d p, final int[][][] cube) {
        return p.x() >= 0 && p.y() >= 0 && p.z() >= 0 && p.x() < cube.length && p.y() < cube[0].length
                && p.z() < cube[0][0].length;
    }

    private List<Point3d> filterAirPockets(final int[][][] cube) {
        final List<Point3d> airPockets = new ArrayList<>();
        for (int x = 0; x < cube.length; x++) {
            for (int y = 0; y < cube[0].length; y++) {
                for (int z = 0; z < cube[0][0].length; z++) {
                    if (cube[x][y][z] == AIR) {
                        airPockets.add(new Point3d(x, y, z));
                    }
                }
            }
        }
        return airPockets;
    }

    private List<Point3d> findAirPockets(final Point3d start, final int[][][] cube) {
        final Deque<Point3d> stack = new ArrayDeque<>();
        final Set<Point3d> visited = new HashSet<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            final Point3d cur = stack.pop();
            visited.add(cur);
            if (cube[cur.x()][cur.y()][cur.z()] == AIR) {
                cube[cur.x()][cur.y()][cur.z()] = WATER;
                for (final Point3d neighbour : cur.getNeighbours()) {
                    if (!visited.contains(neighbour) && withinCube(neighbour, cube)) {
                        stack.push(neighbour);
                    }
                }
            }
        }
        return filterAirPockets(cube);
    }

    private int calcSurface(final List<Point3d> points) {
        int total = points.size() * 6;
        for (int i = 0; i < points.size() - 1; i++) {
            final Point3d point = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                if (point.adjacent(points.get(j))) {
                    total -= 2;
                }
            }
        }
        return total;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Point3d> points = getPoints(input);
        final List<Point3d> airPockets = findAirPockets(new Point3d(0, 0, 0), getCube(points));
        return String.valueOf(calcSurface(points) - calcSurface(airPockets));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(calcSurface(getPoints(input)));
    }

    public static void main(final String... args) {
        new Day18("day18.txt");
    }
}
