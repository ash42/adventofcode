package nl.michielgraat.adventofcode2021.day02;

import nl.michielgraat.adventofcode2021.FileReader;
import java.util.Calendar;
import java.util.List;

public class Day02 {
    private static final String FILENAME = "day02.txt";
    
    private int runPart2(final List<String> lines) {
        int aim = 0;
        int depth = 0;
        int pos = 0;
        for (final String line : lines) {
            final int val = Integer.parseInt(line.substring(line.indexOf(" ") + 1));
            if (line.startsWith("down")) {
                aim += val;
            } else if (line.startsWith("up")) {
                aim -= val;
            } else {
                pos += val;
                depth += aim*val;
            }
        }
        return depth*pos;
    }

    private int runPart1(final List<String> lines) {
        int depth = 0;
        int pos = 0;
        for (final String line : lines) {
            final int val = Integer.parseInt(line.substring(line.indexOf(" ") + 1));
            if (line.startsWith("down")) {
                depth += val;
            } else if (line.startsWith("up")) {
                depth -= val;
            } else {
                pos += val;
            }
        }
        return depth*pos;
    }

    public static void main(final String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day02().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day02().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}

}
