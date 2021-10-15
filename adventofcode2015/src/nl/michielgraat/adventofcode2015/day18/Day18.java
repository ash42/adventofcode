package nl.michielgraat.adventofcode2015.day18;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day18 {

    private static final String FILENAME = "day18.txt";

    private static final int DIMENSION = 100;

    private int[][] getGrid(List<String> lines) {
        int[][] grid = new int[DIMENSION][DIMENSION];

        int x = 0;
        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == '.') {
                    grid[x][i] = 0;
                } else {
                    grid[x][i] = 1;
                }
            }
            x++;
        }
        return grid;
    }

    private int nrOfLightsOn(int[][] grid) {
        int total = 0;
        for (int x = 0; x < DIMENSION; x++) {
            for (int y = 0; y < DIMENSION; y++) {
                total += grid[x][y];
            }
        }
        return total;
    }

    private int getNrOfOnNeighbors(int x, int y, int[][] input) {
        int total = 0;

        int nextX = x - 1;
        int nextY = y - 1;
        if (nextX >= 0 && nextY >= 0) {
            total += input[nextX][nextY];
        }
        nextX = x;
        nextY = y - 1;
        if (nextY >= 0) {
            total += input[nextX][nextY];
        }
        nextX = x + 1;
        nextY = y - 1;
        if (nextX < DIMENSION && nextY >= 0) {
            total += input[nextX][nextY];
        }

        nextX = x - 1;
        nextY = y;
        if (nextX >= 0) {
            total += input[nextX][nextY];
        }
        nextX = x + 1;
        nextY = y;
        if (nextX < DIMENSION) {
            total += input[nextX][nextY];
        }
        
        nextX = x - 1;
        nextY = y + 1;
        if (nextX >= 0 && nextY < DIMENSION) {
            total += input[nextX][nextY];
        }
        nextX = x;
        nextY = y + 1;
        if (nextY < DIMENSION) {
            total += input[nextX][nextY];
        }
        nextX = x + 1;
        nextY = y + 1;
        if (nextX < DIMENSION && nextY < DIMENSION) {
            total += input[nextX][nextY];
        }

        return total;
    }

    private int[][] step(int[][] input) {
        int[][] output = new int[DIMENSION][DIMENSION];

        for (int x = 0; x < DIMENSION; x++) {
            for (int y = 0; y < DIMENSION; y++) {
                int onNeighbors = getNrOfOnNeighbors(x, y, input);
                if (input[x][y] == 1 && (onNeighbors == 2 || onNeighbors == 3)) {
                    output[x][y] = 1;
                } else if (input[x][y] == 0 && onNeighbors == 3) {
                    output[x][y] = 1;
                } else {
                    output[x][y] = 0;
                }
            }
        }
        return output;
    }

    private int[][] step2(int[][] input) {
        int[][] output = new int[DIMENSION][DIMENSION];

        for (int x = 0; x < DIMENSION; x++) {
            for (int y = 0; y < DIMENSION; y++) {
                int onNeighbors = getNrOfOnNeighbors(x, y, input);
                if ((x == 0 && y == 0) ||
                    (x == 0 && y == DIMENSION - 1) ||
                    (x == DIMENSION - 1 && y == 0) ||
                    (x == DIMENSION - 1 && y == DIMENSION - 1)) {
                    output[x][y] = 1;
                } else if (input[x][y] == 1 && (onNeighbors == 2 || onNeighbors == 3)) {
                    output[x][y] = 1;
                } else if (input[x][y] == 0 && onNeighbors == 3) {
                    output[x][y] = 1;
                } else {
                    output[x][y] = 0;
                }
            }
        }
        return output;
    }

    private void print(int[][] grid) {
        for (int x = 0; x < DIMENSION; x++) {
            for (int y = 0; y < DIMENSION; y++) {
                System.out.print(grid[x][y]);
            }
            System.out.println();
        }
    }

    private int runPart2(List<String> lines) {
        int[][] grid = getGrid(lines);
        grid[0][0] = 1;
        grid[0][DIMENSION - 1] = 1;
        grid[DIMENSION - 1][0] = 1;
        grid[DIMENSION - 1][DIMENSION - 1] = 1;
        for (int i = 0; i < 100; i++) {
            grid = step2(grid);
        }
        //print(grid);
        return nrOfLightsOn(grid);
    }

    private int runPart1(List<String> lines) {
        int[][] grid = getGrid(lines);
        //print(grid);
        for (int i = 0; i < 100; i++) {
            grid = step(grid);
        }
        //print(grid);
        return nrOfLightsOn(grid);
    }

    public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day18().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day18().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}
