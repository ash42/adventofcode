package nl.michielgraat.adventofcode2020.day09;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day9 {
	
	private static final String FILENAME = "day09.txt";
	
	private final int preambleSize;
	
	public Day9(final int preambleSize) {
		this.preambleSize = preambleSize;
	}
	
	private long sumList(List<Long> list) {
		long total = 0;
		for (Long l : list) {
			total += l;
		}
		return total;
	}

	public long runPart2(long invalidNr, List<Long> lines) {
		int invalidIndex = lines.indexOf(invalidNr);
		
		List<Long> nrs = new ArrayList<Long>();
		for (int i=0; i<invalidIndex; i++) {
			for (int j = i; j < invalidIndex; j++) {
				nrs.add(lines.get(j));
				if (nrs.size() >= 2) {
					long sum = sumList(nrs);
					if (sum > invalidNr) {
						nrs = new ArrayList<Long>();
						break;
					} else if (sum == invalidNr) {
						Collections.sort(nrs);
						return nrs.get(0) + nrs.get(nrs.size()-1);
					}
				}
			}
		}
		return -1;
	}
	
	public long runPart1(List<Long> lines) {
		List<Long> preamble = new ArrayList<Long>();
		int index = 0;
		while (preamble.size() < preambleSize) {
			preamble.add(index, lines.get(index));
			index++;
		}

		while (index < lines.size()) {
			long current = lines.get(index);
			boolean sumFound = false;
			for (int i=index-preambleSize; i<preamble.size(); i++) {
				for (int j=index-preambleSize; j<preamble.size(); j++) {
					if (i != j) {
						long x = preamble.get(i);
						long y = preamble.get(j);
						if (x+y == current && x != y) {
							sumFound = true;
						}
					}
				}
			}
			if (!sumFound) {
				return current;
			}
			preamble.add(lines.get(index));
			index++;
		}
		return -1;
	}
	
	public static void main(String[] args) {
		List<Long> lines = FileReader.getLongList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		long answer1 = new Day9(125).runPart1(lines);
		System.out.println("Answer to part 1: " + answer1);
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day9(125).runPart2(answer1, lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
	}

}
