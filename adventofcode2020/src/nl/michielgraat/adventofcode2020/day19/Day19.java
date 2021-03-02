package nl.michielgraat.adventofcode2020.day19;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day19 {

	private static final String FILENAME = "day19.txt";
	private static final String FILENAME2 = "day192.txt";

	private Map<Integer, String> getRules(List<String> lines) {
		Map<Integer, String> rules = new TreeMap<Integer, String>();
		for (String line : lines) {
			if (line.isBlank()) {
				return rules;
			} else {
				rules.put(Integer.valueOf(line.substring(0, line.indexOf(':'))), line.substring(line.indexOf(':') + 2));
			}
		}
		return rules;
	}

	private List<String> getMessages(List<String> lines) {
		List<String> result = new ArrayList<String>();
		boolean msgStarted = false;
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (!msgStarted) {
				if (line.isBlank()) {
					msgStarted = true;
				}
			} else {
				result.add(line);
			}
		}
		return result;
	}

	private String getRegex2(int rulenr, Map<Integer, String> rules) {
		
		if (rulenr == 8) {
			return "(" + getRegex2(42, rules) + "+)";
		} else if (rulenr == 11) {
			return handleRule11(rules);
		}

		String rule = rules.get(rulenr);
		if (rule.contains("\"")) {
			return rule.replaceAll("\"", "");
		}
		
		String s = new String("(");
		String[] subrules = rule.split("\\s+");
		for (String subrule : subrules) {
			if (subrule.equals("|")) {
				s += "|";
			} else {
				s += getRegex2(Integer.valueOf(subrule), rules);
			}
		}
		s += ")";
		return s;
	}

	private String handleRule11(Map<Integer, String> rules) {
		String rule31 = getRegex(31, rules);
		String rule42 = getRegex(42, rules);
		String s = "(";
		for (int i=1; i<5; i++) {
			if (i > 1) {
				s += "|";
			} 
			s += "(";
			for (int j=0; j<i; j++) {
				s += rule42;
			}
			for (int j=0; j<i; j++) {
				s += rule31;
			}
			s += ")";
		}
		s += ")";
		return s;
	}

	private String getRegex(int rulenr, Map<Integer, String> rules) {
		String rule = rules.get(rulenr);
		if (rule.contains("\"")) {
			return rule.replaceAll("\"", "");
		}

		String s = new String("(");
		String[] subrules = rule.split("\\s+");
		for (String subrule : subrules) {
			if (subrule.equals("|")) {
				s += "|";
			} else {
				s += getRegex(Integer.valueOf(subrule), rules);
			}
		}
		s += ")";
		return s;
	}

	public long runPart2(List<String> lines) {
		Map<Integer, String> rules = getRules(lines);
		List<String> messages = getMessages(lines);

		String regex = "^" + getRegex2(0, rules) + "$";

		long total = 0;
		for (String msg : messages) {
			if (msg.matches(regex)) {
				total++;
			}
		}

		return total;
	}

	public long runPart1(List<String> lines) {
		Map<Integer, String> rules = getRules(lines);
		List<String> messages = getMessages(lines);

		String regex = "^" + getRegex(0, rules) + "$";

		long total = 0;
		for (String msg : messages) {
			if (msg.matches(regex)) {
				total++;
			}
		}

		return total;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		final List<String> lines2 = FileReader.getStringList(FILENAME2);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day19().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day19().runPart2(lines2));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}

}