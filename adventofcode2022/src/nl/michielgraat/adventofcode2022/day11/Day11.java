package nl.michielgraat.adventofcode2022.day11;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day11 extends AocSolver {

    protected Day11(final String filename) {
        super(filename);
    }

    private int gcd(final int a, final int b) {
        if (a == 0 || b == 0) {
            return a + b;
        } else {
            return gcd(Math.max(a, b) % Math.min(a, b), Math.min(a, b));
        }
    }

    private int lcm(final int a, final int b) {
        return a * b / gcd(a, b);
    }

    private void setLcm(final List<Monkey> monkeys) {
        int lcm = 1;
        for (final Monkey monkey : monkeys) {
            lcm = lcm(lcm, monkey.getDivisor());
        }
        for (final Monkey monkey : monkeys) {
            monkey.setLcm(lcm);
        }
    }

    private List<Monkey> getMonkeys(final List<String> input) {
        final List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 7) {
            monkeys.add(new Monkey(input.subList(i, i + 6)));
        }
        setLcm(monkeys);
        return monkeys;
    }

    private void playRounds(final int nrOfRounds, final List<Monkey> monkeys, final boolean part1) {
        for (int i = 1; i <= nrOfRounds; i++) {
            for (final Monkey monkey : monkeys) {
                final List<Throw> monkeyThrows = monkey.takeTurn(part1);
                for (final Throw t : monkeyThrows) {
                    monkeys.get(t.monkey()).getItems().add(t.item());
                }
            }
        }
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Monkey> monkeys = getMonkeys(input);
        playRounds(10000, monkeys, false);
        return String.valueOf(monkeys.stream().mapToLong(Monkey::getNrInspections).boxed()
                .sorted(Comparator.reverseOrder()).limit(2).reduce(1L, (a, b) -> a * b));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Monkey> monkeys = getMonkeys(input);
        playRounds(20, monkeys, true);
        return String.valueOf(monkeys.stream().mapToInt(Monkey::getNrInspections).boxed()
                .sorted(Comparator.reverseOrder()).limit(2).reduce(1, (a, b) -> a * b));
    }

    public static void main(final String... args) {
        new Day11("day11.txt");
    }
}
