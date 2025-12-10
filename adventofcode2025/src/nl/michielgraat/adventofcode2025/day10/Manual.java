package nl.michielgraat.adventofcode2025.day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Manual {
    
    private Boolean[] lights;
    private List<List<Integer>> buttons;
    private List<Integer> joltageRequirements;

    public Manual(final String line) {
        parse(line);
    }

    public int[][] convertButtonsToArray() {
        int[][] buttonArr = new int[buttons.size()][joltageRequirements.size()];

        for (int i = 0; i < buttons.size(); i++) {
            List<Integer> button = buttons.get(i);
            int[] arr = new int[joltageRequirements.size()];
            for (int j = 0; j < joltageRequirements.size(); j++) {
                arr[j] = button.contains(j) ? 1 : 0;
            }
            buttonArr[i] = arr;
        }

        return buttonArr;
    }

    private void parse(final String line) {
        String lightsString = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
        String buttonsString = line.substring(line.indexOf("]") + 1, line.indexOf("{") - 1).trim();
        String joltageReqsString = line.substring(line.indexOf("{") + 1, line.indexOf("}"));
        
        lights = new Boolean[lightsString.length()];
        for (int i = 0; i < lightsString.length(); i++) {
            lights[i] = lightsString.charAt(i) == '#';
        }

        buttons = new ArrayList<>();
        String[] splitButtons = buttonsString.split(" ");
        for (String splitButton : splitButtons) {
            String[] splitWiring = splitButton.substring(1, splitButton.length() - 1).split(",");
            buttons.add(Arrays.stream(splitWiring).mapToInt(Integer::parseInt).boxed().toList());
        }

        joltageRequirements = new ArrayList<>();
        joltageRequirements = Arrays.stream(joltageReqsString.split(",")).mapToInt(Integer::parseInt).boxed().toList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Arrays.stream(lights).forEach(l -> sb.append(l ? '#' : '.'));
        sb.append("] ");
        for (List<Integer> button : buttons) {
            sb.append('(');
            for (int i = 0; i < button.size(); i++) {
                sb.append(button.get(i));
                if (i != button.size() - 1) {
                    sb.append(',');
                }
            }
            sb.append(") ");
        }
        sb.append('{');
        for (int i = 0 ; i < joltageRequirements.size(); i++) {
            sb.append(joltageRequirements.get(i));
            if (i != joltageRequirements.size() - 1) {
                sb.append(',');
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public Boolean[] getLights() {
        return lights;
    }

    public List<List<Integer>> getButtons() {
        return buttons;
    }

    public List<Integer> getJoltageRequirements() {
        return joltageRequirements;
    }

    
}
