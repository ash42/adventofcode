package nl.michielgraat.adventofcode2016.day19;

public class Day19 {

    // Study the pattern.
    public int runPart2(final int input) {
        int result = 1;
        int i;

        for (i = 1; i < input; i++) {
            result = result % i + 1;
            if (result > (i + 1) / 2) {
                result++;
            }
        }
        return result;
    }

    private int getNextIndex(final int i, final int[] presents) {
        for (int j = (i + 1) % presents.length; j != i; j = (j + 1) % presents.length) {
            if (presents[j] != 0) {
                return j;
            }
        }
        return -1;
    }

    public int runPart1(final int input) {
        final int[] presents = new int[input];
        int nrWithPresents = input;
        int lastIndex = 0;
        for (int i = 0; i < input; i++) {
            presents[i] = 1;
        }

        while (nrWithPresents > 1) {
            for (int i = 0; i < input; i++) {
                if (presents[i] != 0) {
                    final int nextIndex = getNextIndex(i, presents);
                    presents[i] += presents[nextIndex];
                    presents[nextIndex] = 0;
                    lastIndex = i;
                    nrWithPresents--;
                }
            }
        }

        return lastIndex + 1;
    }

    public static void main(final String[] args) {
        final int input = 3014603;
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day19().runPart1(input));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day19().runPart2(input));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
