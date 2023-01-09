package nl.michielgraat.adventofcode2019.day13;

import java.util.List;

import nl.michielgraat.adventofcode2019.AocSolver;

public class Day13 extends AocSolver {

    protected Day13(final String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final ArcadeCabinet cabinet = new ArcadeCabinet(input);
        cabinet.play();
        return String.valueOf(cabinet.getScore());
    }

    @Override
    protected String runPart1(final List<String> input) {
        final ArcadeCabinet cabinet = new ArcadeCabinet(input);
        cabinet.run();
        return String.valueOf(cabinet.getNrOfBlockTiles());
    }

    public static void main(final String... args) {
        new Day13("day13.txt");
    }
}
