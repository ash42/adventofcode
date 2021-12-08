package nl.michielgraat.adventofcode2021.day08;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day08 {

    private static final String FILENAME = "day08.txt";

    private List<Character> findUnique(final String line, final int length) {
        final String[] inputs = line.substring(0, line.indexOf("|") - 1).split(" ");
        List<Character> unique = new ArrayList<>();
        for (final String input : inputs) {
            if (input.length() == length) {
                unique = input.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
                break;
            }
        }
        return unique;
    }

    private List<List<Character>> findMultiple(final String line, final int length) {
        final String[] inputs = line.substring(0, line.indexOf("|") - 1).split(" ");
        final List<List<Character>> multiple = new ArrayList<>();
        for (final String input : inputs) {
            if (input.length() == length) {
                multiple.add(input.chars().mapToObj(i -> (char) i).collect(Collectors.toList()));
            }
        }
        return multiple;
    }

    private List<Character> findNine(final String line, final List<Character> one, final List<Character> four) {
        final List<List<Character>> zeroSixNine = findMultiple(line, 6);

        for (final List<Character> zoson : zeroSixNine) {
            if (zoson.containsAll(one) && zoson.containsAll(four)) {
                return zoson;
            }
        }
        return new ArrayList<>();
    }

    private List<Character> findSix(final String line, final List<Character> one, final List<Character> nine) {
        final List<List<Character>> zeroSixNine = findMultiple(line, 6);
        zeroSixNine.remove(nine);
        for (final List<Character> zos : zeroSixNine) {
            if (zos.contains(one.get(0)) ^ zos.contains(one.get(1))) {
                return zos;
            }
        }
        return new ArrayList<>();
    }

    private List<Character> findZero(final String line, final List<Character> six, final List<Character> nine) {
        final List<List<Character>> zeroSixNine = findMultiple(line, 6);
        zeroSixNine.remove(nine);
        zeroSixNine.remove(six);
        return zeroSixNine.get(0);
    }

    private List<Character> findThree(final String line, final List<Character> one) {
        final List<List<Character>> twoThreeFive = findMultiple(line, 5);
        for (final List<Character> ttf : twoThreeFive) {
            if (ttf.containsAll(one)) {
                return ttf;
            }
        }
        return new ArrayList<>();
    }

    private List<Character> findTwo(final String line, final List<Character> three, final List<Character> four, final List<Character> six) {
        final List<List<Character>> twoThreeFive = findMultiple(line, 5);
        twoThreeFive.remove(three);
        final List<Character> fourComp = six.stream().filter(c -> !four.contains(c)).collect(Collectors.toList());
        for (final List<Character> tf : twoThreeFive) {
            if (tf.containsAll(fourComp)) {
                return tf;
            }
        }
        return new ArrayList<>();
    }

    private List<Character> findFive(final String line, final List<Character> two, final List<Character> three) {
        final List<List<Character>> twoThreeFive = findMultiple(line, 5);
        twoThreeFive.remove(two);
        twoThreeFive.remove(three);
        return twoThreeFive.get(0);
    }

    private int getValue(final String input, final List<Character> zero, final List<Character> two, final List<Character> three, final List<Character> five, final List<Character> six) {
        final List<Character> inputChars = input.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
        if (input.length() == 2) {
            return 1;
        } else if (input.length() == 3) {
            return 7;
        } else if (input.length() == 4) {
            return 4;
        } else if(input.length() == 7) {
            return 8;
        } else if (input.length() == 6) {
            if (inputChars.containsAll(zero)) {
                return 0;
            } else if (inputChars.containsAll(six)) {
                return 6;
            } else {
                return 9;
            }
        } else {
            if (inputChars.containsAll(two)) {
                return 2;
            } else if (inputChars.containsAll(three)) {
                return 3;
            } else {
                return 5;
            }
        }
    }

    private int getOutputValue (final String line, final List<Character> zero, final List<Character> two, final List<Character> three, final List<Character> five, final List<Character> six) {
        final String[] outputs = line.substring(line.indexOf("|") + 2).split(" ");
        final StringBuilder sOutput = new StringBuilder();
        for (final String output : outputs) {
            sOutput.append(getValue(output, zero, two, three, five, six));
        }
        return Integer.parseInt(sOutput.toString());
    }

    public int runPart2(final List<String> lines) {
        int total = 0;
        for (final String line : lines) {
                final List<Character> one = findUnique(line, 2);
                final List<Character> four = findUnique(line, 4);
                final List<Character> nine = findNine(line, one, four);
                final List<Character> six = findSix(line, one, nine);
                final List<Character> zero = findZero(line, six, nine);
                final List<Character> three = findThree(line, one);
                final List<Character> two = findTwo(line, three, four, six);
                final List<Character> five = findFive(line, two, three);
                total += getOutputValue(line, zero, two, three, five, six);

        }
        return total;
    }

    public int runPart1(final List<String> lines) {
        int total = 0;
        for (final String line : lines) {
            final String[] outputs = line.substring(line.indexOf("|") + 2).split(" ");
            for (final String output : outputs) {
                if (output.length() == 2 || output.length() == 3 || output.length() == 4 || output.length() == 7) {
                    total++;
                }
            }
        }
        return total;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day08().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day08().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}
