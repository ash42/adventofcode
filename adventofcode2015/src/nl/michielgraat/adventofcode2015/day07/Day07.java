package nl.michielgraat.adventofcode2015.day07;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day07 {

	private static final String FILENAME = "day07.txt";

	private Map<String, Integer> cache = new HashMap<String, Integer>();
	
	private String getGate(String s) {
		String result = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isUpperCase(c)) {
				result += c;
			}
		}
		if (result.length() > 0) {
			return result;
		}
		return null;
	}

	private int runPart2(int newval, List<String> lines) {
		for (int i=0; i<lines.size(); i++) {
			if (lines.get(i).endsWith("-> b")) {
				lines.remove(i);
				break;
			}
		}
		lines.add(new String(newval + " -> b"));
		cache = new HashMap<String, Integer>();
		return calculateValueOf("a", lines);
	}

	private boolean isNumeric(String s) {
		if (s == null) {
			return false;
		}
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private String findLine(String name, List<String> lines) {
		for (String line : lines) {
			if (line.endsWith(" " + name)) {
				return line;
			}
		}
		return null;
	}

	private int not(String line, List<String> lines) {
		String w1 = line.substring(line.indexOf("NOT") + 4, line.indexOf(" ->"));
		int result = ~calculateValueOf(w1, lines);
		if (result < 0) {
			result = 65536 + result;
		}
		cache.put(w1, result);
		return result;
	}

	private int rshift(String line, List<String> lines) {
		String w1 = line.substring(0, line.indexOf(" "));
		String w2 = line.substring(line.indexOf("RSHIFT") + 7, line.indexOf(" ->"));
		int i1 = calculateValueOf(w1, lines);
		int i2 = calculateValueOf(w2, lines);
		cache.put(w1, i1);
		cache.put(w2, i2);
		return (i1 >>> i2) % 65536;
	}

	private int lshift(String line, List<String> lines) {
		String w1 = line.substring(0, line.indexOf(" "));
		String w2 = line.substring(line.indexOf("LSHIFT") + 7, line.indexOf(" ->"));
		int i1 = calculateValueOf(w1, lines);
		int i2 = calculateValueOf(w2, lines);
		cache.put(w1, i1);
		cache.put(w2, i2);
		return (i1 << i2) % 65336;
	}

	private int or(String line, List<String> lines) {
		String w1 = line.substring(0, line.indexOf(" "));
		String w2 = line.substring(line.indexOf("OR") + 3, line.indexOf(" ->"));
		int i1 = calculateValueOf(w1, lines);
		int i2 = calculateValueOf(w2, lines);
		cache.put(w1, i1);
		cache.put(w2, i2);
		return (i1 | i2)  % 65536;
	}

	private int and(String line, List<String> lines) {
		String w1 = line.substring(0, line.indexOf(" "));
		String w2 = line.substring(line.indexOf("AND") + 4, line.indexOf(" ->"));
		int i1 = calculateValueOf(w1, lines);
		int i2 = calculateValueOf(w2, lines);
		cache.put(w1, i1);
		cache.put(w2, i2);
		return (i1 & i2) % 65536;
	}

	private int assign(String line, List<String> lines) {
		String w1 = line.substring(0, line.indexOf(" "));
		int result = calculateValueOf(w1, lines);
		cache.put(w1, result);
		return result;
	}

	private int calculateValueOf(String name, List<String> lines) {
		if (cache.containsKey(name)) {
			return cache.get(name);
		} else if (isNumeric(name)) {
			return Integer.valueOf(name);
		} else {
			String line = findLine(name, lines);
			String gate = getGate(line);
			if (gate == null)
				return assign(line, lines);
			if (gate.equals("AND"))
				return and(line, lines);
			if (gate.equals("OR"))
				return or(line, lines);
			if (gate.equals("LSHIFT"))
				return lshift(line, lines);
			if (gate.equals("RSHIFT"))
				return rshift(line, lines);
			if (gate.equals("NOT"))
				return not(line, lines);
		}
		return 0;
	}

	private int runPart1(List<String> lines) {
		return calculateValueOf("a", lines);
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		int result = new Day07().runPart1(lines);
		System.out.println("Answer to part 1: " + result);
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day07().runPart2(result, lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}