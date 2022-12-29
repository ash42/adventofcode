package nl.michielgraat.adventofcode2019.day03;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.michielgraat.adventofcode2019.AocSolver;

public class Day03 extends AocSolver {

    protected Day03(final String filename) {
        super(filename);
    }

    private List<Line> getWire(final String input) {
        final List<Line> lines = new ArrayList<>();
        final String[] instructions = input.split(",");
        int x = 0;
        int y = 0;
        int stepNr = 0;
        for (final String instruction : instructions) {
            final int nrSteps = Integer.parseInt(instruction.substring(1));
            int xMod = 0;
            int yMod = 0;
            switch (instruction.charAt(0)) {
                case 'R':
                    xMod = nrSteps;
                    break;
                case 'L':
                    xMod = -nrSteps;
                    break;
                case 'D':
                    yMod = nrSteps;
                    break;
                case 'U':
                    yMod = -nrSteps;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown direction [" + instruction.charAt(0) + "]");
            }
            final Position start = new Position(x, y, stepNr);
            x += xMod;
            y += yMod;
            stepNr += nrSteps;
            final Position end = new Position(x, y, stepNr);
            lines.add(new Line(start, end));
        }
        return lines;
    }

    private int getMinStepsToIntersection(final List<Line> wire1, final List<Line> wire2) {
        int min = Integer.MAX_VALUE;
        final Position origin = new Position(0, 0, 0);
        for (final Line w1 : wire1) {
            for (final Line w2 : wire2) {
                final Optional<Position> intersection = w1.getIntersection(w2);
                if (intersection.isPresent() && !intersection.get().equals(origin)) {
                    final int thisSteps = intersection.get().steps();
                    final Position other = w2.getIntersection(w1).get();
                    final int otherSteps = other.steps();
                    min = Math.min(min, thisSteps + otherSteps);
                }
            }
        }
        return min;
    }

    private int getMDToClosestIntersection(final List<Line> wire1, final List<Line> wire2) {
        int min = Integer.MAX_VALUE;
        final Position origin = new Position(0, 0, 0);
        for (final Line w1 : wire1) {
            for (final Line w2 : wire2) {
                final Optional<Position> intersection = w1.getIntersection(w2);
                if (intersection.isPresent() && !intersection.get().equals(origin)) {
                    min = Math.min(min, intersection.get().getManhattanDistanceToStart());
                }
            }
        }
        return min;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(getMinStepsToIntersection(getWire(input.get(0)), getWire(input.get(1))));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getMDToClosestIntersection(getWire(input.get(0)), getWire(input.get(1))));
    }

    public static void main(final String... args) {
        new Day03("day03.txt");
    }
}
