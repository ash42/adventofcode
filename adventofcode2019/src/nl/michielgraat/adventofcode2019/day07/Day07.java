package nl.michielgraat.adventofcode2019.day07;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2019.AocSolver;
import nl.michielgraat.adventofcode2019.intcode.IntcodeComputer;

public class Day07 extends AocSolver {

    protected Day07(final String filename) {
        super(filename);
    }

    private List<List<Integer>> generatePermutations(final List<Integer> original) {
        if (original.isEmpty()) {
            final List<List<Integer>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }
        final Integer firstElement = original.remove(0);
        final List<List<Integer>> returnValue = new ArrayList<>();
        final List<List<Integer>> permutations = generatePermutations(original);
        for (final List<Integer> smallerPermutated : permutations) {
            for (int index = 0; index <= smallerPermutated.size(); index++) {
                final List<Integer> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }

    private long getOutputForAmp(final int input1, final long input2, final List<String> input) {
        final IntcodeComputer amp = new IntcodeComputer(input);
        amp.addInput(input1);
        amp.addInput(input2);
        amp.run();
        return amp.readOutput();
    }

    private long getOutputForPhases(final List<Integer> phases, final List<String> input) {
        return getOutputForAmp(phases.get(4), getOutputForAmp(phases.get(3),
                getOutputForAmp(phases.get(2),
                        getOutputForAmp(phases.get(1), getOutputForAmp(phases.get(0), 0L, input), input), input),
                input), input);
    }

    private long getOutputForPhaseWithFeedback(final List<Integer> phases, final List<String> input) {
        final IntcodeComputer amp1 = new IntcodeComputer(input);
        final IntcodeComputer amp2 = new IntcodeComputer(input);
        final IntcodeComputer amp3 = new IntcodeComputer(input);
        final IntcodeComputer amp4 = new IntcodeComputer(input);
        final IntcodeComputer amp5 = new IntcodeComputer(input);
        amp1.addInput(phases.get(0));
        amp2.addInput(phases.get(1));
        amp3.addInput(phases.get(2));
        amp4.addInput(phases.get(3));
        amp5.addInput(phases.get(4));

        long result = 0;
        while (!amp5.isHalted()) {
            amp1.addInput(result);
            amp1.run();
            amp2.addInput(amp1.readOutput());
            amp2.run();
            amp3.addInput(amp2.readOutput());
            amp3.run();
            amp4.addInput(amp3.readOutput());
            amp4.run();
            amp5.addInput(amp4.readOutput());
            amp5.run();
            result = amp5.readOutput();
        }
        return result;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<List<Integer>> phasePerms = generatePermutations(new ArrayList<>(List.of(5, 6, 7, 8, 9)));
        long max = Long.MIN_VALUE;
        for (final List<Integer> phasePerm : phasePerms) {
            max = Math.max(max, getOutputForPhaseWithFeedback(phasePerm, input));
        }
        return String.valueOf(max);
    }

    @Override
    protected String runPart1(final List<String> input) {
        long max = Long.MIN_VALUE;
        final List<List<Integer>> phasePerms = generatePermutations(new ArrayList<>(List.of(0, 1, 2, 3, 4)));
        for (final List<Integer> phasePerm : phasePerms) {
            max = Math.max(max, getOutputForPhases(phasePerm, input));
        }
        return String.valueOf(max);
    }

    public static void main(final String... args) {
        new Day07("day07.txt");
    }
}
