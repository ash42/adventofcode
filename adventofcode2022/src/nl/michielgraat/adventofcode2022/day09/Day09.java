package nl.michielgraat.adventofcode2022.day09;

import java.util.List;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day09 extends AocSolver {

    protected Day09(final String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final Rope rope = new Rope(10);
        rope.doMotions(input);
        return String.valueOf(rope.getNrOfTailPositions());
    }

    @Override
    protected String runPart1(final List<String> input) {
        final Rope rope = new Rope(2);
        rope.doMotions(input);
        return String.valueOf(rope.getNrOfTailPositions());
    }

    public static void main(final String... args) {
        new Day09("day09.txt");
    }
}
