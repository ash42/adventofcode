package nl.michielgraat.adventofcode2016.day05;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Random;

public class Day05 {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    private boolean done(char[] result) {
        return result[0] != '-' && result[1] != '-' && result[2] != '-' && result[3] != '-' && result[4] != '-'
                && result[5] != '-' && result[6] != '-' && result[7] != '-';
    }

    private boolean isValid(String s, char[] result) {
        try {
            int r = Integer.parseInt(s);
            return r < result.length && result[r] == '-';
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String password2ToString(char[] result) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < result.length; i++) {
            if (result[i] != '-') {
                sb.append(result[i]);
            } else {
                int idx = r.nextInt(HEX_ARRAY.length);
                sb.append(HEX_ARRAY[idx]);
            }
        }
        return sb.toString();
    }

    private String password1ToString(String pw) {
        StringBuilder sb = new StringBuilder(pw);
        Random r = new Random();
        while (sb.length() < 8) {
            sb.append(HEX_ARRAY[r.nextInt(HEX_ARRAY.length)]);
        }
        return sb.toString();
    }

    private String runPart2(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        char[] result = { '-', '-', '-', '-', '-', '-', '-', '-' };
        int i = 0;
        while (!done(result)) {
            String s = input + i++;
            md.update(s.getBytes());
            byte[] r = md.digest();
            // System.out.println(bytesToHex(result));
            String hash = bytesToHex(r);
            if (hash.startsWith("00000")) {
                String index = hash.substring(5, 6);
                if (isValid(index, result)) {
                    int idx = Integer.parseInt(index);
                    result[idx] = hash.charAt(6);
                }
            }
            if (i % 10000 == 0) {
                System.out.print("Password 2: " + password2ToString(result) + "\r");
            }
        }
        System.out.print("Password 2: " + password2ToString(result) + "\r");
        System.out.println();
        StringBuilder code = new StringBuilder();
        for (int j = 0; j < result.length; j++) {
            code.append(result[j]);
        }
        return code.toString();
    }

    private String runPart1(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (sb.length() < 8) {
            String s = input + i++;
            md.update(s.getBytes());
            byte[] result = md.digest();
            // System.out.println(bytesToHex(result));
            String hash = bytesToHex(result);
            if (hash.startsWith("00000")) {
                sb.append(hash.substring(5, 6));
            }
            if (i % 10000 == 0) {
                System.out.print("Password 1: " + password1ToString(sb.toString()) + "\r");
            }
        }
        System.out.println();
        return sb.toString();
    }

    public static void main(String... args) throws NoSuchAlgorithmException {
        //final String input = "abc";
        final String input = "ffykfhsq";
        long start = Calendar.getInstance().getTimeInMillis();
        new Day05().runPart1(input);
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        new Day05().runPart2(input);
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}
