package nl.michielgraat.adventofcode2018.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {

	private static final String FILENAME = "day6.txt";

	private int manhattanDistance(int x1, int x2, int y1, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	private List<String> readFiles() {
		List<String> coordinates = new ArrayList<String>();
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {
			coordinates = stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return coordinates;
	}

	private List<Coordinate> convertToCoordinates(List<String> coordinates) {
		List<Coordinate> converted = new ArrayList<Coordinate>();
		int maxX = 0;
		int maxY = 0;
		int name = 1;
		for (String coordinate : coordinates) {
			String[] split = coordinate.split(",");
			int x = Integer.valueOf(split[0].trim());
			int y = Integer.valueOf(split[1].trim());
			if (x > maxX)
				maxX = x;
			if (y > maxY)
				maxY = y;
			converted.add(new Coordinate(x, y, name));
			name++;
		}
		return converted;
	}

	private int getMaxX(List<Coordinate> coordinates) {
		int maxX = 0;
		for (Coordinate c : coordinates) {
			if (c.x > maxX)
				maxX = c.x;
		}
		return maxX;
	}

	private int getMaxY(List<Coordinate> coordinates) {
		int maxY = 0;
		for (Coordinate c : coordinates) {
			if (c.x > maxY)
				maxY = c.x;
		}
		return maxY;
	}

	private int[][] createGrid(List<Coordinate> coordinates) {
		int maxX = getMaxX(coordinates);
		int maxY = getMaxY(coordinates);
		int[][] grid = new int[maxX + 2][maxY + 2];
		for (Coordinate c : coordinates) {
			grid[c.x][c.y] = c.n;
		}
		return grid;
	}
	
	private void removeInfiniteAreas(int[][] grid, int maxX, int maxY) {
		for (int edgeX = 0; edgeX <= maxX + 1; edgeX++) {
			int cur = grid[edgeX][0];
			if (cur != 0) {
				for (int y = 0; y <= maxY + 1; y++) {
					for (int x = 0; x <= maxX + 1; x++) {
						if (grid[x][y] == cur) {
							grid[x][y] = 0;
						}
					}
				}
			}
		}
		for (int edgeX = 0; edgeX <= maxX + 1; edgeX++) {
			int cur = grid[edgeX][maxY+1];
			if (cur != 0) {
				for (int y = 0; y <= maxY + 1; y++) {
					for (int x = 0; x <= maxX + 1; x++) {
						if (grid[x][y] == cur) {
							grid[x][y] = 0;
						}
					}
				}
			}
		}
		for (int edgeY = 0; edgeY <= maxY + 1; edgeY++) {
			int cur = grid[0][edgeY];
			if (cur != 0) {
				for (int y = 0; y <= maxY + 1; y++) {
					for (int x = 0; x <= maxX + 1; x++) {
						if (grid[x][y] == cur) {
							grid[x][y] = 0;
						}
					}
				}
			}
		}
		for (int edgeY = 0; edgeY <= maxY + 1; edgeY++) {
			int cur = grid[maxX+1][edgeY];
			if (cur != 0) {
				for (int y = 0; y <= maxY + 1; y++) {
					for (int x = 0; x <= maxX + 1; x++) {
						if (grid[x][y] == cur) {
							grid[x][y] = 0;
						}
					}
				}
			}
		}
	}

	private int fillGridAndGetAnswerPart1(int[][] grid, List<Coordinate> coordinates) {
		int maxX = getMaxX(coordinates);
		int maxY = getMaxY(coordinates);

		for (int x = 0; x <= maxX + 1; x++) {
			for (int y = 0; y <= maxY + 1; y++) {
				int min = Integer.MAX_VALUE;
				for (Coordinate cur : coordinates) {
					int distance = manhattanDistance(x, cur.x, y, cur.y);
					if (distance < min) {
						grid[x][y] = cur.n;
						min = distance;
					} else if (distance == min) {
						grid[x][y] = 0;
					}
				}

			}
		}
		
		removeInfiniteAreas(grid, maxX, maxY);
		
		int max = 0;
		for (int c = 1; c <= coordinates.size(); c++) {
			int total = 0;
			for (int x = 0; x <= maxX; x++) {
				for (int y = 0; y <= maxY; y++) {
					if (grid[x][y] == c) {
						total++;
					}
				}
			}
			if (total > max) {
				max = total;
			}
		}
		return max;
	}
	
	private int fillGridAndGetAnswerPart2(int[][] grid, List<Coordinate> coordinates) {
		int maxX = getMaxX(coordinates);
		int maxY = getMaxY(coordinates);
		int total = 0;
		for (int x = 0; x <= maxX + 1; x++) {
			for (int y = 0; y <= maxY + 1; y++) {
				int totalDistance = 0;
				for (Coordinate c : coordinates) {
					int distance = manhattanDistance(x, c.x, y, c.y);
					totalDistance += distance;
				}
				if (totalDistance < 10000) {
					total++;
				}
			}
		}
		return total;
	}

	public int runPart1() {
		List<String> readCoordinates = readFiles();
		List<Coordinate> coordinates = convertToCoordinates(readCoordinates);
		int[][] grid = createGrid(coordinates);
		return fillGridAndGetAnswerPart1(grid, coordinates);
	}
	
	public int runPart2() {
		List<String> readCoordinates = readFiles();
		List<Coordinate> coordinates = convertToCoordinates(readCoordinates);
		int[][] grid = createGrid(coordinates);
		return fillGridAndGetAnswerPart2(grid, coordinates);
	}

	public static void main(String[] args) {
		System.out.println("Answer to part 1: " + new Day6().runPart1());
		System.out.println("Answer to part 2: " + new Day6().runPart2());
	}

	class Coordinate {
		int x;
		int y;
		int n;

		Coordinate(int x, int y, int n) {
			super();
			this.x = x;
			this.y = y;
			this.n = n;
		}
	}
}
