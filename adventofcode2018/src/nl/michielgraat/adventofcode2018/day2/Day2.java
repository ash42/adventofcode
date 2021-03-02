package nl.michielgraat.adventofcode2018.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 {

	private static final String FILENAME = "day2.txt";

	private int levenshteinDistance(CharSequence lhs, CharSequence rhs) {
		int len0 = lhs.length() + 1;
		int len1 = rhs.length() + 1;

		// the array of distances
		int[] cost = new int[len0];
		int[] newcost = new int[len0];

		// initial cost of skipping prefix in String s0
		for (int i = 0; i < len0; i++)
			cost[i] = i;

		// dynamically computing the array of distances

		// transformation cost for each letter in s1
		for (int j = 1; j < len1; j++) {
			// initial cost of skipping prefix in String s1
			newcost[0] = j;

			// transformation cost for each letter in s0
			for (int i = 1; i < len0; i++) {
				// matching current letters in both strings
				int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

				// computing cost for each transformation
				int cost_replace = cost[i - 1] + match;
				int cost_insert = cost[i] + 1;
				int cost_delete = newcost[i - 1] + 1;

				// keep minimum cost
				newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
			}

			// swap cost/newcost arrays
			int[] swap = cost;
			cost = newcost;
			newcost = swap;
		}

		// the distance is the cost for transforming all letters in both strings
		return cost[len0 - 1];
	}
	
	private String common (String a, String b) {
		char[] achar = a.toCharArray();
		char[] bchar = b.toCharArray();
		String result = new String();
		for (int i = 0; i < achar.length; i++) {
			if (achar[i] == bchar[i]) {
				result += achar[i];
			}
		}
		return result;
	}

	private int countNumberOfXChars(char[] chars, int x) {
		int total = 0;
		int counter = 0;
		char current = 0;

		for (char c : chars) {
			if (c != current) {
				if (counter == x) {
					total++;
				}
				counter = 0;
			}
			current = c;
			counter++;
		}
		if (counter == x) {
			total++;
		}

		return total;
	}

	public void runPart1() {
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {
			List<String> ids = stream.collect(Collectors.toList());
			int totalDoubles = 0;
			int totalTriples = 0;
			for (String id : ids) {
				char[] idArray = id.toCharArray();
				Arrays.sort(idArray);
				int numberOfDoubles = countNumberOfXChars(idArray, 2);
				int numberOfTriples = countNumberOfXChars(idArray, 3);
				if (numberOfDoubles > 0) {
					totalDoubles++;
				}
				if (numberOfTriples > 0) {
					totalTriples++;
				}
			}
			System.out.println("Checksum: " + (totalDoubles * totalTriples));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void runPart2() {
		try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {
			List<String> ids = stream.collect(Collectors.toList());
			
			for (String id : ids) {
				for (String id2: ids) {
					int distance = levenshteinDistance(id, id2);
					if (distance == 1) {
						System.out.println("Found: " + id + ", " + id2 + ", common: " + common(id, id2));
						return;
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) {
		new Day2().runPart1();
		new Day2().runPart2();
	}

}
