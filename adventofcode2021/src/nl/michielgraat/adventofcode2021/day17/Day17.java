package nl.michielgraat.adventofcode2021.day17;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day17 {

    private static final String FILENAME = "day17.txt";

    private boolean shotPast(final int x, final int y, final int maxX, final int minY) {
        return x > maxX || y < minY;
    }

    private boolean inTarget(final int x, final int y, final int minX, final int maxX, final int minY, final int maxY) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    private int findTargetHittingVelocities(final String ins, final boolean part1) {
        final int minX = Integer.parseInt(ins.substring(ins.indexOf("x=") + 2, ins.indexOf("..")));
        final int maxX = Integer.parseInt(ins.substring(ins.indexOf("..") + 2, ins.indexOf(",")));
        final int minY = Integer.parseInt(ins.substring(ins.indexOf("y=") + 2, ins.lastIndexOf((".."))));
        final int maxY = Integer.parseInt(ins.substring(ins.lastIndexOf("..") + 2));

        int max = 0;
        int nrHit = 0;
        for (int x = 0; x <= maxX; x++) {
            for (int y = minY - 1; y <= Math.abs(minY) + 1; y++) {
                boolean hitTarget = false;
                int highest = y;
                int velocityX = x - 1;
                int velocityY = y - 1;
                int posX = x;
                int posY = y;
                while (!shotPast(posX, posY, maxX, minY)) {
                    if (posY > highest) {
                        highest = posY;
                    }
                    if (inTarget(posX, posY, minX, maxX, minY, maxY)) {
                        hitTarget = true;
                    }
                    posX += velocityX;
                    posY += velocityY;
                    velocityY--;
                    if (velocityX > 0) {
                        velocityX--;
                    }
                }
                if (hitTarget) {
                    nrHit++;
                    if (highest > max) {
                        max = highest;
                    }
                }
            }
        }

        return (part1) ? max : nrHit;
    }

    private int runPart2(final List<String> lines) {
        return findTargetHittingVelocities(lines.get(0), false);
    }

    private int runPart1(final List<String> lines) {
        return findTargetHittingVelocities(lines.get(0), true);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day17().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day17().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}
