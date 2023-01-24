package nl.michielgraat.adventofcode2019.day19;

import java.util.List;
import java.util.NoSuchElementException;

import nl.michielgraat.adventofcode2019.AocSolver;
import nl.michielgraat.adventofcode2019.intcode.IntcodeComputer;

public class Day19 extends AocSolver {

    protected Day19(final String filename) {
        super(filename);
    }

    private int getLeftEdge(final int y, final IntcodeComputer droneSystem) {
        for (int x = 0;; x++) {
            droneSystem.addInput(x);
            droneSystem.addInput(y);
            droneSystem.run();
            final long output = droneSystem.readOutput();
            droneSystem.reset();
            if (output == 1) {
                return x;
            }
        }
    }

    private boolean inBeam(final int x, final int y, final IntcodeComputer droneSystem) {
        droneSystem.addInput(x);
        droneSystem.addInput(y);
        droneSystem.run();
        final long output = droneSystem.readOutput();
        droneSystem.reset();
        return output == 1;
    }

    private long getClosestSquare(final int size, final int startY, final IntcodeComputer droneSystem) {
        for (int y = startY;; y++) {
            final int left = getLeftEdge(y, droneSystem);
            for (int x = left;; x++) {
                if (!inBeam(x + size - 1, y, droneSystem)) {
                    break;
                } else {
                    if (inBeam(x, y + size - 1, droneSystem)) {
                        return x * 10000L + y;
                    }
                }
            }
        }
    }

    @Override
    protected String runPart2(final List<String> input) {
        final IntcodeComputer droneSystem = new IntcodeComputer(input);
        return String.valueOf(getClosestSquare(100, 800, droneSystem));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final IntcodeComputer droneSystem = new IntcodeComputer(input);
        int total = 0;
        for (int y = 0; y < 50; y++) {
            for (int x = 0; x < 50; x++) {
                droneSystem.addInput(x);
                droneSystem.addInput(y);
                droneSystem.run();
                final long output = droneSystem.readOutput();
                total += output;
                droneSystem.reset();
            }
        }
        return String.valueOf(total);
    }

    public static void main(final String... args) {
        new Day19("day19.txt");
    }
}
