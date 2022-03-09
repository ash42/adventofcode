package nl.michielgraat.adventofcode2016.day25;

import java.util.List;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day25 {

    private static final String FILENAME = "day25.txt";

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

    private boolean runProgram(int a, int b, int c, int d, final List<String> lines) {
        final int last = lines.size() - 1;
        String result = "";
        int ptr = 0;
        while (ptr <= last) {
            final String line = lines.get(ptr);
            if (line.startsWith("inc")) {
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
            } else if (line.startsWith("out")) {
                final String register = line.substring(line.indexOf(" ") + 1);
                int val = 0;
                if (isNumeric(register)) {
                    val = Integer.parseInt(register);
                } else {
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
                }
                result += val;
                if (result.length() == 10) {
                    return result.equals("0101010101");
                }
                ptr++;
            } else {
                throw new IllegalArgumentException("Unknown instruction: " + line);
            }
        }
        return false;
    }

    public int runPart2(final List<String> lines) {
        return 0;
    }

    public int runPart1(final List<String> lines) {
        int a = -1;
        boolean found = false;
        while (!found) {
            found = runProgram(++a, 0, 0, 0, lines);
        }
        return a;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        final long start = System.nanoTime();
        System.out.println("Answer: " + new Day25().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
