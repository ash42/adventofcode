package nl.michielgraat.adventofcode2024.day14;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day14 extends AocSolver {

    protected Day14(final String filename) {
        super(filename);
    }

    private List<Robot> parseRobots(final List<String> input) {
        final List<Robot> robots = new ArrayList<>();
        for (final String line : input) {
            final int x = Integer.parseInt(line.split("=")[1].split(",")[0]);
            final int y = Integer.parseInt(line.split("=")[1].split(",")[1].split(" ")[0]);
            final int velX = Integer.parseInt(line.split("v=")[1].split(",")[0]);
            final int velY = Integer.parseInt(line.split("v=")[1].split(",")[1]);
            robots.add(new Robot(x, y, velX, velY));
        }
        return robots;
    }

    private void moveRobots(final List<Robot> robots, final int sizeX, final int sizeY, final int times) {
        for (int i = 1; i <= times; i++) {
            for (final Robot robot : robots) {
                robot.move(sizeX, sizeY);
            }
        }
    }

    private long getSafetyFactor(final List<Robot> robots, final int sizeX, final int sizeY) {
        final int middleX = sizeX / 2;
        final int middleY = sizeY / 2;
        final long q1 = robots.stream().filter(r -> r.getX() < middleX && r.getY() < middleY).count();
        final long q2 = robots.stream().filter(r -> r.getX() > middleX && r.getY() < middleY).count();
        final long q3 = robots.stream().filter(r -> r.getX() < middleX && r.getY() > middleY).count();
        final long q4 = robots.stream().filter(r -> r.getX() > middleX && r.getY() > middleY).count();
        return q1 * q2 * q3 * q4;
    }

    private boolean hasHorizontalLine(final List<Robot> robots) {
        final int lineSize = 10;
        for (final Robot robot : robots) {
            final int x = robot.getX();
            for (int i = 1; i <= lineSize; i++) {
                if (!robots.contains(new Robot(x + i, robot.getY(), 0, 0))) {
                    break;
                } else if (i == lineSize) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected String runPart2(final List<String> input) {

        final List<Robot> robots = parseRobots(input);
        final int sizeX = 101;
        final int sizeY = 103;
        int seconds = 1;
        while (true) {
            moveRobots(robots, sizeX, sizeY, 1);
            if (hasHorizontalLine(robots)) {
                break;
            }
            seconds++;

        }
        return String.valueOf(seconds);
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Robot> robots = parseRobots(input);
        final int sizeX = 101;
        final int sizeY = 103;
        moveRobots(robots, sizeX, sizeY, 100);
        return String.valueOf(getSafetyFactor(robots, sizeX, sizeY));
    }

    public static void main(final String... args) {
        new Day14("day14.txt");
    }
}
