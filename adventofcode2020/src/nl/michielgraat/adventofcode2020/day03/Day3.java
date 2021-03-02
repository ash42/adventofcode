package nl.michielgraat.adventofcode2020.day03;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day3 {

	private static final String FILENAME = "day03.txt";

	private void printSlope(char[][] slope) {
		for (int level = 0; level < slope.length; level++) {
			for (int square = 0; square < slope[level].length; square++) {
				System.out.print(slope[level][square]);
			}
			System.out.println();
		}
	}

	private int getNeededWidth(int rightMove, List<String> slopeParts) {
		int neededWidth = rightMove * slopeParts.size();
		while (neededWidth % slopeParts.get(0).length() != 0) {
			neededWidth++;
		}
		return neededWidth;
	}

	private char[][] getSlope(int rightMove) {
		List<String> slopeParts = FileReader.getStringList(FILENAME);
		final int neededWidth = getNeededWidth(rightMove, slopeParts);
		char[][] slope = new char[slopeParts.size()][neededWidth];

		for (int level = 0; level < slopeParts.size(); level++) {
			List<Character> squares = slopeParts.get(level).chars().mapToObj(i -> (char) i)
					.collect(Collectors.toList());
			List<Character> original = new ArrayList<Character>(squares);
			while (squares.size() < neededWidth) {
				squares.addAll(original);
			}
			for (int squareIndex = 0; squareIndex < squares.size(); squareIndex++) {
				slope[level][squareIndex] = squares.get(squareIndex);
			}

		}
		return slope;
	}

	private int getNrOfTrees(int rightMove, int downMove, char[][] slope) {
		int nrOfTrees = 0;
		int step = 1;
		for (int level = downMove; level < slope.length; level += downMove) {
			int squareIndex = step * rightMove;
			if (slope[level][squareIndex] == '#') {
				nrOfTrees++;
			}
			step++;
		}

		return nrOfTrees;
	}

	public long runPart2() {
		char[][] slope = getSlope(7);
		int first = getNrOfTrees(1, 1, slope);
		int second = getNrOfTrees(3, 1, slope);
		int third = getNrOfTrees(5, 1, slope);
		int fourth = getNrOfTrees(7, 1, slope);
		int fifth = getNrOfTrees(1, 2, slope);

		return (long) first * (long) second * (long) third * (long) fourth * (long) fifth;
	}

	public int runPart1() {
		return getNrOfTrees(3, 1, getSlope(3));
	}

	public static void main(String[] args) {
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day3().runPart1());
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day3().runPart2());
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}