package nl.michielgraat.adventofcode2016.day15;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day15 {

    private static final String FILENAME = "day15.txt";
    private static final String FILENAME2 = "day15-2.txt";

    private int[] getOffsets(final List<String> lines) {
        final int[] offsets = new int[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            offsets[i] = Integer
                    .parseInt(line.substring(line.indexOf("position ") + "position ".length(), line.length() - 1));
        }
        return offsets;
    }

    private int[] getPositions(final List<String> lines) {
        final int[] positions = new int[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            positions[i] = Integer
                    .parseInt(line.substring(line.indexOf("has ") + "has ".length(), line.indexOf(" positions")));
        }
        return positions;
    }

    public int runPart2(final List<String> lines) {
        boolean done = false;
        int time = 0;
        int incr = 1;
        final int[] offsets = getOffsets(lines);
        final int[] positions = getPositions(lines);
        while (!done) {
            if ((time + 1 + offsets[0]) % positions[0] == 0) {
                incr = positions[0];
                if ((time + 2 + offsets[1]) % positions[1] == 0) {
                    incr = positions[0] * positions[1];
                    if ((time + 3 + offsets[2]) % positions[2] == 0) {
                        incr = positions[0] * positions[1] * positions[2];
                        if ((time + 4 + offsets[3]) % positions[3] == 0) {
                            incr = positions[0] * positions[1] * positions[2] * positions[3];
                            if ((time + 5 + offsets[4]) % positions[4] == 0) {
                                incr = positions[0] * positions[1] * positions[2] * positions[3] * positions[4];
                                if ((time + 6 + offsets[5]) % positions[5] == 0) {
                                    incr = positions[0] * positions[1] * positions[2] * positions[3] * positions[4]
                                            * positions[5];
                                    if ((time + 7 + offsets[6]) % positions[6] == 0) {
                                        done = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            time += incr;
        }
        time -= incr;
        return time;
    }

    public int runPart1(final List<String> lines) {
        boolean done = false;
        int time = 0;
        int incr = 1;
        final int[] offsets = getOffsets(lines);
        final int[] positions = getPositions(lines);
        while (!done) {
            if ((time + 1 + offsets[0]) % positions[0] == 0) {
                incr = positions[0];
                if ((time + 2 + offsets[1]) % positions[1] == 0) {
                    incr = positions[0] * positions[1];
                    if ((time + 3 + offsets[2]) % positions[2] == 0) {
                        incr = positions[0] * positions[1] * positions[2];
                        if ((time + 4 + offsets[3]) % positions[3] == 0) {
                            incr = positions[0] * positions[1] * positions[2] * positions[3];
                            if ((time + 5 + offsets[4]) % positions[4] == 0) {
                                incr = positions[0] * positions[1] * positions[2] * positions[3] * positions[4];
                                if ((time + 6 + offsets[5]) % positions[5] == 0) {
                                    done = true;
                                }
                            }
                        }
                    }
                }
            }
            time += incr;
        }
        time -= incr;
        return time;
    }

    public static void main(final String... args) {
        List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day15().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        lines = FileReader.getStringList(FILENAME2);
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day15().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }

}
