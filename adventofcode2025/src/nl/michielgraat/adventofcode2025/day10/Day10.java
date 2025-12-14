package nl.michielgraat.adventofcode2025.day10;

import java.util.List;
import nl.michielgraat.adventofcode2025.AocSolver;

public class Day10 extends AocSolver {

    protected Day10(String filename) {
        super(filename);
    }

    private List<Manual> parseManuals(final List<String> input) {
        return input.stream().map(l -> new Manual(l)).toList();
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(parseManuals(input).stream().mapToInt(Manual::getNrOfPressesForVoltages).sum());
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(parseManuals(input).stream().mapToInt(Manual::getMinPressesToLight).sum());
    }

    public static void main(String... args) {
        new Day10("day10.txt");
    }
}
