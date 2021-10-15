package nl.michielgraat.adventofcode2015.day24;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day24 {

    private static final String FILENAME = "day24.txt";
    private static final Boolean DEBUG = false;

    private void getSubsets(List<Integer> nrs, int i, int sum, List<Integer> p, boolean[][] dp, List<List<Integer>> subsets) {
        if (i == 0 && sum != 0 && dp[0][sum]) {
            p.add(nrs.get(i));
            if (DEBUG) Collections.sort(p);
            subsets.add(p);
            return;
        }

        if (i == 0 && sum == 0) {
            if (DEBUG) Collections.sort(p);
            subsets.add(p);
            return;
        }

        if (dp[i-1][sum]) {
            List<Integer> b = new ArrayList<>();
            b.addAll(p);
            getSubsets(nrs, i-1, sum, b, dp, subsets);
        }

        if (sum >= nrs.get(i) && dp[i-1][sum-nrs.get(i)]) {
            p.add(nrs.get(i));
            getSubsets(nrs, i-1, sum-nrs.get(i), p, dp, subsets);
        }
    }

    private List<List<Integer>> getAllSubsetsWithSum(List<Integer> nrs, int sum) {
        if (nrs.isEmpty() || sum < 0) {
            return new ArrayList<>();
        }

        boolean[][] dp = new boolean[nrs.size()][sum+1];
        for (int i = 0; i < nrs.size(); i++) {
            dp[i][0] = true;
        }
        if (nrs.get(0) <= sum) {
            dp[0][nrs.get(0)] = true;
        }

        for (int i = 1; i < nrs.size(); ++i) {
            for (int j = 0; j < sum+1; ++j) {
                dp[i][j] = (nrs.get(i) <= j) ? (dp[i-1][j] || dp[i-1][j-nrs.get(i)]) : dp[i-1][j];
            }
        }

        if (!dp[nrs.size()-1][sum]) {
            return new ArrayList<>();
        }

        List<List<Integer>> subsets = new ArrayList<>();
        getSubsets(nrs, nrs.size()-1, sum, new ArrayList<>(), dp, subsets);
        return subsets;
    }

    private long getSmallestQE(List<List<Integer>> subsets) {
        int minSize = Integer.MAX_VALUE;
        for (List<Integer> subset : subsets) {
            if (subset.size() < minSize) {
                minSize = subset.size();
            }
        }
        long smallestQE = Long.MAX_VALUE;
        for (List<Integer> subset : subsets) {
            if (subset.size() == minSize) {
                if (DEBUG) System.out.print(subset + " ");
                long qe = subset.stream().mapToLong(i -> i).reduce(1, (a, b) -> a * b);
                if (DEBUG) System.out.println("QE: " + qe);
                if (qe < smallestQE) {
                    smallestQE = qe;
                }
            }
        }
        return smallestQE;
    }

    public long runPart2(List<Integer> nrs) {
        int sum = (nrs.stream().reduce(0, (a, b) -> a + b))/4;
        if (DEBUG) System.out.println("Sum: " + sum);
        return getSmallestQE(getAllSubsetsWithSum(nrs, sum));
    }

    public long runPart1(List<Integer> nrs) {
        int sum = (nrs.stream().reduce(0, (a, b) -> a + b))/3;
        if (DEBUG) System.out.println("Sum: " + sum);
        return getSmallestQE(getAllSubsetsWithSum(nrs, sum));
    }

    public static void main(String[] args) {
		final List<Integer> lines = FileReader.getCompleteIntegerList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day24().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day24().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}
