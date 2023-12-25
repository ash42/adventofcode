package nl.michielgraat.adventofcode2023.day23;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day23 extends AocSolver {

    protected Day23(String filename) {
        super(filename);
    }

    private char[][] readGrid(List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            grid[y] = line.toCharArray();
        }
        return grid;
    }

    private Coordinate getStart(List<String> input) {
        String line = input.get(0);
        return new Coordinate(line.indexOf('.'), 0);
    }

    private Coordinate getEnd(List<String> input) {
        String line = input.get(input.size() - 1);
        return new Coordinate(line.indexOf('.'), input.size() - 1);
    }

    //IMPORTANT: increase stack size to at least 2MB: -Xss2m
    private List<Coordinate> getLongestPath(Coordinate start, Coordinate end, Set<Coordinate> visited, char[][] grid, boolean part1) {
        List<Coordinate> path = new ArrayList<>();
        if (visited.contains(start)) {
            return path;
        } else if (start.equals(end)) {
            path.add(start);
            return path;
        } else {
            visited.add(start);
            int maxLength = 0;
            for (Coordinate neighbour : start.getNeighbours(grid, part1)) {
                List<Coordinate> rPath = getLongestPath(neighbour, end, visited, grid, part1);
                if (!rPath.isEmpty() && rPath.size() > maxLength) {
                    maxLength = rPath.size();
                    rPath.add(0, start);
                    path = rPath;
                }
            }
            visited.remove(start);
        }
        return path;
    }

    @Override
    protected String runPart2(final List<String> input) {
        System.out.println(new Date());
        return String.valueOf(getLongestPath(getStart(input), getEnd(input), new HashSet<>(), readGrid(input), false).size() - 1);
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getLongestPath(getStart(input), getEnd(input), new HashSet<>(), readGrid(input), true).size() - 1);
    }

    public static void main(String... args) {
        new Day23("day23.txt");
    }
}
