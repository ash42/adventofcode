package nl.michielgraat.adventofcode2020.day12;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day12 {

	private static final String FILENAME = "day12.txt";

	private static final int NORTH = 0;
	private static final int EAST = 90;
	private static final int SOUTH = 180;
	private static final int WEST = 270;

	private static final String N = "N";
	private static final String S = "S";
	private static final String E = "E";
	private static final String W = "W";
	private static final String L = "L";
	private static final String R = "R";
	private static final String F = "F";

	public long runPart2(List<String> lines) {
		Point ship = new Point(0,0);
		Point waypoint = new Point(10, 1);
		for (String line : lines) {
			String action = line.substring(0, 1);
			int number = Integer.valueOf(line.substring(1));
			
			switch (action) {
			case N:
				waypoint.y += number;
				break;
			case S:
				waypoint.y -= number;
				break;
			case E:
				waypoint.x += number;
				break;
			case W:
				waypoint.x -= number;
				break;
			case L:
			case R:
				int origx = waypoint.x;
				int origy = waypoint.y;
				waypoint.x = setX(origx, origy, number, action);
				waypoint.y = setY(origx, origy, number, action);
				break;
			case F:
				for (int i = 1; i <= number; i++) {
					ship.x += waypoint.x;
					ship.y += waypoint.y;
				}
				break;	
			}
		}
		return getManhattanDistance(ship.x, ship.y);
	}

	private int setX(int x, int y, int number, String direction) {
		if (number == 180) {
			x = -x;
		} else if (direction.equals("R")) {
			if (number == 90) {
				x = y; 
			} else if (number == 270) {
				x = -y;
			}
		} else {
			if (number == 90) {
				x = -y;
			} else if (number == 270) {
				x = y;
			}
		}
		return x;
	}
	
	private int setY(int x, int y, int number, String direction) {
		if (number == 180) {
			y = -y;
		} else if (direction.equals("R")) {
			if (number == 90) {
				y = -x;
			} else if (number == 270) {
				y = x;
			}
		} else {
			if (number == 90) {
				y = x;
			} else {
				y = -x;
			}
		}
		return y;
	}
	
	private long getManhattanDistance(int x, int y) {
		return Math.abs(x) + Math.abs(y);
	}

	private int getNewDirection(int currentDirection, int degrees) {
		return (360 + (currentDirection + degrees)) % 360;
	}

	public long runPart1(List<String> lines) {
		int currentDirection = EAST;
		int x = 0, y = 0;

		for (String line : lines) {
			String action = line.substring(0, 1);
			int number = Integer.valueOf(line.substring(1));
			if (action.equals("L")) {
				number = 0 - number;
			}

			switch (action) {
			case N:
				y += number;
				break;
			case S:
				y -= number;
				break;
			case E:
				x += number;
				break;
			case W:
				x -= number;
				break;
			case L:
			case R:
				currentDirection = getNewDirection(currentDirection, number);
				break;
			default:
				switch (currentDirection) {
				case NORTH:
					y += number;
					break;
				case SOUTH:
					y -= number;
					break;
				case EAST:
					x += number;
					break;
				case WEST:
					x -= number;
					break;
				}
			}
		}
		return getManhattanDistance(x, y);
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day12().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day12().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
	}
}

class Point {
	int x;
	int y;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}