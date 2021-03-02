package nl.michielgraat.adventofcode2020.day01;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day1 {
	
	private static final String FILENAME="day01.txt";

	public int runPart2() {
		final List<Integer> integers = FileReader.getCompleteIntegerList(FILENAME);
		
		for (int i = 0; i < integers.size(); i++) {
			final int current = integers.get(i);
			final int remainder = 2020 - current;
			for (int j = i+1; j < integers.size(); j++) {
				final int second = integers.get(j);
				final int finalRemainder = remainder - second;
				if (integers.contains(finalRemainder)) {
					return current * second * finalRemainder;
				}
			}
		}
		
		return 0;
	}
	
	public int runPart1() {
		final List<Integer> integers = FileReader.getCompleteIntegerList(FILENAME);
		for (int i = 0; i < integers.size(); i++) {
			final int current = integers.get(i);
			final int remainder = 2020 - current;
			if (integers.contains(remainder)) {
				return current * remainder;
			}
		}
		return 0;
	}
	
	public static void main(String[] args) {
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day1().runPart1());
		System.out.println("Took: " +  (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day1().runPart2());
		System.out.println("Took: " +  (Calendar.getInstance().getTimeInMillis() - start) + " ms");
	}

}
