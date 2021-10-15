package nl.michielgraat.adventofcode2016.day03;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day03 {

    private static final String FILENAME = "day03.txt";

    private boolean isValid(int first, int second, int third) {
        return first + second > third && second + third > first && first + third > second;
    }

    private boolean isValid(String first, String second, String third) {
        return isValid(Integer.parseInt(first), Integer.parseInt(second), Integer.parseInt(third));
    }
    
    private boolean isValid(String[] triangle) {
        return isValid(triangle[0], triangle[1], triangle[2]);
    }

    public int runPart2(List<String> lines) {
        while (lines.size() % 3 != 0) {
            lines.add("0 0 0");
        }

        int nr = 0;
        for (int i = 0; i < lines.size(); i+=3) {
            String[] line1 = lines.get(i).trim().split("\\s+");
            String[] line2 = lines.get(i+1).trim().split("\\s+");
            String[] line3 = lines.get(i+2).trim().split("\\s+");

            for (int j=0; j<3; j++) {
                if (isValid(line1[j], line2[j], line3[j])) {
                    nr++;
                }
            }
        }

        return nr;
    }
    
    public int runPart1(List<String> lines) {
        int nr = 0;
        for (String line : lines) {
            if (isValid(line.trim().split("\\s+"))) {
                nr++;
            } 
        }
        return nr;
    }

    public static void main(String... args) {
        List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day03().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day03().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}
