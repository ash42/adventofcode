package nl.michielgraat.adventofcode2022.day10;

import java.util.List;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day10 extends AocSolver {

    protected Day10(final String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final CRT crt = new CRT();
        crt.drawImage(input);
        return crt.printImage();
    }

    @Override
    protected String runPart1(final List<String> input) {
        final CRT crt = new CRT();
        crt.drawImage(input);
        return String.valueOf(crt.getTotalSignalStrength());
    }
    
    public static void main(final String... args) {
        new Day10("day10.txt");
    }
}
