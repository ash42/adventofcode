package nl.michielgraat.adventofcode2017.day04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day04 {

	private static final String FILENAME = "day04.txt";

	public int runPart2(final List<String> lines) {
		int nrOfValid = 0;
		for (final String line : lines) {
			final List<String> words = Arrays.asList(line.split("\\s+"));
			final List<String> sortedWords = new ArrayList<String>();

			for (final String word : words) {
				final char charArray[] = word.toCharArray();
				Arrays.sort(charArray);
				sortedWords.add(new String(charArray));
			}
			boolean invalid = false;
			for (final String sortedWord : sortedWords) {
				if (sortedWords.indexOf(sortedWord) != sortedWords.lastIndexOf(sortedWord)) {
					invalid = true;
				}
			}
			if (!invalid) {
				nrOfValid++;
			}
		}

		return nrOfValid;
	}

	public int runPart1(final List<String> lines) {
		int nrOfValid = 0;
		for (final String line : lines) {
			final List<String> words = Arrays.asList(line.split("\\s+"));
			boolean invalid = false;
			for (final String word : words) {
				if (words.indexOf(word) != words.lastIndexOf(word)) {
					invalid = true;
					break;
				}
			}
			if (!invalid) {
				nrOfValid++;
			}
		}
		return nrOfValid;
	}

	public static void main(final String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = System.nanoTime();
		System.out.println("Answer to part 1: " + new Day04().runPart1(lines));
		System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
		start = System.nanoTime();
		System.out.println("Answer to part 2: " + new Day04().runPart2(lines));
		System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
	}

}
