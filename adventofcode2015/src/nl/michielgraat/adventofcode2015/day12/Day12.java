package nl.michielgraat.adventofcode2015.day12;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day12 {

	private static final String FILENAME = "day12.txt";

	private JSON parseLine(String line) {
		JSON obj = new JSON();
		for (int i = 0; i < line.length(); i++) {
			char cur = line.charAt(i);
			if (Character.isDigit(cur) || cur == '-') {
				String number = String.valueOf(cur);
				while (i < line.length() - 1 && Character.isDigit(line.charAt(++i))) {
					number += line.charAt(i);
				}
				if (!number.equals("-")) {
					obj.numbers.add(Integer.valueOf(number));
				}
			} else if (Character.isLetter(cur)) {
				String string = String.valueOf(cur);
				while (Character.isLetter(line.charAt(++i))) {
					string += line.charAt(i);
				}
				obj.strings.add(string);
			} else if (cur == '[') {
				int arrayCount = 1;
				String array = "";
				while (arrayCount > 0) {
					char cur2 = line.charAt(++i);
					if (cur2 == ']') {
						arrayCount--;
					} else if (cur2 == '[') {
						arrayCount++;
					}
					if (arrayCount > 0) {
						array += cur2;
					}
				}

				obj.arrays.add(parseLine(array));
			} else if (cur == '{') {
				int objCount = 1;
				String object = "";
				while (objCount > 0) {
					char cur2 = line.charAt(++i);
					if (cur2 == '}') {
						objCount--;
					} else if (cur2 == '{') {
						objCount++;
					}
					if (objCount > 0) {
						object += cur2;
					}
				}
				obj.objects.add(parseLine(object));
			}
		}
		return obj;
	}

	private int getTotal(JSON json, boolean isObj) {
		boolean isRed = false;
		if (isObj) {
			for (String s : json.strings) {
				if (s.contains("red")) {
					isRed = true;
				}
			}
		}
		int total = 0;
		if (!isRed) {
			for (int i : json.numbers) {
				total += i;
			}
			for (JSON arr : json.arrays) {
				total += getTotal(arr, false);
			}
			for (JSON obj : json.objects) {
				total += getTotal(obj, true);
			}
		}
		return total;
	}
	
	private int runPart2(List<String> lines) {
		JSON json = parseLine(lines.get(0));
		return getTotal(json, false);
	}

	private int runPart1(List<String> lines) {
		int total = 0;
		for (String line : lines) {
			for (int i = 0; i < line.length();) {
				char cur = line.charAt(i);
				if (Character.isDigit(cur) || cur == '-') {
					String number = String.valueOf(cur);
					while (Character.isDigit(line.charAt(++i))) {
						number += line.charAt(i);
					}
					if (!number.equals("-")) {
						total += Long.valueOf(number);
					}
				} else {
					i++;
				}
			}
		}
		return total;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day12().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day12().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}

class JSON {
	List<Integer> numbers = new ArrayList<Integer>();
	List<String> strings = new ArrayList<String>();
	List<JSON> arrays = new ArrayList<JSON>();
	List<JSON> objects = new ArrayList<JSON>();
}

class JSONDoc extends JSON {
}

class JSONArr extends JSON {
}

class JSONObj extends JSON {
}