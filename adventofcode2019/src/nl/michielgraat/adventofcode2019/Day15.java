package nl.michielgraat.adventofcode2019;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day15 {

	private static final long[] program = { 3, 1033, 1008, 1033, 1, 1032, 1005, 1032, 31, 1008, 1033, 2, 1032, 1005,
			1032, 58, 1008, 1033, 3, 1032, 1005, 1032, 81, 1008, 1033, 4, 1032, 1005, 1032, 104, 99, 1002, 1034, 1,
			1039, 101, 0, 1036, 1041, 1001, 1035, -1, 1040, 1008, 1038, 0, 1043, 102, -1, 1043, 1032, 1, 1037, 1032,
			1042, 1106, 0, 124, 101, 0, 1034, 1039, 101, 0, 1036, 1041, 1001, 1035, 1, 1040, 1008, 1038, 0, 1043, 1,
			1037, 1038, 1042, 1105, 1, 124, 1001, 1034, -1, 1039, 1008, 1036, 0, 1041, 102, 1, 1035, 1040, 1001, 1038,
			0, 1043, 1001, 1037, 0, 1042, 1106, 0, 124, 1001, 1034, 1, 1039, 1008, 1036, 0, 1041, 1002, 1035, 1, 1040,
			1002, 1038, 1, 1043, 101, 0, 1037, 1042, 1006, 1039, 217, 1006, 1040, 217, 1008, 1039, 40, 1032, 1005, 1032,
			217, 1008, 1040, 40, 1032, 1005, 1032, 217, 1008, 1039, 33, 1032, 1006, 1032, 165, 1008, 1040, 35, 1032,
			1006, 1032, 165, 1102, 2, 1, 1044, 1105, 1, 224, 2, 1041, 1043, 1032, 1006, 1032, 179, 1101, 1, 0, 1044,
			1105, 1, 224, 1, 1041, 1043, 1032, 1006, 1032, 217, 1, 1042, 1043, 1032, 1001, 1032, -1, 1032, 1002, 1032,
			39, 1032, 1, 1032, 1039, 1032, 101, -1, 1032, 1032, 101, 252, 1032, 211, 1007, 0, 58, 1044, 1106, 0, 224,
			1101, 0, 0, 1044, 1106, 0, 224, 1006, 1044, 247, 101, 0, 1039, 1034, 101, 0, 1040, 1035, 1001, 1041, 0,
			1036, 1001, 1043, 0, 1038, 1001, 1042, 0, 1037, 4, 1044, 1105, 1, 0, 33, 14, 68, 54, 69, 24, 9, 59, 2, 7,
			68, 23, 97, 53, 74, 21, 32, 37, 55, 83, 3, 26, 85, 52, 38, 10, 81, 19, 82, 47, 70, 27, 60, 32, 98, 40, 46,
			75, 17, 66, 11, 92, 30, 84, 90, 36, 71, 6, 82, 95, 45, 23, 75, 49, 38, 71, 72, 2, 72, 26, 64, 93, 53, 68,
			90, 42, 3, 64, 3, 66, 21, 84, 47, 15, 87, 60, 18, 96, 30, 14, 54, 99, 48, 12, 63, 62, 86, 41, 56, 79, 50,
			99, 38, 68, 16, 15, 69, 53, 90, 59, 28, 41, 7, 94, 47, 74, 68, 56, 43, 70, 22, 55, 72, 87, 28, 50, 28, 55,
			98, 97, 22, 64, 63, 21, 28, 8, 87, 91, 39, 1, 93, 52, 95, 96, 68, 13, 24, 64, 14, 65, 78, 89, 34, 85, 92,
			35, 57, 83, 70, 21, 75, 43, 24, 76, 74, 11, 90, 55, 74, 22, 63, 9, 95, 64, 79, 2, 78, 30, 74, 75, 33, 23,
			47, 93, 93, 56, 77, 48, 72, 35, 42, 82, 36, 25, 20, 81, 15, 56, 95, 96, 33, 94, 53, 46, 64, 31, 46, 98, 43,
			40, 98, 48, 6, 71, 44, 83, 7, 56, 64, 92, 72, 24, 29, 35, 37, 22, 63, 21, 28, 68, 75, 31, 77, 28, 96, 71,
			35, 11, 66, 55, 87, 17, 64, 5, 53, 95, 79, 52, 95, 16, 78, 80, 47, 51, 90, 68, 63, 1, 10, 99, 79, 80, 30,
			97, 32, 82, 27, 62, 49, 1, 61, 93, 71, 7, 39, 93, 40, 75, 50, 94, 68, 22, 3, 44, 5, 93, 55, 53, 92, 92, 16,
			30, 94, 17, 15, 77, 55, 76, 25, 97, 53, 73, 96, 54, 98, 39, 73, 75, 5, 56, 78, 81, 48, 64, 73, 97, 25, 71,
			91, 28, 56, 90, 53, 75, 28, 79, 63, 35, 48, 81, 8, 28, 95, 73, 52, 30, 29, 88, 4, 94, 2, 36, 92, 86, 87, 9,
			34, 92, 98, 30, 99, 40, 37, 87, 36, 49, 34, 99, 72, 38, 54, 71, 1, 74, 41, 20, 72, 40, 90, 89, 6, 1, 74, 50,
			63, 47, 98, 79, 45, 90, 78, 34, 10, 78, 2, 72, 94, 56, 30, 86, 45, 82, 74, 51, 73, 88, 36, 65, 30, 63, 8,
			17, 68, 92, 13, 93, 3, 77, 72, 20, 90, 63, 37, 86, 77, 17, 95, 56, 57, 61, 77, 74, 19, 18, 70, 34, 93, 23,
			96, 8, 93, 1, 79, 81, 66, 27, 38, 2, 12, 31, 81, 43, 48, 93, 67, 60, 17, 93, 44, 99, 39, 72, 35, 92, 99, 42,
			46, 79, 60, 22, 56, 75, 60, 95, 23, 84, 33, 67, 16, 16, 36, 55, 39, 83, 46, 75, 80, 79, 2, 63, 25, 60, 20,
			4, 39, 97, 20, 90, 4, 30, 86, 9, 7, 90, 80, 49, 20, 98, 29, 83, 51, 46, 92, 27, 65, 34, 57, 61, 10, 94, 84,
			90, 3, 51, 64, 5, 37, 19, 51, 69, 73, 39, 96, 99, 24, 34, 66, 21, 76, 81, 33, 85, 14, 67, 54, 29, 94, 17,
			85, 8, 88, 42, 6, 89, 83, 9, 52, 81, 90, 11, 38, 95, 20, 93, 81, 20, 20, 86, 6, 36, 69, 77, 25, 15, 91, 78,
			32, 80, 3, 22, 11, 90, 89, 6, 11, 73, 1, 82, 46, 77, 99, 26, 41, 2, 75, 92, 52, 13, 80, 96, 44, 38, 98, 47,
			96, 87, 28, 65, 77, 17, 48, 93, 93, 46, 8, 82, 86, 26, 84, 64, 38, 53, 83, 67, 97, 30, 64, 39, 53, 31, 63,
			60, 11, 86, 81, 22, 84, 13, 89, 75, 2, 77, 5, 31, 69, 3, 8, 75, 60, 13, 14, 90, 66, 28, 66, 18, 85, 70, 51,
			82, 94, 28, 29, 99, 35, 71, 75, 80, 1, 93, 14, 13, 91, 14, 83, 24, 77, 32, 8, 48, 85, 96, 31, 6, 54, 70, 95,
			32, 35, 66, 80, 88, 3, 96, 35, 80, 54, 8, 70, 30, 2, 18, 59, 81, 27, 31, 85, 73, 35, 79, 68, 30, 14, 21, 67,
			74, 57, 60, 98, 44, 46, 24, 12, 60, 31, 39, 68, 79, 50, 3, 61, 40, 75, 54, 25, 85, 6, 93, 56, 86, 74, 98,
			10, 15, 66, 68, 13, 44, 26, 98, 40, 79, 80, 14, 14, 86, 30, 5, 74, 66, 46, 96, 17, 83, 6, 98, 16, 67, 91,
			90, 56, 97, 1, 68, 14, 85, 93, 69, 56, 88, 40, 79, 29, 91, 25, 68, 69, 74, 48, 66, 73, 76, 17, 61, 31, 62,
			90, 84, 46, 89, 0, 0, 21, 21, 1, 10, 1, 0, 0, 0, 0, 0, 0 };

	private static final int GO_NORTH = 1;
	private static final int GO_SOUTH = 2;
	private static final int GO_WEST = 3;
	private static final int GO_EAST = 4;

	private static final int HIT_WALL = 0;
	private static final int MOVED = 1;
	private static final int DONE = 2;

	private List<Coordinate> coordinates = new ArrayList<Coordinate>();

	private IntcodeComputer computer = new IntcodeComputer(program, 10000);

	public Day15() {
		computer.setPrintOutput(false);
	}

	private boolean recursiveSolve(Coordinate c, int direction, Coordinate prev) {
		computer.addInput(direction);
		computer.run();
		Tile t = convertResultToTile((int) computer.getOutput());
		c.t = t;
		System.out.println("Going: " + getDirection(direction) + ", at: [" + c + "], prev: [" + prev + "]");
		if (c.t == Tile.OXYGEN_TANK) {
			System.out.println("At oxygen tank!");
			return true;
		} else if (c.t == Tile.WALL) {
			System.out.println("Hit wall!");
			return false;
		} else {
			System.out.println("In hallway!");
			if (coordinates.contains(c) && coordinates.get(coordinates.indexOf(c)).distance > c.distance) {
				coordinates.remove(c);
			}
			coordinates.add(c);
			if (!coordinates.contains(new Coordinate(c.x - 1, c.y, c.distance + 1, Tile.HALL))) {
				if (recursiveSolve(new Coordinate(c.x - 1, c.y, c.distance + 1, Tile.HALL), GO_WEST, c)) {
					return true;
				}
			}
			if (!coordinates.contains(new Coordinate(c.x + 1, c.y, c.distance + 1, Tile.HALL))) {
				if (recursiveSolve(new Coordinate(c.x + 1, c.y, c.distance + 1, Tile.HALL), GO_EAST, c)) {
					return true;
				}
			}
			if (!coordinates.contains(new Coordinate(c.x, c.y - 1, c.distance + 1, Tile.HALL))) {
				if (recursiveSolve(new Coordinate(c.x, c.y - 1, c.distance + 1, Tile.HALL), GO_SOUTH, c)) {
					return true;
				}
			}
			if (!coordinates.contains(new Coordinate(c.x, c.y + 1, c.distance + 1, Tile.HALL))) {
				if (recursiveSolve(new Coordinate(c.x, c.y + 1, c.distance + 1, Tile.HALL), GO_NORTH, c)) {
					return true;
				}
			}
		}
		return false;
	}

	private int solve() {
		boolean done = false;
		int distance = 0;
		Coordinate orig = new Coordinate(0, 0, 0, Tile.HALL);
		Coordinate prev = orig;
		coordinates.add(orig);
		System.out.println("Adding " + orig);
		Stack<Integer> route = new Stack<Integer>();
		while (!done) {
			boolean goback = false;
			System.out.println("At: " + prev);
			distance++;
			int direction = findNextDirection(prev);
			if (direction == -1) {
				direction = getOpposite(route.pop());
				goback = true;
			}
			Coordinate next = getNewCoordinate(prev, direction);
			next.distance = distance;
			computer.addInput(direction);
			computer.run();
			Tile t = convertResultToTile((int) computer.getOutput());
			next.t = t;
			coordinates.add(next);
			System.out.println("Added " + next);
			if (t != Tile.WALL) {
				if (goback) {
					route.push(direction);
				}
				prev = next;
			}
			if (route.isEmpty() && findNextDirection(prev) == -1) {
				done = true;
			}
		}
		printMaze();
		return distance;
	}

	private void printMaze() {
		int minX = coordinates.stream().map(Coordinate::getX).min(Integer::compareTo).get();
        int maxX = coordinates.stream().map(Coordinate::getX).max(Integer::compareTo).get();
        int minY = coordinates.stream().map(Coordinate::getY).min(Integer::compareTo).get();
        int maxY = coordinates.stream().map(Coordinate::getY).max(Integer::compareTo).get();

        for(int i = maxY; i >= minY; i--) {
            StringBuilder stringBuilder = new StringBuilder();
            for(int j = minX; j <= maxX; j++) {
            	if (coordinates.contains(new Coordinate(j,i))) {
            		if (j == 0 && i == 0) {
            			stringBuilder.append("E");
            		} else if (Tile.WALL == coordinates.get(coordinates.indexOf(new Coordinate(j,i))).t) {
	                    stringBuilder.append("#");
	                }
	                else if (Tile.HALL == coordinates.get(coordinates.indexOf(new Coordinate(j,i))).t) {
	                    stringBuilder.append(".");
	                }
	
	                else if (Tile.OXYGEN_TANK == coordinates.get(coordinates.indexOf(new Coordinate(j,i))).t) {
	                    stringBuilder.append("0");
	                }
            	} else {
                    stringBuilder.append(" ");
                }
            }
            System.out.println(stringBuilder.toString());
        }
        
	}
	
	private int getOpposite(int direction) {
		switch (direction) {
		case GO_WEST:
			return GO_EAST;
		case GO_EAST:
			return GO_WEST;
		case GO_NORTH:
			return GO_SOUTH;
		default:
			return GO_NORTH;
		}
	}

	private Coordinate getNewCoordinate(Coordinate prev, int direction) {
		switch (direction) {
		case GO_WEST:
			return new Coordinate(prev.x - 1, prev.y);
		case GO_EAST:
			return new Coordinate(prev.x + 1, prev.y);
		case GO_SOUTH:
			return new Coordinate(prev.x, prev.y - 1);
		default:
			return new Coordinate(prev.x, prev.y + 1);
		}
	}

	private int findNextDirection(Coordinate c) {
		if (!coordinates.contains(new Coordinate(c.x - 1, c.y))) {
			return GO_WEST;
		} else if (!coordinates.contains(new Coordinate(c.x + 1, c.y))) {
			return GO_EAST;
		} else if (!coordinates.contains(new Coordinate(c.x, c.y - 1))) {
			return GO_SOUTH;
		} else if (!coordinates.contains(new Coordinate(c.x, c.y + 1))) {
			return GO_NORTH;
		} /*
			 * else if (coordinates.get(coordinates.indexOf(new Coordinate(c.x - 1, c.y))).t
			 * == Tile.HALL) { return GO_WEST; } else if
			 * (coordinates.get(coordinates.indexOf(new Coordinate(c.x + 1, c.y))).t ==
			 * Tile.HALL) { return GO_EAST; } else if
			 * (coordinates.get(coordinates.indexOf(new Coordinate(c.x, c.y - 1))).t ==
			 * Tile.HALL) { return GO_SOUTH; } else if
			 * (coordinates.get(coordinates.indexOf(new Coordinate(c.x, c.y + 1))).t ==
			 * Tile.HALL) { return GO_NORTH; } else { throw new
			 * IllegalArgumentException("Can not go in any direction!"); }
			 */
		return -1;
	}

	private List<Coordinate> findCoordsWithDistance(int distance) {
		return coordinates.stream().filter(c -> c.distance == distance).collect(Collectors.toList());
	}

	private String getDirection(int i) {
		if (i == GO_NORTH) {
			return "North";
		} else if (i == GO_SOUTH) {
			return "South";
		} else if (i == GO_WEST) {
			return "West";
		} else {
			return "East";
		}
	}

	private Tile convertResultToTile(int result) {
		switch (result) {
		case HIT_WALL:
			return Tile.WALL;
		case MOVED:
			return Tile.HALL;
		case DONE:
			return Tile.OXYGEN_TANK;
		default:
			throw new IllegalArgumentException("Unknown result: " + result);
		}
	}

	private int solveMaze() {
		Coordinate curCoord = new Coordinate(0, 0, 0, Tile.HALL);
		recursiveSolve(curCoord, GO_NORTH, curCoord);
		for (Coordinate c : coordinates) {
			if (c.t == Tile.OXYGEN_TANK) {
				return c.distance;
			}
		}
		return 0;
	}

	public void run() {
		System.out.println("Result: " + solve());
	}

	public static void main(String... args) {
		new Day15().run();
	}

	enum Tile {
		WALL, HALL, OXYGEN_TANK;

		public static String print(Tile t) {
			if (t == HALL) {
				return "Hall";
			} else if (t == WALL) {
				return "Wall";
			} else {
				return "Oxygen tank";
			}
		}
	}

	class Coordinate {
		int x;
		int y;
		int distance;
		Tile t;

		public Coordinate(int x, int y, int distance, Tile t) {
			this.x = x;
			this.y = y;
			this.distance = distance;
			this.t = t;
		}

		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		@Override
		public int hashCode() {
			return x * y;
		}

		@Override
		public boolean equals(Object o) {
			Coordinate other = (Coordinate) o;
			return this.x == other.x && this.y == other.y;
		}

		public String toString() {
			return "(" + x + "," + y + "), distance: " + distance + ", tile: " + Tile.print(t);
		}
	}
}
