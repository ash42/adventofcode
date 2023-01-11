package nl.michielgraat.adventofcode2019.day14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2019.AocSolver;

public class Day14 extends AocSolver {

    protected Day14(final String filename) {
        super(filename);
    }

    private Map<Chemical, List<Chemical>> getChemReactions(final List<String> input) {
        final Map<Chemical, List<Chemical>> reactions = new HashMap<>();
        for (final String line : input) {
            final String[] parts = line.split("=>");
            final String name = parts[1].trim().split(" ")[1];
            final int quantity = Integer.parseInt(parts[1].trim().split(" ")[0]);
            final Chemical output = new Chemical(name, quantity);
            final List<Chemical> inputs = new ArrayList<>();
            for (final String inputChem : parts[0].trim().split(",")) {
                inputs.add(
                        new Chemical(inputChem.trim().split(" ")[1], Integer.parseInt(inputChem.trim().split(" ")[0])));
            }
            reactions.put(output, inputs);
        }
        return reactions;
    }

    private long getNrForTrillion(final Map<Chemical, List<Chemical>> reactions) {
        final long oreNeededForOne = new Chemical("FUEL", 1).getOreNeeded(reactions);
        long low = 1000000000000L / oreNeededForOne;
        long high = low * 10;
        while (low < high) {
            final long mid = (long) Math.ceil((low + high + 1) / (double) 2);
            if (new Chemical("FUEL", mid).getOreNeeded(reactions) <= 1000000000000L) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final Map<Chemical, List<Chemical>> reactions = getChemReactions(input);
        return String.valueOf(getNrForTrillion(reactions));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final Map<Chemical, List<Chemical>> reactions = getChemReactions(input);
        return String.valueOf(new Chemical("FUEL", 1).getOreNeeded(reactions));
    }

    public static void main(final String... args) {
        new Day14("day14.txt");
    }
}
