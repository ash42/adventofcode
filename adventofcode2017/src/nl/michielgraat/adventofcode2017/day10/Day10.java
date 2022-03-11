package nl.michielgraat.adventofcode2017.day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day10 {

    private static final String FILENAME = "day10.txt";
    private static final int SIZE = 256;
    private static final int BLOCK_SIZE = 16;
    private static final int[] SUFFIX = { 17, 31, 73, 47, 23 };

    private String getDenseHash(final List<Integer> sparse) {
        final List<Integer> dense = new ArrayList<>();
        for (int i = 0; i < SIZE; i += BLOCK_SIZE) {
            int total = sparse.get(i);
            for (int j = i + 1; j < i + BLOCK_SIZE; j++) {
                total = total ^ sparse.get(j);
            }
            dense.add(total);
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dense.size(); i++) {
            String hexString = Integer.toHexString(dense.get(i));
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            sb.append(hexString);
        }

        return sb.toString();
    }

    private List<Integer> getIndexes(final int start, final int end, final int size) {
        final List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < (end - start); i++) {
            indexes.add((i + start) % size);
        }
        return indexes;
    }

    private void reverseSection(final List<Integer> nrs, final List<Integer> indexes) {
        final List<Integer> reversed = new ArrayList<>();
        for (int i = 0; i < indexes.size(); i++) {
            final int idx = indexes.get(i);
            reversed.add(nrs.get(idx));
        }
        Collections.reverse(reversed);
        for (int i = 0; i < indexes.size(); i++) {
            final int idx = indexes.get(i);
            nrs.set(idx, reversed.get(i));
        }
    }

    private String solve2(final String sInput) {
        final byte[] input = sInput.getBytes();
        final List<Integer> lengths = IntStream.concat(IntStream.range(0, input.length).map(i -> input[i]),
                Arrays.stream(SUFFIX)).boxed()
                .collect(Collectors.toList());
        final List<Integer> nrs = IntStream.range(0, SIZE).boxed().collect(Collectors.toList());

        int pos = 0;
        int skipSize = 0;
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < lengths.size(); j++) {
                final int length = lengths.get(j);
                reverseSection(nrs, getIndexes(pos, pos + length, SIZE));
                pos = (pos + length + skipSize) % SIZE;
                skipSize++;
            }
        }
        return getDenseHash(nrs);
    }

    private int solve(final List<Integer> lengths) {
        final List<Integer> nrs = IntStream.range(0, SIZE).boxed().collect(Collectors.toList());
        int pos = 0;
        int skipSize = 0;
        for (int i = 0; i < lengths.size(); i++) {
            final int length = lengths.get(i);
            reverseSection(nrs, getIndexes(pos, pos + length, SIZE));
            pos = (pos + length + skipSize) % SIZE;
            skipSize++;
        }
        return nrs.get(0) * nrs.get(1);
    }

    public String runPart2(final List<String> lines) {
        return solve2(lines.get(0));
    }

    public int runPart1(final List<String> lines) {
        return solve(Arrays.stream(lines.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList()));
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day10().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day10().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}