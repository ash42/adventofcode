package nl.michielgraat.adventofcode2017.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {

	private final String filename;

	public FileReader(String filename) {
		this.filename = filename;
	}

	public Stream<String> getStringStream() {
		try {
			return Files.lines(Paths.get(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getStringList() {
		Stream<String> stream = getStringStream();
		return stream.collect(Collectors.toList());
	}

	public List<Integer> getIntegerList() {
		List<Integer> values = new ArrayList<Integer>();
		Stream<String> stream = getStringStream();
		List<String> strings = stream.collect(Collectors.toList());
		values = strings.get(0).chars().map(c -> c - '0').boxed().collect(Collectors.toList());
		return values;
	}
	
	public List<Integer> getCompleteIntegerList() {
		List<Integer> values = new ArrayList<Integer>();
		Stream<String> stream = getStringStream();
		List<String> strings = stream.collect(Collectors.toList());
		for (String string : strings) {
			values.add(Integer.parseInt(string));
		}
		return values;
	}
}
