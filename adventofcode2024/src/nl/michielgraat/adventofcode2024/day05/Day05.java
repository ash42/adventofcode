package nl.michielgraat.adventofcode2024.day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day05 extends AocSolver {

    protected Day05(final String filename) {
        super(filename);
    }

    private List<String> getOrderingRules(final List<String> input) {
        final List<String> rules = new ArrayList<>();
        for (final String line : input) {
            if (line.isBlank()) {
                break;
            } else {
                rules.add(line);
            }
        }
        return rules;
    }

    private List<String> getUpdates(final List<String> input) {
        final List<String> pages = new ArrayList<>();
        int i = 0;
        while (!(input.get(i++)).isBlank()) {
        }
        for (; i < input.size(); i++) {
            pages.add(input.get(i));
        }
        return pages;
    }

    private Map<Integer, List<Integer>> getRuleMap(final List<String> input) {
        final Map<Integer, List<Integer>> map = new HashMap<>();
        for (final String rule : input) {
            final int key = Integer.parseInt(rule.split("\\|")[0]);
            final int val = Integer.parseInt(rule.split("\\|")[1]);
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(val);
        }
        return map;
    }

    private boolean isBefore(final int val1, final int val2, final Map<Integer, List<Integer>> map) {
        return map.containsKey(val1) && map.get(val1).contains(val2);
    }

    private boolean isValid(final List<Integer> update, final Map<Integer, List<Integer>> map) {
        for (int i = 0; i < update.size() - 1; i++) {
            for (int j = i + 1; j < update.size(); j++) {
                if (!isBefore(update.get(i), update.get(j), map)) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<List<Integer>> getValidUpdates(final List<String> updates, final Map<Integer, List<Integer>> ruleMap) {
        final List<List<Integer>> validUpdates = new ArrayList<>();
        for (final String u : updates) {
            final List<Integer> update = Arrays.stream(u.split(",")).map(Integer::valueOf).collect(Collectors.toList());
            if (isValid(update, ruleMap)) {
                validUpdates.add(update);
            }
        }
        return validUpdates;
    }

    private List<List<Integer>> getInvalidUpdates(final List<String> updates, final Map<Integer, List<Integer>> ruleMap) {
        final List<List<Integer>> invalidUpdates = new ArrayList<>();
        for (final String u : updates) {
            final List<Integer> update = Arrays.stream(u.split(",")).map(Integer::valueOf).collect(Collectors.toList());
            if (!isValid(update, ruleMap)) {
                invalidUpdates.add(update);
            }
        }
        return invalidUpdates;
    }

    private List<Integer> fix(final List<Integer> update, final Map<Integer, List<Integer>> map) {
        final ArrayList<Integer> fixed = new ArrayList<>(update);
        for (int i = 0; i < update.size() - 1; i++) {
            final int val1 = update.get(i);
            for (int j = i + 1; j < update.size(); j++) {
                final int val2 = update.get(j);
                if (!isBefore(val1, val2, map)) {
                    fixed.set(i, val2);
                    fixed.set(j, val1);
                    return fixed;
                }
            }
        }
        return fixed;
    }

    private List<List<Integer>> fixUpdates(final List<List<Integer>> invalidUpdates, final Map<Integer, List<Integer>> ruleMap) {
        final List<List<Integer>> fixedUpdates = new ArrayList<>();
        for (List<Integer> update : invalidUpdates) {
            while (!isValid(update, ruleMap)) {
                update = fix(update, ruleMap);
            }
            fixedUpdates.add(update);
        }
        return fixedUpdates;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<String> rules = getOrderingRules(input);
        final List<String> updates = getUpdates(input);
        final Map<Integer, List<Integer>> ruleMap = getRuleMap(rules);
        final List<List<Integer>> invalidUpdates = getInvalidUpdates(updates, ruleMap);
        final List<List<Integer>> fixedUpdates = fixUpdates(invalidUpdates, ruleMap);
        return String.valueOf(fixedUpdates.stream().mapToInt(v -> v.get(v.size() / 2)).sum());
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<String> rules = getOrderingRules(input);
        final List<String> updates = getUpdates(input);
        final Map<Integer, List<Integer>> ruleMap = getRuleMap(rules);
        return String.valueOf(getValidUpdates(updates, ruleMap).stream().mapToInt(v -> v.get(v.size() / 2)).sum());
    }

    public static void main(final String... args) {
        new Day05("day05.txt");
    }
}
