package nl.michielgraat.adventofcode2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 {

	private long[] program = { 3, 225, 1, 225, 6, 6, 1100, 1, 238, 225, 104, 0, 1102, 17, 65, 225, 102, 21, 95, 224,
			1001, 224, -1869, 224, 4, 224, 1002, 223, 8, 223, 101, 7, 224, 224, 1, 224, 223, 223, 101, 43, 14, 224,
			1001, 224, -108, 224, 4, 224, 102, 8, 223, 223, 101, 2, 224, 224, 1, 223, 224, 223, 1101, 57, 94, 225, 1101,
			57, 67, 225, 1, 217, 66, 224, 101, -141, 224, 224, 4, 224, 102, 8, 223, 223, 1001, 224, 1, 224, 1, 224, 223,
			223, 1102, 64, 34, 225, 1101, 89, 59, 225, 1102, 58, 94, 225, 1002, 125, 27, 224, 101, -2106, 224, 224, 4,
			224, 102, 8, 223, 223, 1001, 224, 5, 224, 1, 224, 223, 223, 1102, 78, 65, 225, 1001, 91, 63, 224, 101, -127,
			224, 224, 4, 224, 102, 8, 223, 223, 1001, 224, 3, 224, 1, 223, 224, 223, 1102, 7, 19, 224, 1001, 224, -133,
			224, 4, 224, 102, 8, 223, 223, 101, 6, 224, 224, 1, 224, 223, 223, 2, 61, 100, 224, 101, -5358, 224, 224, 4,
			224, 102, 8, 223, 223, 101, 3, 224, 224, 1, 224, 223, 223, 1101, 19, 55, 224, 101, -74, 224, 224, 4, 224,
			102, 8, 223, 223, 1001, 224, 1, 224, 1, 224, 223, 223, 1101, 74, 68, 225, 4, 223, 99, 0, 0, 0, 677, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 1105, 0, 99999, 1105, 227, 247, 1105, 1, 99999, 1005, 227, 99999, 1005, 0, 256,
			1105, 1, 99999, 1106, 227, 99999, 1106, 0, 265, 1105, 1, 99999, 1006, 0, 99999, 1006, 227, 274, 1105, 1,
			99999, 1105, 1, 280, 1105, 1, 99999, 1, 225, 225, 225, 1101, 294, 0, 0, 105, 1, 0, 1105, 1, 99999, 1106, 0,
			300, 1105, 1, 99999, 1, 225, 225, 225, 1101, 314, 0, 0, 106, 0, 0, 1105, 1, 99999, 107, 677, 677, 224, 102,
			2, 223, 223, 1006, 224, 329, 1001, 223, 1, 223, 1008, 226, 677, 224, 102, 2, 223, 223, 1006, 224, 344, 1001,
			223, 1, 223, 7, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 359, 1001, 223, 1, 223, 8, 226, 226, 224, 102,
			2, 223, 223, 1006, 224, 374, 1001, 223, 1, 223, 1007, 226, 226, 224, 102, 2, 223, 223, 1006, 224, 389, 101,
			1, 223, 223, 8, 677, 226, 224, 1002, 223, 2, 223, 1005, 224, 404, 101, 1, 223, 223, 1108, 677, 226, 224,
			102, 2, 223, 223, 1006, 224, 419, 1001, 223, 1, 223, 1108, 226, 677, 224, 102, 2, 223, 223, 1006, 224, 434,
			101, 1, 223, 223, 1108, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 449, 101, 1, 223, 223, 1008, 677, 677,
			224, 1002, 223, 2, 223, 1006, 224, 464, 101, 1, 223, 223, 7, 677, 226, 224, 1002, 223, 2, 223, 1006, 224,
			479, 101, 1, 223, 223, 108, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 494, 101, 1, 223, 223, 107, 226,
			677, 224, 1002, 223, 2, 223, 1006, 224, 509, 101, 1, 223, 223, 107, 226, 226, 224, 102, 2, 223, 223, 1006,
			224, 524, 1001, 223, 1, 223, 1107, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 539, 101, 1, 223, 223, 1008,
			226, 226, 224, 102, 2, 223, 223, 1006, 224, 554, 1001, 223, 1, 223, 8, 226, 677, 224, 1002, 223, 2, 223,
			1006, 224, 569, 101, 1, 223, 223, 1007, 677, 677, 224, 102, 2, 223, 223, 1005, 224, 584, 1001, 223, 1, 223,
			1107, 677, 226, 224, 1002, 223, 2, 223, 1006, 224, 599, 101, 1, 223, 223, 7, 226, 226, 224, 1002, 223, 2,
			223, 1005, 224, 614, 101, 1, 223, 223, 108, 677, 226, 224, 1002, 223, 2, 223, 1005, 224, 629, 1001, 223, 1,
			223, 108, 226, 226, 224, 1002, 223, 2, 223, 1005, 224, 644, 101, 1, 223, 223, 1007, 677, 226, 224, 1002,
			223, 2, 223, 1006, 224, 659, 101, 1, 223, 223, 1107, 226, 226, 224, 102, 2, 223, 223, 1005, 224, 674, 1001,
			223, 1, 223, 4, 223, 99, 226};

	public void run1() {
		List<Long> input = new ArrayList<Long>();
		input.add(1L);
		new IntcodeComputer(program, input).run();
	}

	public void run2() {
		List<Long> input = new ArrayList<Long>();
		input.add(5L);
		new IntcodeComputer(program, input).run();
	}

	public static void main(String[] args) {
		new Day5().run1();
		new Day5().run2();
	}

}
