package nl.michielgraat.adventofcode2020.day05;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2020.util.FileReader;


public class Day5 {
	
	private static final String FILENAME = "day05.txt";
	
	private int getRow(String rows) {
		int min = 0;
		int max = 127;	
		
		for (char c : rows.toCharArray()) {
			if (c == 'F') {
				max = Math.floorDiv(max-min, 2) + min;
			} else {
				min = (int) Math.ceil((max-min) / 2.0) + min;
			}
		}
		return min;
	}
	
	private int getColumn(String columns) {
		int min = 0;
		int max = 7;
		for (char c : columns.toCharArray()) {
			if (c == 'L') {
				max = Math.floorDiv(max-min, 2) + min;
			} else {
				min = (int) Math.ceil((max-min) / 2.0) + min;
			}
		}
		return min;
	}
	
	private int getSeatId(String pass) {
		return getRow(pass.substring(0,7))*8 + getColumn(pass.substring(7));
	}
	
	public int runPart2() {
		int[] seats = new int[1024];
			
		List<String> lines = FileReader.getStringList(FILENAME);
		for (String line : lines) {
			seats[getSeatId(line)] = 1;
		}
		
		for (int i=8; i < seats.length; i++) {
			if (seats[i] == 0 && seats[i-1] == 1 && seats[i+1] == 1) {
				return i;
			}
		}
		return 0;
	}
	
	public int runPart1() {
		List<String> lines = FileReader.getStringList(FILENAME);
		int max = 0;
		for (String line : lines) {
			int id = getSeatId(line);
			if (id > max)  max = id;
		}
		return max;
	}

	public static void main(String[] args) {
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day5().runPart1());
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day5().runPart2());
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
	}
}