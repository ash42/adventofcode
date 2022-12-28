package nl.michielgraat.adventofcode2019.day02;

import java.util.List;

import nl.michielgraat.adventofcode2019.AocSolver;
import nl.michielgraat.adventofcode2019.intcode.IntcodeComputer;

public class Day02 extends AocSolver {

    protected Day02(final String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final IntcodeComputer c = new IntcodeComputer(input);
        for (int noun = 0; noun <= 99; noun++) {
            for (int verb = 0; verb <= 99; verb++) {
                c.setValueAtAddress(1, noun);
                c.setValueAtAddress(2, verb);
                c.run();
                if (19690720 == c.getValueAtAddress(0)) {
                    return String.valueOf((100 * noun + verb));
                }
                c.reset();
            }
        }
        return "no answer found";
    }

    @Override
    protected String runPart1(final List<String> input) {
        final IntcodeComputer c = new IntcodeComputer(input);
        c.setValueAtAddress(1, 12);
        c.setValueAtAddress(2, 2);
        c.run();
        return String.valueOf(c.getValueAtAddress(0));
    }

    public static void main(final String... args) {
        new Day02("day02.txt");
    }
}
