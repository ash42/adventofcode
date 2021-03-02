package nl.michielgraat.adventofcode2017.day2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2017.util.FileReader;

public class Day2 {

	private static final String FILENAME = "day2.txt";
	
	public int runPart1() {
		FileReader reader = new FileReader(FILENAME);
		List<String> lines = reader.getStringList();
		int total = 0;
		for (String line : lines) {
			List<String> sValues = Arrays.asList(line.split("\\s+"));
			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;
			for (String value : sValues) {
				int i = Integer.valueOf(value);
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
	
	public int runPart2() {
		FileReader reader = new FileReader(FILENAME);
		List<String> lines = reader.getStringList();
		int total = 0;
		for (String line : lines) {
			List<String> sValues = Arrays.asList(line.split("\\s+"));
			List<Integer> values = sValues.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
			
			for (int i = 0; i < values.size(); i++) {
				int value1 = values.get(i);
				for (int j=i+1; j < values.size(); j++) {
					int value2 = values.get(j);
					if (value1 > value2) {
						if (value1 % value2 == 0) {
							total += value1/value2;
						}
					} else {
						if (value2 % value1 == 0) {
							total += value2/value1;
						}
					}
				}
			}
		}
		return total;
	}
	
	public static void main(String[] args) {
		System.out.println("Part 1: " + new Day2().runPart1());
		System.out.println("Part 2: " + new Day2().runPart2());
	}

}
