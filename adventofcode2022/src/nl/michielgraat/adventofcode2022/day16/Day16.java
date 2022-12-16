package nl.michielgraat.adventofcode2022.day16;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day16 extends AocSolver {

    private Map<State, Integer> cache = new HashMap<>();

    protected Day16(String filename) {
        super(filename);
    }

    private List<Valve> parseValves(final List<String> input) {
        List<Valve> valves = new ArrayList<>();
        final Pattern namePattern = Pattern.compile("([A-Z]{2})");
        final Pattern flowPattern = Pattern.compile("\\d+");
        for (String line : input) {
            Matcher flowMatcher = flowPattern.matcher(line);
            flowMatcher.find();
            int flow = Integer.parseInt(flowMatcher.group());

            Matcher nameMatcher = namePattern.matcher(line);
            nameMatcher.find();
            String name = nameMatcher.group();
            Valve valve = new Valve(name, flow);
            while (nameMatcher.find()) {
                String neighbourName = nameMatcher.group();
                valve.addNeighbourName(neighbourName);
            }
            valves.add(valve);
        }
        return valves;
    }

    public int calcPressure(Valve start, int minute, int pressure, List<Valve> opened, List<Valve> valves) {
        pressure += opened.stream().mapToInt(Valve::getFlowRate).sum();
        if (minute == 30) {
            return pressure;
        }
        State state = new State(start, minute, opened);
        if (cache.keySet().contains(state)) {
            return cache.get(state);
        }

        int max = 0;
        if (start.getFlowRate() > 0 && !opened.contains(start)) {
            opened.add(start);
            // Make sure the opened valves are sorted, to make the cache work
            Collections.sort(opened);
            int val = calcPressure(start, minute + 1, pressure, opened, valves);
            opened.remove(start);
            cache.put(state, val);
            max = val;
        }

        for (String n : start.getNeighbourNames()) {
            Valve neighbour = valves.stream().filter(v -> v.getName().equals(n)).findFirst()
                    .orElseThrow(NoSuchElementException::new);
            int val = calcPressure(neighbour, minute + 1, pressure, opened, valves);
            cache.put(state, val);
            max = Math.max(max, val);
        }
        return max;

    }

    @Override
    protected String runPart2(final List<String> input) {
        return "part 2";
    }

    @Override
    protected String runPart1(final List<String> input) {
        List<Valve> valves = parseValves(input);
        Valve start = valves.stream().filter(f -> f.getName().equals("AA")).findFirst()
                .orElseThrow(NoSuchElementException::new);
        cache = new HashMap<>();
        return String.valueOf(calcPressure(start, 1, 0, new ArrayList<>(), valves));
    }

    public static void main(String... args) {
        new Day16("day16.txt");
    }
}
