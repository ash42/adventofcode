package nl.michielgraat.adventofcode2015.day04;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;

public class Day04 {

	private long runPart2(String secretKey) throws NoSuchAlgorithmException {
		long current = 346387;
		while (true) {
			String input = secretKey + current;
			String result = hash(input);
			if (result.startsWith("000000")) {
				return current;
			}
			current++;
		}
	}

	private long runPart1(String secretKey) throws NoSuchAlgorithmException {
		long current = 0;
		while (true) {
			String input = secretKey + current;
			String result = hash(input);
			if (result.startsWith("00000")) {
				return current;
			}
			current++;
		}
	}

	private String toHexString(byte[] bytes) {
	    StringBuilder hexString = new StringBuilder();

	    for (int i = 0; i < bytes.length; i++) {
	        String hex = Integer.toHexString(0xFF & bytes[i]);
	        if (hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }

	    return hexString.toString();
	}
	
	private String hash(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] hash = md.digest(input.getBytes());
		return toHexString(hash);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day04().runPart1("iwrupvqb"));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day04().runPart2("iwrupvqb"));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}


}
