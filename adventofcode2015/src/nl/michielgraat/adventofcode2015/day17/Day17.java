package nl.michielgraat.adventofcode2015.day17;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day17 {

	private static final String FILENAME = "day17.txt";
	
	private int nr = 0;
	

	private int runPart2(List<Integer> nrs) {
		int target = 150;
		Map<Integer, Integer> sizeToTimes = new TreeMap<Integer, Integer>();
		for (int i = 0; i < nrs.size(); i++) {
			sum(i, nrs, new ArrayList<Integer>(), 0, target, sizeToTimes);
		}
		Set<Integer> sizes = sizeToTimes.keySet();
		int size = sizes.iterator().next();
		return sizeToTimes.get(size);
	}



	private void sum (int curIdx, List<Integer> nrs, List<Integer> used, int sum, int target, Map<Integer, Integer> sizeToTimes) {
		if (curIdx >= nrs.size()) {
			return;
		}
		
		sum = sum + nrs.get(curIdx);
		used.add(nrs.get(curIdx));
		if (sum == target) {
			nr++;
			int size = used.size();
			int times = 1;
			if (sizeToTimes.get(size) != null) {
				times += sizeToTimes.get(size);
			}
			sizeToTimes.put(size, times);
			//System.out.print("Solution " + nr + ": ");
			//used.forEach(u -> System.out.print(u + " "));
			//System.out.println();
			return;
		}

		if (sum > target) {
			return;
		}

		for (int i = curIdx + 1; i < nrs.size(); i++) {
			sum(i, nrs, new ArrayList<>(used), sum, target, sizeToTimes);
		}
 	}

	private int runPart1(List<Integer> nrs) {
		int target = 150;
		for (int i = 0; i < nrs.size(); i++) {
			sum(i, nrs, new ArrayList<Integer>(), 0, target, new TreeMap<Integer, Integer>());
		}

		return nr;
	}


	public static void main(String[] args) {
		final List<Integer> lines = FileReader.getCompleteIntegerList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day17().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day17().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}
