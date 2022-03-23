package nl.michielgraat.adventofcode2017.day23;

import java.util.List;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day23 {

    private static final String FILENAME = "day23.txt";

    public long runPart2(final List<String> lines) {
        // Just translated assembly into Java code, optimized at some points (for
        // instance, it is not necessary to initialize all variables immediately, a and
        // e are not really used at all).
        int g = 0;
        int h = 0;
        // b seems to be the only "real" variable between input files.
        int b = Integer.parseInt(lines.get(0).substring(lines.get(0).lastIndexOf(" ") + 1));
        b = b * 100 + 100000;
        final int c = b + 17000;
        do {
            // F is basically a boolean "prime".
            int f = 1;
            // Lines 10 to 32 basically check if b is prime.
            for (int d = 2; d * d <= b; d++) {
                if ((b % d == 0)) {
                    // Not prime.
                    f = 0;
                    break;
                }
            }
            if (f == 0) {
                h++;
            }
            // Because c = b + 17000 initially and we increase b with 17, this do/while loop
            // runs 1000 times.
            g = b - c;
            b += 17;
        } while (g != 0);

        return h;
    }

    public long runPart1(final List<String> lines) {
        return new Program(lines).runProgram();
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day23().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day23().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
