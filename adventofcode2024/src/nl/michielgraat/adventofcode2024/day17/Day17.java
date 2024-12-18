package nl.michielgraat.adventofcode2024.day17;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day17 extends AocSolver {

    protected Day17(final String filename) {
        super(filename);
    }

    private Set<Long> findCandidates(final long start, final int position, final List<String> input) {
        final Set<Long> candidates = new HashSet<>();

        for (long a = start; a < start + 8; a++) {
            final Computer computer = new Computer(input);
            computer.setA(a);
            computer.run();
            final int[] program = computer.getProgram();
            final int[] output = computer.getOutput();
            boolean valid = true;
            for (int i = position; i > 0; i--) {
                if (program[program.length - position] != output[output.length - position]) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                candidates.add(position < program.length ? a << 3 : a);
            }
        }
        return candidates;
    }

    private long findA(final List<String> input) {
        // Work from end of program backwards. We will get the last digit when a is
        // somewhere between 0 and 7.
        // After we have this first a, shift it left by 3 and start from that a until
        // a+7. When an a is found for the last and last-1 digits, shift a left by 3
        // again. Rinse and repeat.
        // For a digit there are potentially multiple a's that fit. So use these all
        // as candidates for generating the next digit.
        // In the end, get the minimum of all a's which lead to a copy of the program.
        Set<Long> candidates = new HashSet<>();
        candidates.add(0L);
        final Computer computer = new Computer(input);
        for (int i = 1; i <= computer.getProgram().length; i++) {
            final Set<Long> newCandidates = new HashSet<>();
            for (final long candidate : candidates) {
                newCandidates.addAll(findCandidates(candidate, i, input));
            }
            candidates = newCandidates;
        }
        return candidates.stream().mapToLong(l -> l).min().getAsLong();
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(findA(input));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final Computer computer = new Computer(input);
        computer.run();
        return Arrays.toString(computer.getOutput());
    }

    public static void main(final String... args) {
        new Day17("day17.txt");
    }
}
