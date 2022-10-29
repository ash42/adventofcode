package nl.michielgraat.adventofcode2018.day08;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day08 {
    private static final String FILENAME = "day08.txt";
    private int totalSum = 0;

    private int buildTree(final List<Integer> input, final Node current, int idx) {
        final int children = input.get(idx++);
        final int metadata = input.get(idx++);

        for (int i = 0; i < children; i++) {
            final Node child = new Node();
            current.children.add(child);
            idx = buildTree(input, child, idx);
        }
        for (int i = 0; i < metadata; i++) {
            final int val = input.get(idx + i);
            current.metadata.add(val);
            totalSum += val;
        }
        return idx + metadata;
    }

    public int runPart2(final List<String> lines) {
        final List<Integer> input = Arrays.stream(lines.get(0).split(" ")).map(Integer::parseInt)
                .collect(Collectors.toList());
        final Node root = new Node();
        buildTree(input, root, 0);
        return root.getValue();
    }

    public int runPart1(final List<String> lines) {
        final List<Integer> input = Arrays.stream(lines.get(0).split(" ")).map(Integer::parseInt)
                .collect(Collectors.toList());
        buildTree(input, new Node(), 0);
        return totalSum;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day08().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day08().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}