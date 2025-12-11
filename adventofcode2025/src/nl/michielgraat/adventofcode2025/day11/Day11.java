package nl.michielgraat.adventofcode2025.day11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.michielgraat.adventofcode2025.AocSolver;

public class Day11 extends AocSolver {

    protected Day11(final String filename) {
        super(filename);
    }

    private Map<String, List<String>> parseDevices(final List<String> input) {
        final Map<String, List<String>> devices = new HashMap<>();
        for (final String line : input) {
            final String dev = line.substring(0, line.indexOf(':'));
            devices.put(dev, Arrays.stream(line.substring(line.indexOf(' ')).trim().split(" ")).toList());
        }
        return devices;
    }

    /**
     * Counts the number of path between any two nodes.
     */
    private long countNrPaths(final String current, final String finish,
            final Map<String, List<String>> devices, final Map<String, Long> memo) {
        if (!finish.equals("out") && current.equals("out")) {
            // If the finish is not defined as "out", we have this extra piece of logic,
            // because "out" does not have an entry in the devices map.
            return 0;
        } else if (current.equals(finish)) {
            return 1;
        } else {
            if (memo.containsKey(current)) {
                return memo.get(current);
            }
            long total = 0;
            for (final String output : devices.get(current)) {
                total += countNrPaths(output, finish, devices, memo);
            }
            memo.put(current, total);
            return total;
        }
    }

    @Override
    protected String runPart2(final List<String> input) {
        final Map<String, List<String>> devices = parseDevices(input);

        final long fftPaths = countNrPaths("svr", "fft", devices, new HashMap<>());
        final long dacPaths = countNrPaths("fft", "dac", devices, new HashMap<>());
        final long outPaths = countNrPaths("dac", "out", devices, new HashMap<>());

        return String.valueOf(fftPaths * dacPaths * outPaths);
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(countNrPaths("you", "out", parseDevices(input), new HashMap<>()));
    }

    public static void main(final String... args) {
        new Day11("day11.txt");
    }
}
