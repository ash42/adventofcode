package nl.michielgraat.adventofcode2017.day14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KnotHash {

    private static final int SIZE = 256;
    private static final int BLOCK_SIZE = 16;
    private static final int[] SUFFIX = { 17, 31, 73, 47, 23 };

    private String sInput;

    private KnotHash() {}    

    public KnotHash(String sInput) {
        this.sInput = sInput;
    }

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

    public String hash() {
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
    
}
