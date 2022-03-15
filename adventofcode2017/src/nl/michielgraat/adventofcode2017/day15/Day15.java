package nl.michielgraat.adventofcode2017.day15;

public class Day15 {

    private static final int FACA = 16807;
    private static final int FACB = 48271;
    private static final int DIV = 2147483647;
    private static final int MODA = 4;
    private static final int MODB = 8;
    private static final int BITMASK = 0x0000FFFF;

    private long nextB(long b) {
        while (true) {
            b *= FACB;
            b %= DIV;
            if (b % MODB == 0) {
                return b;
            }
        }
    }

    private long nextA(long a) {
        while (true) {
            a *= FACA;
            a %= DIV;
            if (a % MODA == 0) {
                return a;
            }
        }
    }

    public int runPart2(long a, long b) {
        int total = 0;
        for (int i = 0; i < 5000000; i++) {
            a = nextA(a);
            b = nextB(b);
            if ((a & BITMASK) == (b & BITMASK)) {
                total++;
            }
        }
        return total;
    }

    public int runPart1(long a, long b) {
        int total = 0;
        for (int i = 0; i < 40000000; i++) {
            a *= FACA;
            a %= DIV;
            b *= FACB;
            b %= DIV;
            if ((a & BITMASK) == (b & BITMASK)) {
                total++;
            }
        }
        return total;
    }

    public static void main(final String[] args) {
        long start = System.nanoTime();
        final long a = 722;
        final long b = 354;
        System.out.println("Answer to part 1: " + new Day15().runPart1(a, b));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day15().runPart2(a, b));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
