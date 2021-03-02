package nl.michielgraat.adventofcode2015.day11;

import java.util.Calendar;

public class Day11 {

	private boolean hasThreeIncreasing(String s) {
		for (int i = 0; i < s.length() - 2; i++) {
			int cur = s.charAt(i);
			int cur2 = s.charAt(i + 1);
			int cur3 = s.charAt(i + 2);
			int count = 0;
			if (cur >= 97 && cur <= 122) {
				if (cur == cur2 - 1 && cur == cur3 - 2) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean containsIllegalChar(String s) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == 'i' || c == 'o' || c == 'l') {
				return true;
			}
		}
		return false;
	}
	
	private int indexOfPair(String s) {
		for (int i = 0; i < s.length() - 1; i++) {
			char cur = s.charAt(i);
			char cur2 = s.charAt(i+1);
			if (cur == cur2) {
				return i;
			}
		}
		return -1;
	}
	
	private boolean hasTwoPairs(String s) {
		int pairIdx = indexOfPair(s);
		if (pairIdx >= 0) {
			if (pairIdx < s.length() - 2) {
				pairIdx = indexOfPair(s.substring(pairIdx+2));
				if (pairIdx >= 0) {
					return true;
				}
			}
		}
		
		return false;
	}

	private boolean isValid(String pw) {
		return hasThreeIncreasing(pw) && !containsIllegalChar(pw) && hasTwoPairs(pw);
	}
	
	private String nextPassword(String pw) {
		String result = "";
		int c1 = pw.charAt(0);
		int c2 = pw.charAt(1);
		int c3 = pw.charAt(2);
		int c4 = pw.charAt(3);
		int c5 = pw.charAt(4);
		int c6 = pw.charAt(5);
		int c7 = pw.charAt(6);
		int c8 = pw.charAt(7);
		c8++;
		if (c8 > 122) {
			c8 = 97;
			c7++;
		}
		if (c7 > 122) {
			c7 = 97;
			c6++;
		}
		if (c6 > 122) {
			c6 = 97;
			c5++;
		}
		if (c5 > 122) {
			c5 = 97;
			c4++;
		}
		if (c4 > 122) {
			c4 = 97;
			c3++;
		}
		if (c3 > 122) {
			c3 = 97;
			c2++;
		}
		if (c2 > 122) {
			c2 = 97;
			c1++;
		}
		if (c1 > 122) {
			c1 = 97;
		}
		return Character.toString((char) c1) +
				Character.toString((char) c2) +
				Character.toString((char) c3) +
				Character.toString((char) c4) +
				Character.toString((char) c5) +
				Character.toString((char) c6) +
				Character.toString((char) c7) +
				Character.toString((char) c8);
	}

	private String runPart2(String input) {
		input = nextPassword(input); 
		while (!isValid(input)) {
			input = nextPassword(input); 
		}
		return input;
	}

	private String runPart1(String input) {
		while (!isValid(input)) {
			input = nextPassword(input); 
		}
		return input;
	}

	public static void main(String[] args) {
		final String input = "hepxcrrq";
		long start = Calendar.getInstance().getTimeInMillis();
		final String result = new Day11().runPart1(input);
		System.out.println("Answer to part 1: " + result);
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day11().runPart2(result));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}