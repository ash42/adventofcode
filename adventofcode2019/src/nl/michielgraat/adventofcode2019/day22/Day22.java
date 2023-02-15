package nl.michielgraat.adventofcode2019.day22;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import nl.michielgraat.adventofcode2019.AocSolver;

public class Day22 extends AocSolver {

    protected Day22(final String filename) {
        super(filename);
    }

    private BigInteger seekPosition(final List<Shuffle> shuffles, final BigInteger deckSize,
            final BigInteger timesShuffled,
            final int position) {
        final BigInteger[] calc = new BigInteger[] { Shuffle.getAsBigInt(1), Shuffle.getAsBigInt(0) };
        Collections.reverse(shuffles);
        for (final Shuffle shuffle : shuffles) {
            shuffle.run(calc, deckSize);
            for (int i = 0; i < calc.length; i++)
                calc[i] = calc[i].mod(deckSize);
        }
        final BigInteger pow = calc[0].modPow(timesShuffled, deckSize);
        return pow.multiply(Shuffle.getAsBigInt(position))
                .add(calc[1].multiply(pow.add(deckSize).subtract(Shuffle.getAsBigInt(1))).multiply(calc[0]
                        .subtract(Shuffle.getAsBigInt(1)).modPow(deckSize.subtract(Shuffle.getAsBigInt(2)), deckSize)))
                .mod(deckSize);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Shuffle> shuffles = input.stream().map(Shuffle::new).collect(Collectors.toList());
        return seekPosition(shuffles, Shuffle.getAsBigInt(119315717514047L), Shuffle.getAsBigInt(101741582076661L),
                2020).toString();
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Shuffle> shuffles = input.stream().map(Shuffle::new).collect(Collectors.toList());
        List<Integer> cards = IntStream.range(0, 10007).boxed().collect(Collectors.toList());
        for (final Shuffle shuffle : shuffles) {
            cards = shuffle.run(cards);
        }
        return String.valueOf(cards.indexOf(2019));
    }

    public static void main(final String... args) {
        new Day22("day22.txt");
    }
}
