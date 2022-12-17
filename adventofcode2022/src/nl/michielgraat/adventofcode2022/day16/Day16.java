package nl.michielgraat.adventofcode2022.day16;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day16 extends AocSolver {

    private Map<State, Integer> cache = new HashMap<>();

    protected Day16(final String filename) {
        super(filename);
    }

    private List<Valve> parseValves(final List<String> input) {
        final List<Valve> valves = new ArrayList<>();
        final Pattern namePattern = Pattern.compile("([A-Z]{2})");
        final Pattern flowPattern = Pattern.compile("\\d+");
        for (final String line : input) {
            final Matcher flowMatcher = flowPattern.matcher(line);
            flowMatcher.find();
            final int flow = Integer.parseInt(flowMatcher.group());

            final Matcher nameMatcher = namePattern.matcher(line);
            nameMatcher.find();
            final String name = nameMatcher.group();
            final Valve valve = new Valve(name, flow);
            while (nameMatcher.find()) {
                final String neighbourName = nameMatcher.group();
                valve.addNeighbourName(neighbourName);
            }
            valves.add(valve);
        }
        return valves;
    }

    public int calcPressure(final Valve start, final int minute, final List<Valve> opened, final List<Valve> valves, final int nrOfOtherPlayers) {
        if (minute == 0) {
            final Valve aa = valves.stream().filter(f -> f.getName().equals("AA")).findFirst()
                    .orElseThrow(NoSuchElementException::new);
            return nrOfOtherPlayers > 0 ? calcPressure(aa, 26, opened, valves, nrOfOtherPlayers - 1) : 0;
        }
        final State state = new State(start, minute, opened, nrOfOtherPlayers);
        if (cache.keySet().contains(state)) {
            return cache.get(state);
        }

        int max = 0;
        if (start.getFlowRate() > 0 && !opened.contains(start)) {
            opened.add(start);
            // Make sure the opened valves are sorted, to make the cache work
            Collections.sort(opened);
            final int val = (minute - 1) * start.getFlowRate()
                    + calcPressure(start, minute - 1, opened, valves, nrOfOtherPlayers);
            opened.remove(start);
            max = val;
        }

        for (final String n : start.getNeighbourNames()) {
            final Valve neighbour = valves.stream().filter(v -> v.getName().equals(n)).findFirst()
                    .orElseThrow(NoSuchElementException::new);
            max = Math.max(max, calcPressure(neighbour, minute - 1, opened, valves, nrOfOtherPlayers));
        }
        cache.put(state, max);

        return max;

    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Valve> valves = parseValves(input);
        final Valve start = valves.stream().filter(f -> f.getName().equals("AA")).findFirst()
                .orElseThrow(NoSuchElementException::new);
        cache = new HashMap<>();
        return String.valueOf(calcPressure(start, 26, new ArrayList<>(), valves, 1));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Valve> valves = parseValves(input);
        final Valve start = valves.stream().filter(f -> f.getName().equals("AA")).findFirst()
                .orElseThrow(NoSuchElementException::new);
        cache = new HashMap<>();
        return String.valueOf(calcPressure(start, 30, new ArrayList<>(), valves, 0));
    }

    public static void main(final String... args) {
        new Day16("day16.txt");
    }
}