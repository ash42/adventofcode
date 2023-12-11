package nl.michielgraat.adventofcode2023.day11;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day11 extends AocSolver {

    protected Day11(String filename) {
        super(filename);
    }

    private List<Integer> getColumnNrsToExpand(final List<String> input) {
        List<Integer> columns = new ArrayList<>();
        for (int x = 0; x < input.get(0).length(); x++) {
            boolean foundGalaxy = false;
            for (int y = 0; y < input.size(); y++) {
                String line = input.get(y);
                if (line.charAt(x) == '#') {
                    foundGalaxy = true;
                    break;
                }
            }
            if (!foundGalaxy) {
                columns.add(x);
            }
        }
        return columns;
    }

    private List<Integer> getRowNrsToExpand(final List<String> input) {
        List<Integer> rows = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            if (line.chars().allMatch(c -> c == '.')) {
                rows.add(y);
            }
        }

        return rows;
    }

    private List<Coordinate> getCoordinates(final List<String> image) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int y = 0; y < image.size(); y++) {
            String row = image.get(y);
            for (int x = 0; x < row.length(); x++) {
                coordinates.add(new Coordinate(x, y, row.charAt(x) == '#'));
            }
        }
        return coordinates;
    }

    private long getNrToAdd(int i, int j, List<Integer> toAdd, int timesToExpand) {
        long total = 0;
        int start = Math.min(i, j);
        int end = Math.max(i, j);
        for (int k = start; k < end; k++) {
            if (toAdd.contains(k)) {
                total++;
            }
        }
        return total * (timesToExpand - 1);
    }

    private long getTotalDistance(List<Coordinate> galaxies, int timesToExpand, List<Integer> rowNrsToExpand,
            List<Integer> columnNrsToExpand) {
        long total = 0;
        for (int g1Idx = 0; g1Idx < galaxies.size() - 1; g1Idx++) {
            for (int g2Idx = g1Idx + 1; g2Idx < galaxies.size(); g2Idx++) {
                Coordinate first = galaxies.get(g1Idx);
                Coordinate second = galaxies.get(g2Idx);
                total += Math.abs(first.x() - second.x()) + Math.abs(first.y() - second.y())
                        + getNrToAdd(first.x(), second.x(), columnNrsToExpand, timesToExpand)
                        + getNrToAdd(first.y(), second.y(), rowNrsToExpand, timesToExpand);
            }
        }
        return total;
    }

    @Override
    protected String runPart2(final List<String> input) {
        List<Coordinate> galaxies = getCoordinates(input).stream().filter(Coordinate::isGalaxy).toList();
        List<Integer> rowNrsToExpand = getRowNrsToExpand(input);
        List<Integer> columnNrsToExpand = getColumnNrsToExpand(input);
        return String.valueOf(getTotalDistance(galaxies, 1000000, rowNrsToExpand, columnNrsToExpand));
    }

    @Override
    protected String runPart1(final List<String> input) {
        List<Coordinate> galaxies = getCoordinates(input).stream().filter(Coordinate::isGalaxy).toList();
        List<Integer> rowNrsToExpand = getRowNrsToExpand(input);
        List<Integer> columnNrsToExpand = getColumnNrsToExpand(input);
        return String.valueOf(getTotalDistance(galaxies, 2, rowNrsToExpand, columnNrsToExpand));
    }

    public static void main(String... args) {
        new Day11("day11.txt");
    }
}
