package nl.michielgraat.adventofcode2015.day23;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day23 {

    private static final String FILENAME = "day23.txt";

    private int runProgram(List<String> lines, int a, int b) {
        int nrOfInstructions = lines.size();

        int ptr = 0;

        while (ptr >= 0 && ptr < nrOfInstructions) {
            String line = lines.get(ptr);
            String parts[] = line.split(" ");
            String instruction = parts[0];
            if (instruction.equals("hlf")) {
                String register = parts[1];
                if (register.equals("a")) {
                    a /= 2;
                } else {
                    b /= 2;
                }
                ptr++;
            } else if (instruction.equals("tpl")) {
                String register = parts[1];
                if (register.equals("a")) {
                    a *= 3;
                } else {
                    b *= 3;
                }
                ptr++;
            } else if (instruction.equals("inc")) {
                String register = parts[1];
                if (register.equals("a")) {
                    a++;
                } else {
                    b++;
                }
                ptr++;
            } else if (instruction.equals("jmp")) {
                ptr += Integer.valueOf(parts[1]);
            } else if (instruction.equals("jie")) {
                String register = parts[1];
                if (register.startsWith("a")) {
                    if (a % 2 == 0) {
                        ptr += Integer.valueOf(parts[2]);
                    } else {
                        ptr++;
                    }
                } else {
                    if (b % 2 == 0) {
                        ptr += Integer.valueOf(parts[2]);
                    } else {
                        ptr++;
                    }
                }
            } else if (instruction.equals("jio")) {
                String register = parts[1];
                if (register.startsWith("a")) {
                    if (a == 1) {
                        ptr += Integer.valueOf(parts[2]);
                    } else {
                        ptr++;
                    }
                } else {
                    if (b == 1) {
                        ptr += Integer.valueOf(parts[2]);
                    } else {
                        ptr++;
                    }
                }
            }
        }

        return b;
    }

    public int runPart2(List<String> lines) {
        return runProgram(lines, 1, 0);
    }

    public int runPart1(List<String> lines) {
        return runProgram(lines, 0, 0);
    }

    public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day23().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day23().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}
