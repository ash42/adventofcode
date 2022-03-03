package nl.michielgraat.adventofcode2016.day16;

import java.util.Calendar;

public class Day16 {

    private String checksum(final String a) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length() - 1; i += 2) {
            final char c1 = a.charAt(i);
            final char c2 = a.charAt(i + 1);
            if (c1 == c2) {
                sb.append("1");
            } else {
                sb.append("0");
            }
        }
        return sb.toString();
    }

    private String doStep(final String a) {
        final String b = new StringBuilder(a).reverse().toString().replaceAll("0", "x").replaceAll("1", "0")
                .replaceAll("x", "1");
        return a + "0" + b;
    }

    private String solve(String input, final int length) {
        while (input.length() < length) {
            input = doStep(input);
        }
        input = input.substring(0, length);
        String checksum = checksum(input);
        while (checksum.length() % 2 == 0) {
            checksum = checksum(checksum);
        }
        return checksum;
    }

    public String runPart2(final String input, final int length) {
        return solve(input, length);
    }

    public String runPart1(final String input, final int length) {
        return solve(input, length);
    }

    public static void main(final String... args) {
        final String input = "11100010111110100";
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day16().runPart1(input, 272));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day16().runPart2(input, 35651584));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}
