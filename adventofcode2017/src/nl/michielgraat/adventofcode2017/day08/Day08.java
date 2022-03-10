package nl.michielgraat.adventofcode2017.day08;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day08 {

    private static final String FILENAME = "day08.txt";

    private static final String INS_DEC = "dec";
    private static final String OP_GT = ">";
    private static final String OP_LT = "<";
    private static final String OP_GTE = ">=";
    private static final String OP_LTE = "<=";
    private static final String OP_E = "==";
    private static final String OP_NE = "!=";

    private String getInstruction(final String s) {
        return s.substring(s.indexOf(" ") + 1, s.indexOf(" ", s.indexOf(" ") + 1));
    }

    private String getOperator(final String s) {
        return s.substring(s.indexOf(" ", s.indexOf("if ") + 3) + 1, s.lastIndexOf(" "));
    }

    private int getSecondValue(final String s) {
        return Integer.parseInt(s.substring(s.lastIndexOf(" ") + 1));
    }

    private int getFirstValue(final String s) {
        return Integer.parseInt(s.substring(s.indexOf(" ", s.indexOf(" ") + 1) + 1, s.indexOf(" if")));
    }

    private String getSecondRegister(final String s) {
        return s.substring(s.indexOf("if ") + 3, s.indexOf(" ", s.indexOf("if ") + 3));
    }

    private String getFirstRegister(final String s) {
        return s.substring(0, s.indexOf(" "));
    }

    private boolean checkCond(final int r, final String op, final int v) {
        switch (op) {
            case OP_GT:
                return r > v;
            case OP_LT:
                return r < v;
            case OP_GTE:
                return r >= v;
            case OP_LTE:
                return r <= v;
            case OP_E:
                return r == v;
            case OP_NE:
                return r != v;
            default:
                throw new IllegalArgumentException("Unknown operator '" + op + "'");
        }
    }

    private int runProgram(final List<String> lines, final boolean part2) {
        final Map<String, Integer> registerValues = new HashMap<>();
        int highest = 0;
        for (final String line : lines) {
            final String r1 = getFirstRegister(line);
            final String r2 = getSecondRegister(line);
            final int val1 = getFirstValue(line);
            final int val2 = getSecondValue(line);
            final String op = getOperator(line);
            final String ins = getInstruction(line);
            if (!registerValues.containsKey(r1)) {
                registerValues.put(r1, 0);
            }
            if (!registerValues.containsKey(r2)) {
                registerValues.put(r2, 0);
            }
            if (checkCond(registerValues.get(r2), op, val2)) {
                final int nr1 = (ins.equals(INS_DEC)) ? registerValues.get(r1) - val1 : registerValues.get(r1) + val1;
                registerValues.put(r1, nr1);
                if (nr1 > highest) {
                    highest = nr1;
                }
            }
        }
        return part2 ? highest : Collections.max(registerValues.values());
    }

    private int runPart2(final List<String> lines) {
        return runProgram(lines, true);
    }

    private int runPart1(final List<String> lines) {
        return runProgram(lines, false);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day08().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day08().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}