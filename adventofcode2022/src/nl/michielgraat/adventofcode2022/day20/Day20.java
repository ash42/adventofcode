package nl.michielgraat.adventofcode2022.day20;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day20 extends AocSolver {

    protected Day20(final String filename) {
        super(filename);
    }

    private void mix(final List<Number> mixFile, final List<Number> origFile) {
        for (final Number nr : origFile) {
            final int idx = mixFile.indexOf(nr);
            mixFile.remove(idx);
            // Use -1 on the modulo operation because we have just removed an element.
            mixFile.add(Math.floorMod(nr.val() + idx, origFile.size() - 1), nr);
        }
    }

    private long getSum(final List<Number> mixFile, final List<Number> origFile) {
        final int idx = mixFile.indexOf(
                mixFile.stream().filter(n -> n.val() == 0).findFirst().orElseThrow(NoSuchElementException::new));
        final long nr1 = mixFile.get(Math.floorMod(1000 + idx, origFile.size())).val();
        final long nr2 = mixFile.get(Math.floorMod(2000 + idx, origFile.size())).val();
        final long nr3 = mixFile.get(Math.floorMod(3000 + idx, origFile.size())).val();
        return nr1 + nr2 + nr3;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final long decriptionKey = 811589153L;
        final List<Number> mixFile = IntStream.range(0, input.size())
                .mapToObj(i -> new Number(Long.parseLong(input.get(i)) * decriptionKey, i))
                .collect(Collectors.toList());
        final List<Number> origFile = mixFile.stream().toList();

        for (int i = 0; i < 10; i++) {
            mix(mixFile, origFile);
        }

        return String.valueOf(getSum(mixFile, origFile));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Number> mixFile = IntStream.range(0, input.size())
                .mapToObj(i -> new Number(Long.parseLong(input.get(i)), i)).collect(Collectors.toList());
        final List<Number> origFile = mixFile.stream().toList();

        mix(mixFile, origFile);

        return String.valueOf(getSum(mixFile, origFile));
    }

    public static void main(final String... args) {
        new Day20("day20.txt");
    }
}
