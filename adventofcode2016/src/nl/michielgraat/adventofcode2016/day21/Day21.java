package nl.michielgraat.adventofcode2016.day21;

import java.util.List;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day21 {

    private static final String FILENAME = "day21.txt";

    private static final String INS_SWAP_POSITION = "swap position ";
    private static final String WITH_POSITION = " with position ";
    private static final String INS_SWAP_LETTER = "swap letter ";
    private static final String WITH_LETTER = " with letter ";
    private static final String INS_ROTATE_LEFT = "rotate left ";
    private static final String INS_ROTATE_RIGHT = "rotate right ";
    private static final String STEP = " step";
    private static final String INS_ROTATE_BASED = "rotate based on position of letter ";
    private static final String INS_REVERSE_POSITIONS = "reverse positions ";
    private static final String THROUGH = " through ";
    private static final String INS_MOVE_POSITION = "move position ";
    private static final String TO_POSITION = " to position ";

    private String swap(final String input, final int i1, final int i2) {
        final char[] ca = input.toCharArray();
        final char c1 = ca[i1];
        final char c2 = ca[i2];
        ca[i1] = c2;
        ca[i2] = c1;
        return new String(ca);
    }

    private String swapLetters(final String input, final String l1, final String l2) {
        return input.replaceAll(l1, "-").replaceAll(l2, l1).replaceAll("-", l2);
    }

    private String rotateLeft(final String input, final int i) {
        return input.substring(i) + input.substring(0, i);
    }

    private String rotateRight(final String input, final int i) {
        return rotateLeft(input, input.length() - i);
    }

    private String rotatePositionReverse(final String input, final String letter) {
        boolean found = false;
        String result = new String(input);
        int nrOfRotations = 1;
        while (!found) {
            result = rotateLeft(input, nrOfRotations);
            final String result2 = rotatePosition(result, letter);
            if (result2.equals(input)) {
                found = true;
            } else {
                nrOfRotations++;
            }
        }
        return result;
    }

    private String rotatePosition(String input, final String letter) {
        final int index = input.indexOf(letter);
        input = rotateRight(input, 1);
        input = rotateRight(input, index);
        if (index >= 4) {
            input = rotateRight(input, 1);
        }
        return input;
    }

    private String reverse(final String input, final int i1, final int i2) {
        final String rev = input.substring(i1, i2 + 1);
        final String pre = (i1 > 0) ? input.substring(0, i1) : "";
        final String post = (i2 < (input.length() - 1)) ? input.substring(i2 + 1) : "";
        StringBuilder rsb = new StringBuilder(rev);
        rsb = rsb.reverse();
        final StringBuilder result = new StringBuilder(pre);
        result.append(rsb);
        result.append(post);
        return result.toString();
    }

    private String move(final String input, final int x, final int y) {
        final char cx = input.charAt(x);
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (i != x) {
                if (i == y && x > y) {
                    result.append(cx);
                }
                result.append(input.charAt(i));
                if (i == y && x < y) {
                    result.append(cx);
                }
            }
        }
        return result.toString();
    }

    private String parseInstructions(String input, final List<String> lines) {
        for (final String line : lines) {
            if (line.startsWith(INS_SWAP_POSITION)) {
                final int i1 = Integer.parseInt(line.substring(
                        line.indexOf(INS_SWAP_POSITION) + INS_SWAP_POSITION.length(), line.indexOf(WITH_POSITION)));
                final int i2 = Integer.parseInt(line.substring(line.indexOf(WITH_POSITION) + WITH_POSITION.length()));
                input = swap(input, i1, i2);
            } else if (line.startsWith(INS_SWAP_LETTER)) {
                final String l1 = line.substring(line.indexOf(INS_SWAP_LETTER) + INS_SWAP_LETTER.length(),
                        (line.indexOf(INS_SWAP_LETTER) + INS_SWAP_LETTER.length() + 1));
                final String l2 = line.substring(line.indexOf(WITH_LETTER) + WITH_LETTER.length());
                input = swapLetters(input, l1, l2);
            } else if (line.startsWith(INS_ROTATE_LEFT)) {
                final int i = Integer.parseInt(
                        line.substring(line.indexOf(INS_ROTATE_LEFT) + INS_ROTATE_LEFT.length(), line.indexOf(STEP)));
                input = rotateLeft(input, i);
            } else if (line.startsWith(INS_ROTATE_RIGHT)) {
                final int i = Integer.parseInt(
                        line.substring(line.indexOf(INS_ROTATE_RIGHT) + INS_ROTATE_RIGHT.length(), line.indexOf(STEP)));
                input = rotateRight(input, i);
            } else if (line.startsWith(INS_ROTATE_BASED)) {
                final String letter = line.substring(line.indexOf(INS_ROTATE_BASED) + INS_ROTATE_BASED.length());
                input = rotatePosition(input, letter);
            } else if (line.startsWith(INS_REVERSE_POSITIONS)) {
                final int i1 = Integer.parseInt(line.substring(
                        line.indexOf(INS_REVERSE_POSITIONS) + INS_REVERSE_POSITIONS.length(), line.indexOf(THROUGH)));
                final int i2 = Integer.parseInt(line.substring(line.indexOf(THROUGH) + THROUGH.length()));
                input = reverse(input, i1, i2);
            } else if (line.startsWith(INS_MOVE_POSITION)) {
                final int x = Integer.parseInt(line.substring(
                        line.indexOf(INS_MOVE_POSITION) + INS_MOVE_POSITION.length(), line.indexOf(TO_POSITION)));
                final int y = Integer.parseInt(line.substring(line.indexOf(TO_POSITION) + TO_POSITION.length()));
                input = move(input, x, y);
            }
        }
        return input;
    }

    private String parseInstructionsReverse(String input, final List<String> lines) {
        for (int i = lines.size() - 1; i >= 0; i--) {
            final String line = lines.get(i);
            if (line.startsWith(INS_SWAP_POSITION)) {
                final int i1 = Integer.parseInt(line.substring(
                        line.indexOf(INS_SWAP_POSITION) + INS_SWAP_POSITION.length(), line.indexOf(WITH_POSITION)));
                final int i2 = Integer.parseInt(line.substring(line.indexOf(WITH_POSITION) + WITH_POSITION.length()));
                input = swap(input, i2, i1);
            } else if (line.startsWith(INS_SWAP_LETTER)) {
                final String l1 = line.substring(line.indexOf(INS_SWAP_LETTER) + INS_SWAP_LETTER.length(),
                        (line.indexOf(INS_SWAP_LETTER) + INS_SWAP_LETTER.length() + 1));
                final String l2 = line.substring(line.indexOf(WITH_LETTER) + WITH_LETTER.length());
                input = swapLetters(input, l1, l2);
            } else if (line.startsWith(INS_ROTATE_LEFT)) {
                final int j = Integer.parseInt(
                        line.substring(line.indexOf(INS_ROTATE_LEFT) + INS_ROTATE_LEFT.length(), line.indexOf(STEP)));
                input = rotateRight(input, j);
            } else if (line.startsWith(INS_ROTATE_RIGHT)) {
                final int j = Integer.parseInt(
                        line.substring(line.indexOf(INS_ROTATE_RIGHT) + INS_ROTATE_RIGHT.length(), line.indexOf(STEP)));
                input = rotateLeft(input, j);
            } else if (line.startsWith(INS_ROTATE_BASED)) {
                final String letter = line.substring(line.indexOf(INS_ROTATE_BASED) + INS_ROTATE_BASED.length());
                input = rotatePositionReverse(input, letter);
            } else if (line.startsWith(INS_REVERSE_POSITIONS)) {
                final int i1 = Integer.parseInt(line.substring(
                        line.indexOf(INS_REVERSE_POSITIONS) + INS_REVERSE_POSITIONS.length(), line.indexOf(THROUGH)));
                final int i2 = Integer.parseInt(line.substring(line.indexOf(THROUGH) + THROUGH.length()));
                input = reverse(input, i1, i2);
            } else if (line.startsWith(INS_MOVE_POSITION)) {
                final int x = Integer.parseInt(line.substring(
                        line.indexOf(INS_MOVE_POSITION) + INS_MOVE_POSITION.length(), line.indexOf(TO_POSITION)));
                final int y = Integer.parseInt(line.substring(line.indexOf(TO_POSITION) + TO_POSITION.length()));
                input = move(input, y, x);
            }
        }
        return input;
    }

    public String runPart2(final String input, final List<String> lines) {
        return parseInstructionsReverse(input, lines);
    }

    public String runPart1(final String input, final List<String> lines) {
        return parseInstructions(input, lines);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        String input = "abcdefgh";
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day21().runPart1(input, lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        input = "fbgdceah";
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day21().runPart2(input, lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
