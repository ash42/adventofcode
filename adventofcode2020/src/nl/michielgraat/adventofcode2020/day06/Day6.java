package nl.michielgraat.adventofcode2020.day06;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day6 {

	private static final String FILENAME = "day06.txt";

	public int runPart2() {
		List<String> lines = FileReader.getStringList(FILENAME);
		int total = 0;
		int nrOfLines = 0;
		List<Character> answers = new ArrayList<Character>();

		for (String line : lines) {
			if (line.isBlank()) {
				List<Character> unique = answers.stream().distinct().collect(Collectors.toList());
				for (Character c : unique) {
					if (nrOfLines == (int) answers.stream().filter(a -> a == c).count())
						total++;
				}
				nrOfLines = 0;
				answers = new ArrayList<Character>();
			} else {
				nrOfLines++;
				answers.addAll(line.chars().mapToObj(i -> (char) i).collect(Collectors.toSet()));
			}
		}
		List<Character> unique = answers.stream().distinct().collect(Collectors.toList());
		for (Character c : unique) {
			if (nrOfLines == (int) answers.stream().filter(a -> a == c).count())
				total++;
		}
		return total;
	}

	public int runPart1() {
		List<String> lines = FileReader.getStringList(FILENAME);
		Set<Character> answers = new HashSet<Character>();
		int total = 0;
		for (String line : lines) {
			if (line.isBlank()) {
				total += answers.size();
				answers = new HashSet<Character>();
			} else {
				answers.addAll(line.chars().mapToObj(i -> (char) i).collect(Collectors.toSet()));
			}
		}
		total += answers.size();
		return total;
	}

	public static void main(String[] args) {
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day6().runPart1());
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day6().runPart2());
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
	}
}
