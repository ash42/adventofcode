package nl.michielgraat.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AocSolver {

	protected AocSolver(final String filename) {
		final List<String> input = getStringList(filename);

		Instant start = Instant.now();
		final String output1 = runPart1(input);
		Instant end = Instant.now();
		System.out.println("Answer to part 1: " + output1);
		System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
		
		start = Instant.now();
		final String output2 = runPart2(input);
		end = Instant.now();
		System.out.println("Answer to part 2: " + output2);
		System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
	}

	protected abstract String runPart2(List<String> input);

	protected abstract String runPart1(List<String> input);

	private List<String> getStringList(final String filename) {
		try (Stream<String> stream = Files.lines(Paths.get("resources", filename))) {
			return stream.collect(Collectors.toList());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	protected List<Integer> convertToIntList(final List<String> input) {
		return input.stream().map(Integer::parseInt).collect(Collectors.toList());
	}

	protected List<Long> convertToLongList(final List<String> input) {
		return input.stream().map(Long::parseLong).collect(Collectors.toList());
	}
}