package nl.michielgraat.adventofcode2018.day14;

import java.util.ArrayList;
import java.util.List;

public class Day14 {

    private int runPart2(String n) {
        int nrOfDigits = n.length();
        List<Integer> scores = new ArrayList<>();
        scores.add(3);
        scores.add(7);
        int idx1 = 0;
        int idx2 = 1;
        StringBuilder lastDigits = new StringBuilder("37");
        while (true) {
            int score1 = scores.get(idx1);
            int score2 = scores.get(idx2);
            int newScore = score1 + score2;
            String[] sScore = String.valueOf(newScore).split("");
            for (String s : sScore) {
                int intToAdd = Integer.parseInt(s);
                scores.add(intToAdd);
                if (lastDigits.length() >= nrOfDigits) {
                    lastDigits.delete(0, 1);
                }
                lastDigits.append(intToAdd);
                if (lastDigits.toString().equals(n)) {
                    return scores.size() - nrOfDigits;
                }
            }
            idx1 = (idx1 + 1 + score1) % scores.size();
            idx2 = (idx2 + 1 + score2) % scores.size();
        }

    }

    private String runPart1(int n) {
        List<Integer> scores = new ArrayList<>();
        scores.add(3);
        scores.add(7);
        int idx1 = 0;
        int idx2 = 1;
        while (scores.size() < n + 10) {
            int score1 = scores.get(idx1);
            int score2 = scores.get(idx2);
            int newScore = score1 + score2;
            String[] sScore = String.valueOf(newScore).split("");
            for (String s : sScore) {
                scores.add(Integer.parseInt(s));
            }
            idx1 = (idx1 + 1 + score1) % scores.size();
            idx2 = (idx2 + 1 + score2) % scores.size();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = n; i < n + 10; i++) {
            sb.append(scores.get(i));
        }
        return sb.toString();
    }

    public static void main(final String[] args) {
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day14().runPart1(290431));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day14().runPart2("290431"));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
