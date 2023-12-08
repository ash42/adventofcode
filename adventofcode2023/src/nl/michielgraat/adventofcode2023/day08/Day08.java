package nl.michielgraat.adventofcode2023.day08;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day08 extends AocSolver {

    protected Day08(String filename) {
        super(filename);
    }

    private List<Integer> getInstructions(final List<String> input) {
        List<Integer> instructions = new ArrayList<>();
        String instructionLine = input.get(0);
        for (char c : instructionLine.toCharArray()) {
            instructions.add(c == 'L' ? 0 : 1);
        }
        return instructions;
    }

    private Map<String, String[]> getNodeMap(final List<String> input) {
        Map<String, String[]> nodeMap = new TreeMap<>();
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            Matcher matcher = Pattern.compile("([A-Z]{3})\\s=\\s\\(([A-Z]{3}),\\s([A-Z]{3})\\)").matcher(line);
            matcher.find();
            String[] nextElements = { matcher.group(2), matcher.group(3) };
            nodeMap.put(matcher.group(1), nextElements);
        }
        return nodeMap;
    }

    private List<Ghost> getGhosts(Map<String, String[]> nodeMap, List<Integer> instructions) {
        List<String> startingNodes = nodeMap.keySet().stream().filter(k -> k.endsWith("A")).toList();
        return startingNodes.stream().map(s -> new Ghost(s, instructions, nodeMap)).toList();
    }

    private long lcm(long x, long y) {
        long max = Math.max(x, y);
        long min = Math.min(x, y);
        long lcm = max;
        while (lcm % min != 0) {
            lcm += max;
        }
        return lcm;
    }

    @Override
    protected String runPart2(final List<String> input) {
        List<Integer> stepsUntilEndNode = getGhosts(getNodeMap(input), getInstructions(input)).stream()
                .mapToInt(g -> g.getStepsUntilEndNode()).boxed().toList();
        long total = stepsUntilEndNode.get(0);
        for (int i = 1; i < stepsUntilEndNode.size(); i++) {
            total = lcm(total, stepsUntilEndNode.get(i));
        }
        return String.valueOf(total);
    }

    @Override
    protected String runPart1(final List<String> input) {
        List<Integer> instructions = getInstructions(input);
        Map<String, String[]> nodeMap = getNodeMap(input);
        String currentNode = "AAA";
        int insPtr = 0;
        int total = 0;
        while (!currentNode.equals("ZZZ")) {
            int instruction = instructions.get(insPtr);
            currentNode = nodeMap.get(currentNode)[instruction];
            total++;
            insPtr = (insPtr + 1) % instructions.size();
        }
        return String.valueOf(total);
    }

    public static void main(String... args) {
        new Day08("day08.txt");
    }
}
