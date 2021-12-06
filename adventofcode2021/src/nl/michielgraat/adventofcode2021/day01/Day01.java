package nl.michielgraat.adventofcode2021.day01;

import nl.michielgraat.adventofcode2021.FileReader;
import java.util.Calendar;
import java.util.List;

public class Day01 {
    private static final String FILENAME = "day01.txt";
    
    private int runPart2(final List<Integer> lines) {
        int total = 0;
        for (int i=3; i<lines.size(); i++) {
            final int prev = lines.get(i-3) + lines.get(i-2) + lines.get(i-1);
            final int cur = lines.get(i-2) + lines.get(i-1) + lines.get(i);
            if (prev < cur) {
                total++;
            }
        }
        return total;
    }

    private int runPart1(final List<Integer> lines) {
        int total = 0;
        for (int i=1; i<lines.size(); i++) {
            if (lines.get(i-1) < lines.get(i)) {
                total++;
            }
        }
        return total;
    }

    public static void main(final String[] args) {
		final List<Integer> lines = FileReader.getCompleteIntegerList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day01().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day01().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}

}
