package nl.michielgraat.adventofcode2017.day6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {
	
	private List<Integer> getInput() {
		//return Stream.of(0,2,7,0).collect(Collectors.toList());
		return Stream.of(2,8,8,5,4,2,3,1,5,5,1,2,15,13,5,14).collect(Collectors.toList());
	}
	
	private boolean hasKnownConfig(List<Integer> currentConfiguration, List<List<Integer>> configurations) {
		for (List<Integer> config : configurations) {
			if (currentConfiguration.equals(config)) {
				return true;
			}
		}
		return false;
	}
	
	private int getNextIndex(int index, int size) {
		if (index+1 < (size)) {
			return index + 1;
		} else {
			return 0;
		}
	}
	
	private List<Integer> redistributedList(List<Integer> old) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i : old) {
			result.add(i);
		}
		int max = Collections.max(result);
		int nrToRedistribute = max;
		int	nextIndex = result.indexOf(max);
		result.set(nextIndex, 0);
		while (nrToRedistribute > 0) {
			nextIndex = getNextIndex(nextIndex, result.size());
			result.set(nextIndex, result.get(nextIndex) + 1);
			nrToRedistribute--;
		}
		return result;
	}

	public int runPart1() {
		int nrOfSteps = 0;
		List<Integer> currentConfiguration = getInput();
		List<List<Integer>> configurations = new ArrayList<List<Integer>>();
		while (!hasKnownConfig(currentConfiguration, configurations)) {
			configurations.add(currentConfiguration);
			currentConfiguration = redistributedList(currentConfiguration);
			nrOfSteps++;
		}
		return nrOfSteps;
	}
	
	public int runPart2() {
		List<Integer> currentConfiguration = getInput();
		List<List<Integer>> configurations = new ArrayList<List<Integer>>();
		while (!hasKnownConfig(currentConfiguration, configurations)) {
			configurations.add(currentConfiguration);
			currentConfiguration = redistributedList(currentConfiguration);
		}
		int nrOfCycles = 0;
		List<Integer> newConfig = new ArrayList<Integer>();
		while (!currentConfiguration.equals(newConfig)) {
			if (nrOfCycles == 0) {
				for (int i : currentConfiguration) {
					newConfig.add(i);
				}
			}
			newConfig = redistributedList(newConfig);
			nrOfCycles++;
		}
		return nrOfCycles;
	}
	
	public static void main(String[] args) {
		System.out.println("Part 1: " + new Day6().runPart1());
		System.out.println("Part 2: " + new Day6().runPart2());
	}
}
