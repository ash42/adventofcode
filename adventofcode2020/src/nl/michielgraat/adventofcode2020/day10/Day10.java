package nl.michielgraat.adventofcode2020.day10;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day10 {
	
	private static final String FILENAME = "day10.txt";

	private List<Integer> getAdapters() {
		List<Integer> adapters = new ArrayList<Integer>();
		adapters.add(0);
		adapters.addAll(FileReader.getCompleteIntegerList(FILENAME));
		Collections.sort(adapters);
		adapters.add(adapters.get(adapters.size()-1) + 3);
		return adapters;
	}
	
	private long findNr(List<Integer> adapters) {
		long[] paths = new long[adapters.size()];
		paths[0] = 1;
		for (int i=1; i<adapters.size(); i++) {
			if (adapters.get(i) - adapters.get(i-1) <= 3) {
				paths[i] += paths[i-1];
			}
			if (i > 1 && adapters.get(i) - adapters.get(i-2) <= 3) {
				paths[i] += paths[i-2];
			}
			if (i > 2 && adapters.get(i) - adapters.get(i-3) <= 3) {
				paths[i] += paths[i-3];
			}
			
		}
		return paths[adapters.size() - 1];
	}
	
	public long runPart2() {
		return findNr(getAdapters());
	}
	
	public int runPart1() {
		List<Integer> adapters = getAdapters();
		int nrOf1s = 0;
		int nrOf3s = 0;
		for (int i=1; i<adapters.size(); i++) {
			int diff = adapters.get(i) - adapters.get(i-1);
			if (diff == 1) nrOf1s++;
			if (diff == 3) nrOf3s++;
		}
		return nrOf1s * nrOf3s;
	}
	
	public static void main(String[] args) {
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day10().runPart1());
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day10().runPart2());
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}

}
