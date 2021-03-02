package nl.michielgraat.adventofcode2017.day4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.michielgraat.adventofcode2017.util.FileReader;

public class Day4 {

	private static final String FILENAME = "day4.txt";
	
	public int runPart1() {
		int nrOfValid = 0;
		FileReader reader = new FileReader(FILENAME);
		List<String> lines = reader.getStringList();
		for (String line : lines) {
			List<String> words = Arrays.asList(line.split("\\s+"));
			boolean invalid = false;
			for (String word : words) {
				if (words.indexOf(word) != words.lastIndexOf(word)) {
					invalid = true;
					break;
				}
			}
			if (!invalid) {
				nrOfValid++;
			}
		}
		return nrOfValid;
	}
	
	public int runPart2() {
		int nrOfValid = 0;
		FileReader reader = new FileReader(FILENAME);
		List<String> lines = reader.getStringList();
		for (String line : lines) {
			List<String> words = Arrays.asList(line.split("\\s+"));
			List<String> sortedWords = new ArrayList<String>();
			
			for (String word : words) {
				char charArray[] = word.toCharArray();
				Arrays.sort(charArray);
				sortedWords.add(new String(charArray));
			}
			boolean invalid = false;
			for (String sortedWord : sortedWords) {
				if (sortedWords.indexOf(sortedWord) != sortedWords.lastIndexOf(sortedWord)) {
					invalid = true;
				}
			}
			if (!invalid) {
				nrOfValid++;
			}
		}
		
		return nrOfValid;
	}
	
	public static void main(String[] args) {
		System.out.println("Part1: " + new Day4().runPart1());
		System.out.println("Part2: " + new Day4().runPart2());
	}

}
