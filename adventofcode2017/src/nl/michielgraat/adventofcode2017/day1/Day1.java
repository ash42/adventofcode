package nl.michielgraat.adventofcode2017.day1;

import java.util.List;

import nl.michielgraat.adventofcode2017.util.FileReader;

public class Day1 {

	private static final String FILENAME = "day1.txt";

	public int runPart1() {
		int total = 0;
		FileReader reader = new FileReader(FILENAME);
		List<Integer> values = reader.getIntegerList();
		if (values.get(0) == values.get(values.size() - 1)) {
			total += values.get(0);
		}
		for (int i = 1; i < values.size(); i++) {
			if (values.get(i) == values.get(i - 1)) {
				total += values.get(i);
			}
		}
		return total;
	}
	
	public int runPart2() {
		FileReader reader = new FileReader(FILENAME);	
		List<Integer> values = reader.getIntegerList();
		int halfway = values.size() / 2;
		int total = 0;
		for (int i = 0; i < values.size(); i++) {
			int nextIndex = (i+halfway) % values.size();
			if (values.get(i) == values.get(nextIndex)) {
				total += values.get(i);
			}
		}
		return total;
	}

	public static void main(String[] args) {
		System.out.println("Part 1: " + new Day1().runPart1());
		System.out.println("Part 2: " + new Day1().runPart2());
	}

}
