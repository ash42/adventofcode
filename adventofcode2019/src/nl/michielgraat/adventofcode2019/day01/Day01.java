package nl.michielgraat.adventofcode2019.day01;

import java.util.List;

import nl.michielgraat.adventofcode2019.AocSolver;

public class Day01 extends AocSolver {

    protected Day01(final String filename) {
        super(filename);
    }

    private int calculateFuel(final int mass) {
        final int fuel = Math.floorDiv(mass, 3) - 2;
        return fuel <= 0 ? 0 : fuel + calculateFuel(fuel);
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(convertToIntList(input).stream().mapToInt(this::calculateFuel).sum());
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(convertToIntList(input).stream().mapToInt(m -> Math.floorDiv(m, 3) - 2).sum());
    }

    public static void main(final String... args) {
        new Day01("day01.txt");
    }
}
