package nl.michielgraat.adventofcode2025.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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
     * Counts the number of steps from the first item added to the queue and the
     * finish.
     */
    private int getNrSteps(final String finish, final Queue<QueueNode> queue, final Map<String, List<String>> devices) {
        final QueueNode current = queue.poll();
        if (!finish.equals("out") && current.device().equals("out")) {
            // Extra piece of logic for the case where "out" is not the finish (because the
            // devices map does not contain an "out" entry).
            return 0;
        }
        if (current.device().equals(finish)) {
            return current.nrSteps();
        } else {
            for (final String device : devices.get(current.device())) {
                final QueueNode node = new QueueNode(device, current.nrSteps() + 1);
                if (!queue.contains(node)) {
                    queue.add(node);
                }
            }
            return getNrSteps(finish, queue, devices);
        }
    }

    /**
     * For a given node, calculate all the possible from which it can be reached.
     */
    private List<String> buildPossiblePathMembers(final String finish, final Map<String, List<String>> devices) {
        final List<String> result = new ArrayList<>();
        result.add(finish);
        for (final String node : devices.keySet()) {
            if (!node.equals(finish)) {
                final Queue<QueueNode> queue = new LinkedList<>();
                queue.add(new QueueNode(node, 0));
                if (getNrSteps(finish, queue, devices) > 0) {
                    result.add(node);
                }
            }
        }
        return result;
    }

    /**
     * Calculates all paths between two nodes.
     */
    private void getAllPaths(final String current, final String finish, final List<String> path,
            final List<List<String>> result,
            final List<String> visited, final List<String> neighbours, final Map<String, List<String>> devices) {
        if (!finish.equals("out") && current.equals("out")) {
            // Extra piece of logic for when the finish is not "out".
            return;
        }
        if (current.equals(finish)) {
            // Found a path to the finish, add it to the result list.
            result.add(path.stream().toList());
        } else {
            for (final String neighbour : devices.get(current)) {
                // Check if the finish is reachable for the current neighbour (for pruning of
                // the search tree) and if it has not been visited yet.
                if (neighbours.contains(neighbour) && !visited.contains(neighbour)) {
                    visited.add(neighbour);
                    path.add(neighbour);
                    getAllPaths(neighbour, finish, path, result, visited, neighbours, devices);
                    visited.remove(neighbour);
                    path.removeLast();
                }
            }
        }
    }

    /**
     * Counts the number of path between any two nodes.
     */
    private long countNrPaths(final String current, final String finish, final Map<String, List<String>> devices) {
        if (!finish.equals("out") && current.equals("out")) {
            // If the finish is not defined as "out", we have this extra piece of logic,
            // because "out" does not have an entry in the devices map.
            return 0;
        } else if (current.equals(finish)) {
            return 1;
        } else {
            long total = 0;
            for (final String output : devices.get(current)) {
                total += countNrPaths(output, finish, devices);
            }
            return total;
        }
    }

    private long getNrPathsBetween(final String start, final String finish, final Map<String, List<String>> devices) {
        final List<String> neighbours = buildPossiblePathMembers(finish, devices);
        final List<List<String>> result = new ArrayList<>();
        final List<String> path = new ArrayList<>();
        path.add(start);
        final List<String> visited = new ArrayList<>();
        visited.add(start);
        getAllPaths(start, finish, path, result, visited, neighbours, devices);
        return result.size();
    }

    @Override
    protected String runPart2(final List<String> input) {
        final Map<String, List<String>> devices = parseDevices(input);

        final long fftPaths = getNrPathsBetween("svr", "fft", devices);
        final long dacPaths = getNrPathsBetween("fft", "dac", devices);
        final long outPaths = getNrPathsBetween("dac", "out", devices);

        return String.valueOf(fftPaths * dacPaths * outPaths);
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(countNrPaths("you", "out", parseDevices(input)));
    }

    public static void main(final String... args) {
        new Day11("day11.txt");
    }
}
