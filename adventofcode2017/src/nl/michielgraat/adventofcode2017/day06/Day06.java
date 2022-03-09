package nl.michielgraat.adventofcode2017.day06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day06 {

	private List<Integer> getInput() {
		return Stream.of(2, 8, 8, 5, 4, 2, 3, 1, 5, 5, 1, 2, 15, 13, 5, 14).collect(Collectors.toList());
	}

	private boolean hasKnownConfig(final List<Integer> currentConfiguration, final List<List<Integer>> configurations) {
		for (final List<Integer> config : configurations) {
			if (currentConfiguration.equals(config)) {
				return true;
			}
		}
		return false;
	}

	private int getNextIndex(final int index, final int size) {
		if (index + 1 < (size)) {
			return index + 1;
		} else {
			return 0;
		}
	}

	private List<Integer> redistributedList(final List<Integer> old) {
		final List<Integer> result = new ArrayList<Integer>();
		for (final int i : old) {
			result.add(i);
		}
		final int max = Collections.max(result);
		int nrToRedistribute = max;
		int nextIndex = result.indexOf(max);
		result.set(nextIndex, 0);
		while (nrToRedistribute > 0) {
			nextIndex = getNextIndex(nextIndex, result.size());
			result.set(nextIndex, result.get(nextIndex) + 1);
			nrToRedistribute--;
		}
		return result;
	}

	public int runPart2() {
		List<Integer> currentConfiguration = getInput();
		final List<List<Integer>> configurations = new ArrayList<List<Integer>>();
		while (!hasKnownConfig(currentConfiguration, configurations)) {
			configurations.add(currentConfiguration);
			currentConfiguration = redistributedList(currentConfiguration);
		}
		int nrOfCycles = 0;
		List<Integer> newConfig = new ArrayList<Integer>();
		while (!currentConfiguration.equals(newConfig)) {
			if (nrOfCycles == 0) {
				for (final int i : currentConfiguration) {
					newConfig.add(i);
				}
			}
			newConfig = redistributedList(newConfig);
			nrOfCycles++;
		}
		return nrOfCycles;
	}

	public int runPart1() {
		int nrOfSteps = 0;
		List<Integer> currentConfiguration = getInput();
		final List<List<Integer>> configurations = new ArrayList<List<Integer>>();
		while (!hasKnownConfig(currentConfiguration, configurations)) {
			configurations.add(currentConfiguration);
			currentConfiguration = redistributedList(currentConfiguration);
			nrOfSteps++;
		}
		return nrOfSteps;
	}

	public static void main(final String[] args) {
		long start = System.nanoTime();
		System.out.println("Answer to part 1: " + new Day06().runPart1());
		System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
		start = System.nanoTime();
		System.out.println("Answer to part 2: " + new Day06().runPart2());
		System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
	}
}
