package nl.michielgraat.adventofcode2015.day19;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day19 {
    
    private static final String FILENAME = "day19.txt";
    
    private String replace (String s, String toReplace, String replacement, int pos) {
        return s.substring(0, pos) + replacement + s.substring(pos + toReplace.length());
    }

    public int runPart2(List<String> lines) {
        Map<String, List<String>> replacements = getReplacements(lines);
        String molecule = getMolecule(lines);
        int nr = 0;
        while (!molecule.equals("e")) {
            for (String key : replacements.keySet()) {
                List<String> values = replacements.get(key);
                for (String val : values) {
                    if (molecule.contains(val)) {
                        molecule = replace(molecule, val, key, molecule.lastIndexOf(val));
                        nr++;
                    }
                }
            }
        }

        return nr;
    }

    private Map<String, List<String>> getReplacements(List<String> lines) {
        Map<String, List<String>> replacements = new HashMap<>();

        for (String line : lines) {
            if (line.isBlank()) {
                break;
            }
            String[] elements = line.split(" => ");
            String key = elements[0];
            String value = elements[1];
            List<String> values = replacements.get(key);
            if (values == null) {
                values = new ArrayList<>();
            }
            values.add(value);
            replacements.put(key, values);
        }
        return replacements;
    }

    private String getMolecule(List<String> lines) {
        boolean getIt = false;
        for (String line : lines) {
            if (getIt) {
                return line;
            }
            if (line.isBlank()) {
                getIt = true;
            }
        }
        return null;
    }

    private String getNextElement(String molecule, Map<String, List<String>> replacements) {
        if (molecule.length() != 0) {
            String element = molecule.substring(0,1);
            if (molecule.length() > 1) {
                String nextChar = molecule.substring(1,2);
                if (Character.isLowerCase(nextChar.charAt(0))) {
                    element += nextChar;
                }
            }
            return element;
        }
        return null;
    }

    public int runPart1(List<String> lines) {
        Map<String, List<String>> replacements = getReplacements(lines);
        String molecule = getMolecule(lines);
        
        Set<String> molecules = new TreeSet<>();
        int nrOfCombinations = 0;

        for (int i = 0; i < molecule.length(); i++) {
            String element = getNextElement(molecule.substring(i), replacements);
            List<String> values = replacements.get(element);
            if (values != null) {
                for (String value : values) {
                    String newMolecule = molecule.substring(0,i) + value; 
                    if (i < molecule.length()) {
                        newMolecule += molecule.substring(i+element.length());
                    }
                    molecules.add(newMolecule);
                }
            }
           // System.out.println(element);
            if (element.length() == 2) {
                i++;
            }
        }
        return molecules.size();
        /*for (String key : replacements.keySet()) {
            System.out.print(key + " => ");
            for (String value : replacements.get(key)) {
                System.out.print(value + " ");
            }
            System.out.println();
        }*/
    }

    public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day19().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day19().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}
