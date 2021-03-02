package nl.michielgraat.adventofcode2018.day5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day5 {
	
	private static final String FILENAME = "day5.txt";

	private int getIndexOfFirstReaction(String input) {
		String previous = "";
		for (int i=0; i < input.length(); i++) {
			String cur = String.valueOf(input.charAt(i));
			if (!cur.equals(previous) && cur.toLowerCase().equals(previous.toLowerCase())) {
				return i-1;
			}
			previous = cur;
		}
		return -1;
	}
	
	private String react(String input) {
		int indexOfFirstReaction = getIndexOfFirstReaction(input);
		if (indexOfFirstReaction >= 0) {
			String removedReaction = input.substring(0,indexOfFirstReaction);
			if (indexOfFirstReaction + 2 < input.length()) {
				removedReaction += input.substring(indexOfFirstReaction+2);
			}
			return react(removedReaction);
		}
		
		return input;
	}
	
	private int getLengthWithRemoved(char rem) {
		String result = "";
		Character lcRem = Character.toLowerCase(rem);
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(FILENAME)));
			int i;
			while ((i = br.read()) != - 1) {
				Character c = (char) i;
				Character lcCur = Character.toLowerCase(c);
				if (!lcRem.equals(lcCur)) {
					result += c;
					if (result.length() >= 2) {
						Character prev = result.charAt(result.length()-2);
						Character lcPrev = Character.toLowerCase(prev);
						if (!prev.equals(c) && lcPrev.equals(lcCur)) {
							result = result.substring(0, result.length()-2);
						} 
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return result.trim().length();
	}
	
	public String runPart2() {
		int min = Integer.MAX_VALUE;
		char minChar = 'a';
		for (char c = 'a'; c <= 'z'; c++) {
			int result = getLengthWithRemoved(c);
			if (result < min) {
				min = result;
				minChar = c;
			}
		}
		return "Removing '" + minChar + "' leads to the smallest polymer with length " + min;
	}
	
	public int runPart1() {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(FILENAME)));
			int i;
			while ((i = br.read()) != - 1) {
				Character c = (char) i;
				result += c;
				if (result.length() >= 2) {
					Character prev = result.charAt(result.length()-2);
					Character lcPrev = Character.toLowerCase(prev);
					Character lcCur = Character.toLowerCase(c);
					if (!prev.equals(c) && lcPrev.equals(lcCur)) {
						result = result.substring(0, result.length()-2);
					} 
				} 
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return result.trim().length();
	}
	
	public static void main(String[] args) {
		System.out.println("Answer to part1: " + new Day5().runPart1());
		System.out.println("Answer to part2: " + new Day5().runPart2());
	}

}
