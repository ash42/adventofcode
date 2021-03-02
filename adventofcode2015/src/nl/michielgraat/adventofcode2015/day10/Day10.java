package nl.michielgraat.adventofcode2015.day10;

import java.util.Calendar;

public class Day10 {

	private String convert(String s) {
		StringBuilder result = new StringBuilder();
		
		for (int i=0; i<s.length(); ) {
			int cur = Character.getNumericValue(s.charAt(i));
			if (i == s.length() - 1) {
				return result.append(1).append(cur).toString();
			} 
			int count = 1;
			int nextIndex = -1;
			for (int j = i + 1; j < s.length(); j++) {
				int next = Character.getNumericValue(s.charAt(j));
				if (next == cur) {
					count++;
					if (j == s.length()-1) {
						nextIndex = s.length();
						break;
					}
				} else {
					nextIndex = j;
					break;
				}
			}
			result.append(count).append(cur);
			i = nextIndex;
		}
		return result.toString();
	}
	
	private int runPart2(String input) {
		for (int i = 1; i <= 50; i++) {
			input = convert(input);
		}
		return input.length();
	}
	
	private int runPart1(String input) {
		for (int i = 1; i <= 40; i++) {
			input = convert(input);
		}
		return input.length();
	}

	public static void main(String[] args) {
		final String input = "1113222113";
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day10().runPart1(input));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day10().runPart2(input));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}