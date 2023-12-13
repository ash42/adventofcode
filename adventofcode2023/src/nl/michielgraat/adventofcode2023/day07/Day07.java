package nl.michielgraat.adventofcode2023.day07;

import java.util.List;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day07 extends AocSolver {

    protected Day07(String filename) {
        super(filename);
    }

    private List<Hand> getSortedHands(final boolean jokerIsWildcard, final List<String> input) {
        return input
                .stream().map(i -> new Hand(i.substring(0, i.indexOf(" ")),
                        Integer.valueOf(i.substring(i.indexOf(" ") + 1)), jokerIsWildcard))
                .sorted()
                .collect(Collectors.toList());
    }

    private int getTotalWinnings(final boolean jokerIsWildcard, final List<String> input) {
        List<Hand> hands = getSortedHands(jokerIsWildcard, input);

        int total = 0;
        for (int i = 0; i < hands.size(); i++) {
            total += (i + 1) * hands.get(i).getBid();
        }
        return total;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(getTotalWinnings(true, input));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getTotalWinnings(false, input));
    }

    public static void main(String... args) {
        new Day07("day07.txt");
    }
}
