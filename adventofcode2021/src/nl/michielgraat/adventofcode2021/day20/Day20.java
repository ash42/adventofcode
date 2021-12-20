package nl.michielgraat.adventofcode2021.day20;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day20 {

    private static final String FILENAME = "day20.txt";

    private int[] getEnhancementArray(final List<String> lines) {
        final String enhancementLine = lines.get(0);
        final int[] enhancementArray = new int[enhancementLine.length()];
        for (int i = 0; i < enhancementLine.length(); i++) {
            enhancementArray[i] = (enhancementLine.charAt(i) == '.') ? 0 : 1;
        }
        return enhancementArray;
    }

    private Point getNewPoint(final int x, final int y, final int index, final int[] enhancementArray) {
        final Point p = new Point(x, y);
        p.lit = enhancementArray[index] == 1;
        return p;
    }

    private List<Point> getInitialPoints(final List<String> lines) {
        final List<Point> result = new ArrayList<>();
        for (int i = 2; i < lines.size(); i++) {
            final String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    result.add(new Point(i - 2, j, true));
                } else {
                    result.add(new Point(i - 2, j, false));
                }
            }
        }
        return result;
    }

    private int getIndexForPoint(final int x, final int y, final List<Point> points, final int infinity) {
        final StringBuilder sb = new StringBuilder();
        Point cur = new Point(x - 1, y - 1);
        sb.append(points.contains(cur) ? (points.get(points.indexOf(cur)).lit) ? 1 : 0 : infinity);
        cur = new Point(x - 1, y);
        sb.append(points.contains(cur) ? (points.get(points.indexOf(cur)).lit) ? 1 : 0 : infinity);
        cur = new Point(x - 1, y + 1);
        sb.append(points.contains(cur) ? (points.get(points.indexOf(cur)).lit) ? 1 : 0 : infinity);
        cur = new Point(x, y - 1);
        sb.append(points.contains(cur) ? (points.get(points.indexOf(cur)).lit) ? 1 : 0 : infinity);
        cur = new Point(x, y);
        sb.append(points.contains(cur) ? (points.get(points.indexOf(cur)).lit) ? 1 : 0 : infinity);
        cur = new Point(x, y + 1);
        sb.append(points.contains(cur) ? (points.get(points.indexOf(cur)).lit) ? 1 : 0 : infinity);
        cur = new Point(x + 1, y - 1);
        sb.append(points.contains(cur) ? (points.get(points.indexOf(cur)).lit) ? 1 : 0 : infinity);
        cur = new Point(x + 1, y);
        sb.append(points.contains(cur) ? (points.get(points.indexOf(cur)).lit) ? 1 : 0 : infinity);
        cur = new Point(x + 1, y + 1);
        sb.append(points.contains(cur) ? (points.get(points.indexOf(cur)).lit) ? 1 : 0 : infinity);
        return Integer.parseInt(sb.toString(), 2);
    }

    private int calculateInfinity(final int current, final int[] enhancementArray) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append(current);
        }
        return enhancementArray[Integer.parseInt(sb.toString(), 2)];
    }

    private long enhance(final int totalTimes, final List<String> lines) {
        final int[] enhancementArray = getEnhancementArray(lines);
        List<Point> initialPoints = getInitialPoints(lines);
        List<Point> result = new ArrayList<>();
        int infinity = 0;
        int times = 1;
        while (times <= totalTimes) {
            final int minX = initialPoints.stream().mapToInt(p -> p.x).min().getAsInt() - 1;
            final int maxX = initialPoints.stream().mapToInt(p -> p.x).max().getAsInt() + 1;
            final int minY = initialPoints.stream().mapToInt(p -> p.y).min().getAsInt() - 1;
            final int maxY = initialPoints.stream().mapToInt(p -> p.y).max().getAsInt() + 1;
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    final int index = getIndexForPoint(x, y, initialPoints, infinity);
                    final Point p = getNewPoint(x, y, index, enhancementArray);
                    result.add(p);
                }
            }
            initialPoints = result;
            result = new ArrayList<>();
            infinity = calculateInfinity(infinity, enhancementArray);
            times++;
        }
        return initialPoints.stream().filter(p -> p.lit).count();
    }

    private long runPart2(final List<String> lines) {
        return enhance(50, lines);
    }

    private long runPart1(final List<String> lines) {
        return enhance(2, lines);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day20().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day20().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}

class Point implements Comparable<Point> {
    int x;
    int y;
    boolean lit;

    Point(final int x, final int y) {
        this.x = x;
        this.y = y;
        this.lit = false;
    }

    Point(final int x, final int y, final boolean lit) {
        this.x = x;
        this.y = y;
        this.lit = lit;
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
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Point other = (Point) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public int compareTo(final Point o) {
        if (this.x != o.x) {
            return this.x - o.x;
        } else {
            return this.y - o.y;
        }
    }

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]: " + ((lit) ? "#" : ".");
    }
}