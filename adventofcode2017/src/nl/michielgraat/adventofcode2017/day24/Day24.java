package nl.michielgraat.adventofcode2017.day24;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day24 {

    private static final String FILENAME = "day24.txt";
    private int maxStrength = 0;
    private int maxLength = 0;
    private int maxLengthMaxStrength = 0;

    private List<Component> getComponents(List<String> lines) {
        List<Component> result = new ArrayList<>();
        for (String line : lines) {
            String[] nrs = line.split("/");
            result.add(new Component(Integer.parseInt(nrs[0]), Integer.parseInt(nrs[1])));
        }
        return result;
    }

    private void dfs(int portToFind, int strength, int length, List<Component> comps) {
        maxStrength = Integer.max(strength, maxStrength);
        if (length > maxLength) {
            maxLength = length;
            maxLengthMaxStrength = strength;
        }
        if (length == maxLength) {
            maxLengthMaxStrength = Integer.max(strength,maxLengthMaxStrength);
        }

        for (Component comp : comps) {
            if (!comp.visited && comp.connects(portToFind)) {
                comp.visited = true;
                dfs(comp.getOther(portToFind), strength + comp.getStrength(), length+1, comps);
                comp.visited = false;
            }
        }
    }

    public int runPart2(List<String> lines) {
        dfs(0,0,0,getComponents(lines));
        return maxLengthMaxStrength;
    }

    public int runPart1(List<String> lines) {
        dfs(0,0,0,getComponents(lines));
        return maxStrength;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day24().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day24().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
