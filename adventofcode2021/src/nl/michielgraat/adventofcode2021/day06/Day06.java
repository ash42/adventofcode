package nl.michielgraat.adventofcode2021.day06;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day06 {

    private static final String FILENAME = "day06.txt";

    private List<Integer> getInitialFish(final List<String> lines) {
        final String[] sNrs = lines.get(0).split(",");
        final List<Integer> nrs = new ArrayList<>();
        for (int i = 0; i < sNrs.length; i++) {
            nrs.add(Integer.parseInt(sNrs[i]));
        }
        return nrs;
    }

    private long getNrOfFishSmarter(final int day, final List<Integer> nrs) {
        long nrOf0s = Collections.frequency(nrs, 0);
        long nrOf1s = Collections.frequency(nrs, 1);
        long nrOf2s = Collections.frequency(nrs, 2);
        long nrOf3s = Collections.frequency(nrs, 3);
        long nrOf4s = Collections.frequency(nrs, 4);
        long nrOf5s = Collections.frequency(nrs, 5);
        long nrOf6s = Collections.frequency(nrs, 6);
        long nrOf7s = Collections.frequency(nrs, 7);
        long nrOf8s = Collections.frequency(nrs, 8);

        for (int i = 0; i < day; i++) {
            final long oldNrOf0s = nrOf0s;
            nrOf0s = nrOf1s;
            nrOf1s = nrOf2s;
            nrOf2s = nrOf3s;
            nrOf3s = nrOf4s;
            nrOf4s = nrOf5s;
            nrOf5s = nrOf6s;
            nrOf6s = nrOf7s + oldNrOf0s;
            nrOf7s = nrOf8s;
            nrOf8s = oldNrOf0s;
        }

        return nrOf0s + nrOf1s + nrOf2s + nrOf3s + nrOf4s + nrOf5s + nrOf6s + nrOf7s + nrOf8s;
    }

    private int getNrOfFishBruteForce(final int day, final List<Integer> nrs) {
        for (int i = 0; i < day; i++) {
            final List<Integer> fishToAdd = new ArrayList<>();
            for (int j = 0; j < nrs.size(); j++) {
                if (nrs.get(j) == 0) {
                    fishToAdd.add(8);
                    nrs.set(j, 6);
                } else {
                    nrs.set(j, nrs.get(j) - 1);
                }
            }
            nrs.addAll(fishToAdd);
        }
        return nrs.size();
    }

    public long runPart2(final List<String> lines) {
        return getNrOfFishSmarter(256, getInitialFish(lines));
    }

    public int runPart1(final List<String> lines) {
        return getNrOfFishBruteForce(80, getInitialFish(lines));
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day06().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day06().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}
