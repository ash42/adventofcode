package nl.michielgraat.adventofcode2020.day24;

import java.util.Calendar;
import java.util.List;
import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day24 {

	private static final String FILENAME = "day24.txt";

	private static final int WHITE = 0;
	private static final int BLACK = 1;

	private int[][][] flipTiles(List<String> lines, int size, int offset) {
		int[][][] grid = new int[size][size][size];

		for (String line : lines) {
			Path p = new Path(line);
			int x = offset;
			int y = offset;
			int z = offset;
			Direction d;
			while ((d = p.getNextDirection()) != null) {
				if (d == Direction.E) {
					x++;
					y--;
				} else if (d == Direction.W) {
					x--;
					y++;
				} else if (d == Direction.SE) {
					y--;
					z++;
				} else if (d == Direction.SW) {
					x--;
					z++;
				} else if (d == Direction.NW) {
					y++;
					z--;
				} else if (d == Direction.NE) {
					x++;
					z--;
				}
			}
			if (grid[x][y][z] == WHITE) {
				grid[x][y][z] = BLACK;
			} else {
				grid[x][y][z] = WHITE;
			}
		}
		return grid;
	}
	


	private int[][][] flip100Days(List<String> lines, int size, int offset) {
		int[][][] grid = flipTiles(lines, size, offset);

		int[][][] newGrid = new int[size][size][size];

		for (int day = 1; day <= 100; day++) {
			for (int x = 0; x < size; x++) {
				for (int y = 0; y < size; y++) {
					for (int z = 0; z < size; z++) {
						newGrid[x][y][z] = grid[x][y][z];
						int blackNeighbors = 0;

						if (x < size - 1 && y > 0 && grid[x + 1][y - 1][z] == BLACK)
							blackNeighbors++; // E
						if (x > 0 && y < size - 1 && grid[x - 1][y + 1][z] == BLACK)
							blackNeighbors++; // W
						if (y > 0 && z < size - 1 && grid[x][y - 1][z + 1] == BLACK)
							blackNeighbors++; // SE
						if (x > 0 && z < size - 1 && grid[x - 1][y][z + 1] == BLACK)
							blackNeighbors++; // SW
						if (y < size - 1 && z > 0 && grid[x][y + 1][z - 1] == BLACK)
							blackNeighbors++; // NW
						if (x < size - 1 && z > 0 && grid[x + 1][y][z - 1] == BLACK)
							blackNeighbors++; // NE

						if (grid[x][y][z] == BLACK && (blackNeighbors == 0 || blackNeighbors > 2)) {
							newGrid[x][y][z] = WHITE;
						} else if (grid[x][y][z] == WHITE && blackNeighbors == 2) {
							newGrid[x][y][z] = BLACK;
						}
					}
				}
			}
			grid = newGrid;
			newGrid = new int[size][size][size];
		}
		return grid;
	}

	public long runPart2(List<String> lines) {
		int size = 140;
		int offset = size / 2;
		int[][][] grid = flip100Days(lines, size, offset);
		long total = 0;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				for (int z = 0; z < size; z++) {
					if (grid[x][y][z] == BLACK) {
						total++;
					}
				}
			}
		}
		return total;
	}

	public long runPart1(List<String> lines) {
		int size = 100;
		int offset = size / 2;
		int[][][] grid = flipTiles(lines, size, offset);

		long total = 0;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				for (int z = 0; z < size; z++) {
					if (grid[x][y][z] == BLACK) {
						total++;
					}
				}
			}
		}

		return total;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day24().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day24().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}

}

enum Direction {
	E, SE, SW, W, NW, NE;
}

class Path {

	String path;

	Path(String path) {
		this.path = path;
	}

	Direction getNextDirection() {
		if (path.length() > 0) {
			if (path.startsWith("e")) {
				path = path.substring(1);
				return Direction.E;
			} else if (path.startsWith("w")) {
				path = path.substring(1);
				return Direction.W;
			} else if (path.startsWith("s") && path.charAt(1) == 'e') {
				path = path.substring(2);
				return Direction.SE;
			} else if (path.startsWith("s") && path.charAt(1) == 'w') {
				path = path.substring(2);
				return Direction.SW;
			} else if (path.startsWith("n") && path.charAt(1) == 'w') {
				path = path.substring(2);
				return Direction.NW;
			} else if (path.startsWith("n") && path.charAt(1) == 'e') {
				path = path.substring(2);
				return Direction.NE;
			} else {
				throw new IllegalArgumentException("Unknown direction");
			}
		} else {
			return null;
		}
	}
}