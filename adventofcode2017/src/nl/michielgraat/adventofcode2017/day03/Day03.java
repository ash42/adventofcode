package nl.michielgraat.adventofcode2017.day03;

public class Day03 {

	private int manhattanDistance(final int x1, final int x2, final int y1, final int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	private int calculateRing(final int i) {
		if (i == 1) {
			return 0;
		} else {
			int total = 2;
			for (int ringNr = 1;; ringNr++) {
				total += (ringNr * 8);
				if (total > i) {
					return ringNr;
				}
			}
		}
	}

	private int calcRight(final int ringNr) {
		if (ringNr == 0) {
			return 1;
		} else if (ringNr == 1) {
			return 2;
		} else {
			return (ringNr - 1) * 8 + 1 + calcRight(ringNr - 1);
		}
	}

	private int calcUp(final int ringNr) {
		if (ringNr == 0) {
			return 1;
		} else if (ringNr == 1) {
			return 4;
		} else {
			return (ringNr - 1) * 8 + 3 + calcUp(ringNr - 1);
		}
	}

	private int calcLeft(final int ringNr) {
		if (ringNr == 0) {
			return 1;
		} else if (ringNr == 1) {
			return 6;
		} else {
			return (ringNr - 1) * 8 + 5 + calcLeft(ringNr - 1);
		}
	}

	private int calcDown(final int ringNr) {
		if (ringNr == 0) {
			return 1;
		} else if (ringNr == 1) {
			return 8;
		} else {
			return (ringNr) * 8 - 1 + calcDown(ringNr - 1);
		}
	}

	public int runPart2(final int max) {
		int current = 0;
		final int[][] grid = new int[20][20];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				grid[i][j] = 0;
			}
		}
		final int mod = 10;
		int x = mod;
		int y = mod;
		int countModRightUp = 1;
		int countModLeftDown = 2;
		int lastRight = 0;
		int lastUp = 0;
		int lastLeft = 0;
		int lastDown = 0;
		while (current <= max) {
			final int curX = x - mod;
			final int curY = y - mod;
			if (curX == 0 && curY == 0) {
				current = 1;
			} else {
				current = grid[x + 1][y] + grid[x + 1][y + 1] + grid[x][y + 1] + grid[x - 1][y + 1] + grid[x - 1][y]
						+ grid[x - 1][y - 1] + grid[x][y - 1] + grid[x + 1][y - 1];
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
				countModRightUp += 2;
				countModLeftDown += 2;
				lastRight = 0;
				lastUp = 0;
				lastLeft = 0;
				lastDown = 0;
			}
		}
		return current;
	}

	public int runPart1(final int i) {
		final int ringNr = calculateRing(i);
		final int right = calcRight(ringNr);
		final int up = calcUp(ringNr);
		final int left = calcLeft(ringNr);
		final int down = calcDown(ringNr);
		final int rightFurther = calcRight(ringNr + 1);
		if (i >= right && i < up) {
			final int diffRight = i - right;
			final int diffUp = up - i;
			if (diffRight < diffUp) {
				return manhattanDistance(0, ringNr, 0, diffRight);
			} else {
				return manhattanDistance(0, diffUp, 0, ringNr);
			}
		} else if (i >= up && i < left) {
			final int diffUp = i - up;
			final int diffLeft = left - i;
			if (diffUp < diffLeft) {
				return manhattanDistance(0, diffUp, 0, ringNr);
			} else {
				return manhattanDistance(0, ringNr, 0, diffLeft);
			}
		} else if (i >= left && i < down) {
			final int diffLeft = i - left;
			final int diffDown = down - i;
			if (diffLeft < diffDown) {
				return manhattanDistance(0, ringNr, 0, diffLeft);
			} else {
				return manhattanDistance(0, diffDown, 0, ringNr);
			}
		} else {
			final int diffDown = i - down;
			final int diffRight = rightFurther - i;
			if (diffDown < diffRight) {
				return manhattanDistance(0, diffDown, 0, ringNr);
			} else {
				return manhattanDistance(0, ringNr, 0, diffRight);
			}
		}
	}

	public static void main(final String[] args) {
		long start = System.nanoTime();
		System.out.println("Answer to part 1: " + new Day03().runPart1(325489));
		System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
		start = System.nanoTime();
		System.out.println("Answer to part 2: " + new Day03().runPart2(325489));
		System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
	}

}
