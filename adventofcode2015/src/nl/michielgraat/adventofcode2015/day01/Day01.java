package nl.michielgraat.adventofcode2015.day01;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day01 {

	private static final String FILENAME = "day01.txt";
	
	public long runPart2(List<String> lines) {
		String line = lines.get(0);
		
		long floor = 0;
		
		for (int i=0; i<line.length(); i++) {
			if (line.charAt(i) == '(') {
				floor++;
			} else {
				floor--;
			}
			if (floor == -1) {
				return i+1;
			}
		}
		
		return floor;
	}
	
	public long runPart1(List<String> lines) {
		String line = lines.get(0);
		long floor = 0;
		
		for (int i=0; i<line.length(); i++) {
			if (line.charAt(i) == '(') {
				floor++;
			} else {
				floor--;
			}
		}
		
		return floor;
	}
	
	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day01().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day01().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}

}
