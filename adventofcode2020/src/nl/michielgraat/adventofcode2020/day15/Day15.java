package nl.michielgraat.adventofcode2020.day15;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 {
	
	public long runPart2(List<Long> numbers) {
		long turnNr = numbers.size() + 1;
		
		Map<Long, Long> nrToLast = new HashMap<Long, Long>();
		for (int i = 0; i < numbers.size(); i++) {
			nrToLast.put(numbers.get(i), i+1L);
		}
		
		long nextNr = 0;
		while (turnNr < 30000000) {
			if (nrToLast.containsKey(nextNr)) {
				long nrToAdd = turnNr - nrToLast.get(nextNr);
				nrToLast.put(nextNr, turnNr);
				nextNr = nrToAdd;
			} else {
				nrToLast.put(nextNr, turnNr);
				nextNr = 0;
			}
			turnNr++;
		}
		return nextNr;
	}


	public long runPart2initial(List<Long> numbers) {
		int turnNr = numbers.size() + 1;
		
		Map<Long, Occurences> nrToOccurences = new HashMap<Long, Occurences>();
		for (int i = 0; i < numbers.size(); i++) {
			nrToOccurences.put(numbers.get(i), new Occurences(i+1, -1));
		}
				
		while (turnNr <= 30000000) {
			long lastNr = numbers.get(numbers.size() - 1);
			Occurences oc = nrToOccurences.get(lastNr);
			int lastSpoken = (oc != null) ? oc.secondToLast : -1;
			if (lastSpoken >= 0) {
				long nrToAdd = turnNr - 1 - lastSpoken;
				numbers.add(nrToAdd);
				Occurences o = nrToOccurences.get(nrToAdd);
				if (o != null) {
					o = new Occurences(turnNr, nrToOccurences.get(nrToAdd).last);
				} else {
					o = new Occurences(turnNr, -1);
				}
				nrToOccurences.put(nrToAdd, o);
			} else {
				numbers.add(0L);
				Occurences o = nrToOccurences.get(0L);
				if (o != null) {
					o = new Occurences(turnNr, o.last);
				} else {
					o = new Occurences(turnNr, -1);
				}
				nrToOccurences.put(0L, o);
				
			}
			turnNr++;
		}
		return numbers.get(30000000-1);
	}

	public long runPart1(List<Long> numbers) {
		int turnNr = numbers.size() + 1;
		while (turnNr <= 2020) {
			long lastNr = numbers.get(numbers.size() - 1);
			int lastSpoken = -1;
			for (int i = numbers.size() - 2; i >= 0; i--) {
				if (numbers.get(i) == lastNr) {
					lastSpoken = i + 1;
					break;
				}
			}
			if (lastSpoken >= 0) {
				numbers.add((long) (turnNr - 1 - lastSpoken));
			} else {
				numbers.add(0L);
			}
			turnNr++;
		}
		return numbers.get(2019);
	}

	public static void main(String[] args) {
		List<Long> numberList = new ArrayList<Long>();
		numberList.add(9L);
		numberList.add(19L);
		numberList.add(1L);
		numberList.add(6L);
		numberList.add(0L);
		numberList.add(5L);
		numberList.add(4L);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day15().runPart1(numberList));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		numberList = new ArrayList<Long>();
		numberList.add(9L);
		numberList.add(19L);
		numberList.add(1L);
		numberList.add(6L);
		numberList.add(0L);
		numberList.add(5L);
		numberList.add(4L);
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day15().runPart2(numberList));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}

class Occurences {
	int last;
	int secondToLast;
	
	public Occurences(int last, int secondToLast) {
		this.last = last;
		this.secondToLast = secondToLast;
	}

	@Override
	public String toString() {
		return "Occurences [last=" + last + ", secondToLast=" + secondToLast + "]";
	}
	
}