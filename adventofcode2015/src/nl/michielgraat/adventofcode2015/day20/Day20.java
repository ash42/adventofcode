package nl.michielgraat.adventofcode2015.day20;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Day20 {

    private List<Integer> getDivisors(int n) {
        List<Integer> divisors = new ArrayList<>();
        for (int i = 1; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                if (n/i == i) {
                    divisors.add(i);
                } else {
                    divisors.add(i);
                    divisors.add(n/i);
                }
            }
        }
        Collections.sort(divisors);
        return divisors;
    }

    public int runPart2(int nr) {
        Map<Integer, Integer> timesUsed = new TreeMap<>();
        for (int houseNr = 1; ; houseNr++) {
            int total = 0;
            List<Integer> divisors = getDivisors(houseNr);
            for (int div : divisors) {
                Integer times = timesUsed.get(div);
                if (times == null) {
                    times = 0;
                }
                if (times < 50) {
                    total += div*11;
                    times++;
                    timesUsed.put(div, times);
                } 
                if (total >= nr) {
                    return houseNr;
                }
            }
        }
    }

    public int runPart1(int nr) {
        for (int houseNr = 1; ; houseNr++) {
            int total = 0;
            List<Integer> divisors = getDivisors(houseNr);
            for (int div : divisors) {
                total += div*10;
                if (total >= nr) {
                    return houseNr;
                }
            }
        }
    }

    public static void main(String[] args) {
		final int nr = 29000000;
        //final int nr = 130;
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day20().runPart1(nr));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day20().runPart2(nr));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}
