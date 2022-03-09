package nl.michielgraat.adventofcode2017.day02;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day02 {

	private static final String FILENAME = "day02.txt";

	public int runPart2(final List<String> lines) {
		int total = 0;
		for (final String line : lines) {
			final List<String> sValues = Arrays.asList(line.split("\\s+"));
			final List<Integer> values = sValues.stream().map(Integer::parseInt).collect(Collectors.toList());

			for (int i = 0; i < values.size(); i++) {
				final int value1 = values.get(i);
				for (int j = i + 1; j < values.size(); j++) {
					final int value2 = values.get(j);
					if (value1 > value2) {
						if (value1 % value2 == 0) {
							total += value1 / value2;
						}
					} else {
						if (value2 % value1 == 0) {
							total += value2 / value1;
						}
					}
				}
			}
		}
		return total;
	}

	public int runPart1(final List<String> lines) {
		int total = 0;
		for (final String line : lines) {
			final List<String> sValues = Arrays.asList(line.split("\\s+"));
			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;
			for (final String value : sValues) {
				final int i = Integer.parseInt(value);
				if (i < min) {
					min = i;
				}
				if (i > max) {
					max = i;
				}
			}
			total += (max - min);
		}
		return total;
	}

	public static void main(final String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = System.nanoTime();
		System.out.println("Answer to part 1: " + new Day02().runPart1(lines));
		System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
		start = System.nanoTime();
		System.out.println("Answer to part 2: " + new Day02().runPart2(lines));
		System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
	}

}
