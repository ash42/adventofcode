package nl.michielgraat.adventofcode2019.day05;

import java.util.List;

import nl.michielgraat.adventofcode2019.AocSolver;
import nl.michielgraat.adventofcode2019.intcode.IntcodeComputer;

public class Day05 extends AocSolver {

    protected Day05(final String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final IntcodeComputer c = new IntcodeComputer(input);
        c.addInput(5);
        c.run();
        return String.valueOf(c.readOutput());
    }

    @Override
    protected String runPart1(final List<String> input) {
        final IntcodeComputer c = new IntcodeComputer(input);
        c.addInput(1);
        c.run();
        return String.valueOf(c.readOutput());
    }

    public static void main(final String... args) {
        new Day05("day05.txt");
    }
}
