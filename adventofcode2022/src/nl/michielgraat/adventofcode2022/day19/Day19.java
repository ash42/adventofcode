package nl.michielgraat.adventofcode2022.day19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day19 extends AocSolver {

    private static final int ORE_ROBOT = 0;
    private static final int CLAY_ROBOT = 1;
    private static final int OB_ROBOT = 2;
    private static final int GEO_ROBOT = 3;

    private Map<State, Integer> cache = new HashMap<>();

    protected Day19(final String filename) {
        super(filename);
    }

    private List<Blueprint> getBlueprints(final List<String> input) {
        final List<Blueprint> blueprints = new ArrayList<>();
        final Pattern p = Pattern.compile(
                "Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.");
        for (final String line : input) {
            final Matcher m = p.matcher(line);
            if (m.find()) {
                blueprints.add(new Blueprint(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
                        Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)),
                        Integer.parseInt(m.group(6)), Integer.parseInt(m.group(7))));
            }
        }
        return blueprints;
    }

    private int getMaxGeodesForType(final Blueprint b, int minutesLeft, final int goal, int nrOre, int nrClay, int nrOb,
            int nrGeo,
            final int nrOreRobots, final int nrClayRobots, final int nrObRobots, final int nrGeoRobots) {
        if (minutesLeft == 0) {
            return nrGeo;
        }
        // Stop building a robot if we have more of the resource it builds than we need.
        final int maxOre = Math.max(b.oreCost(), Math.max(b.clayOreCost(), Math.max(b.obOreCost(), b.geoObCost())));
        if (goal == ORE_ROBOT && nrOre >= maxOre || goal == CLAY_ROBOT && nrClay >= b.obClayCost()
                || goal == OB_ROBOT && (nrOb >= b.geoObCost() || nrClay == 0) || goal == GEO_ROBOT && nrOb == 0) {
            return 0;
        }

        final State state = new State(nrOre, nrClay, nrOb, nrGeo, nrOreRobots, nrClayRobots, nrObRobots, nrGeoRobots,
                minutesLeft, goal);

        if (cache.containsKey(state)) {
            return cache.get(state);
        }
        int max = 0;

        while (minutesLeft > 0) {
            if (goal == ORE_ROBOT && nrOre >= b.oreCost()) { // Build ore robot
                int tmpMax = 0;
                for (int newGoal = 0; newGoal < 4; newGoal++) {
                    tmpMax = Math.max(tmpMax,
                            getMaxGeodesForType(b, minutesLeft - 1, newGoal, nrOre - b.oreCost() + nrOreRobots,
                                    nrClay + nrClayRobots, nrOb + nrObRobots, nrGeo + nrGeoRobots, nrOreRobots + 1,
                                    nrClayRobots, nrObRobots, nrGeoRobots));
                }
                max = Math.max(max, tmpMax);
                cache.put(state, max);
                return max;
            } else if (goal == CLAY_ROBOT && nrOre >= b.clayOreCost()) { // Build clay robot
                int tmpMax = 0;
                for (int newGoal = 0; newGoal < 4; newGoal++) {
                    tmpMax = Math.max(tmpMax,
                            getMaxGeodesForType(b, minutesLeft - 1, newGoal, nrOre - b.clayOreCost() + nrOreRobots,
                                    nrClay + nrClayRobots, nrOb + nrObRobots, nrGeo + nrGeoRobots, nrOreRobots,
                                    nrClayRobots + 1, nrObRobots, nrGeoRobots));
                }
                max = Math.max(max, tmpMax);
                cache.put(state, max);
                return max;
            } else if (goal == OB_ROBOT && nrOre >= b.obOreCost() && nrClay >= b.obClayCost()) { // Build ob robot
                int tmpMax = 0;
                for (int newGoal = 0; newGoal < 4; newGoal++) {
                    tmpMax = Math.max(tmpMax,
                            getMaxGeodesForType(b, minutesLeft - 1, newGoal, nrOre - b.obOreCost() + nrOreRobots,
                                    nrClay - b.obClayCost() + nrClayRobots, nrOb + nrObRobots, nrGeo + nrGeoRobots,
                                    nrOreRobots, nrClayRobots, nrObRobots + 1, nrGeoRobots));
                }
                max = Math.max(max, tmpMax);
                cache.put(state, max);
                return max;
            } else if (goal == GEO_ROBOT && nrOre >= b.geoOreCost() && nrOb >= b.geoObCost()) { // Build geo robot
                int tmpMax = 0;
                for (int newGoal = 0; newGoal < 4; newGoal++) {
                    tmpMax = Math.max(tmpMax,
                            getMaxGeodesForType(b, minutesLeft - 1, newGoal, nrOre - b.geoOreCost() + nrOreRobots,
                                    nrClay + nrClayRobots, nrOb - b.geoObCost() + nrObRobots, nrGeo + nrGeoRobots,
                                    nrOreRobots, nrClayRobots, nrObRobots, nrGeoRobots + 1));
                }
                max = Math.max(max, tmpMax);
                cache.put(state, max);
                return max;
            }
            // Can not build a robot, so continue gathering resources.
            minutesLeft--;
            nrOre += nrOreRobots;
            nrClay += nrClayRobots;
            nrOb += nrObRobots;
            nrGeo += nrGeoRobots;
            max = Math.max(max, nrGeo);
        }
        cache.put(state, max);
        return max;
    }

    private int getMaxGeodes(final Blueprint b, final int nrMinutes) {
        cache = new HashMap<>();
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = Math.max(result, getMaxGeodesForType(b, nrMinutes, i, 0, 0, 0, 0, 1, 0, 0, 0));
        }
        return result;
    }

    private int getQualityLevel(final Blueprint b) {
        return getMaxGeodes(b, 24) * b.nr();
    }

    @Override
    protected String runPart2(final List<String> input) {
        List<Blueprint> blueprints = getBlueprints(input);
        blueprints = blueprints.subList(0, Math.min(3, blueprints.size()));
        return String.valueOf(blueprints.stream().mapToInt(b -> getMaxGeodes(b, 32)).reduce(1, (a, b) -> a * b));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Blueprint> blueprints = getBlueprints(input);
        return String.valueOf(blueprints.stream().mapToInt(this::getQualityLevel).sum());
    }

    public static void main(final String... args) {
        new Day19("day19.txt");
    }
}
