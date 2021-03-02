package nl.michielgraat.adventofcode2020.day18;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day18 {
	
	private static final String FILENAME = "day18.txt";
	
	private String evalAdditions(String expr) {
		while (expr.contains("+")) {
			int indexOf = expr.indexOf("+");
			int indexOfX = -1;
			for (int i = indexOf - 2;; i--) {
				if (i == 0) {
					indexOfX = 0;
					break;
				} else if (expr.charAt(i) == ' ') {
					indexOfX = i+1;
					break;
				}
			}
			int endIndexOfY = -1;
			for (int i = indexOf+2;; i++) {
				if (i == expr.length() || expr.charAt(i) == ' ') {
					endIndexOfY = i-1;
					break;
				}
			}
			long x = Long.valueOf(expr.substring(indexOfX, indexOf-1));
			long y = Long.valueOf(expr.substring(indexOf+2, endIndexOfY+1));
			long result = x+y;
			expr = expr.replace(expr.substring(indexOfX, endIndexOfY+1), String.valueOf(result));
		}
		return expr;
	}
		
	private long evaluate(String expr, boolean addsFirst) {
		if (addsFirst) {
			expr = evalAdditions(expr);
		}
		if (expr.indexOf(' ') == -1) {
			return Long.valueOf(expr);
		}
		long x = Long.valueOf(expr.substring(0, expr.indexOf(' ')));
		expr = expr.substring(expr.indexOf(' ') + 1);
		while (expr.indexOf(' ') > -1) {
			String op = expr.substring(0, expr.indexOf(' '));
			expr = expr.substring(expr.indexOf(' ') + 1);
			long y = -1;
			if (expr.indexOf(' ') > -1) {
				y = Long.valueOf(expr.substring(0, expr.indexOf(' ')));
			} else {
				y = Long.valueOf(expr);
			}
			if (op.equals("+")) {
				x = x+y;
			} else {
				x = x*y;
			}
			if (expr.indexOf(' ') > -1) {
				expr = expr.substring(expr.indexOf(' ') + 1);
			}
		}
		return x;
	}
	
	private String findInnerMostExpr(String line) {
		int indexOfLast = -1;
		for (int i = 0; i<line.length(); i++) {
			char c = line.charAt(i);
			if (c == '(') {
				indexOfLast = i;
			} else if (c == ')') {
				return line.substring(indexOfLast, i+1);
			}
		}
		return null;
	}
	
	private long calc(String line, boolean addFirst) {
		String innerMost = null;
		while ((innerMost = findInnerMostExpr(line)) != null) {
			line = line.replace(innerMost, String.valueOf(evaluate(innerMost.substring(1, innerMost.length()-1), addFirst)));
		}
		return evaluate(line, addFirst);
	}
	
	public long runPart2(List<String> lines) {
		long total = 0;
		for (String line : lines) {
			total += calc(line, true);
		}
		return total;
	}
	
	public long runPart1(List<String> lines) {
		long total = 0;
		for (String line : lines) {
			total += calc(line, false);
		}
		return total;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day18().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day18().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}