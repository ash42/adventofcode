package nl.michielgraat.adventofcode2015.day06;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day06 {

	private static final String FILENAME = "day06.txt";
	
	private void performOperation2(String operation, int sx, int sy, int ex, int ey, int[][] grid) {
		for (int x = sx; x <= ex; x++) {
			for (int y = sy; y <= ey; y++) {
				if (operation.equals("turn on")) {
					grid[x][y] += 1;
				} else if (operation.equals("turn off")) {
					if (grid[x][y] > 0) {
						grid[x][y] -= 1;
					}
				} else {
					grid[x][y] += 2;
				}
			}
		}
	}

	private void performOperation(String operation, int sx, int sy, int ex, int ey, int[][] grid) {
		for (int x = sx; x <= ex; x++) {
			for (int y = sy; y <= ey; y++) {
				if (operation.equals("turn on")) {
					grid[x][y] = 1;
				} else if (operation.equals("turn off")) {
					grid[x][y] = 0;
				} else {
					if (grid[x][y] == 0) {
						grid[x][y] = 1;
					} else {
						grid[x][y] = 0;
					}
				}
			}
		}
	}
	
	private int run(List<String> lines, boolean part1) {
		int[][] grid = new int[1000][1000];
		for (String line : lines) {
			String[] s = line.split("through");
			String firstPart = s[0].trim();
			String secondPart = s[1].trim();
			String operation = firstPart.substring(0, firstPart.lastIndexOf(" "));
			int sx = Integer.valueOf(firstPart.substring(firstPart.lastIndexOf(" ") + 1, firstPart.indexOf(",")));
			int sy = Integer.valueOf(firstPart.substring(firstPart.indexOf(",") + 1));
			int ex = Integer.valueOf(secondPart.substring(0, secondPart.indexOf(",")));
			int ey = Integer.valueOf(secondPart.substring(secondPart.indexOf(",") + 1));
			if (part1) {
				performOperation(operation, sx, sy, ex, ey, grid);
			} else {
				performOperation2(operation, sx, sy, ex, ey, grid);
			}
		}
		int total = 0;
		for (int x = 0; x < 1000; x++) {
			for (int y = 0; y < 1000; y++) {
				total += grid[x][y];
			}
		}
		return total;
	}
	
	private int runPart2(List<String> lines) {
		return run(lines, false);
	}

	private int runPart1(List<String> lines) {
		return run(lines, true);
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day06().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day06().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}