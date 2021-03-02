package nl.michielgraat.adventofcode2015.day05;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day05 {

	private static final String FILENAME = "day05.txt";

	private boolean containsRepeatingLetters(String s) {

		for (int i = 0; i < s.length() - 2; i++) {
			char cur = s.charAt(i);
			char nxt = s.charAt(i + 2);
			if (cur == nxt) {
				return true;
			}
		}
		return false;
	}

	private boolean containsNonOverlappingPair(String s) {
		List<String> pairs = new ArrayList<String>();
		for (int i = 0; i < s.length()-1; i++) {
			String pair = s.substring(i, i+2);
			pairs.add(pair);
		}
		for (String pair : pairs) {
			int index = s.indexOf(pair);
			for (int i = index+2; i < s.length()-1; i++) {
				String pair2 = s.substring(i, i+2);
				if (pair.equals(pair2)) {
					return true;
				}
			}
			
		}
		
		
		return false;
	}
	

	private boolean isNice2(String s) {
		return containsNonOverlappingPair(s) && containsRepeatingLetters(s);
	}

	private boolean isNice(String s) {
		List<Character> vowelsFound = new ArrayList<Character>();
		char prev = ' ';
		boolean doubleFound = false;

		for (int i = 0; i < s.length(); i++) {
			char cur = s.charAt(i);
			if (prev == 'a' && cur == 'b') {
				return false;
			}
			if (prev == 'c' && cur == 'd') {
				return false;
			}
			if (prev == 'p' && cur == 'q') {
				return false;
			}
			if (prev == 'x' && cur == 'y') {
				return false;
			}
			if (!doubleFound && prev == cur) {
				doubleFound = true;
			}
			if (cur == 'a' || cur == 'e' || cur == 'i' || cur == 'o' || cur == 'u') {
				vowelsFound.add(cur);
			}
			prev = cur;
		}

		return doubleFound && vowelsFound.size() >= 3;
	}

	private long runPart2(List<String> lines) {
		return lines.stream().filter(l -> isNice2(l)).count();
	}

	private long runPart1(List<String> lines) {
		return lines.stream().filter(l -> isNice(l)).count();
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day05().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day05().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}

}
