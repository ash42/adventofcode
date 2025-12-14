package nl.michielgraat.adventofcode2025.day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

public class Manual {

    private Boolean[] lights;
    private List<List<Integer>> buttons;
    private List<Integer> joltageRequirements;
    private Boolean[][] buttonInfluences;
    private Map<List<Boolean>, Integer> lightPatternToNrPresses;
    private Map<List<Integer>, Integer> joltageIncrToNrPresses;

    public Manual(final String line) {
        parse(line);
        calculatePatterns();
    }

    public int getMinPressesToLight() {
        return lightPatternToNrPresses.get(Arrays.asList(lights));
    }

    public int getNrOfPressesForVoltages() {
        return getNrOfPressesForVoltages(joltageRequirements, new HashMap<>());
    }

    // All credits to
    // https://www.reddit.com/r/adventofcode/comments/1pk87hl/2025_day_10_part_2_bifurcate_your_way_to_victory/
    private int getNrOfPressesForVoltages(final List<Integer> current, final Map<List<Integer>, Integer> memoMap) {
        if (current.stream().allMatch(i -> i == 0)) {
            return 0;
        }
        if (memoMap.containsKey(current)) {
            return memoMap.get(current);
        }

        int result = Integer.MAX_VALUE;
        for (final Entry<List<Integer>, Integer> entry : joltageIncrToNrPresses.entrySet()) {
            final List<Integer> pattern = entry.getKey();
            if (isValid(current, pattern)) {
                // Initialize the new goal as a list of 0's.
                final List<Integer> newGoal = new ArrayList<>(current.size());
                IntStream.range(0, current.size()).forEach(i -> newGoal.add(0));

                for (int i = 0; i < pattern.size(); i++) {
                    newGoal.set(i, (current.get(i) - pattern.get(i)) / 2);
                }

                final int nr = getNrOfPressesForVoltages(newGoal, memoMap);
                if (nr < Integer.MAX_VALUE) {
                    result = Math.min(result, entry.getValue() + (2 * nr));
                }
            }
        }
        memoMap.put(current, result);
        return result;
    }

    private boolean isValid(final List<Integer> current, final List<Integer> pattern) {
        boolean valid = true;
        for (int i = 0; i < pattern.size(); i++) {
            final int incr = pattern.get(i);
            final int cur = current.get(i);
            if (!(incr <= cur && incr % 2 == cur % 2)) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    private void calculatePatterns() {
        // We want to calculate all patterns arising from every button combination
        // pressed.
        // How do we do this?
        // If there are 3 buttons than we can represent them by their index:
        // 0 1 2 3
        // Than we can just any combination of them (0 = not pressed, 1 = press)
        // 0 0 0 1
        // 0 0 1 0
        // 0 0 1 1 etc.
        // This is just binary. So for 3 buttons there are 2^3 = 8 combinations.
        final int nrPatterns = (int) Math.pow(2, buttons.size());

        // Calculate:
        // - The light pattern a certain combination of button presses can lead to (for
        // part 1).
        // - The joltage increases a certain combination of button presses can lead to
        // (for part 2).
        lightPatternToNrPresses = new HashMap<>();
        joltageIncrToNrPresses = new HashMap<>();

        // Loop over all combinations of button presses
        for (int i = 0; i < nrPatterns; i++) {
            // Initialize the resulting lists as lists of false's and 0's respectively.
            final List<Boolean> lightPattern = new ArrayList<>();
            final List<Integer> joltageIncreases = new ArrayList<>();
            IntStream.range(0, joltageRequirements.size()).forEach(j -> lightPattern.add(false));
            IntStream.range(0, joltageRequirements.size()).forEach(j -> joltageIncreases.add(0));

            final String binary = getBinaryRepresentation(i);
            int nrButtonsPressed = 0;
            // Press all the buttons
            for (int buttonNr = 0; buttonNr < binary.length(); buttonNr++) {
                if (binary.charAt(buttonNr) == '1') {
                    // Get the meters which are influenced by this button.
                    final Boolean[] influence = buttonInfluences[buttonNr];

                    for (int k = 0; k < influence.length; k++) {
                        lightPattern.set(k, influence[k] && lightPattern.get(k) ? false
                                : influence[k] && !lightPattern.get(k) ? true : lightPattern.get(k));

                        joltageIncreases.set(k, influence[k] ? joltageIncreases.get(k) + 1 : joltageIncreases.get(k));
                    }
                    nrButtonsPressed++;
                }
            }
            // Add the pattern/joltage increases if the map does not contain the pattern yet
            // or replace it when it has a button press count which is greater than the one
            // we just calculated.
            if (!lightPatternToNrPresses.containsKey(lightPattern)
                    || lightPatternToNrPresses.get(lightPattern) > nrButtonsPressed) {
                lightPatternToNrPresses.put(lightPattern, nrButtonsPressed);
            }
            if (!joltageIncrToNrPresses.containsKey(joltageIncreases)
                    || joltageIncrToNrPresses.get(joltageIncreases) > nrButtonsPressed) {
                joltageIncrToNrPresses.put(joltageIncreases, nrButtonsPressed);
            }
        }
    }

    private void parse(final String line) {
        final String lightsString = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
        final String buttonsString = line.substring(line.indexOf("]") + 1, line.indexOf("{") - 1).trim();
        final String joltageReqsString = line.substring(line.indexOf("{") + 1, line.indexOf("}"));

        lights = new Boolean[lightsString.length()];
        for (int i = 0; i < lightsString.length(); i++) {
            lights[i] = lightsString.charAt(i) == '#';
        }

        buttons = new ArrayList<>();
        final String[] splitButtons = buttonsString.split(" ");
        for (final String splitButton : splitButtons) {
            final String[] splitWiring = splitButton.substring(1, splitButton.length() - 1).split(",");
            buttons.add(Arrays.stream(splitWiring).mapToInt(Integer::parseInt).boxed().toList());
        }

        joltageRequirements = new ArrayList<>();
        joltageRequirements = Arrays.stream(joltageReqsString.split(",")).mapToInt(Integer::parseInt).boxed().toList();

        buttonInfluences = new Boolean[buttons.size()][joltageRequirements.size()];
        for (int i = 0; i < buttons.size(); i++) {
            final Boolean[] influence = new Boolean[joltageRequirements.size()];
            Arrays.fill(influence, false);
            final List<Integer> button = buttons.get(i);
            for (final int j : button) {
                influence[j] = true;
            }
            buttonInfluences[i] = influence;
        }
    }

    private String getBinaryRepresentation(final int i) {
        final StringBuilder sb = new StringBuilder(Integer.toBinaryString(i)).reverse();
        while (sb.length() < buttons.size()) {
            sb.append('0');
        }
        final String binary = sb.toString().substring(0, buttons.size());
        return binary;
    }
}
