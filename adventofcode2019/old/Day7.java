package nl.michielgraat.adventofcode2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day7 {

	private long[] program = { 3, 8, 1001, 8, 10, 8, 105, 1, 0, 0, 21, 34, 55, 68, 85, 106, 187, 268, 349, 430, 99999, 3,
			9, 1001, 9, 5, 9, 1002, 9, 5, 9, 4, 9, 99, 3, 9, 1002, 9, 2, 9, 1001, 9, 2, 9, 1002, 9, 5, 9, 1001, 9, 2, 9,
			4, 9, 99, 3, 9, 101, 3, 9, 9, 102, 3, 9, 9, 4, 9, 99, 3, 9, 1002, 9, 5, 9, 101, 3, 9, 9, 102, 5, 9, 9, 4, 9,
			99, 3, 9, 1002, 9, 4, 9, 1001, 9, 2, 9, 102, 3, 9, 9, 101, 3, 9, 9, 4, 9, 99, 3, 9, 1001, 9, 2, 9, 4, 9, 3,
			9, 101, 2, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3,
			9, 1001, 9, 2, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3,
			9, 102, 2, 9, 9, 4, 9, 99, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3,
			9, 1001, 9, 1, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3,
			9, 101, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 99, 3, 9, 1001, 9, 2, 9, 4, 9,
			3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3,
			9, 1002, 9, 2, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9,
			1002, 9, 2, 9, 4, 9, 99, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3,
			9, 102, 2, 9, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9,
			102, 2, 9, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 99, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9,
			1001, 9, 1, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9,
			102, 2, 9, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9,
			102, 2, 9, 9, 4, 9, 99 };

	public List<List<Integer>> generatePermutations(List<Integer> original) {
		if (original.isEmpty()) {
			List<List<Integer>> result = new ArrayList<>();
			result.add(new ArrayList<>());
			return result;
		}
		Integer firstElement = original.remove(0);
		List<List<Integer>> returnValue = new ArrayList<>();
		List<List<Integer>> permutations = generatePermutations(original);
		for (List<Integer> smallerPermutated : permutations) {
			for (int index = 0; index <= smallerPermutated.size(); index++) {
				List<Integer> temp = new ArrayList<>(smallerPermutated);
				temp.add(index, firstElement);
				returnValue.add(temp);
			}
		}
		return returnValue;
	}

	public void run1() {
		List<Integer> phases = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
		List<List<Integer>> permutations = generatePermutations(phases);
		long maxResult = -1;
		for (List<Integer> permutation : permutations) {
			long result = 0;
			for (int phase : permutation) {
				List<Long> input = new ArrayList<Long>();
				input.add((long) phase);
				input.add(result);
				IntcodeComputer computer = new IntcodeComputer(program, input, false);
				computer.run();
				result = computer.getOutput();
			}
			if (result > maxResult) {
				maxResult = result;
			}
		}
		System.out.println("Max result: " + maxResult);
	}

	public void run2() {
		List<Integer> phases = new ArrayList<Integer>(Arrays.asList(5, 6, 7, 8, 9));
		List<List<Integer>> permutations = generatePermutations(phases);
		long maxResult = -1;
		for (List<Integer> permutation : permutations) {
			IntcodeComputer a1 = new IntcodeComputer(program,
					new ArrayList<Long>(Arrays.asList((long) permutation.get(0))));
			IntcodeComputer a2 = new IntcodeComputer(program,
					new ArrayList<Long>(Arrays.asList((long) permutation.get(1))));
			IntcodeComputer a3 = new IntcodeComputer(program,
					new ArrayList<Long>(Arrays.asList((long) permutation.get(2))));
			IntcodeComputer a4 = new IntcodeComputer(program,
					new ArrayList<Long>(Arrays.asList((long) permutation.get(3))));
			IntcodeComputer a5 = new IntcodeComputer(program,
					new ArrayList<Long>(Arrays.asList((long) permutation.get(4))));

			a1.setPrintOutput(false);
			a2.setPrintOutput(false);
			a3.setPrintOutput(false);
			a4.setPrintOutput(false);
			a5.setPrintOutput(false);

			long result = 0;
			while (!a5.isHalted()) {
				a1.addInput(result);
				a1.run();
				result = a1.getOutput();

				a2.addInput(result);
				a2.run();
				result = a2.getOutput();

				a3.addInput(result);
				a3.run();
				result = a3.getOutput();

				a4.addInput(result);
				a4.run();
				result = a4.getOutput();

				a5.addInput(result);
				a5.run();
				result = a5.getOutput();
			}
			if (result > maxResult) {
				maxResult = result;
			}
		}
		System.out.println("Max result: " + maxResult);
	}

	public static void main(String[] args) {
		new Day7().run1();
		new Day7().run2();
	}

}
