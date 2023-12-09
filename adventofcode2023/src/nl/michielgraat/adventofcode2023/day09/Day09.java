package nl.michielgraat.adventofcode2023.day09;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day09 extends AocSolver {

    protected Day09(String filename) {
        super(filename);
    }

    private List<List<Integer>> getSequences(final List<String> input) {
        List<List<Integer>> sequences = new ArrayList<>();
        Pattern nrPattern = Pattern.compile("-?\\d+");

        for (String line : input) {
            List<Integer> sequence = new ArrayList<>();
            Matcher matcher = nrPattern.matcher(line);
            while (matcher.find()) {
                sequence.add(Integer.valueOf(matcher.group()));
            }
            sequences.add(sequence);
        }
        return sequences;
    }

    private List<Integer> getDifferences(List<Integer> sequence) {
        List<Integer> differences = new ArrayList<>();
        for (int i = 0; i < sequence.size() - 1; i++) {
            differences.add(sequence.get(i + 1) - sequence.get(i));
        }
        return differences;
    }

    private int getNextNumber(List<Integer> sequence) {
        List<Integer> differences = getDifferences(sequence);
        if (differences.stream().filter(x -> x != 0).findAny().isPresent()) {
            return sequence.get(sequence.size() - 1) + getNextNumber(differences);
        } else {
            return sequence.get(sequence.size() - 1);
        }
    }

    private int getPreviousNumber(List<Integer> sequence) {
        List<Integer> differences = getDifferences(sequence);
        if (differences.stream().filter(x -> x != 0).findAny().isPresent()) {
            return sequence.get(0) - getPreviousNumber(differences);
        } else {
            return sequence.get(0);
        }
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(getSequences(input).stream().mapToInt(s -> getPreviousNumber(s)).sum());
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getSequences(input).stream().mapToInt(s -> getNextNumber(s)).sum());
    }

    public static void main(String... args) {
        new Day09("day09.txt");
    }
}
