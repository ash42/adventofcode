package nl.michielgraat.adventofcode2023.day15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day15 extends AocSolver {

    protected Day15(String filename) {
        super(filename);
    }

    private List<List<Integer>> getSteps(List<String> input) {
        List<List<Integer>> steps = new ArrayList<>();
        for (String s : input) {
            steps.add(s.chars().mapToObj(i -> Integer.valueOf(i)).toList());
        }
        return steps;
    }

    private int calcHash(List<Integer> ints) {
        int current = 0;
        for (int i : ints) {
            current += i;
            current = (current * 17) % 256;
        }
        return current;
    }

    private String getLabel(List<Integer> step) {
        int equalsIdx = step.indexOf((int) '=');
        StringBuilder sb = new StringBuilder();
        if (equalsIdx >= 0) {
            for (int i = 0; i < equalsIdx; i++) {
                sb.append((char) step.get(i).intValue());
            }
            return sb.toString();
        } else {
            for (int i = 0; i < step.indexOf((int) '-'); i++) {
                sb.append((char) step.get(i).intValue());
            }
            return sb.toString();
        }
    }

    private int getCorrectBox(List<Integer> step) {
        int equalsIdx = step.indexOf((int) '=');
        if (equalsIdx >= 0) {
            return calcHash(step.subList(0, equalsIdx));
        } else {
            return calcHash(step.subList(0, step.indexOf((int) '-')));
        }
    }

    private int getFocalLength(List<Integer> step) {
        int equalsIdx = step.indexOf((int) '=');
        StringBuilder sb = new StringBuilder();
        for (int i = equalsIdx + 1; i < step.size(); i++) {
            sb.append((char) step.get(i).intValue());
        }
        return Integer.valueOf(sb.toString());
    }

    private List<Box> getInitialBoxes() {
        List<Box> boxes = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            boxes.add(new Box(i));
        }
        return boxes;
    }

    private void runSteps(List<List<Integer>> steps, List<Box> boxes) {
        for (List<Integer> step : steps) {
            int boxNr = getCorrectBox(step);
            String label = getLabel(step);
            boolean isRemove = step.indexOf((int) '-') >= 0;
            if (isRemove) {
                boxes.get(boxNr).removeLens(label);
            } else {
                int focalLength = getFocalLength(step);
                boxes.get(boxNr).addOrReplaceLens(new Lens(label, focalLength));
            }
        }
    }

    @Override
    protected String runPart2(final List<String> input) {
        List<List<Integer>> steps = getSteps(Arrays.asList(input.get(0).split(",")));
        List<Box> boxes = getInitialBoxes();
        runSteps(steps, boxes);
        return String.valueOf(boxes.stream().mapToLong(Box::getFocusingPower).sum());
    }

    @Override
    protected String runPart1(final List<String> input) {
        List<List<Integer>> steps = getSteps(Arrays.asList(input.get(0).split(",")));
        int total = 0;
        for (List<Integer> step : steps) {
            total += calcHash(step);
        }
        return String.valueOf(total);
    }

    public static void main(String... args) {
        new Day15("day15.txt");
    }
}
