package nl.michielgraat.adventofcode2016.day10;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day10 {

    private static final String FILENAME = "day10.txt";

    private static final int DIMENSION = 250;

    private int[] low = new int[DIMENSION];
    private int[] high = new int[DIMENSION];
    private int[] output = new int[DIMENSION];

    public Day10() {
        for (int i=0; i < DIMENSION; i++) {
            low[i] = -1;
            high[i] = -1;
            output[i] = -1;
        }
    }

    private void printBots() {
        for (int i=0; i<DIMENSION; i++) {
            System.out.println("Bot " + i + " has low value: " + low[i] + ", high value: " + high[i]);
        }
    }

    private void setVal(int bot, int val) {
        if (low[bot] == -1) {
            low[bot] = val;
        } else {
            if (val < low[bot]) {
                high[bot] = low[bot];
                low[bot] = val;
            } else {
                high[bot] = val;
            }
        }
    }

    private int setInitialValues(List<String> lines, int l, int r) {
        for (String line : lines) {
            if (line.startsWith("value")) {
                String[] parts = line.split(" ");
                int val = Integer.parseInt(parts[1]);
                int bot = Integer.parseInt(parts[5]);
                //System.out.println("Bot " + bot + " starts with " + val);
                setVal(bot, val);
                if (hasResult(bot, l, r)) {
                    return bot;
                }
            }
        }
        //printBots();
        return -1;
    }

    private boolean canPerform(int bot) {
        return low[bot] != -1 && high[bot] != -1;
    }

    private boolean hasResult(int bot, int l, int h) {
        return low[bot] == l && high[bot] == h;
    }

    private int handleInstructions(List<String> lines, int l, int h) {
        for (String line : lines) {
            if (line.startsWith("bot")) {
                String parts[] = line.split(" ");
                int startBot = Integer.parseInt(parts[1]);
                if (canPerform(startBot)) {
                    int dest1 = Integer.parseInt(parts[6]);
                    int dest2 = Integer.parseInt(parts[11]);
                    if (parts[5].equals("bot")) {
                        setVal(dest1,low[startBot]);
                    } else {
                        output[dest1] = low[startBot];
                    }
                    if (parts[10].equals("bot")) {
                        setVal(dest2, high[startBot]);
                    } else {
                        output[dest2] = high[startBot];
                    }
                    low[startBot] = -1;
                    high[startBot] = -1;
                    if (hasResult(dest1, l, h)) {
                        return dest1;
                    }
                    if (hasResult(dest2, l, h)) {
                        return dest2;
                    }
                }
            }
        }
        return -1;
    }

    private int runPart2(List<String> lines, int l, int h) {
        setInitialValues(lines, l, h);
        while (output[0] == -1 || output[1] == -1 || output[2] == -1) {
            handleInstructions(lines, l, h);
        }
        return output[0]*output[1]*output[2];
    }

    private int runPart1(List<String> lines, int l, int h) {
        int result = setInitialValues(lines, l, h);
        while (result == -1) {
            result = handleInstructions(lines, l, h);
        }
        return result;

    }

    public static void main(String... args) {
        List<String> lines = FileReader.getStringList(FILENAME);
        int low = 17;
        int high = 61;
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day10().runPart1(lines, low, high));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day10().runPart2(lines, low, high));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }

}
