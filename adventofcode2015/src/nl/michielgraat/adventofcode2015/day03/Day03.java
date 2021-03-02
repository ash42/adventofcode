package nl.michielgraat.adventofcode2015.day03;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day03 {

	private static final String FILENAME = "day03.txt";

	private long runPart2(List<String> lines) {
		Set<Point> points = new HashSet<Point>();
		Point cSanta = new Point(0, 0);
		Point cRobo = new Point(0, 0);
		points.add(cSanta);
		String line = lines.get(0);
		for (int i=0; i<line.length(); i+=2) {
			char c = line.charAt(i);
			char c2 = line.charAt(i+1);
			Point newSanta = new Point(cSanta.x, cSanta.y);
			Point newRobo = new Point(cRobo.x, cRobo.y);
			if (c == '^') {
				newSanta.y++;
			} else if (c == 'v'){
				newSanta.y--;
			} else if (c == '>') {
				newSanta.x++;
			} else {
				newSanta.x--;
			}
			if (c2 == '^') {
				newRobo.y++;
			} else if (c2 == 'v'){
				newRobo.y--;
			} else if (c2 == '>') {
				newRobo.x++;
			} else {
				newRobo.x--;
			}
			points.add(newSanta);
			points.add(newRobo);
			cSanta = newSanta;
			cRobo = newRobo;
		}
		return points.size();
	}

	private long runPart1(List<String> lines) {
		Set<Point> points = new HashSet<Point>();
		Point current = new Point(0, 0);
		points.add(current);
		String line = lines.get(0);
		for (int i=0; i<line.length(); i++) {
			char c = line.charAt(i);
			Point newPoint = new Point(current.x, current.y);
			if (c == '^') {
				newPoint.y++;
			} else if (c == 'v'){
				newPoint.y--;
			} else if (c == '>') {
				newPoint.x++;
			} else {
				newPoint.x--;
			}
			points.add(newPoint);
			current = newPoint;
		}
		return points.size();
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day03().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day03().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}

class Point {
	int x;
	int y;

	Point(int x, int y) {
		super();
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
}