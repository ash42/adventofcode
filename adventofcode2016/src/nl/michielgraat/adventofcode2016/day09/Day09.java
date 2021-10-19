package nl.michielgraat.adventofcode2016.day09;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day09 {

    private static final String FILENAME = "day09.txt";

    private long runPart2(String input) {
        //System.out.println(input);
        int[] weights = new int[input.length()];
        long total = 0;
        for (int i = 0; i < weights.length; i++) {
            weights[i] = 1;
        }
        for (int i=0; i < input.length(); i++) {
            char c = input.charAt(i);
            //System.out.println("At: " + c);
            if (c == ' ' || c == ')') {
                //ignore
            } else if (c != '(') { 
                total += weights[i];
            } else {
                String marker = input.substring(i + 1, input.indexOf(")", i));
                int nrOfChars = Integer.parseInt(marker.substring(0, marker.indexOf("x")));
                int times = Integer.parseInt(marker.substring(marker.indexOf("x") + 1));
                int end = input.indexOf(")", i);
                for (int j=end+1; j < end+nrOfChars+1; j++) {
                    weights[j] = weights[j]*times;
                }
                i = end;
            }
        }
        return total;
    }

    private int runPart1(String input) {
        // System.out.println("Input: " + input);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            // System.out.println("Index: " + i);
            char c = input.charAt(i);
            // System.out.println("Current: " + c);
            if (c != '(') {
                sb.append(c);
            } else if (c == ' ') {
                // ignore
            } else {
                String marker = input.substring(i + 1, input.indexOf(")", i));
                // System.out.print("Marker: " + marker + " times: ");
                int nrOfChars = Integer.parseInt(marker.substring(0, marker.indexOf("x")));
                int times = Integer.parseInt(marker.substring(marker.indexOf("x") + 1));
                // System.out.print(times + " nrOfChars: " + nrOfChars + " string to repeat: ");
                int end = input.indexOf(")", i);
                String repeatString = input.substring(end + 1, end + 1 + nrOfChars);
                // System.out.println(repeatString);
                for (int j = 0; j < times; j++) {
                    sb.append(repeatString);
                }
                i = end + repeatString.length();
            }
        }
        // System.out.println(sb.toString());
        return sb.length();
    }

    public static void main(String... args) {
        List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day09().runPart1(lines.get(0)));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day09().runPart2(lines.get(0)));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }

}
