package nl.michielgraat.adventofcode2016.day14;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Day14 {

    private String encodeHexString(final byte[] byteArray) {
        final StringBuilder hexStringBuffer = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }

    private String byteToHex(final byte num) {
        final char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    private String getMD5Hash(final String input) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            final byte[] digest = md.digest();
            return encodeHexString(digest);
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getStrechtedKey(final String input) {
        String result = getMD5Hash(input);
        for (int i = 0; i < 2016; i++) {
            result = getMD5Hash(result);
        }
        return result;
    }

    private String getTriple(final String input) {
        for (int i = 0; i < input.length() - 2; i++) {
            final char c1 = input.charAt(i);
            final char c2 = input.charAt(i + 1);
            final char c3 = input.charAt(i + 2);
            if (c1 == c2 && c1 == c3) {
                return String.valueOf(c1);
            }
        }
        return null;
    }

    private Map<Integer, String> getInitialMap(final String input, final boolean part1) {
        final Map<Integer, String> indexToHash = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            indexToHash.put(i, part1 ? getMD5Hash(input + i) : getStrechtedKey(input + i));
        }
        return indexToHash;
    }

    private int solve(final String input, final boolean part1) {
        int nrKeys = 0;
        int index = 0;
        final Map<Integer, String> indexToHash = getInitialMap(input, part1);
        while (nrKeys < 64) {
            final String hash = indexToHash.remove(index);
            final int next = index + 1000;
            indexToHash.put(next, part1 ? getMD5Hash(input + next) : getStrechtedKey(input + next));
            final String triple = getTriple(hash);
            if (triple != null) {
                final String five = triple + triple + triple + triple + triple;
                for (int i = index + 1; i < index + 1001; i++) {
                    final String nHash = indexToHash.get(i);
                    if (nHash.contains(five)) {
                        nrKeys++;
                        break;
                    }
                }
            }
            index++;
        }
        return index - 1;
    }

    public int runPart2(final String input) {
        return solve(input, false);
    }

    public int runPart1(final String input) {
        return solve(input, true);
    }

    public static void main(final String[] args) {
        final String input = "ahsbgdzn";
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day14().runPart1(input));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day14().runPart2(input));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
