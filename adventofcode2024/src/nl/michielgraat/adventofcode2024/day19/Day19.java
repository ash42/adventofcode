package nl.michielgraat.adventofcode2024.day19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day19 extends AocSolver {

    protected Day19(final String filename) {
        super(filename);
    }

    private List<String> parseTowels(final List<String> input) {
        final List<String> towels = new ArrayList<>();
        for (int i = 2; i < input.size(); i++) {
            towels.add(input.get(i));
        }
        return towels;
    }

    private long countNrWays(final String towel, final String[] colors, final Map<String, Long> memoMap) {
        if (memoMap.containsKey(towel)) {
            return memoMap.get(towel);
        }
        if (towel.isEmpty()) {
            return 1;
        } else {
            long total = 0;
            for (int i = 0; i < colors.length; i++) {
                if (towel.startsWith(colors[i])) {
                    total += countNrWays(towel.substring(colors[i].length()), colors, memoMap);
                }
            }
            memoMap.put(towel, total);
            return total;
        }
    }

    private boolean canBeDesigned(final String towel, final String[] colors) {
        if (towel.isEmpty()) {
            return true;
        } else {
            boolean found = false;
            for (int i = 0; i < colors.length; i++) {
                final String color = colors[i];
                if (towel.startsWith(color)) {
                    found = found || canBeDesigned(towel.substring(color.length()), colors);
                }
            }
            return found;
        }
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(parseTowels(input).stream()
                .mapToLong(t -> countNrWays(t, input.get(0).split(", "), new HashMap<>())).sum());
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String
                .valueOf(parseTowels(input).stream().filter(t -> canBeDesigned(t, input.get(0).split(", "))).count());
    }

    public static void main(final String... args) {
        new Day19("day19.txt");
    }
}
