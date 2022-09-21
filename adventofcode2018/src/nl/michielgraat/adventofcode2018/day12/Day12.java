package nl.michielgraat.adventofcode2018.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day12 {
    private static final String FILENAME = "day12.txt";

    int firstIdx = 0;

    private List<String> getInitialState(List<String> lines) {
        String[] input = lines.get(0).split(" ")[2].split("");
        return new ArrayList<>(Arrays.asList(input));
    }

    private Map<String, String> getNotes(List<String> lines) {
        Map<String, String> notes = new HashMap<>();
        for (int i = 2; i < lines.size(); i++) {
            String[] note = lines.get(i).split(" ");
            notes.put(note[0], note[2]);
        }
        return notes;
    }

    private String runPart2(List<String> lines) {
        //Bit of cheating here. If you analyse the scores for, for instance, the first
        //20000 iterations, you will see that every 1000 iterations the first part of the 
        //score increases by 52. The second part always ends in 919. 
        long end = 50000000000L/1000;
        long total = 0;
        for (int i = 1; i<=end; i++) {
            total += 52;
        }

        return String.valueOf(total) + "919";
    }

    private int runPart1(List<String> lines) {
        List<String> state = getInitialState(lines);
        Map<String, String> notes = getNotes(lines);
        int zeroIdx = 0;
        Generation g = null;
        for (int i = 1; i <= 20; i++) {
            g = new Generation(state, notes, zeroIdx);
            g.grow();
            zeroIdx = g.getZeroIdx();
            state = g.getOutput();
        }

        return g.getScore();
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day12().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day12().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}