package nl.michielgraat.adventofcode2021.day18;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day18 {

    private static final String FILENAME = "day18.txt";

    private static final String NR_PATTERN = "\\d+";
    private static final String PAIR_PATTERN = "\\[\\d+,\\d+\\]";

    private boolean isExplodablePair(final String input, final int pairIndex) {
        int pairCounter = 0;
        for (int i = 0; i < pairIndex; i++) {
            final char c = input.charAt(i);
            if (c == '[') {
                pairCounter++;
            } else if (c == ']') {
                pairCounter--;
            }
        }
        return pairCounter >= 4;
    }

    private boolean containsExplodablePair(final String input) {
        final Pattern pairPattern = Pattern.compile(PAIR_PATTERN);
        final Matcher matcher = pairPattern.matcher(input);
        while (matcher.find()) {
            final String pair = matcher.group();
            if (isExplodablePair(input, input.indexOf(pair, matcher.start()))) {
                return true;
            }
        }
        return false;
    }

    private boolean containsSplittablePair(final String input) {
        final Pattern nrPattern = Pattern.compile(NR_PATTERN);
        final Matcher nrMatcher = nrPattern.matcher(input);
        while (nrMatcher.find()) {
            final int nr = Integer.parseInt(nrMatcher.group());
            if (nr >= 10) {
                return true;
            }
        }
        return false;
    }

    private String reverseString(final String input) {
        final StringBuilder sb = new StringBuilder(input);
        return sb.reverse().toString();
    }

    private String getNrToTheRight(final String input, final int index) {
        final Pattern nrPattern = Pattern.compile(NR_PATTERN);
        final Matcher matcher = nrPattern.matcher(input);
        return matcher.find(index) ? matcher.group() : null;
    }

    private String getNrToTheLeft(final String input, final int index) {
        final String reverse = reverseString(input.substring(0, index));
        final String nr = getNrToTheRight(reverse, 0);
        return (nr != null) ? reverseString(nr) : null;
    }

    private String buildExplodedResult(final String input, final String pair, final int pairIndex) {
        final String nrLeftOfPair = getNrToTheLeft(input, pairIndex);
        final String nrRightOfPair = getNrToTheRight(input, pairIndex + pair.length());

        final Pattern nrPattern = Pattern.compile(NR_PATTERN);
        final Matcher nrMatcher = nrPattern.matcher(pair);
        nrMatcher.find();
        final int pairLeftNr = Integer.parseInt(nrMatcher.group());
        nrMatcher.find();
        final int pairRightNr = Integer.parseInt(nrMatcher.group());

        final StringBuilder result = new StringBuilder();
        final String leftPart = input.substring(0, pairIndex);
        final String rightPart = input.substring(pairIndex + pair.length());
        if (nrLeftOfPair != null) {
            final int leftNrIndex = leftPart.lastIndexOf(nrLeftOfPair);
            result.append(input.substring(0, leftNrIndex));
            result.append((Integer.parseInt(nrLeftOfPair) + pairLeftNr));
            result.append(input.substring(leftNrIndex + String.valueOf(nrLeftOfPair).length(), pairIndex));
        } else {
            result.append(leftPart);
        }
        result.append("0");

        if (nrRightOfPair != null) {
            final int rightNrIndex = rightPart.indexOf(nrRightOfPair);
            result.append(rightPart.substring(0, rightNrIndex));
            result.append((Integer.parseInt(nrRightOfPair) + pairRightNr));
            result.append(rightPart.substring(rightNrIndex + String.valueOf(nrRightOfPair).length()));
        } else {
            result.append(rightPart);
        }
        return result.toString();
    }

    private String explode(final String input) {
        final Pattern pairPattern = Pattern.compile(PAIR_PATTERN);
        final Matcher matcher = pairPattern.matcher(input);
        int i = 0;
        while (matcher.find(i)) {
            final String pair = matcher.group();
            final int pairIndex = matcher.start();
            i = pairIndex + pair.length();
            if (isExplodablePair(input, pairIndex)) {
                return buildExplodedResult(input, pair, pairIndex);
            }
        }
        return input;
    }

    private String split(final String input) {
        final Pattern nrPattern = Pattern.compile(NR_PATTERN);
        final Matcher nrMatcher = nrPattern.matcher(input);
        while (nrMatcher.find()) {
            final String sNr = nrMatcher.group();
            final int nr = Integer.parseInt(sNr);
            if (nr >= 10) {
                final StringBuilder result = new StringBuilder();
                final int leftNr = nr / 2;
                final int rightNr = (int) Math.ceil(nr / 2.0);
                result.append(input.substring(0, input.indexOf(sNr)));
                result.append("[" + leftNr + "," + rightNr + "]");
                result.append(input.substring(input.indexOf(sNr) + sNr.length()));
                return result.toString();
            }
        }
        return input;
    }

    private String reduce(String result) {
        while (containsExplodablePair(result) || containsSplittablePair(result)) {
            if (containsExplodablePair(result)) {
                result = explode(result);
            }
            if (!containsExplodablePair(result) && containsSplittablePair(result)) {
                result = split(result);
            }
        }
        return result;
    }

    private String add(final String left, final String right) {
        return "[" + left + "," + right + "]";
    }

    private int getMagnitude(String input) {
        final Pattern pairPattern = Pattern.compile(PAIR_PATTERN);
        Matcher matcher = pairPattern.matcher(input);
        while (matcher.find()) {
            final String pair = matcher.group();
            final Pattern nrPattern = Pattern.compile(NR_PATTERN);
            final Matcher nrMatcher = nrPattern.matcher(pair);
            nrMatcher.find();
            final int pairLeftNr = Integer.parseInt(nrMatcher.group());
            nrMatcher.find();
            final int pairRightNr = Integer.parseInt(nrMatcher.group());
            final int result = 3 * pairLeftNr + 2 * pairRightNr;
            input = input.substring(0, input.indexOf(pair)) + result
                    + input.substring(input.indexOf(pair) + pair.length());
            matcher = pairPattern.matcher(input);
        }
        return Integer.parseInt(input);
    }

    private int getHighestSum(final List<String> lines) {
        int highest = 0;
        for (int i = 0; i < lines.size() - 1; i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                final String nr1 = lines.get(i);
                final String nr2 = lines.get(j);

                String sum = add(nr1, nr2);
                sum = reduce(sum);
                int magnitude = getMagnitude(sum);
                if (magnitude > highest) {
                    highest = magnitude;
                }

                sum = add(nr2, nr1);
                sum = reduce(sum);
                magnitude = getMagnitude(sum);
                if (magnitude > highest) {
                    highest = magnitude;
                }
            }
        }
        return highest;
    }

    private int getTotalMagnitude(final List<String> lines) {
        String result = lines.get(0);
        for (int i = 1; i < lines.size(); i++) {
            result = add(result, lines.get(i));
            result = reduce(result);
        }
        return getMagnitude(result);
    }

    private int runPart2(final List<String> lines) {
        return getHighestSum(lines);
    }

    private int runPart1(final List<String> lines) {
        return getTotalMagnitude(lines);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day18().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day18().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}
