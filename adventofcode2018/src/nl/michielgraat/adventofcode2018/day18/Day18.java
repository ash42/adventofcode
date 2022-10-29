package nl.michielgraat.adventofcode2018.day18;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day18 {
    private static final String FILENAME = "day18.txt";

    private static final char OPEN = '.';
    private static final char TREE = '|';
    private static final char LUMBERYARD = '#';

    private char[][] getGrid(final List<String> lines) {
        final char[][] grid = new char[lines.get(0).length()][lines.size()];
        for (int y = 0; y < lines.size(); y++) {
            final String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                grid[x][y] = line.charAt(x);
            }
        }
        return grid;
    }

    private void printGrid(final char[][] grid) {
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                System.out.print(grid[x][y]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private char getChar(final int x, final int y, final char[][] input) {
        if (x >= 0 && y >= 0 && x < input.length && y < input.length) {
            return input[x][y];
        }
        return ' ';
    }

    private int getNrTrees(final int x, final int y, final char[][] input) {
        int nrTrees = 0;
        if (getChar(x - 1, y, input) == TREE)
            nrTrees++; // left
        if (getChar(x - 1, y - 1, input) == TREE)
            nrTrees++; // left-up
        if (getChar(x, y - 1, input) == TREE)
            nrTrees++; // up
        if (getChar(x + 1, y - 1, input) == TREE)
            nrTrees++; // right-up
        if (getChar(x + 1, y, input) == TREE)
            nrTrees++; // right
        if (getChar(x + 1, y + 1, input) == TREE)
            nrTrees++; // right-down
        if (getChar(x, y + 1, input) == TREE)
            nrTrees++; // down
        if (getChar(x - 1, y + 1, input) == TREE)
            nrTrees++; // left-down
        return nrTrees;
    }

    private int getNrLumberyards(final int x, final int y, final char[][] input) {
        int nrLumberyards = 0;
        if (getChar(x - 1, y, input) == LUMBERYARD)
            nrLumberyards++; // left
        if (getChar(x - 1, y - 1, input) == LUMBERYARD)
            nrLumberyards++; // left-up
        if (getChar(x, y - 1, input) == LUMBERYARD)
            nrLumberyards++; // up
        if (getChar(x + 1, y - 1, input) == LUMBERYARD)
            nrLumberyards++; // right-up
        if (getChar(x + 1, y, input) == LUMBERYARD)
            nrLumberyards++; // right
        if (getChar(x + 1, y + 1, input) == LUMBERYARD)
            nrLumberyards++; // right-down
        if (getChar(x, y + 1, input) == LUMBERYARD)
            nrLumberyards++; // down
        if (getChar(x - 1, y + 1, input) == LUMBERYARD)
            nrLumberyards++; // left-down
        return nrLumberyards;
    }

    private char[][] runMinute(final char[][] input) {
        final char[][] output = new char[input.length][input[0].length];
        for (int y = 0; y < input[0].length; y++) {
            for (int x = 0; x < input.length; x++) {
                if (input[x][y] == OPEN) {
                    output[x][y] = getNrTrees(x, y, input) >= 3 ? TREE : OPEN;
                } else if (input[x][y] == TREE) {
                    output[x][y] = getNrLumberyards(x, y, input) >= 3 ? LUMBERYARD : TREE;
                } else if (input[x][y] == LUMBERYARD) {
                    final int nrLumberyards = getNrLumberyards(x, y, input);
                    final int nrTrees = getNrTrees(x, y, input);
                    output[x][y] = nrLumberyards >= 1 && nrTrees >= 1 ? LUMBERYARD : OPEN;
                } else {
                    throw new IllegalArgumentException(
                            "Unknown acre type: '" + input[x][y] + "' for (" + x + "," + y + ")");
                }
            }
        }
        return output;
    }

    private int getResourceValue(final char[][] grid) {
        int nrTrees = 0;
        int nrLumberyards = 0;
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] == TREE) {
                    nrTrees++;
                } else if (grid[x][y] == LUMBERYARD) {
                    nrLumberyards++;
                }
            }
        }
        return nrTrees * nrLumberyards;
    }

    private List<Value> buildValueList(char[][] grid, final int size) {
        final List<Value> values = new ArrayList<>();
        for (int min = 0; min < size; min++) {
            grid = runMinute(grid);
            values.add(new Value(min, getResourceValue(grid)));
        }
        return values;
    }

    private List<Integer> findCycle(final List<Value> values) {
        int slowIdx = 0;
        int slow = values.get(slowIdx).val;
        int fastIdx = 0;
        int fast;
        boolean met = false;
        while (fastIdx < values.size()) {
            slowIdx++;
            slow = values.get(slowIdx).val;
            fastIdx += 2;
            fast = values.get(fastIdx).val;
            if (slow == fast) {
                met = true;
                break;
            }
        }
        if (!met) {
            throw new IllegalArgumentException("No cycle found");
        }
        final int startIdx = slowIdx;
        slowIdx++;
        int slow2 = values.get(slowIdx).val;
        while (slow2 != slow) {
            slowIdx++;
            slow2 = values.get(slowIdx).val;
        }
        final List<Integer> cycle = new ArrayList<>();
        for (int i = startIdx; i < slowIdx; i++) {
            cycle.add(values.get(i).val);
        }
        return cycle;
    }

    public int runPart2(final List<String> lines) {
        final char[][] grid = getGrid(lines);
        final List<Value> values = buildValueList(grid, 1000); // 1000 should be enough to find a cycle
        final List<Integer> cycle = findCycle(values);

        int valToFetch = (1000000000 % cycle.size()) - 1;
        if (valToFetch < 0) {
            valToFetch = cycle.size() - 1;
        }

        return cycle.get(valToFetch);
    }

    public int runPart1(final List<String> lines) {
        char[][] grid = getGrid(lines);
        for (int min = 1; min <= 10; min++) {
            grid = runMinute(grid);
        }
        return getResourceValue(grid);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day18().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day18().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
