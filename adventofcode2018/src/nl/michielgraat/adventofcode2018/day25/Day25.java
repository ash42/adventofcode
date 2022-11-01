package nl.michielgraat.adventofcode2018.day25;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day25 {
    private static final String FILENAME = "day25.txt";

    private List<Point> getPoints(final List<String> lines) {
        final List<Point> points = new ArrayList<>();
        for (final String line : lines) {
            final String[] parts = line.split(",");
            points.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3])));
        }
        return points;
    }

    public int runPart2(final List<String> lines) {
        return 0;
    }

    public int runPart1(final List<String> lines) {
        final List<Point> points = getPoints(lines);
        final List<List<Point>> constellations = new ArrayList<>();
        for (final Point point : points) {
            final List<Point> current = new ArrayList<>();
            current.add(point);
            final List<List<Point>> constellationsToRemove = new ArrayList<>();
            for (final List<Point> constellation : constellations) {
                // We loop over all constellations. If the current point is in multiple
                // constellations, all of these can be merged together to form one
                // constellation. That is: for every constellation this point is a part of
                // we add all of its stars to the current constellation (containing the current
                // point) and remove it from the list of constellation. After considering
                // all constellations we add the current one (with all merged constellations) to
                // the list of constellations.
                for (final Point consPoint : constellation) {
                    if (point.inSameConstellation(consPoint)) {
                        current.addAll(constellation);
                        constellationsToRemove.add(constellation);
                        break;
                    }
                }
            }
            constellations.removeAll(constellationsToRemove);
            constellations.add(current);
        }
        return constellations.size();
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        final long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day25().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
