package nl.michielgraat.adventofcode2016.day02;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day02 {

    private static final String FILENAME = "day02.txt";
    
    private int[][] values = {
        {0, 0, 1, 0, 0},
        {0, 2, 3, 4, 0},
        {5, 6, 7, 8, 9},
        {0, 0xA, 0xB, 0xC, 0},
        {0, 0, 0xD, 0, 0},
    };

    public String runPart2(List<String> lines) {
        int i = 2;
        int j = 0;
        StringBuilder code = new StringBuilder();
        for (String line : lines) {
            for (String instruction : line.split("")) {
                if (instruction.equals("U") && i-1 >= 0 && values[i-1][j] > 0) {
                    i--;
                } else if (instruction.equals("D") && i+1 < 5 && values[i+1][j] > 0) {
                    i++;
                } else if (instruction.equals("L") && j-1 >= 0 && values[i][j-1] > 0) {
                    j--;
                } else if (instruction.equals("R") && j+1 < 5 && values[i][j+1] > 0) {
                    j++;
                }
            }
            code.append(Integer.toHexString(values[i][j]).toUpperCase());
        }
        return code.toString();
    }

    public String runPart1(List<String> lines) {
        int cur = 5;
        StringBuilder code = new StringBuilder();
        for (String line : lines) {
            for (String instruction : line.split("")) {
                if (instruction.equals("U") && cur >= 4) {
                    cur -= 3;
                } else if (instruction.equals("D") && cur <= 6) {
                    cur += 3;
                } else if (instruction.equals("L") && cur != 1 && cur != 4 && cur != 7) {
                    cur--;
                } else if (instruction.equals("R") && cur != 3 && cur != 6 && cur != 9) {
                    cur++;
                }
            }
            code.append(cur);
        }
        return code.toString();
    }

    public static void main(String... args) {
        List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day02().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day02().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}
