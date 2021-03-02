package nl.michielgraat.adventofcode2017.day3;

public class Day3 {
	
	private int manhattanDistance(int x1, int x2, int y1, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
	
	private int calculateRing(int i) {
		if (i == 1) {
			return 0;
		} else {
			int total = 2;
			for (int ringNr = 1; ; ringNr++) {
				total += (ringNr*8);
				if (total > i) {
					return ringNr;
				}
			}
		}
	}
	
	private int calcRight(int ringNr) {
		if (ringNr == 0) {
			return 1;
		} else if (ringNr == 1) {
			return 2;
		} else {
			return (ringNr-1)*8 + 1 + calcRight(ringNr-1);
		}
	}
	
	private int calcUp(int ringNr) {
		if (ringNr == 0) {
			return 1;
		} else if (ringNr == 1) {
			return 4;
		} else {
			return (ringNr-1)*8 + 3 + calcUp(ringNr-1);
		}
	}
	
	private int calcLeft(int ringNr) {
		if (ringNr == 0) {
			return 1;
		} else if (ringNr == 1) {
			return 6;
		} else {
			return (ringNr-1)*8 + 5 + calcLeft(ringNr-1);
		}
	}
	
	private int calcDown(int ringNr) {
		if (ringNr == 0) {
			return 1;
		} else if (ringNr == 1) {
			return 8;
		} else {
			return (ringNr)*8 - 1 + calcDown(ringNr-1);
		}
	}
	
	public int runPart1(int i) {
		int ringNr = calculateRing(i);
		int right = calcRight(ringNr);
		int up = calcUp(ringNr);
		int left = calcLeft(ringNr);
		int down = calcDown(ringNr);
		int rightFurther = calcRight(ringNr+1);
		if (i >= right && i < up) {
			int diffRight = i - right;
			int diffUp = up - i;
			if (diffRight < diffUp) {
				return manhattanDistance(0, ringNr, 0, diffRight);
			} else {
				return manhattanDistance(0, diffUp, 0, ringNr);
			}
		} else if (i >= up && i < left) {
			int diffUp = i - up;
			int diffLeft = left - i;
			if (diffUp < diffLeft) {
				return manhattanDistance(0, diffUp, 0, ringNr);
			} else {
				return manhattanDistance(0, ringNr, 0, diffLeft);
			}
		} else if (i >= left && i < down) {
			int diffLeft = i - left;
			int diffDown = down - i;
			if (diffLeft < diffDown) {
				return manhattanDistance(0, ringNr , 0, diffLeft);
			} else {
				return manhattanDistance(0, diffDown, 0, ringNr);
			}
		} else {
			int diffDown = i - down;
			int diffRight = rightFurther - i;
			if (diffDown < diffRight) {
				return manhattanDistance(0, diffDown, 0, ringNr);
			} else {
				return manhattanDistance(0, ringNr, 0, diffRight);
			}
		}
	}
	
	public int runPart2(int max) {
		int current = 0;
		int[][] grid = new int[20][20];
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				grid[i][j] = 0;
			}
		}
		int mod = 10;
		int x = mod;
		int y = mod;
		int countModRightUp = 1;
		int countModLeftDown = 2;
		int lastRight = 0;
		int lastUp = 0;
		int lastLeft = 0;
		int lastDown = 0;
		while (current <= max) {
			int curX = x - mod;
			int curY = y - mod;
			if (curX == 0 && curY == 0) {
				current = 1;
			} else {
				current = grid[x+1][y] + grid[x+1][y+1] + grid[x][y+1] + grid[x-1][y+1] + grid[x-1][y] + grid[x-1][y-1] + grid[x][y-1] + grid[x+1][y-1];
			}
			grid[x][y] = current;
			if (lastRight < countModRightUp) {
				x++;
				lastRight++;
			} else if (lastUp < countModRightUp) {
				y++;
				lastUp++;
			} else if (lastLeft < countModLeftDown) {
				x--;
				lastLeft++;
			} else if (lastDown < countModLeftDown) {
				y--;
				lastDown++;
			} else {
				countModRightUp+=2;
				countModLeftDown+=2;
				lastRight = 0;
				lastUp = 0;
				lastLeft = 0;
				lastDown = 0;
			}
		}
		return current;
	}
	
	public static void main(String[] args) {
		System.out.println("Part1: " + new Day3().runPart1(325489));
		System.out.println("Part2: " + new Day3().runPart2(325489));
	}

}
