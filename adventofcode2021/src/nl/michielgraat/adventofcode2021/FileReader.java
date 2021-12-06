package nl.michielgraat.adventofcode2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {

	private FileReader() {
	}

	public static Stream<String> getStringStream(String filename) {
		try {
			return Files.lines(Paths.get("resources", filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> getStringList(String filename) {
		Stream<String> stream = getStringStream(filename);
		return stream.collect(Collectors.toList());
	}

	public static List<Integer> getIntegerList(String filename) {
		Stream<String> stream = getStringStream(filename);
		List<String> strings = stream.collect(Collectors.toList());
		List<Integer> values = strings.get(0).chars().map(c -> c - '0').boxed().collect(Collectors.toList());
		return values;
	}

	public static List<Long> getLongList(String filename) {
		List<Long> values = new ArrayList<>();
		Stream<String> stream = getStringStream(filename);
		List<String> strings = stream.collect(Collectors.toList());
		for (String string : strings) {
			values.add(Long.parseLong(string));
		}
		return values;
	}

	public static List<Integer> getCompleteIntegerList(String filename) {
		List<Integer> values = new ArrayList<>();
		Stream<String> stream = getStringStream(filename);
		List<String> strings = stream.collect(Collectors.toList());
		for (String string : strings) {
			values.add(Integer.parseInt(string));
		}
		return values;
	}
}
