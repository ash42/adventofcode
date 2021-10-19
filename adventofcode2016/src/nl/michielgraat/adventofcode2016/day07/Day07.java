package nl.michielgraat.adventofcode2016.day07;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day07 {

    private static final String FILENAME = "day07.txt";

    private void getSubstrings(String s, List<String> nb, List<String> b) {
        boolean inBracket = false;
        StringBuilder sbb = new StringBuilder();
        StringBuilder sbnb = new StringBuilder();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (c == '[') {
                inBracket = true;
                if (sbnb.length() > 0) {
                    nb.add(sbnb.toString());
                    sbnb = new StringBuilder();
                }
            } else if (c == ']') {
                inBracket = false;
                if (sbb.length() > 0) {
                    b.add(sbb.toString());
                    sbb = new StringBuilder();
                }
            } else {
                if (inBracket) {
                    sbb.append(c);
                } else {
                    sbnb.append(c);
                }
            }
        }
        if (sbnb.length() > 0) {
            nb.add(sbnb.toString());
        }
        if (sbb.length() > 0) {
            b.add(sbb.toString());
        }
    }

    private boolean containsAbba(String s) {
        for (int i = 0; i < s.length() - 3; i += 1) {
            if (s.charAt(i) != s.charAt(i + 1) && s.charAt(i) == s.charAt(i + 3)
                    && s.charAt(i + 1) == s.charAt(i + 2)) {
                return true;
            }
        }
        return false;
    }

    private List<String> getAllAbas(String s) {
        List<String> abas = new ArrayList<>();
        for (int i=0; i<s.length()-2; i+=1) {
            String sub = s.substring(i, i+3);
            if (sub.charAt(0) != sub.charAt(1) && sub.charAt(0) == sub.charAt(2)) {
                abas.add(sub);
            }
        }
        return abas;
    }

    private List<String> getAllAbas(List<String> lines) {
        List<String> abas = new ArrayList<>();
       for (String line : lines) {
           abas.addAll(getAllAbas(line));
       } 
       return abas;
    }

    private boolean containsAbba(List<String> lines) {
        for (String line : lines) {
            if (containsAbba(line)) {
                return true;
            }
        }
        return false;
    }

    private String abaToBab(String s) {
        return Character.toString(s.charAt(1)) + Character.toString(s.charAt(0)) + Character.toString(s.charAt(1));
    }

    private boolean abaBab(String aba, String bab) {

        return aba.charAt(0) == bab.charAt(1) && aba.charAt(1) == bab.charAt(0);
    }

    private int runPart2(List<String> lines) {
        int total = 0;
        for (String line : lines) {
            List<String> bracket = new ArrayList<>();
            List<String> noBracket = new ArrayList<>();
            getSubstrings(line, noBracket, bracket);
            List<String> abas = getAllAbas(noBracket);
            List<String> babs = getAllAbas(bracket);
            for (String aba : abas) {
                if (babs.contains(abaToBab(aba))) {
                    total++;
                    break;
                }
            }
        }
        return total;
    }

    private int runPart1(List<String> lines) {
        int total = 0;
        for (String line : lines) {
            List<String> bracket = new ArrayList<>();
            List<String> noBracket = new ArrayList<>();
            getSubstrings(line, noBracket, bracket);
            if (containsAbba(noBracket) && !containsAbba(bracket)) {
                total++;
            }
        }
        return total;
    }

    public static void main(String... args) {
        List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day07().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day07().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }

}
