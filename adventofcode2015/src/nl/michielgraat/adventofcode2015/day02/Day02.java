package nl.michielgraat.adventofcode2015.day02;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day02 {

	private static final String FILENAME = "day02.txt";

	private long getLength(String dimensions) {
		return Long.valueOf(dimensions.substring(0, dimensions.indexOf("x")));
	}

	private long getWidth(String dimensions) {
		return Long.valueOf(dimensions.substring(dimensions.indexOf("x") + 1, dimensions.lastIndexOf("x")));
	}

	private long getHeight(String dimensions) {
		return Long.valueOf(dimensions.substring(dimensions.lastIndexOf("x") + 1));
	}

	private long calculateNeededSurface(long l, long w, long h) {
		long first = 2 * l * w;
		long second = 2 * w * h;
		long third = 2 * h * l;
		long fourth = Math.min(first, Math.min(second, third)) / 2;
		return first + second + third + fourth;
	}

	private long calculateNeededRibbonLength(long l, long w, long h) {
		long[] a = {l, w, h};
		Arrays.sort(a);
		long first = 2 * a[0];
		long second = 2 * a[1];
		long third = l * w * h;
		return first + second + third;
	}

	public long runPart1(List<String> lines) {
		long total = 0;

		for (String line : lines) {
			total += calculateNeededSurface(getLength(line), getWidth(line), getHeight(line));
		}
		return total;
	}

	public long runPart2(List<String> lines) {
		long total = 0;

		for (String line : lines) {
			total += calculateNeededRibbonLength(getLength(line), getWidth(line), getHeight(line));
		}
		return total;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day02().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day02().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}

}
