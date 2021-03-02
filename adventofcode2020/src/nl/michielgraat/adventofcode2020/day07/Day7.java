package nl.michielgraat.adventofcode2020.day07;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day7 {
	
	private static final String FILENAME = "day07.txt";
	
	private Bag parseRule(String line) {
		String color = line.substring(0, line.indexOf("bags")).trim();
		Bag bag = new Bag(color);
		String bagsLine = line.substring(line.indexOf("contain ")+8).trim();
		if (bagsLine.equals("no other bags.")) {
			return bag;
		}
		String[] bags = bagsLine.split(",");
		for (String b : bags) {
			b = b.trim();
			int number = Integer.valueOf(b.substring(0, b.indexOf(" ")));
			b = b.substring(b.indexOf(" ")+1);
			String c = b.substring(0, b.indexOf(" bag"));
			bag.addBag(new Bag(c), number);
		}
		return bag;
	}
	
	private List<Bag> parseRules(List<String> lines) {
		List<Bag> rules = new ArrayList<Bag>();
		lines.forEach(l -> rules.add(parseRule(l)));
		return rules;
	}
	
	private Bag findBag(String color, List<Bag> bags) {
		for (Bag bag : bags) {
			if (bag.color.equals(color)) {
				return bag;
			}
		}
		return null;
	}
	
	private boolean containsShinyGold(Bag b, List<Bag> rules) {
		if (b.color.equals("shiny gold")) {
			return true;
		} else if (b.subbags.isEmpty()) {
			return false;
		} else {
			for (Bag subbag : b.subbags.keySet()) {
				if (containsShinyGold(findBag(subbag.color, rules), rules)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private int numberOfSubBags(Bag b, List<Bag> bags) {
		if (b.subbags.isEmpty()) {
			return 0;
		} else {
			int total = 0;
			for (Bag subbag : b.subbags.keySet()) {
				total += b.subbags.get(subbag) + b.subbags.get(subbag)*numberOfSubBags(findBag(subbag.color, bags), bags);
			}
			
			return total;
		}
	}
	
	public int runPart2(List<String> lines) {
		List<Bag> bags = parseRules(lines);
		return numberOfSubBags(findBag("shiny gold", bags), bags);
	}
	
	public int runPart1(List<String> lines) {
		List<Bag> rules = parseRules(lines);
		int total = -1; //Start with -1 because we don't count the shiny gold bag itself
		for (Bag rule : rules) {
			if (containsShinyGold(rule, rules)) {
				total++;
			}
		}
		return total;
	}

	public static void main(String[] args) {
		List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day7().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day7().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
	}

}

class Bag {
	String color;
	Map<Bag, Integer> subbags = new HashMap<Bag, Integer>();
	
	Bag(String color) {
		this.color = color;
	}
	
	void addBag(Bag b, int number) {
		subbags.put(b, number);
	}
}