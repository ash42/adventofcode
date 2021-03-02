package nl.michielgraat.adventofcode2017.day5;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nl.michielgraat.adventofcode2017.util.FileReader;

public class Day5 {

	private static final String FILENAME = "day5.txt";
	
	private List<Integer> getInstructions() {
		//return Stream.of(0,3,0,1,-3).collect(Collectors.toList());
		return new FileReader(FILENAME).getCompleteIntegerList();
	}
	
	public int runPart1() {
		List<Integer> instructions = getInstructions();
		int nrOfSteps = 0;
		int maxIndex = instructions.size();
		int pointer = 0;
		while (pointer >= 0 && pointer < maxIndex) {
			nrOfSteps++;
			int offset = instructions.get(pointer);
//			if (nrOfSteps % 1000 == 0) {
//				System.out.println("[" + nrOfSteps + "] at " + pointer + ", offset: " + offset + ", going to: " + (pointer + offset));
//			}
			instructions.set(pointer, offset + 1);
			pointer = pointer + offset;
		}
		
		return nrOfSteps;
	}
	
	public int runPart2() {
		List<Integer> instructions = getInstructions();
		int nrOfSteps = 0;
		int maxIndex = instructions.size();
		int pointer = 0;
		while (pointer >= 0 && pointer < maxIndex) {
			nrOfSteps++;
			int offset = instructions.get(pointer);
			if (offset < 3) {
				instructions.set(pointer, offset + 1);
			} else {
				instructions.set(pointer, offset - 1);
			}
			pointer = pointer + offset;
		}
		
		return nrOfSteps;
	}
	
	public static void main(String[] args) {
		System.out.println("Part 1: " + new Day5().runPart1());
		System.out.println("Part 2: " + new Day5().runPart2());
	}

}
