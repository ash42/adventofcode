package nl.michielgraat.adventofcode2023.day24;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day24 extends AocSolver {

    protected Day24(String filename) {
        super(filename);
    }

    private List<Line> readLines(final List<String> input) {
        List<Line> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(
                "(-{0,1}(?!0)\\d+),\\s+(-{0,1}(?!0)\\d+),\\s+(-{0,1}(?!0)\\d+)\\s+@\\s+(-{0,1}(?!0)\\d+),\\s+(-{0,1}(?!0)\\d+),\\s+(-{0,1}(?!0)\\d+)");
        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            Line l = new Line(Long.valueOf(matcher.group(1)), Long.valueOf(matcher.group(2)),
                    Long.valueOf(matcher.group(3)), Long.valueOf(matcher.group(4)),
                    Long.valueOf(matcher.group(5)), Long.valueOf(matcher.group(6)));
            result.add(l);
        }
        return result;
    }

    private int getNrCollisionsIn(long start, long end, List<Line> lines) {
        int total = 0;
        for (int i = 0; i < lines.size() - 1; i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                Line l1 = lines.get(i);
                Line l2 = lines.get(j);
                Coordinate c = l1.crossesAt(l2);
                // Could condense the logic below into one if-statement to get the collision
                // inside the test area, but this is a bit more clear I think.
                if ((l1.velX() > 0 && c.x() < l1.x()) || (l1.velX() < 0 && c.x() > l1.x())
                        || (l1.velY() > 0 && c.y() < l1.y()) || (l1.velY() < 0 && c.y() > l1.y())) {
                    // Hailstones crossed in the past for hailstone A
                } else if ((l2.velX() > 0 && c.x() < l2.x()) || (l2.velX() < 0 && c.x() > l2.x())
                        || (l2.velY() > 0 && c.y() < l2.y()) || (l2.velY() < 0 && c.y() > l2.y())) {
                    // Hailstones crossed in the past for hailstone B
                } else if (c.x() >= start && c.x() <= end && c.y() >= start && c.y() <= end) {
                    total++;
                } else {
                    // Hailstones' paths will cross outside the test area
                }
            }
        }
        return total;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return "part 2";
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getNrCollisionsIn(200000000000000L, 400000000000000L, readLines(input)));
    }

    public static void main(String... args) {
        new Day24("day24.txt");
    }
}
