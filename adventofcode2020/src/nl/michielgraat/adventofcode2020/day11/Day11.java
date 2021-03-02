package nl.michielgraat.adventofcode2020.day11;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day11 {

	private static final String FILENAME = "day11.txt";

	private static final char OCCUPIED = '#';
	private static final char EMPTY = 'L';
	private static final char FLOOR = '.';

	private char[][] fillInitialGrid(final List<String> lines) {
		char[][] grid = new char[lines.size()][lines.get(0).length()];
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				grid[i][j] = line.charAt(j);
			}
		}
		return grid;
	}

	private int nrOfOccupiedAdjacentSeats(int x, int y, final char[][] grid) {
		int nr = 0;
		if (x - 1 >= 0 && grid[x - 1][y] == OCCUPIED)
			nr++;
		if (x - 1 >= 0 && y + 1 <= grid[x].length - 1 && grid[x - 1][y + 1] == OCCUPIED)
			nr++;
		if (y + 1 <= grid[x].length - 1 && grid[x][y + 1] == OCCUPIED)
			nr++;
		if (x + 1 <= grid.length - 1 && y + 1 <= grid[x].length - 1 && grid[x + 1][y + 1] == OCCUPIED)
			nr++;
		if (x + 1 <= grid.length - 1 && grid[x + 1][y] == OCCUPIED)
			nr++;
		if (x + 1 <= grid.length - 1 && y - 1 >= 0 && grid[x + 1][y - 1] == OCCUPIED)
			nr++;
		if (y - 1 >= 0 && grid[x][y - 1] == OCCUPIED)
			nr++;
		if (x - 1 >= 0 && y - 1 >= 0 && grid[x - 1][y - 1] == OCCUPIED)
			nr++;
		return nr;
	}

	private int nrOfClosestOccupiedSeats(int startX, int startY, final char[][] grid) {
		int total = 0;
		int x = startX;
		int y = startY;
		boolean done = false;
		// x-1, y;
		while (!done) {
			x--;
			if (x < 0 || grid[x][y] == EMPTY) {
				done = true;
			} else if (grid[x][y] == OCCUPIED) {
				total++;
				done = true;
			}
		}
		x = startX;
		y = startY;
		done = false;
		// x-1, y+1
		while (!done) {
			x--;
			y++;
			if (x < 0 || y >= grid[x].length || grid[x][y] == EMPTY) {
				done = true;
			} else if (grid[x][y] == OCCUPIED) {
				total++;
				done = true;
			}
		}
		x = startX;
		y = startY;
		done = false;
		// x, y+1
		while (!done) {
			y++;
			if (y >= grid[x].length || grid[x][y] == EMPTY) {
				done = true;
			} else if (grid[x][y] == OCCUPIED) {
				total++;
				done = true;
			}
		}
		x = startX;
		y = startY;
		done = false;
		// x+1, y+1
		while (!done) {
			x++;
			y++;
			if (x >= grid.length || y >= grid[x].length || grid[x][y] == EMPTY) {
				done = true;
			} else if (grid[x][y] == OCCUPIED) {
				total++;
				done = true;
			}
		}
		x = startX;
		y = startY;
		done = false;
		// x+1, y
		while (!done) {
			x++;
			if (x >= grid.length || grid[x][y] == EMPTY) {
				done = true;
			} else if (grid[x][y] == OCCUPIED) {
				total++;
				done = true;
			}
		}
		x = startX;
		y = startY;
		done = false;
		// x+1, y-1
		while (!done) {
			x++;
			y--;
			if (x >= grid.length || y < 0 || grid[x][y] == EMPTY) {
				done = true;
			} else if (grid[x][y] == OCCUPIED) {
				total++;
				done = true;
			}
		}
		x = startX;
		y = startY;
		done = false;
		// x, y-1
		while (!done) {
			y--;
			if (y < 0 || grid[x][y] == EMPTY) {
				done = true;
			} else if (grid[x][y] == OCCUPIED) {
				total++;
				done = true;
			}
		}
		x = startX;
		y = startY;
		done = false;
		// x-1, y-1
		while (!done) {
			x--;
			y--;
			if (x < 0 || y < 0 || grid[x][y] == EMPTY) {
				done = true;
			} else if (grid[x][y] == OCCUPIED) {
				total++;
				done = true;
			}
		}
		return total;
	}

	private void printGrid(final char[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	private int nrOfOccSeats(final char[][] grid) {
		int nr = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == OCCUPIED) {
					nr++;
				}
			}
		}
		return nr;
	}

	private boolean same(char[][] grid1, char[][] grid2) {
		for (int i = 0; i < grid1.length; i++) {
			for (int j = 0; j < grid1[i].length; j++) {
				if (grid1[i][j] != grid2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	private char[][] copyGrid(char[][] newGrid) {
		char[][] grid = new char[newGrid.length][newGrid[0].length];
		for (int i = 0; i < newGrid.length; i++) {
			for (int j = 0; j < newGrid[i].length; j++) {
				if (newGrid[i][j] == OCCUPIED) {
					grid[i][j] = OCCUPIED;
				} else if (newGrid[i][j] == EMPTY) {
					grid[i][j] = EMPTY;
				} else {
					grid[i][j] = FLOOR;
				}
			}
		}
		return grid;
	}

	public long runPart2(final List<String> lines) {
		char[][] grid = fillInitialGrid(lines);
		char[][] newGrid = new char[lines.size()][lines.get(0).length()];

		while (true) {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (grid[i][j] == EMPTY && nrOfClosestOccupiedSeats(i, j, grid) == 0) {
						newGrid[i][j] = OCCUPIED;
					} else if (grid[i][j] == OCCUPIED && nrOfClosestOccupiedSeats(i, j, grid) >= 5) {
						newGrid[i][j] = EMPTY;
					} else if (grid[i][j] == OCCUPIED) {
						newGrid[i][j] = OCCUPIED;
					} else if (grid[i][j] == EMPTY) {
						newGrid[i][j] = EMPTY;
					} else {
						newGrid[i][j] = FLOOR;
					}
				}
			}
			if (same(grid, newGrid)) {
				return nrOfOccSeats(newGrid);
			} else {
				grid = copyGrid(newGrid);
				newGrid = new char[lines.size()][lines.get(0).length()];
			}
		}
	}

	public long runPart1(final List<String> lines) {
		char[][] grid = fillInitialGrid(lines);
		char[][] newGrid = new char[lines.size()][lines.get(0).length()];

		while (true) {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (grid[i][j] == EMPTY && nrOfOccupiedAdjacentSeats(i, j, grid) == 0) {
						newGrid[i][j] = OCCUPIED;
					} else if (grid[i][j] == OCCUPIED && nrOfOccupiedAdjacentSeats(i, j, grid) >= 4) {
						newGrid[i][j] = EMPTY;
					} else if (grid[i][j] == OCCUPIED) {
						newGrid[i][j] = OCCUPIED;
					} else if (grid[i][j] == EMPTY) {
						newGrid[i][j] = EMPTY;
					} else {
						newGrid[i][j] = FLOOR;
					}
				}
			}
			if (same(grid, newGrid)) {
				return nrOfOccSeats(newGrid);
			} else {
				grid = copyGrid(newGrid);
				newGrid = new char[lines.size()][lines.get(0).length()];
			}
		}
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day11().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day11().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}

}
