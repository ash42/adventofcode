package nl.michielgraat.adventofcode2015.day25;

import java.util.Calendar;

public class Day25 {

    private int findNr(int rowNr, int colNr) {
        int offset = 1;
        int nr = 1;
        int i = 1;
        for (; i<rowNr; i++) {
            //System.out.println("Row at " + i + ", column 1 has number: " + nr);
            nr += offset++;
        }

        offset = i+1;
        int result = nr;
        for (i=1; i < colNr; i++) {
           // System.out.println("Column at " + i + ", row " + nr + " has number: " + result);
            result += offset++;
        }

        return result;
    }

    private long runPart1(int rowNr, int colNr) {
        int nr = findNr(rowNr, colNr);
        //System.out.println("Nr: " + nr);
        long result = 20151125;
        int mult = 252533;
        int div = 33554393;

        //System.out.println("Start " + result);
        for (int i = 1; i < nr; i++) {
            result = (result * mult) % div;
            //System.out.println(result);
        }

        return result;
    }

    public static void main(String[] args) {
		final int rowNr = 2981;
        final int colNr = 3075;
        //final int nr = 130;
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day25().runPart1(rowNr, colNr));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}
