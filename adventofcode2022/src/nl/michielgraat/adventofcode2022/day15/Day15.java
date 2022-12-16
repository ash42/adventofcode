package nl.michielgraat.adventofcode2022.day15;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day15 extends AocSolver {

    protected Day15(final String filename) {
        super(filename);
    }

    private Map<Position, Position> parseSources(final List<String> input) {
        final Map<Position, Position> devices = new HashMap<>();
        final Pattern p = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
        for (final String line : input) {
            final Matcher m = p.matcher(line);
            if (m.find()) {
                devices.put(new Position(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))),
                        new Position(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))));
            }
        }
        return devices;
    }

    private boolean inRange(final int x, final int y, final int max) {
        return x >= 0 && y >= 0 && x <= max && y <= max;
    }

    private long scan(final Position p, final Map<Position, Position> devices) {
        boolean inRange = false;
        for (final Entry<Position, Position> entry : devices.entrySet()) {
            final Position sensor = entry.getKey();
            final Position beacon = entry.getValue();
            final int md = sensor.getManhattanDistance(beacon);
            if (sensor.getManhattanDistance(p) <= md) {
                inRange = true;
                break;
            }
        }
        if (!inRange) {
            return (long) p.x() * 4000000 + p.y();
        }
        return 0;
    }

    private long scanQuadrant4(final Position sensor, final Map<Position, Position> devices, final int md, final int max) {
        for (int x = -md; x <= 0; x++) {
            if (inRange(sensor.x() + x, sensor.y() + (md + x), max)) {
                final Position p = new Position(sensor.x() + x, sensor.y() + (md + x));
                final long val = scan(p, devices);
                if (val > 0)
                    return val;
            }
        }
        return 0;
    }

    private long scanQuadrant3(final Position sensor, final Map<Position, Position> devices, final int md, final int max) {
        for (int x = md; x >= 0; x--) {
            if (inRange(sensor.x() + x, sensor.y() + (md - x), max)) {
                final Position p = new Position(sensor.x() + x, sensor.y() + (md - x));
                final long val = scan(p, devices);
                if (val > 0)
                    return val;
            }
        }
        return 0;
    }

    private long scanQuadrant2(final Position sensor, final Map<Position, Position> devices, final int md, final int max) {
        for (int y = -md; y <= 0; y++) {
            if (inRange(sensor.x() + (md + y), sensor.y() + y, max)) {
                final Position p = new Position(sensor.x() + (md + y), sensor.y() + y);
                final long val = scan(p, devices);
                if (val > 0)
                    return val;
            }
        }
        return 0;
    }

    private long scanQuadrant1(final Position sensor, final Map<Position, Position> devices, final int md, final int max) {
        for (int y = md; y >= 0; y--) {
            if (inRange(sensor.x() + (md - y), sensor.y() + y, max)) {
                final Position p = new Position(sensor.x() + (md - y), sensor.y() + y);
                final long val = scan(p, devices);
                if (val > 0)
                    return val;
            }
        }
        return 0;
    }

    private long findDistressBeacon(final Position sensor, final Map<Position, Position> devices, final int md, final int max) {
        // Walk along the edge of the perimeter created by the sensor and the Manhattan
        // distance to its beacon. If the position is not within Manhattan distance of
        // any sensor, we have our answer.
        long val = 0;
        val = scanQuadrant1(sensor, devices, md, max);
        if (val > 0)
            return val;
        val = scanQuadrant2(sensor, devices, md, max);
        if (val > 0)
            return val;
        val = scanQuadrant3(sensor, devices, md, max);
        if (val > 0)
            return val;
        val = scanQuadrant4(sensor, devices, md, max);
        return val;
    }

    private int calculateNrOfNonBeacons(final Map<Position, Position> devices, final int row) {
        // Calculate the range of X-values on the desired row which are within Manhattan
        // distance range from sensors.
        int minX = Integer.MAX_VALUE;
        int maxX = 0;
        for (final Entry<Position, Position> entry : devices.entrySet()) {
            final Position sensor = entry.getKey();
            final Position beacon = entry.getValue();
            final int md = sensor.getManhattanDistance(beacon);
            // Calculate the number of x's on the row which are within the Manhattan
            // distance between sensor and beacon.
            final int xRange = md - Math.abs(sensor.y() - row);
            // Keep track of the minimum and maximum values for x-values over all sensors.
            minX = Math.min(sensor.x() - xRange, minX);
            maxX = Math.max(sensor.x() + xRange, maxX);
        }
        // Add one to include maxX.
        int total = maxX + 1 - minX;
        // Subtract 1 for every beacon which is on the row.
        total -= devices.values().stream().filter(d -> d.y() == row).distinct().count();
        return total;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final Map<Position, Position> devices = parseSources(input);

        for (final Entry<Position, Position> entry : devices.entrySet()) {
            final Position sensor = entry.getKey();
            final Position beacon = entry.getValue();
            final int md = sensor.getManhattanDistance(beacon) + 1;
            final long val = findDistressBeacon(sensor, devices, md, 4000000);
            if (val > 0) {
                return String.valueOf(val);
            }
        }

        return "Not found";
    }

    @Override
    protected String runPart1(final List<String> input) {
        final Map<Position, Position> devices = parseSources(input);
        return String.valueOf(calculateNrOfNonBeacons(devices, 2000000));
    }

    public static void main(final String... args) {
        new Day15("day15.txt");
    }
}
