package nl.michielgraat.adventofcode2019.day24;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import nl.michielgraat.adventofcode2019.AocSolver;

public class Day24 extends AocSolver {

    protected Day24(final String filename) {
        super(filename);
    }

    private Map<Integer, Grid> copy(final Map<Integer, Grid> orig) {
        final Map<Integer, Grid> result = new TreeMap<>();
        for (final Entry<Integer, Grid> entry : orig.entrySet()) {
            result.put(entry.getKey(), new Grid(entry.getKey(), entry.getValue().getBugs()));
        }
        return result;
    }

    private void addSurroundingGrids(final Map<Integer, Grid> grids) {
        final List<Grid> newGrids = new ArrayList<>();
        for (final Grid grid : grids.values()) {
            if (!grids.containsKey(grid.getDepth() - 1)) {
                newGrids.add(new Grid(grid.getDepth() - 1));
            }
            if (!grids.containsKey(grid.getDepth() + 1)) {
                newGrids.add(new Grid(grid.getDepth() + 1));
            }
        }
        newGrids.forEach(n -> grids.put(n.getDepth(), n));
    }

    @Override
    protected String runPart2(final List<String> input) {
        final Map<Integer, Grid> depthToGrid = new TreeMap<>();
        final Grid zero = new Grid(input);
        depthToGrid.put(0, zero);
        for (int i = 1; i <= 200; i++) {
            addSurroundingGrids(depthToGrid);
            final Map<Integer, Grid> copy = copy(depthToGrid);
            for (final Grid grid : depthToGrid.values()) {
                grid.updateRecursive(copy);
            }
        }

        return String.valueOf(depthToGrid.entrySet().stream().mapToInt(e -> e.getValue().getBugs().size()).sum());
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<List<Position>> layouts = new ArrayList<>();
        final Grid eris = new Grid(input);
        layouts.add(eris.getBugs());
        boolean done = false;
        while (!done) {
            eris.update();
            final List<Position> layout = eris.getBugs();
            done = layouts.contains(layout);
            layouts.add(layout);
        }
        return String.valueOf(eris.getBiodiversityRating());
    }

    public static void main(final String... args) {
        new Day24("day24.txt");
    }
}
