package nl.michielgraat.adventofcode2017.day05;

import java.util.List;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day05 {

	private static final String FILENAME = "day05.txt";

	public int runPart2(final List<Integer> instructions) {
		int nrOfSteps = 0;
		final int maxIndex = instructions.size();
		int pointer = 0;
		while (pointer >= 0 && pointer < maxIndex) {
			nrOfSteps++;
			final int offset = instructions.get(pointer);
			if (offset < 3) {
				instructions.set(pointer, offset + 1);
			} else {
				instructions.set(pointer, offset - 1);
			}
			pointer = pointer + offset;
		}

		return nrOfSteps;
	}

	public int runPart1(final List<Integer> instructions) {
		int nrOfSteps = 0;
		final int maxIndex = instructions.size();
		int pointer = 0;
		while (pointer >= 0 && pointer < maxIndex) {
			nrOfSteps++;
			final int offset = instructions.get(pointer);
			instructions.set(pointer, offset + 1);
			pointer = pointer + offset;
		}

		return nrOfSteps;
	}

	public static void main(final String[] args) {
		List<Integer> lines = FileReader.getCompleteIntegerList(FILENAME);
		long start = System.nanoTime();
		System.out.println("Answer to part 1: " + new Day05().runPart1(lines));
		System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
		lines = FileReader.getCompleteIntegerList(FILENAME);
		start = System.nanoTime();
		System.out.println("Answer to part 2: " + new Day05().runPart2(lines));
		System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
	}

}
