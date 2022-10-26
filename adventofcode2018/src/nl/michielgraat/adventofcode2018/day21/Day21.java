package nl.michielgraat.adventofcode2018.day21;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day21 {
    private static final String FILENAME = "day21.txt";

    /**
     * I basically just reverse engineered the program in the input and wrote it as
     * a 'normal' program.
     * 
     * For part 1, the first time 256 > e, print the result in c (see line 30 in the
     * input).
     * For part 2, the first time the value in c repeats, the previous result should
     * be the one with the most instructions.
     * 
     * @param part1 set to true to run part 1, to false for part 2
     * @return The result.
     */
    private int getResult(final boolean part1) {
        int c = 0;
        final List<Integer> found = new ArrayList<>();
        int prev = 0;
        while (true) {
            int e = c | 65536;
            c = 6718165;
            while (true) {
                final int d = e & 255;
                c += d;
                c &= 16777215;
                c *= 65899;
                c &= 16777215;
                if (256 > e) {
                    if (part1) {
                        return c;
                    } else {
                        if (!found.contains(c)) {
                            found.add(c);
                            prev = c;
                            break;
                        } else {
                            return prev;
                        }
                    }
                }
                e /= 256;
            }
        }
    }

    private int runPart2(final List<String> lines) {
        return getResult(false);
    }

    private long runPart1(final List<String> lines) {
        return getResult(true);

    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day21().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day21().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
