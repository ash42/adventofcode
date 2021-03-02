package nl.michielgraat.adventofcode2020.day02;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day2 {

	private static final String FILENAME = "day02.txt";

	public int runPart2() {
		List<String> unparsedPasswords = FileReader.getStringList(FILENAME);
		List<Password> passwords = unparsedPasswords.stream().map(p -> new Password(p)).collect(Collectors.toList());
		int nrValid = 0;
		for (Password password : passwords) {
			if (password.password.charAt(password.min - 1) == password.letter
					^ password.password.charAt(password.max - 1) == password.letter)
				nrValid++;
		}
		return nrValid;
	}

	public int runPart1() {
		List<String> unparsedPasswords = FileReader.getStringList(FILENAME);
		List<Password> passwords = unparsedPasswords.stream().map(p -> new Password(p)).collect(Collectors.toList());
		int nrValid = 0;
		for (Password password : passwords) {
			long nr = password.password.chars().filter(c -> c == password.letter).count();
			if (nr >= password.min && nr <= password.max)
				nrValid++;
		}
		return nrValid;
	}

	public static void main(String[] args) {
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day2().runPart1());
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day2().runPart2());
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}

}

class Password {
	int min;
	int max;
	char letter;
	String password;
	String unparsed;

	public Password(String unparsed) {
		this.unparsed = unparsed;
		this.min = Integer.parseInt(unparsed.substring(0, unparsed.indexOf('-')));
		this.max = Integer.parseInt(unparsed.substring(unparsed.indexOf('-') + 1, unparsed.indexOf(' ')));
		this.letter = unparsed.substring(unparsed.indexOf(' ') + 1, unparsed.indexOf(':')).charAt(0);
		this.password = unparsed.substring(unparsed.indexOf(':') + 2);
	}

	public String toString() {
		return unparsed;
	}
}
