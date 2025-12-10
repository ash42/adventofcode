package nl.michielgraat.adventofcode2025.day10;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


import nl.michielgraat.adventofcode2025.AocSolver;

public class Day10 extends AocSolver {

    protected Day10(String filename) {
        super(filename);
    }

    private List<Manual> parseManuals(final List<String> input) {
        return input.stream().map(l -> new Manual(l)).toList();
    }

    private int getNrSteps(Boolean[] finish, Queue<Node> queue, List<List<Integer>> buttons) {
        Node current = queue.poll();
        if (Arrays.equals(finish, current.lights())) {
            return current.steps();
        } else {
            for (List<Integer> button : buttons) {
                Boolean[] newLights = new Boolean[current.lights().length];
                for (int i = 0; i < current.lights().length; i++) {
                    newLights[i] = button.contains(i) ? !current.lights()[i] : current.lights()[i];
                }
                Node node = new Node(newLights, current.steps() + 1);
                if (!queue.contains(node)) {
                    queue.add(node);
                }
            }
            return getNrSteps(finish, queue, buttons);
        }
    }

    private int calculateMinTotalPresses(List<Manual> manuals) {
        int totalPresses = 0;
        for (Manual manual : manuals) {
            Queue<Node> queue = new LinkedList<>();
            Boolean[] off = new Boolean[manual.getLights().length];
            Arrays.fill(off, false);
            Node start = new Node(off, 0);
            queue.add(start);
            int presses = getNrSteps(manual.getLights(), queue, manual.getButtons());
            totalPresses += presses;
        }
        return totalPresses;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return "";
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(calculateMinTotalPresses(parseManuals(input)));
    }
    
    public static void main(String... args) {
        new Day10("day10.txt");
    }
}
