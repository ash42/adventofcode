package nl.michielgraat.adventofcode2020.day16;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day16 {

	private static final String FILENAME = "day16.txt";

	private List<String> getNearbyTickets(List<String> lines) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (line.isBlank()) {
				return lines.subList(i + 5, lines.size());
			}
		}
		return result;
	}

	private String getOwnTicket(List<String> lines) {
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (line.startsWith("your ticket:")) {
				return lines.get(i + 1);
			}
		}
		return null;
	}

	private List<String> getRules(List<String> lines) {
		List<String> result = new ArrayList<String>();
		for (String line : lines) {
			if (line.isBlank()) {
				break;
			} else {
				result.add(line);
			}
		}
		return result;
	}

	private Set<Integer> parseRange(String range) {
		int first = Integer.parseInt(range.substring(0, range.indexOf('-')));
		int second = Integer.parseInt(range.substring(range.indexOf('-') + 1));
		Set<Integer> nrs = new TreeSet<Integer>();
		for (int i = first; i <= second; i++) {
			nrs.add(i);
		}
		return nrs;
	}

	private Set<Integer> getValidValues(List<String> rules) {
		Set<Integer> valid = new TreeSet<Integer>();
		for (String rule : rules) {
			valid.addAll(parseRange(rule.substring(rule.indexOf(':') + 2, rule.indexOf(" or "))));
			valid.addAll(parseRange(rule.substring(rule.indexOf(" or ") + 4)));
		}
		return valid;
	}

	private List<String> getValidTickets(List<String> tickets, Set<Integer> validNrs) {
		List<String> validTickets = new ArrayList<String>();
		for (String ticket : tickets) {
			String[] sValues = ticket.split(",");
			boolean valid = true;
			for (String sValue : sValues) {
				int val = Integer.parseInt(sValue);
				if (!validNrs.contains(val)) {
					valid = false;
					break;
				}
			}
			if (valid) {
				validTickets.add(ticket);
			}
		}
		return validTickets;
	}

	private List<Integer> getValues(String ticket) {
		List<Integer> values = new ArrayList<Integer>();
		String[] sValues = ticket.split(",");
		for (String sValue : sValues) {
			values.add(Integer.parseInt(sValue));
		}
		return values;
	}

	private Map<Integer, UnknownField> getPositionToField(List<String> validTickets) {
		Map<Integer, UnknownField> positionToField = new HashMap<Integer, UnknownField>();
		for (String ticket : validTickets) {
			List<Integer> fields = getValues(ticket);
			for (int i = 0; i < fields.size(); i++) {
				UnknownField current = positionToField.get(i);
				int value = fields.get(i);
				if (current == null) {
					Set<Integer> values = new TreeSet<Integer>();
					values.add(value);
					current = new UnknownField(values);
				} else {
					current.getRange().add(value);
				}
				positionToField.put(i, current);
			}

		}
		return positionToField;
	}

	private List<Field> getFields(List<String> rules) {
		List<Field> fields = new ArrayList<Field>();
		for (String rule : rules) {
			String name = rule.substring(0, rule.indexOf(":"));
			Set<Integer> range = new TreeSet<Integer>();
			range.addAll(parseRange(rule.substring(rule.indexOf(':') + 2, rule.indexOf(" or "))));
			range.addAll(parseRange(rule.substring(rule.indexOf(" or ") + 4)));
			fields.add(new Field(name, range));
		}
		return fields;
	}

	private Map<Integer, List<Field>> getCandidates(Map<Integer, UnknownField> positionToField, List<Field> fields) {
		Map<Integer, List<Field>> candidates = new HashMap<Integer, List<Field>>();
		for (Integer position : positionToField.keySet()) {
			UnknownField u = positionToField.get(position);
			Set<Integer> values = u.getRange();
			for (Field field : fields) {
				if (field.getValues().containsAll(values)) {
					List<Field> cFields = candidates.get(position);
					if (cFields == null) {
						cFields = new ArrayList<Field>();
						cFields.add(field);
						candidates.put(position, cFields);
					} else {
						candidates.get(position).add(field);
					}
				}
			}
		}
		return candidates;
	}

	private boolean solutionFound(Map<Integer, List<Field>> candidates) {
		for (List<Field> fields : candidates.values()) {
			if (fields.size() > 1) {
				return false;
			}
		}
		return true;
	}

	private List<String> getNamesToRemove(Map<Integer, List<Field>> candidates) {
		List<String> result = new ArrayList<String>();
		for (List<Field> fields : candidates.values()) {
			if (fields.size() == 1) {
				result.add(fields.get(0).getName());
			}
		}
		return result;
	}

	private void mapCorrectFields(Map<Integer, List<Field>> candidates) {
		while (!solutionFound(candidates)) {
			List<String> namesToRemove = getNamesToRemove(candidates);
			for (int position : candidates.keySet()) {
				List<Field> fields = candidates.get(position);
				if (fields.size() == 1) {
					candidates.put(position, fields);
				} else {
					List<Field> newFields = new ArrayList<Field>();
					for (Field field : fields) {
						if (!namesToRemove.contains(field.getName())) {
							newFields.add(field);
						}
					}
					candidates.put(position, newFields);
				}
			}
		}
	}

	private List<Integer> getPositionOfDepartureFields(Map<Integer, List<Field>> candidates) {
		List<Integer> result = new ArrayList<Integer>();
		for (int position : candidates.keySet()) {
			List<Field> fields = candidates.get(position);
			if (fields.get(0).getName().startsWith("departure")) {
				result.add(position);
			}
		}
		return result;
	}

	public long runPart2(List<String> lines) {
		List<String> rules = getRules(lines);
		List<String> validTickets = getValidTickets(getNearbyTickets(lines), getValidValues(rules));
		Map<Integer, UnknownField> positionToField = getPositionToField(validTickets);
		List<Field> fields = getFields(rules);
		Map<Integer, List<Field>> candidates = getCandidates(positionToField, fields);
		mapCorrectFields(candidates);
		List<Integer> positions = getPositionOfDepartureFields(candidates);
		String[] ticketValues = getOwnTicket(lines).split(",");
		long total = 1;
		for (int position : positions) {
			total *= Long.valueOf(ticketValues[position]);
		}
		return total;
	}

	public int runPart1(List<String> lines) {
		Set<Integer> valid = getValidValues(getRules(lines));
		List<String> tickets = getNearbyTickets(lines);
		int errorRate = 0;
		for (String ticket : tickets) {
			String[] sValues = ticket.split(",");
			for (String sValue : sValues) {
				int val = Integer.parseInt(sValue);
				if (!valid.contains(val)) {
					errorRate += val;
				}
			}
		}
		return errorRate;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day16().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day16().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
	}
}

class Field {
	String name;
	Set<Integer> values;

	Field(String name, Set<Integer> values) {
		super();
		this.name = name;
		this.values = values;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	Set<Integer> getValues() {
		return values;
	}

	void setValues(Set<Integer> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "Field [name=" + name + ", values=" + values + "]";
	}

}

class UnknownField {
	Set<Integer> range;

	UnknownField(Set<Integer> range) {
		super();
		this.range = range;
	}

	Set<Integer> getRange() {
		return range;
	}

	void setRange(Set<Integer> range) {
		this.range = range;
	}

	@Override
	public String toString() {
		return "UnknownField [range=" + range + "]";
	}

}