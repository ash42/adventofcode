package nl.michielgraat.adventofcode2016.day06;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day06 {

    private static final String FILENAME = "day06.txt";

    private String getCharWithMinFreq(Map<Character, Integer> m) {
        int min = Integer.MAX_VALUE;
        Entry<Character, Integer> minEntry = null;
        for (Entry<Character, Integer> entry : m.entrySet()) {
            if (entry.getValue() < min) {
                minEntry = entry;
                min = entry.getValue();
            }
        }
        return Character.toString(minEntry.getKey());
    }

    private String getCharWithMaxFreq(Map<Character, Integer> m) {
        int max = Integer.MIN_VALUE;
        Entry<Character, Integer> maxEntry = null;
        for (Entry<Character, Integer> entry : m.entrySet()) {
            if (entry.getValue() > max) {
                maxEntry = entry;
                max = entry.getValue();
            }
        }
        return Character.toString(maxEntry.getKey());
    }

    private String runPart2(List<String> lines) {
        Map<Character, Integer> charToFreq1 = new HashMap<>();
        Map<Character, Integer> charToFreq2 = new HashMap<>();
        Map<Character, Integer> charToFreq3 = new HashMap<>();
        Map<Character, Integer> charToFreq4 = new HashMap<>();
        Map<Character, Integer> charToFreq5 = new HashMap<>();
        Map<Character, Integer> charToFreq6 = new HashMap<>();
        Map<Character, Integer> charToFreq7 = new HashMap<>();
        Map<Character, Integer> charToFreq8 = new HashMap<>();

        for (String line : lines) {
            char c1 = line.charAt(0);
            char c2 = line.charAt(1);
            char c3 = line.charAt(2);
            char c4 = line.charAt(3);
            char c5 = line.charAt(4);
            char c6 = line.charAt(5);
            char c7 = line.charAt(6);
            char c8 = line.charAt(7);

            int freq1 = (charToFreq1.keySet().contains(c1)) ? charToFreq1.get(c1) + 1 : 1;
            int freq2 = (charToFreq2.keySet().contains(c2)) ? charToFreq2.get(c2) + 1 : 1;
            int freq3 = (charToFreq3.keySet().contains(c3)) ? charToFreq3.get(c3) + 1 : 1;
            int freq4 = (charToFreq4.keySet().contains(c4)) ? charToFreq4.get(c4) + 1 : 1;
            int freq5 = (charToFreq5.keySet().contains(c5)) ? charToFreq5.get(c5) + 1 : 1;
            int freq6 = (charToFreq6.keySet().contains(c6)) ? charToFreq6.get(c6) + 1 : 1;
            int freq7 = (charToFreq7.keySet().contains(c7)) ? charToFreq7.get(c7) + 1 : 1;
            int freq8 = (charToFreq8.keySet().contains(c8)) ? charToFreq8.get(c8) + 1 : 1;

            charToFreq1.put(c1, freq1);
            charToFreq2.put(c2, freq2);
            charToFreq3.put(c3, freq3);
            charToFreq4.put(c4, freq4);
            charToFreq5.put(c5, freq5);
            charToFreq6.put(c6, freq6);
            charToFreq7.put(c7, freq7);
            charToFreq8.put(c8, freq8);
        }
        return getCharWithMinFreq(charToFreq1) + getCharWithMinFreq(charToFreq2) + getCharWithMinFreq(charToFreq3)
                + getCharWithMinFreq(charToFreq4) + getCharWithMinFreq(charToFreq5) + getCharWithMinFreq(charToFreq6)
                + getCharWithMinFreq(charToFreq7) + getCharWithMinFreq(charToFreq8);
    }

    private String runPart1(List<String> lines) {

        Map<Character, Integer> charToFreq1 = new HashMap<>();
        Map<Character, Integer> charToFreq2 = new HashMap<>();
        Map<Character, Integer> charToFreq3 = new HashMap<>();
        Map<Character, Integer> charToFreq4 = new HashMap<>();
        Map<Character, Integer> charToFreq5 = new HashMap<>();
        Map<Character, Integer> charToFreq6 = new HashMap<>();
        Map<Character, Integer> charToFreq7 = new HashMap<>();
        Map<Character, Integer> charToFreq8 = new HashMap<>();

        for (String line : lines) {
            char c1 = line.charAt(0);
            char c2 = line.charAt(1);
            char c3 = line.charAt(2);
            char c4 = line.charAt(3);
            char c5 = line.charAt(4);
            char c6 = line.charAt(5);
            char c7 = line.charAt(6);
            char c8 = line.charAt(7);

            int freq1 = (charToFreq1.keySet().contains(c1)) ? charToFreq1.get(c1) + 1 : 1;
            int freq2 = (charToFreq2.keySet().contains(c2)) ? charToFreq2.get(c2) + 1 : 1;
            int freq3 = (charToFreq3.keySet().contains(c3)) ? charToFreq3.get(c3) + 1 : 1;
            int freq4 = (charToFreq4.keySet().contains(c4)) ? charToFreq4.get(c4) + 1 : 1;
            int freq5 = (charToFreq5.keySet().contains(c5)) ? charToFreq5.get(c5) + 1 : 1;
            int freq6 = (charToFreq6.keySet().contains(c6)) ? charToFreq6.get(c6) + 1 : 1;
            int freq7 = (charToFreq7.keySet().contains(c7)) ? charToFreq7.get(c7) + 1 : 1;
            int freq8 = (charToFreq8.keySet().contains(c8)) ? charToFreq8.get(c8) + 1 : 1;

            charToFreq1.put(c1, freq1);
            charToFreq2.put(c2, freq2);
            charToFreq3.put(c3, freq3);
            charToFreq4.put(c4, freq4);
            charToFreq5.put(c5, freq5);
            charToFreq6.put(c6, freq6);
            charToFreq7.put(c7, freq7);
            charToFreq8.put(c8, freq8);
        }
        return getCharWithMaxFreq(charToFreq1) + getCharWithMaxFreq(charToFreq2) + getCharWithMaxFreq(charToFreq3)
                + getCharWithMaxFreq(charToFreq4) + getCharWithMaxFreq(charToFreq5) + getCharWithMaxFreq(charToFreq6)
                + getCharWithMaxFreq(charToFreq7) + getCharWithMaxFreq(charToFreq8);
    }

    public static void main(String... args) {
        List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day06().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day06().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }

}