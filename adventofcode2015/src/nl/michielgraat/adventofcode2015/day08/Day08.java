package nl.michielgraat.adventofcode2015.day08;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day08 {

	private static final String FILENAME = "day08.txt";

	
	private int escapedLength(String s) {
		int total = 0;
		int i = 0;
		while (i < s.length()) {
			char cur = s.charAt(i);
			if (cur == '"' || cur == '\\') {
				total += 2;
			} else {
				total++;
			}
			i++;
		}
		return total + 2;
	}

	private int runPart2(List<String> lines) {
		int encodedLength = 0;
		int originalLength = 0;
		for (String line : lines) {
			encodedLength += escapedLength(line);
			originalLength += line.length();
		}
		return encodedLength - originalLength;
	}

	private int unescapedLength(String s) {
		int total = 0;
		int i = 0;
		while (i < s.length()) {
			char cur = s.charAt(i);
			if (cur == '\\') {
				char nextChar = s.charAt(i + 1);
				if (nextChar == '"' || nextChar == '\\') {
					i += 2;
				} else if (nextChar == 'x') {
					i += 4;
				}
			} else {
				i++;
			}
			total++;
		}
		return total;
	}

	private int runPart1(List<String> lines) {
		int totalCode = 0;
		int totalMemory = 0;
		for (String line : lines) {
			int codeLength = line.length();
			int memoryLength = unescapedLength(line.substring(1, line.length() - 1));
			totalCode += codeLength;
			totalMemory += memoryLength;
		}
		return totalCode - totalMemory;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day08().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day08().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}