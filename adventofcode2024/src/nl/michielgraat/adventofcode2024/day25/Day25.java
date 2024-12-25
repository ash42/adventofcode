package nl.michielgraat.adventofcode2024.day25;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day25 extends AocSolver {

    protected Day25(final String filename) {
        super(filename);
    }

    private List<Lock> getLocks(final List<String> input) {
        final List<Lock> result = new ArrayList<>();
        boolean firstLine = true;
        for (int i = 0; i < input.size(); i++) {
            final String line = input.get(i);
            if (firstLine) {
                if (line.equals("#####")) {
                    final int[] heights = new int[5];
                    for (int x = 0; x < line.length(); x++) {
                        int pin = 0;
                        for (int y = i + 1; y <= i + 6; y++) {
                            if (input.get(y).charAt(x) == '#') {
                                pin++;
                            } else {
                                break;
                            }
                        }
                        heights[x] = pin;
                    }
                    result.add(new Lock(heights[0], heights[1], heights[2], heights[3], heights[4]));
                }
                firstLine = false;
            }
            if (line.isBlank()) {
                firstLine = true;
            }
        }
        return result;
    }

    private List<Key> getKeys(final List<String> input) {
        final List<Key> result = new ArrayList<>();
        boolean firstLine = true;
        for (int i = 0; i < input.size(); i++) {
            final String line = input.get(i);
            if (firstLine) {
                if (input.get(i + 6).equals("#####")) {
                    final int[] heights = new int[5];
                    for (int x = 0; x < line.length(); x++) {
                        int pin = 0;
                        for (int y = i + 5; y > i; y--) {
                            if (input.get(y).charAt(x) == '#') {
                                pin++;
                            } else {
                                break;
                            }
                        }
                        heights[x] = pin;
                    }
                    result.add(new Key(heights[0], heights[1], heights[2], heights[3], heights[4]));
                }
                firstLine = false;
            }
            if (line.isBlank()) {
                firstLine = true;
            }
        }
        return result;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return "Merry Christmas!";
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Lock> locks = getLocks(input);
        final List<Key> keys = getKeys(input);
        int totalFit = 0;
        for (final Key key : keys) {
            for (final Lock lock : locks) {
                if (key.fits(lock)) {
                    totalFit++;
                }
            }
        }
        return String.valueOf(totalFit);
    }

    public static void main(final String... args) {
        new Day25("day25.txt");
    }
}

record Lock(int pin1, int pin2, int pin3, int pin4, int pin5) {
}

record Key(int pin1, int pin2, int pin3, int pin4, int pin5) {
    boolean fits(final Lock l) {
        final int c1 = pin1 + l.pin1();
        final int c2 = pin2 + l.pin2();
        final int c3 = pin3 + l.pin3();
        final int c4 = pin4 + l.pin4();
        final int c5 = pin5 + l.pin5();
        return c1 <= 5 && c2 <= 5 && c3 <= 5 && c4 <= 5 && c5 <= 5;
    }
}
