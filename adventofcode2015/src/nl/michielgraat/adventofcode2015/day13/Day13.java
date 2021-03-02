package nl.michielgraat.adventofcode2015.day13;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day13 {

	private static final String FILENAME = "day13.txt";

	private Guest getGuest(String name, List<Guest> guests) {
		Guest guest = new Guest(name);
		if (guests.contains(guest)) {
			return guests.get(guests.indexOf(guest));
		}
		guests.add(guest);
		return guest;
	}

	private List<Guest> getGuests(List<String> lines) {
		List<Guest> guests = new ArrayList<Guest>();
		for (String line : lines) {
			String guest1 = line.substring(0, line.indexOf(' '));
			String guest2 = line.substring(line.indexOf("to") + 3, line.indexOf('.'));
			int happiness = -1;
			if (line.contains("lose")) {
				happiness = -1
						* Integer.valueOf(line.substring(line.indexOf("lose") + 5, line.indexOf("happiness") - 1));
			} else {
				happiness = Integer.valueOf(line.substring(line.indexOf("gain") + 5, line.indexOf("happiness") - 1));
			}
			Guest guest = getGuest(guest1, guests);
			guest.addHappiness(new Guest(guest2), happiness);
		}
		return guests;
	}
	
	private List<String> getUniqueGuests(List<String> lines) {
		List<String> names = new ArrayList<String>();

		for (String line : lines) {
			String guest1 = line.substring(0, line.indexOf(' '));
			if (!names.contains(guest1)) {
				names.add(guest1);
			}
		}
		return names;
	}

	private int runPart2(List<String> lines) {
		List<String> guests = getUniqueGuests(lines);
		for (String guest : guests) {
			lines.add("Michiel would gain 0 happiness units by sitting next to " + guest + ".");
			lines.add(guest + " would gain 0 happiness units by sitting next to Michiel.");
		}
		return runPart1(lines);
	}

	private int getHappiness(List<Guest> guests) {
		int total = 0;
		for (int i = 0; i < guests.size(); i++) {
			Guest current = guests.get(i);
			Guest next = (i==guests.size()-1) ? guests.get(0) : guests.get(i+1);
			total += current.happinessTo.get(next);
			total += next.happinessTo.get(current);
		}
		return total;
	}

	private void getPermutations(List<Guest> original, int element, List<List<Guest>> perms) {
		for (int i = element; i < original.size(); i++) {
			java.util.Collections.swap(original, i, element);
			getPermutations(new ArrayList<Guest>(original), element + 1, perms);
			java.util.Collections.swap(original, element, i);
		}
		if (element == original.size() - 1) {
			perms.add(original);
		}
	}

	private int runPart1(List<String> lines) {
		List<Guest> guests = getGuests(lines);
		List<List<Guest>> perms = new ArrayList<List<Guest>>();
		getPermutations(guests, 0, perms);

		int max = 0;
		
		for (List<Guest> perm : perms) {
			max = Math.max(max, getHappiness(perm));
		}

		return max;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day13().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day13().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}

class Guest {
	String name;
	Map<Guest, Integer> happinessTo = new HashMap<Guest, Integer>();

	Guest(String name) {
		this.name = name;
	}

	void addHappiness(Guest g, int happiness) {
		happinessTo.put(g, happiness);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Guest other = (Guest) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}