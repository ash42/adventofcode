package nl.michielgraat.adventofcode2017.day14;

import java.math.BigInteger;

public class Day14 {

    private static final int DIM = 128;

    private String hexToBinary(final String input) {
        final StringBuilder output = new StringBuilder(new BigInteger(input, 16).toString(2));
        while (output.length() < DIM) {
            output.insert(0, "0");
        }
        return output.toString();
    }

    private int[][] getGrid(final String input) {
        final int[][] grid = new int[DIM][DIM];
        for (int i = 0; i < DIM; i++) {
            final String row = hexToBinary(new KnotHash(input + "-" + i).hash());
            for (int j = 0; j < DIM; j++) {
                grid[i][j] = row.charAt(j) - '0';
            }
        }
        return grid;
    }

    private int popCount(final String input) {
        final BigInteger b = new BigInteger(input, 16);
        return b.bitCount();
    }

    private void mark(final int i, final int j, final int nr, final int[][] groupGrid, final int[][] grid) {
        if (i >= 0 && i < DIM && j >= 0 && j < DIM) {
            if (grid[i][j] == 1 && groupGrid[i][j] == 0) {
                groupGrid[i][j] = nr;
                mark(i - 1, j, nr, groupGrid, grid);
                mark(i + 1, j, nr, groupGrid, grid);
                mark(i, j - 1, nr, groupGrid, grid);
                mark(i, j + 1, nr, groupGrid, grid);
            }
        }
    }

    public int runPart2(final String input) {
        final int[][] grid = getGrid(input);
        final int[][] groupGrid = new int[DIM][DIM];
        int nrOfGroups = 0;
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                if (grid[i][j] == 1 && groupGrid[i][j] == 0) {
                    mark(i, j, ++nrOfGroups, groupGrid, grid);
                }
            }
        }
        return nrOfGroups;
    }

    public long runPart1(final String input) {
        long totalUsed = 0;
        for (int i = 0; i < DIM; i++) {
            totalUsed += popCount(new KnotHash(input + "-" + i).hash());
        }
        return totalUsed;
    }

    public static void main(final String[] args) {
        final String input = "stpzcrnm";
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day14().runPart1(input));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day14().runPart2(input));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
