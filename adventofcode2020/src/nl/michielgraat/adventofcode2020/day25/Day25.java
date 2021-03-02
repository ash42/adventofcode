package nl.michielgraat.adventofcode2020.day25;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day25 {
	
	private static final String FILENAME = "day25.txt";

	public long runPart2(List<String> lines) {
		return 0;
	}
	
	private long transform(long val, long subjNr) {
		val = val * subjNr;
		val = val % 20201227;
		return val;
	}
	
	private long getLoopSize(long pk, long subjNr) {
		long loopNr = 0;
		long val = 1;
		while (val != pk) {
			val = transform(val, subjNr);
			loopNr++;
		}
		return loopNr;
	}
	
	private long calcEncryptionKey(long subjNr, long loopSize) {
		long val = 1;
		for (int i = 0; i<loopSize; i++) {
			val = transform(val, subjNr);
		}
		return val;
	}
	
	public long runPart1(List<String> lines) {
		long cardPk = Long.valueOf(lines.get(0));
		long doorPk = Long.valueOf(lines.get(1));
		
		return calcEncryptionKey(doorPk, getLoopSize(cardPk, 7));
	}
	
	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day25().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day25().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
	}

}
