package nl.michielgraat.adventofcode2019.day21;

import java.util.List;

import nl.michielgraat.adventofcode2019.AocSolver;
import nl.michielgraat.adventofcode2019.intcode.IntcodeComputer;

public class Day21 extends AocSolver {

    protected Day21(final String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final IntcodeComputer springdroid = new IntcodeComputer(input);
        /**
         * I more or less stumbled upon this solution after lots of trial and error.
         * The first part is the same. For the second part:
         * 
         * If you did jump, check if E or H are also available. We do this by checking
         * if we jumped and E or T are true. So basically (E OR H) AND J. But only if we
         * did jump, so if we did jump, first set T to false, OR E and H with T and if
         * J and T are true, go ahead (notice how J would be false in the final AND if
         * we did not jump).
         * 
         * Apparently it is enough to only have E or H available after jumping to D to
         * solve all patterns.
         */
        springdroid.addAsciiInput("NOT A J");
        springdroid.addAsciiInput("NOT B T");
        springdroid.addAsciiInput("OR T J");
        springdroid.addAsciiInput("NOT C T");
        springdroid.addAsciiInput("OR T J");
        springdroid.addAsciiInput("AND D J");
        springdroid.addAsciiInput("NOT J T");
        springdroid.addAsciiInput("OR E T");
        springdroid.addAsciiInput("OR H T");
        springdroid.addAsciiInput("AND T J");
        springdroid.addAsciiInput("RUN");
        springdroid.run();
        return String.valueOf(springdroid.readOutput());
    }

    @Override
    protected String runPart1(final List<String> input) {
        final IntcodeComputer springdroid = new IntcodeComputer(input);
        /**
         * A jump goes 4 spaces forward. So, you want to jump if positions A, B or C
         * don't have ground beneath it and D does.
         * 
         * So: IF (NOT A OR NOT B OR NOT C) AND D -> JUMP
         * 
         * IF NOT A JUMP becomes NOT A J
         * 
         * SET T to value of NOT B, OR this with J (so, if A doesn't have groud beneath
         * it, or B doesn't have ground beneath it, or both, jump). This becomes:
         * 
         * NOT B T
         * OR T J
         * 
         * Do the same for C:
         * 
         * NOT C T
         * OR T J
         * 
         * Finally, D should have ground beneath it, otherwise you land in a hole.
         * 
         * AND D J
         * 
         * And start walking:
         * 
         * WALK
         * 
         */
        springdroid.addAsciiInput("NOT A J");
        springdroid.addAsciiInput("NOT B T");
        springdroid.addAsciiInput("OR T J");
        springdroid.addAsciiInput("NOT C T");
        springdroid.addAsciiInput("OR T J");
        springdroid.addAsciiInput("AND D J");
        springdroid.addAsciiInput("WALK");
        springdroid.run();
        return String.valueOf(springdroid.readOutput());
    }

    public static void main(final String... args) {
        new Day21("day21.txt");
    }
}
