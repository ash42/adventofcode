package nl.michielgraat.adventofcode2020.day08;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day8 {

	private static final String FILENAME = "day08.txt";
	
	private int runReplaceProgram(Console console, boolean replaceJmps) {
		List<Integer> pointers = (replaceJmps) ? console.getJmpPointers() : console.getNopPointers();
		for (int index : pointers) {
			console.reset();
			console.replaceInstruction(index, !replaceJmps);
			while (!console.isInInfiniteLoop() && !console.isTerminated()) {
				console.runNext();
			}
			if (console.isTerminated()) {
				return console.getAccumulator();
			} else {
				console.replaceInstruction(index, replaceJmps);
			}
		}
		return -1;
	}
	
	public int runPart2(List<String> lines) {
		Console console = new Console(lines);
		int result = runReplaceProgram(console, true);
		if (result != -1) {
			return result;
		} else {
			return runReplaceProgram(console, false);
		}
	}
	
	public int runPart1(List<String> lines) {
		Console console = new Console(lines);
		while (!console.isInInfiniteLoop()) {
			console.runNext();
		}
		return console.getAccumulator();
	}
	
	public static void main(String[] args) {
		List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day8().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day8().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
	}

}

class Console {
	
	private static final String JMP = "jmp";
	private static final String NOP = "nop";
	
	List<String> program;
	List<Integer> prevInstructions = new ArrayList<Integer>();
	List<Integer> jmpPointers = new ArrayList<Integer>();
	List<Integer> nopPointers = new ArrayList<Integer>();

	int accumulator = 0;
	int instructionPointer = 0;
	int nrOfInstructionsRun = 0;
	boolean terminated = false;
	
	Console (List<String> program) {
		this.program = program;
		fillPointers(true);
		fillPointers(false);
	}
	
	public void reset() {
		accumulator = 0;
		instructionPointer = 0;
		nrOfInstructionsRun = 0;
		terminated = false;
		prevInstructions = new ArrayList<Integer>();
	}
	
	private void fillPointers(boolean jmp) {
		for (int i = 0; i < program.size(); i++) {
			String instruction = program.get(i);
			if (jmp && instruction.startsWith(JMP)) {
				jmpPointers.add(i);
			} else if (!jmp && instruction.startsWith(NOP)) {
				nopPointers.add(i);
			}
		}
	}
	
	public void replaceInstruction(int index, boolean replaceByJmp) {
		if (replaceByJmp) {
			program.set(index, program.get(index).replace(NOP, JMP));
		} else {
			program.set(index, program.get(index).replace(JMP, NOP));
		}
	}
	
	public boolean isInInfiniteLoop() {
		return prevInstructions.contains(getInstructionPointer());
	}
	
	public void runNext() {
		if (!terminated) {
			prevInstructions.add(getInstructionPointer());
			String instruction = program.get(instructionPointer);
			if (instruction.startsWith(NOP)) {
				instructionPointer++;
			} else if (instruction.startsWith("acc")) {
				int incr = Integer.valueOf(instruction.substring(instruction.indexOf(" ")+1));
				accumulator += incr;
				instructionPointer++;
			} else if (instruction.startsWith(JMP)) {
				int incr = Integer.valueOf(instruction.substring(instruction.indexOf(" ")+1));
				instructionPointer += incr;
			}
			nrOfInstructionsRun++;
		}
		if (instructionPointer >= program.size()) {
			terminated = true;
		}
	}

	public int getAccumulator() {
		return accumulator;
	}

	public void setAccumulator(int accumulator) {
		this.accumulator = accumulator;
	}

	public int getInstructionPointer() {
		return instructionPointer;
	}

	public void setInstructionPointer(int instructionPointer) {
		this.instructionPointer = instructionPointer;
	}

	public List<String> getProgram() {
		return program;
	}

	public void setProgram(List<String> program) {
		this.program = program;
	}

	public int getNrOfInstructionsRun() {
		return nrOfInstructionsRun;
	}

	public void setNrOfInstructionsRun(int nrOfInstructionsRun) {
		this.nrOfInstructionsRun = nrOfInstructionsRun;
	}

	public List<Integer> getJmpPointers() {
		return jmpPointers;
	}

	public void setJmpPointers(List<Integer> jmpPointers) {
		this.jmpPointers = jmpPointers;
	}

	public List<Integer> getNopPointers() {
		return nopPointers;
	}

	public void setNopPointers(List<Integer> nopPointers) {
		this.nopPointers = nopPointers;
	}

	public boolean isTerminated() {
		return terminated;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}
	
	public void printProgram() {
		program.forEach(System.out::println);
	}
}