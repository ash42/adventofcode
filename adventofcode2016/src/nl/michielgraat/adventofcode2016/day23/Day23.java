package nl.michielgraat.adventofcode2016.day23;

import java.util.List;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day23 {

    private static final String FILENAME = "day23.txt";

    private String getNewInstruction(final String ins) {
        String result = "";
        if (ins.startsWith("dec") || ins.startsWith("tgl")) {
            result = "inc" + ins.substring(ins.indexOf(" ") + 1);
        } else if (ins.startsWith("inc")) {
            result = "dec " + ins.substring(ins.indexOf(" ") + 1);
        } else if (ins.startsWith("jnz")) {
            result = "cpy " + ins.substring(ins.indexOf(" ") + 1);
        } else if (ins.startsWith("cpy")) {
            result = "jnz " + ins.substring(ins.indexOf(" ") + 1);
        } else {
            throw new IllegalArgumentException("Unknown instruction " + ins);
        }
        return result;
    }

    private boolean isNumeric(final String s) {
        final Pattern p = Pattern.compile("-?\\d+");
        return p.matcher(s).matches();
    }

    /**
     * inc a
     * dec c
     * jnz c -2
     * dec d
     * jnz d -5
     * 
     * =
     * 
     * a = b*d
     * 
     * Yes, this is not generic.
     */
    private boolean isMultiplication(final int ptr, final List<String> lines) {
        if (lines.get(ptr).equals("inc a")) {
            final String l2 = lines.get(ptr + 1);
            final String l3 = lines.get(ptr + 2);
            final String l4 = lines.get(ptr + 3);
            final String l5 = lines.get(ptr + 4);
            return (l2.equals("dec c") && l3.equals("jnz c -2") && l4.equals("dec d") && l5.equals("jnz d -5"));
        }
        return false;
    }

    private int runProgram(int a, int b, int c, int d, final List<String> lines) {
        final int last = lines.size() - 1;
        int ptr = 0;
        while (ptr <= last) {
            final String line = lines.get(ptr);
            if (isMultiplication(ptr, lines)) {
                a = b * d;
                ptr += 5;
            } else if (line.startsWith("inc")) {
                final String register = line.substring(line.indexOf(" ") + 1);
                switch (register) {
                    case "a":
                        a++;
                        break;
                    case "b":
                        b++;
                        break;
                    case "c":
                        c++;
                        break;
                    case "d":
                        d++;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown register: " + register);
                }
                ptr++;
            } else if (line.startsWith("dec")) {
                final String register = line.substring(line.indexOf(" ") + 1);
                switch (register) {
                    case "a":
                        a--;
                        break;
                    case "b":
                        b--;
                        break;
                    case "c":
                        c--;
                        break;
                    case "d":
                        d--;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown register: " + register);
                }
                ptr++;
            } else if (line.startsWith("cpy")) {
                final String register = line.substring(line.lastIndexOf(" ") + 1);
                final String first = line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" "));
                int val = 0;
                if (isNumeric(first)) {
                    val = Integer.parseInt(first);
                } else {
                    switch (first) {
                        case "a":
                            val = a;
                            break;
                        case "b":
                            val = b;
                            break;
                        case "c":
                            val = c;
                            break;
                        case "d":
                            val = d;
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown register: " + register);
                    }
                }
                switch (register) {
                    case "a":
                        a = val;
                        break;
                    case "b":
                        b = val;
                        break;
                    case "c":
                        c = val;
                        break;
                    case "d":
                        d = val;
                        break;
                    default:
                        break;
                }
                ptr++;
            } else if (line.startsWith("jnz")) {
                final String register = line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" "));
                final String sJmp = line.substring(line.lastIndexOf(" ") + 1);
                int jmp = -1;
                if (isNumeric(sJmp)) {
                    jmp = Integer.parseInt(sJmp);
                } else {
                    switch (sJmp) {
                        case "a":
                            jmp = a;
                            break;
                        case "b":
                            jmp = b;
                            break;
                        case "c":
                            jmp = c;
                            break;
                        case "d":
                            jmp = d;
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown register: " + sJmp);
                    }
                }
                if (isNumeric(register)) {
                    final int val = Integer.parseInt(register);
                    ptr = (val == 0) ? ptr + 1 : ptr + jmp;
                } else {
                    switch (register) {
                        case "a":
                            ptr = (a == 0) ? ptr + 1 : ptr + jmp;
                            break;
                        case "b":
                            ptr = (b == 0) ? ptr + 1 : ptr + jmp;
                            break;
                        case "c":
                            ptr = (c == 0) ? ptr + 1 : ptr + jmp;
                            break;
                        case "d":
                            ptr = (d == 0) ? ptr + 1 : ptr + jmp;
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown register: " + register);
                    }
                }
            } else if (line.startsWith("tgl")) {
                final String register = line.substring(line.indexOf(" ") + 1);
                int val = Integer.MAX_VALUE;
                switch (register) {
                    case "a":
                        val = a;
                        break;
                    case "b":
                        val = b;
                        break;
                    case "c":
                        val = c;
                        break;
                    case "d":
                        val = d;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown register: " + register);
                }
                if ((ptr + val) < lines.size()) {
                    final String instruction = lines.get(ptr + val);
                    final String newInstruction = getNewInstruction(instruction);
                    lines.set(ptr + val, newInstruction);
                }
                ptr++;
            } else {
                throw new IllegalArgumentException("Unknown instruction: " + line);
            }
        }
        return a;
    }

    public int runPart2(final List<String> lines) {
        return runProgram(12, 0, 0, 0, lines);
    }

    public int runPart1(final List<String> lines) {
        return runProgram(7, 0, 0, 0, lines);
    }

    public static void main(final String[] args) {
        List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day23().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        lines = FileReader.getStringList(FILENAME);
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day23().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
