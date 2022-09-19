package nl.michielgraat.adventofcode2018.day10;

import java.util.ArrayList;
import java.util.List;

public class Sky {
    private List<Point> points = new ArrayList<>();

    public Sky(List<String> lines) {
        for (String line : lines) {
            int x = Integer.parseInt(line.substring(line.indexOf("<") + 1, line.indexOf(",")).trim());
            int y = Integer.parseInt(line.substring(line.indexOf(",") + 1, line.indexOf(">")).trim());
            int velX = Integer.parseInt(line.substring(line.lastIndexOf("<") + 1, line.lastIndexOf(",")).trim());
            int velY = Integer.parseInt(line.substring(line.lastIndexOf(",") + 1, line.lastIndexOf(">")).trim());
            Point p = new Point(x, y, velX, velY);
            points.add(p);
        }
    }

    private void move() {
        points.forEach(Point::move);
    }

    private void moveBack() {
        points.forEach(Point::moveBack);
    }

    public int moveUntilMessageFound() {
        int seconds = 0;
        long prevSize = size();
        long newSize = size();
        // Move until the size of the area covered by the lights starts increasing
        // (message is shown when this area is at its smallest).
        while (newSize <= prevSize) {
            prevSize = size();
            move();
            seconds++;
            newSize = size();
        }
        // Move one step back, because the size increased.
        moveBack();
        seconds--;
        return seconds;
    }

    public String getMessage() {
        moveUntilMessageFound();

        int minX = points.stream().map(Point::getX).mapToInt(x -> x).min().getAsInt();
        int maxX = points.stream().map(Point::getX).mapToInt(x -> x).max().getAsInt();
        int minY = points.stream().map(Point::getY).mapToInt(y -> y).min().getAsInt();
        int maxY = points.stream().map(Point::getY).mapToInt(y -> y).max().getAsInt();

        StringBuilder sb = new StringBuilder();
        sb.append("\n\n");
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Point p = new Point(x, y);
                if (points.contains(p)) {
                    sb.append("#");
                } else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private long size() {
        int minX = points.stream().map(Point::getX).mapToInt(x -> x).min().getAsInt();
        int maxX = points.stream().map(Point::getX).mapToInt(x -> x).max().getAsInt();
        int minY = points.stream().map(Point::getY).mapToInt(y -> y).min().getAsInt();
        int maxY = points.stream().map(Point::getY).mapToInt(y -> y).max().getAsInt();
        return (long) (maxX - minX) * (long) (maxY - minY);
    }
}