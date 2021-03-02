package nl.michielgraat.adventofcode2018.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1 {
	
	private static final String FILENAME = "day1.txt";
	
	public void runPart1() {
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {
			List<Integer> values = stream.map(Integer::valueOf).collect(Collectors.toList());
			System.out.println("Resulting frequency: " + values.stream().reduce(0, Integer::sum));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void runPart2() {
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {
			List<Integer> values = stream.map(Integer::valueOf).collect(Collectors.toList());
			List<Integer> reachedFrequencies = new ArrayList<Integer>();
			
			boolean doubleFreqReached = false;
			int currentFrequency = 0;
			int nrTimesLooped = 0;
			while (!doubleFreqReached) {
				nrTimesLooped++;
				for (int value : values) {
					reachedFrequencies.add(currentFrequency);
					currentFrequency += value;
					doubleFreqReached = reachedFrequencies.contains(currentFrequency);
					if (doubleFreqReached) break;
				}
			}
			System.out.println("First frequency reached twice: " + currentFrequency + ", in loop " + nrTimesLooped + 
					" of value list, total number of unique frequencies found: " + reachedFrequencies.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main (String... args) {
		new Day1().runPart1();
		new Day1().runPart2();
	}
}
