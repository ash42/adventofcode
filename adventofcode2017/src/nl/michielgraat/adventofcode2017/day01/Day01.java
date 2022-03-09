package nl.michielgraat.adventofcode2017.day01;

import java.util.List;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day01 {

	private static final String FILENAME = "day01.txt";

	public int runPart2(final List<Integer> values) {
		final int halfway = values.size() / 2;
		int total = 0;
		for (int i = 0; i < values.size(); i++) {
			final int nextIndex = (i + halfway) % values.size();
			if (values.get(i).equals(values.get(nextIndex))) {
				total += values.get(i);
			}
		}
		return total;
	}
	
	public int runPart1(final List<Integer> values) {
		int total = 0;
		if (values.get(0).equals(values.get(values.size() - 1))) {
			total += values.get(0);
		}
		for (int i = 1; i < values.size(); i++) {
			if (values.get(i).equals(values.get(i - 1))) {
				total += values.get(i);
			}
		}
		return total;
	}

	public static void main(final String[] args) {
		final List<Integer> lines = FileReader.getIntegerList(FILENAME);
		long start = System.nanoTime();
		System.out.println("Answer to part 1: " + new Day01().runPart1(lines));
		System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
		start = System.nanoTime();
		System.out.println("Answer to part 2: " + new Day01().runPart2(lines));
		System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
	}

}
