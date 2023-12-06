package nl.michielgraat.adventofcode2023.day06;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day06 extends AocSolver {

    protected Day06(String filename) {
        super(filename);
    }

    private List<Long> getNrs(String line) {
        List<Long> nrs = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\d+").matcher(line);
        while (matcher.find()) {
            nrs.add(Long.valueOf(matcher.group()));
        }
        return nrs;
    }

    private long getSingleNr(String line) {
        StringBuilder sb = new StringBuilder();
        Matcher matcher = Pattern.compile("\\d+").matcher(line);
        while (matcher.find()) {
            sb.append(matcher.group());
        }
        return Long.valueOf(sb.toString());
    }

    private List<Long> getDistances(final List<String> input) {
        return getNrs(input.get(1));
    }

    private List<Long> getTimes(final List<String> input) {
        return getNrs(input.get(0));
    }

    private long getSingleDistance(final List<String> input) {
        return getSingleNr(input.get(1));
    }

    private long getSingleTime(final List<String> input) {
        return getSingleNr(input.get(0));
    }

    private long getWaysToWin(long time, long distance) {
        // Strategy: start in the middle of the possible button-press-times and go on
        // until we reach a point at which we drop below the original record. Then we
        // return all ways to win above the middle times 2 (for the ways to win below
        // the middle).
        long waysToWin = 0;
        boolean isEvenTime = time % 2 == 0;

        // We use a ceil div here for the cases where the current time is odd. In
        // that case you want to start at the high end to prevent counting times twice.
        for (long i = Math.ceilDiv(time, 2); i <= time; i++) {
            long curDistance = i * (time - i);
            if (curDistance > distance) {
                waysToWin++;
            } else if (waysToWin > 0) {
                // We have already found ways to win. We are now dropping below the record, so
                // for all further times in which we press the button, the distance will only go
                // down. So it is not necessary to check further ways for this race.
                break;
            }
        }
        // If the original time was even, we subtract 1 to prevent counting the middle
        // twice.
        return isEvenTime ? waysToWin * 2 - 1 : waysToWin * 2;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(getWaysToWin(getSingleTime(input), getSingleDistance(input)));
    }

    @Override
    protected String runPart1(final List<String> input) {
        List<Long> times = getTimes(input);
        List<Long> distances = getDistances(input);

        int total = 1;
        for (int i = 0; i < times.size(); i++) {
            total *= getWaysToWin(times.get(i), distances.get(i));
        }

        return String.valueOf(total);
    }

    public static void main(String... args) {
        new Day06("day06.txt");
    }
}
