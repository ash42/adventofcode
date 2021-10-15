package nl.michielgraat.adventofcode2016.day01;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day01 {

    private static final String FILENAME = "day01.txt";

    private Direction getNewDirection(Direction cur, Turn turn) {
        switch (turn) {
            case L:
                switch (cur) {
                    case N:
                        return Direction.W;
                    case E:
                        return Direction.N;
                    case S:
                        return Direction.E;
                    case W:
                        return Direction.S;
                }
            case R:
                switch (cur) {
                    case N:
                        return Direction.E;
                    case E:
                        return Direction.S;
                    case S:
                        return Direction.W;
                    case W:
                        return Direction.N;
                }
        }
        throw new IllegalArgumentException("Unknown direction: " + cur + " or turn: " + turn);
    }

    public int runPart2(List<String> lines) {
        String[] instructions = lines.get(0).split(", ");
        Direction curDir = Direction.N;
        List<Point> visited = new ArrayList<>();
        Point cur = new Point(0, 0);
        visited.add(cur);
        for (String instruction : instructions) {
            int steps = Integer.parseInt(instruction.substring(1));
            curDir = getNewDirection(curDir, Turn.valueOf(instruction.substring(0, 1)));

            for (int i = 0; i < steps; i++) {
                switch (curDir) {
                    case N:
                        cur = new Point(cur.x, cur.y + 1);
                        break;
                    case E:
                        cur = new Point(cur.x + 1, cur.y);
                        break;
                    case S:
                        cur = new Point(cur.x, cur.y - 1);
                        break;
                    case W:
                        cur = new Point(cur.x - 1, cur.y);
                        break;
                }
                if (!visited.contains(cur)) {
                    visited.add(cur);
                } else {
                    return Math.abs(cur.x) + Math.abs(cur.y);
                }
            }
        }
        return -1;
    }

    public int runPart1(List<String> lines) {
        String[] instructions = lines.get(0).split(", ");
        Direction curDir = Direction.N;
        int x = 0;
        int y = 0;
        for (String instruction : instructions) {
            int steps = Integer.parseInt(instruction.substring(1));
            curDir = getNewDirection(curDir, Turn.valueOf(instruction.substring(0, 1)));

            switch (curDir) {
                case N:
                    y += steps;
                    break;
                case E:
                    x += steps;
                    break;
                case S:
                    y -= steps;
                    break;
                case W:
                    x -= steps;
                    break;
            }
        }

        return Math.abs(x) + Math.abs(y);
    }

    public static void main(String... args) {
        List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day01().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day01().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }

}

enum Direction {
    N, E, S, W;
}

enum Turn {
    L, R;
}

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }
}