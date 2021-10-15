package nl.michielgraat.adventofcode2016.day04;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day04 {

    private static final String FILENAME = "day04.txt";

    private String calcChecksum(Set<Freq> freqs) {
        StringBuilder sb = new StringBuilder();
        for (Freq f : freqs) {
            sb.append(f.getS());
        }
        return sb.toString().substring(0, 5);
    }

    private Set<Freq> getFreqs(String line) {
        String[] elements = line.split("-");
        Map<String, Integer> letterToFreq = new HashMap<>();
        for (int i = 0; i < elements.length - 1; i++) {
            String[] letters = elements[i].split("");
            for (String letter : letters) {
                int freq = letterToFreq.keySet().contains(letter) ? letterToFreq.get(letter) : 0;
                freq++;
                letterToFreq.put(letter, freq);
            }
        }
        Set<Freq> freqs = new TreeSet<>();
        for (String key : letterToFreq.keySet()) {
            Freq f = new Freq(key, letterToFreq.get(key));
            freqs.add(f);
        }
        return freqs;
    }

    private String rotate(String s) {
        StringBuilder result = new StringBuilder();
        int nr = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1, s.indexOf("[")));
        for (int i = 0; i < s.lastIndexOf("-"); i++) {
            char c = s.charAt(i);
            if (c == '-') {
                result.append(" ");
            } else {
                int ascii = (int) c;
                ascii = ascii - 97;
                ascii += nr;
                ascii = ascii % 26;
                ascii += 97;
                result.append((char) ascii);
            }
        }
        return result.toString();
    }

    public int runPart2(List<String> lines) {
        for (String line : lines) {
            Set<Freq> freqs = getFreqs(line);
            String checkSum = calcChecksum(freqs);
            String given = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
            if (checkSum.equals(given)) {
                String result = rotate(line);
                if (result.contains("north")) {
                    return Integer.parseInt(line.substring(line.lastIndexOf("-") + 1, line.indexOf("[")));
                }
            }
        }
        return -1;
    }

    public int runPart1(List<String> lines) {
        int total = 0;
        for (String line : lines) {
            Set<Freq> freqs = getFreqs(line);
            String checkSum = calcChecksum(freqs);
            String given = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
            if (checkSum.equals(given)) {
                int id = Integer.parseInt(line.substring(line.lastIndexOf("-") + 1, line.indexOf("[")));
                total += id;
            }
        }
        return total;
    }

    public static void main(String... args) {
        List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day04().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day04().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}

class Freq implements Comparable<Freq> {
    String s;
    Integer f;

    public Freq(String s, int freq) {
        this.s = s;
        this.f = freq;
    }

    public String getS() {
        return s;
    }

    public Integer getF() {
        return f;
    }

    @Override
    public int compareTo(Freq o) {
        if (this.getF().equals(o.getF())) {
            return this.getS().compareTo(o.getS());
        } else {
            return o.getF().compareTo(this.getF());
        }
    }
}