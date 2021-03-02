package nl.michielgraat.adventofcode2018.day8;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 {

	private void printArray(String[] s) {
		Stream.of(s).forEach(e -> System.out.print(e + " "));
		System.out.println();
	}
	
	private int sumMetadata(String[] s) {
		printArray(s);
		if (s.length > 2) {
			int nrOfMetadata = Integer.parseInt(s[1]);
			System.out.println("Nr of metadata: " + nrOfMetadata);
			if (nrOfMetadata > 0) {
				int metadata = 0; 
				for (int i = s.length - nrOfMetadata; i < s.length; i++) {
					metadata += Integer.parseInt(s[i]);
				}
				System.out.println("Metadata sum: " + metadata);
				List<String> nextList = Stream.of(s).limit(s.length - nrOfMetadata).collect(Collectors.toList());
				nextList = nextList.stream().skip(2).collect(Collectors.toList());
				String[] next = new String[nextList.size()];
				next = nextList.toArray(next);
				return metadata + sumMetadata(next);
			} else {
				List<String> nextList = Stream.of(s).limit(s.length - nrOfMetadata).collect(Collectors.toList());
				String[] next = new String[nextList.size()];
				next = nextList.toArray(next);
				return sumMetadata(next);
			}
		} else {
			return 0;
		}
	}
	
	public String runPart1() {
		String data = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";
		String[] dataArray = data.split("\\s+");
		return "The answer to part1: " + sumMetadata(dataArray);
	}
	
	public static void main (String... args) {
		System.out.println(new Day8().runPart1());
	}
}
