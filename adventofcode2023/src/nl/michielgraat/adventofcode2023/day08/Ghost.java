package nl.michielgraat.adventofcode2023.day08;

import java.util.List;
import java.util.Map;

public class Ghost {
    private String currentNode;
    private List<Integer> instructions;
    private Map<String, String[]> nodeMap;
    private int insPtr = 0;

    public Ghost(String currentNode, List<Integer> instructions, Map<String, String[]> nodeMap) {
        this.currentNode = currentNode;
        this.instructions = instructions;
        this.nodeMap = nodeMap;
    }

    private void doStep() {
        int instruction = instructions.get(insPtr);
        currentNode = nodeMap.get(currentNode)[instruction];
        insPtr = (insPtr + 1) % instructions.size();
    }

    public int getStepsUntilEndNode() {
        int steps = 0;
        while (!done()) {
            doStep();
            steps++;
        }
        return steps;
    }

    private boolean done() {
        return currentNode.endsWith("Z");
    }
}
