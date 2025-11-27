package nl.michielgraat.adventofcode2025.skeleton;

import java.util.List;

import nl.michielgraat.adventofcode2025.AocSolver;

public class Skeleton extends AocSolver {

    protected Skeleton(String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        return "part 2";
    }

    @Override
    protected String runPart1(final List<String> input) {
        return "part 1";
    }
    
    public static void main(String... args) {
        new Skeleton("skeleton.txt");
    }
}
