package nl.michielgraat.adventofcode2022.day10;

import java.util.Arrays;
import java.util.List;

public class CRT {
    private final char[][] image = new char[6][40];
    private int cycle = 0;
    private int registerX = 1;
    private int curPixel = 0;
    private int totalSignalStrength = 0;
    private StringBuilder row = new StringBuilder();

    private boolean isInSprite(final int curPixel, final int middle) {
        return curPixel == middle - 1 || curPixel == middle || curPixel == middle + 1;
    }

    private void drawRow() {
        if (cycle % 40 == 0) {
            final String rowString = row.toString();
            image[(cycle - 1) / 40] = Arrays.copyOf(rowString.toCharArray(), rowString.length());
            row = new StringBuilder();
            curPixel = 0;
        }
    }

    private void drawPixel() {
        row.append(isInSprite(curPixel, registerX) ? "#" : ".");
    }

    private void cycle() {
        cycle++;
        curPixel++;
    }

    private boolean checkSignalStrength(final int cycle) {
        return cycle <= 220 && cycle % 20 == 0 && cycle % 40 != 0;
    }
    
    private int increaseTotal(final int cycle, final int register) {
        if (checkSignalStrength(cycle)) {
            return register * cycle;
        }
        return 0;
    }

    public void drawImage(final List<String> input) {
        for (final String line : input) {
            drawRow();
            drawPixel();
            if (line.equals("noop")) {
                totalSignalStrength += increaseTotal(cycle+1, registerX);
                cycle();
            } else {
                final int value = Integer.parseInt(line.split(" ")[1]);
                totalSignalStrength += increaseTotal(cycle+1, registerX);
                cycle();
                drawRow();
                drawPixel();
                totalSignalStrength += increaseTotal(cycle+1, registerX);
                cycle();
                registerX += value;
            }
        }
        drawRow();
    }

    public String printImage() {
        final StringBuilder sb = new StringBuilder();
        sb.append('\n');
        sb.append('\n');
        for (int y = 0; y < image.length; y++) {
            for (int x = 0; x < image[0].length; x++) {
                sb.append(image[y][x] == '#' ? '#' : ' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public int getTotalSignalStrength() {
        return totalSignalStrength;
    }
}
