package nl.michielgraat.adventofcode2019.day11;

import java.util.List;

import nl.michielgraat.adventofcode2019.AocSolver;

public class Day11 extends AocSolver {

    protected Day11(final String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final Robot robot = new Robot(new Panel(0, 0, Color.WHITE), input);
        robot.run();
        return robot.getIdentifier();
    }

    @Override
    protected String runPart1(final List<String> input) {
        final Robot robot = new Robot(new Panel(0, 0, Color.BLACK), input);
        robot.run();
        return String.valueOf(robot.getNrOfPaintedPanels());
    }

    public static void main(final String... args) {
        new Day11("day11.txt");
    }
}
