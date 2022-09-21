package nl.michielgraat.adventofcode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {
	public static Stream<String> getStringStream(final String filename) {
		try {
			return Files.lines(Paths.get("resources", filename));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> getStringList(final String filename) {
		final Stream<String> stream = getStringStream(filename);
		return stream.collect(Collectors.toList());
	}

	public static List<Integer> getIntegerList(final String filename) {
		List<Integer> values = new ArrayList<Integer>();
		final Stream<String> stream = getStringStream(filename);
		final List<String> strings = stream.collect(Collectors.toList());
		values = strings.get(0).chars().map(c -> c - '0').boxed().collect(Collectors.toList());
		return values;
	}

	public static List<Long> getLongList(final String filename) {
		final List<Long> values = new ArrayList<Long>();
		final Stream<String> stream = getStringStream(filename);
		final List<String> strings = stream.collect(Collectors.toList());
		for (final String string : strings) {
			values.add(Long.parseLong(string));
		}
		return values;
	}

	public static List<Integer> getCompleteIntegerList(final String filename) {
		final List<Integer> values = new ArrayList<Integer>();
		final Stream<String> stream = getStringStream(filename);
		final List<String> strings = stream.collect(Collectors.toList());
		for (final String string : strings) {
			values.add(Integer.parseInt(string));
		}
		return values;
	}
}
