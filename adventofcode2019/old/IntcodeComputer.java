package nl.michielgraat.adventofcode2019;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class IntcodeComputer {

	private static final int ADD = 1;
	private static final int MULTIPLY = 2;
	private static final int SAVE_INPUT = 3;
	private static final int OUTPUT = 4;
	private static final int JUMP_IF_TRUE = 5;
	private static final int JUMP_IF_FALSE = 6;
	private static final int LESS_THAN = 7;
	private static final int EQUALS = 8;
	private static final int ADJUST_RELATIVE_BASE = 9;
	private static final int HALT = 99;

	private static final int POSITION_MODE = 0;
	private static final int IMMEDIATE_MODE = 1;
	private static final int RELATIVE_MODE = 2;

	private final long[] program;
	private Queue<Long> input = new LinkedList<Long>();
	private Queue<Long> output = new LinkedList<Long>();

	private int instructionPointer = 0;
	private boolean halted = false;
	private boolean printOutput = true; // Determines whether output should be send to System.out
	private long relativeBase = 0;

	public IntcodeComputer(long[] program) {
		this.program = program;
	}

	public IntcodeComputer(long[] program, int memoryCapacity) {
		this.program = new long[memoryCapacity];
		copyProgram(program);
	}
	
	public IntcodeComputer(long[] program, List<Long> input) {
		this.program = program;
		this.input = new LinkedList<Long>(input); 
	}

	public IntcodeComputer(long[] program, List<Long> input, boolean printOutput) {
		this.program = program;
		this.input = new LinkedList<Long>(input);
		this.printOutput = printOutput;
	}

	public IntcodeComputer(long[] program, List<Long> input, boolean printOutput, int memoryCapacity) {
		this.program = new long[memoryCapacity];
		copyProgram(program);
		this.input = new LinkedList<Long>(input);
		this.printOutput = printOutput;
	}

	private void copyProgram(long[] program) {
		for (int i = 0; i < program.length; i++) {
			this.program[i] = program[i];
		}
	}

	private void add() {
		long param1 = program[instructionPointer + 1];
		long param2 = program[instructionPointer + 2];
		long param3 = program[instructionPointer + 3];
		int mode1 = getMode1();
		int mode2 = getMode2();
		int mode3 = getMode3();
		long x = (mode1 != RELATIVE_MODE) ? (mode1 == POSITION_MODE ? program[(int) param1] : param1)
				: program[(int) (param1 + relativeBase)];
		long y = (mode2 != RELATIVE_MODE) ? (mode2 == POSITION_MODE ? program[(int) param2] : param2)
				: program[(int) (param2 + relativeBase)];
		if (mode3 == RELATIVE_MODE) {
			param3 = param3 + relativeBase;
		}
		program[(int) param3] = x + y;
		instructionPointer += 4;
	}

	private void multiply() {
		long param1 = program[instructionPointer + 1];
		long param2 = program[instructionPointer + 2];
		long param3 = program[instructionPointer + 3];
		int mode1 = getMode1();
		int mode2 = getMode2();
		int mode3 = getMode3();
		long x = (mode1 != RELATIVE_MODE) ? (mode1 == POSITION_MODE ? program[(int) param1] : param1)
				: program[(int) (param1 + relativeBase)];
		long y = (mode2 != RELATIVE_MODE) ? (mode2 == POSITION_MODE ? program[(int) param2] : param2)
				: program[(int) (param2 + relativeBase)];
		if (mode3 == RELATIVE_MODE) {
			param3 = param3 + relativeBase;
		}
		program[(int) param3] = x * y;
		instructionPointer += 4;
	}

	private InputState saveInput() {
		if (!input.isEmpty()) {
			long param = program[instructionPointer + 1];
			int mode = getMode1();
			if (mode != RELATIVE_MODE) {
				program[(int) param] = input.remove();
			} else {
				program[(int) (param + relativeBase)] = input.remove();
			}
			instructionPointer += 2;
			return InputState.CONTINUE;
		} else {
			return InputState.INPUT_NEEDED;
		}
	}

	private long output() {
		long param = program[instructionPointer + 1];
		int mode = getMode1();
		long value = (mode != RELATIVE_MODE) ? (mode == POSITION_MODE ? program[(int) param] : param)
				: program[(int) (param + relativeBase)];
		output.add(value);
		if (printOutput) {
			System.out.println(value);
		}
		instructionPointer += 2;
		return value;
	}

	private void jumpIfTrue() {
		long param1 = program[instructionPointer + 1];
		long param2 = program[instructionPointer + 2];
		int mode1 = getMode1();
		int mode2 = getMode2();
		long value1 = (mode1 != RELATIVE_MODE) ? (mode1 == POSITION_MODE ? program[(int) param1] : param1)
				: program[(int) (param1 + relativeBase)];
		long value2 = (mode2 != RELATIVE_MODE) ? (mode2 == POSITION_MODE ? program[(int) param2] : param2)
				: program[(int) (param2 + relativeBase)];
		instructionPointer = (int) (value1 != 0 ? value2 : instructionPointer + 3);
	}

	private void jumpIfFalse() {
		long param1 = program[instructionPointer + 1];
		long param2 = program[instructionPointer + 2];
		int mode1 = getMode1();
		int mode2 = getMode2();
		long value1 = (mode1 != RELATIVE_MODE) ? (mode1 == POSITION_MODE ? program[(int) param1] : param1)
				: program[(int) (param1 + relativeBase)];
		long value2 = (mode2 != RELATIVE_MODE) ? (mode2 == POSITION_MODE ? program[(int) param2] : param2)
				: program[(int) (param2 + relativeBase)];
		instructionPointer = (int) (value1 == 0 ? value2 : instructionPointer + 3);
	}

	private void lessThan() {
		long param1 = program[instructionPointer + 1];
		long param2 = program[instructionPointer + 2];
		long param3 = program[instructionPointer + 3];
		int mode1 = getMode1();
		int mode2 = getMode2();
		int mode3 = getMode3();
		long value1 = (mode1 != RELATIVE_MODE) ? (mode1 == POSITION_MODE ? program[(int) param1] : param1)
				: program[(int) (param1 + relativeBase)];
		long value2 = (mode2 != RELATIVE_MODE) ? (mode2 == POSITION_MODE ? program[(int) param2] : param2)
				: program[(int) (param2 + relativeBase)];
		if (mode3 == RELATIVE_MODE) {
			param3 = param3 + relativeBase;
		}
		program[(int) param3] = value1 < value2 ? 1 : 0;
		instructionPointer += 4;
	}

	private void equals() {
		long param1 = program[instructionPointer + 1];
		long param2 = program[instructionPointer + 2];
		long param3 = program[instructionPointer + 3];
		int mode1 = getMode1();
		int mode2 = getMode2();
		int mode3 = getMode3();
		long value1 = (mode1 != RELATIVE_MODE) ? (mode1 == POSITION_MODE ? program[(int) param1] : param1)
				: program[(int) (param1 + relativeBase)];
		long value2 = (mode2 != RELATIVE_MODE) ? (mode2 == POSITION_MODE ? program[(int) param2] : param2)
				: program[(int) (param2 + relativeBase)];
		if (mode3 == RELATIVE_MODE) {
			param3 = param3 + relativeBase;
		}
		program[(int) param3] = value1 == value2 ? 1 : 0;
		instructionPointer += 4;
	}

	private void adjustRelativeBase() {
		long param = program[instructionPointer + 1];
		int mode = getMode1();
		long value = (mode != RELATIVE_MODE) ? (mode == POSITION_MODE ? program[(int) param] : param)
				: program[(int) (param + relativeBase)];
		relativeBase += value;
		instructionPointer += 2;
	}

	private int getMode1() {
		int instruction = (int) program[instructionPointer];
		return instruction < 100 ? 0 : (instruction / 100) % 10;
	}

	private int getMode2() {
		int instruction = (int) program[instructionPointer];
		return instruction < 100 || instruction < 1000 ? 0 : (instruction / 1000) % 10;
	}

	private int getMode3() {
		int instruction = (int) program[instructionPointer];
		return instruction < 100 || instruction < 1000 || instruction < 10000 ? 0 : (instruction / 10000) % 10;
	}

	private int getOpcode() {
		int instruction = (int) program[instructionPointer];
		return instruction < 10 ? instruction : instruction % 100;
	}

	public long getOutput() {
		return output.remove();
	}
	
	public Queue<Long> getOutputQueue() {
		return output;
	}

	public boolean hasOutput() {
		return !output.isEmpty();
	}
	
	public void addInput(long input) {
		this.input.add(input);
	}

	public void printProgram() {
		for (int i = 0; i < program.length; i++) {
			if (i < program.length - 1) {
				System.out.print(program[i] + ", ");
			} else {
				System.out.println(program[i]);
			}
		}
	}

	public boolean isHalted() {
		return halted;
	}

	public void setPrintOutput(boolean printOutput) {
		this.printOutput = printOutput;
	}

	public ReturnCode run() {
		while (instructionPointer < program.length && !halted) {
			switch (getOpcode()) {
			case ADD:
				add();
				break;
			case MULTIPLY:
				multiply();
				break;
			case SAVE_INPUT:
				InputState state = saveInput();
				if (state == InputState.INPUT_NEEDED) {
					return ReturnCode.INPUT_NEEDED;
				}
				break;
			case OUTPUT:
				output();
				break;
			case JUMP_IF_TRUE:
				jumpIfTrue();
				break;
			case JUMP_IF_FALSE:
				jumpIfFalse();
				break;
			case LESS_THAN:
				lessThan();
				break;
			case EQUALS:
				equals();
				break;
			case ADJUST_RELATIVE_BASE:
				adjustRelativeBase();
				break;
			case HALT:
				halted = true;
				break;
			}
		}
		return ReturnCode.HALTED;
	}

	public enum ReturnCode {
		INPUT_NEEDED, HALTED;
	}

	private enum InputState {
		CONTINUE, INPUT_NEEDED;
	}
}
