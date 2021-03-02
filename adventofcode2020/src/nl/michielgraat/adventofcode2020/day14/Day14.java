package nl.michielgraat.adventofcode2020.day14;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day14 {

	private static final String FILENAME = "day14.txt";
	
	private long getNrOfFloating(String mask) {
		return mask.chars().filter(c -> c == 'X').count();
	}
	
	private long getAddress(String nr, String address) {
		String result = new String();
		int nrIndex = 0;
		for (int i=0; i<address.length(); i++) {
			if (address.charAt(i) == 'X') {
				result += nr.charAt(nrIndex);
				nrIndex++;
			} else {
				result += address.charAt(i);
			}
		}
		return Long.parseLong(result, 2);
	}
	
	private List<Long> getAddresses(String mask) {
		int nrOfAddresses = (int) Math.pow(2, getNrOfFloating(mask));
		List<Long> addresses = new ArrayList<Long>();
		for (int i=0; i< nrOfAddresses; i++) {
			String m = Integer.toBinaryString(i);
			while (m.length() < getNrOfFloating(mask)) {
				m = "0" + m;
			}
			addresses.add(getAddress(m, mask));
		}
		return addresses;
	}
	
	private String applyMask(String value, String mask) {
		String result = new String();
		for (int i = 0; i < mask.length(); i++) {
			char curMask = mask.charAt(i);
			char curVal = value.charAt(i);
			if (curMask == 'X') {
				result += 'X';
			} else if (curMask == '0') {
				result += curVal;
			} else {
				result += '1';
			} 
		}
		return result;
	}
	
	private void writeToMemory(long value, long address, String currentMask, Map<Long, Long> memory) {
		String memoryString = Long.toBinaryString(address);
		while (memoryString.length() < 36) {
			memoryString = "0" + memoryString;
		}
		memoryString = applyMask(memoryString, currentMask);
		List<Long> addresses = getAddresses(memoryString);
		for (Long a : addresses) {
			memory.put(a, value);
		}
	}

	public long runPart2(List<String> lines) {
		Map<Long, Long> memory = new HashMap<Long, Long>();
		String currentMask = new String();
		
		for (String line : lines) {
			if (line.startsWith("mask")) {
				currentMask = getMask(line);
			} else if (line.startsWith("mem")) {
				long address = getAddress(line);
				long value = getValue(line);
				writeToMemory(value, address, currentMask, memory);
			}
		}
		
		long total = 0;
		for (Long value : memory.values()) {
			total += value;
		}
		
		return total;
	}
	
	private long convertToLong(int[] value) {
		String sVal = new String();
		for (int v : value) {
			sVal += v;
		}
		return Long.parseLong(sVal, 2);
	}

	private int[] convertToBinaryArray(String s) {
		int[] ba = new int[36];
		int nr = ba.length - 1;
		for (int j = s.length() - 1; j>=0; j--) {
			ba[nr] = s.charAt(j) - '0';
			nr--;
		}
		return ba;
	}
	
	private int[] convertToBinaryArray(long i) {
		return convertToBinaryArray(Long.toBinaryString(i));
	}
	
	private String getMask(String line) {
		return line.substring(line.indexOf("=") + 2);
	}
	
	private int getAddress(String line) {
		return Integer.valueOf(line.substring(line.indexOf("[")+1, line.indexOf("]")));
	}
	
	private int getValue(String line) {
		return Integer.valueOf(line.substring(line.indexOf("=") + 2));
	}
	
	private void applyMask(int[] value, String mask) {
		for (int i=0; i<mask.length(); i++) {
			if (mask.charAt(i) != 'X') {
				int bit = mask.charAt(i) - '0';
				value[i] = bit;
			}
		}
	}

	public long runPart1(List<String> lines) {
		long[] memory = new long[100000];
		String currentMask = new String();
		for (String line : lines) {
			if (line.startsWith("mask")) {
				currentMask = getMask(line);
			} else if (line.startsWith("mem")) {
				int address = getAddress(line);
				int value = getValue(line);
				int[] bValue = convertToBinaryArray(value);
				applyMask(bValue, currentMask);
				memory[address] = convertToLong(bValue);
			}
		}
		long total = 0;
		for (int i=0; i<memory.length; i++) {
			total += memory[i];
		}
		return total;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day14().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day14().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
	}

}
