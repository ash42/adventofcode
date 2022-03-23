package nl.michielgraat.adventofcode2017.day23;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Program {

    private final Map<String, Long> regToValue = new HashMap<>();
    private final List<String> lines;
    private int ptr = 0;
    private long timesMul = 0L;

    public Program(final List<String> lines) {
        this.lines = lines;
    }

    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    private boolean isNumeric(final String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    private boolean hasSingleParam(final String line) {
        return line.startsWith("snd") || line.startsWith("rcv");
    }

    private long getSecondVal(final String line, final Map<String, Long> regToValue) {
        if (hasSingleParam(line)) {
            throw new IllegalArgumentException("Instruction does not have a second parameter");
        }
        final String reg = line.substring(line.indexOf(" ", 4) + 1);
        if (isNumeric(reg)) {
            return Long.parseLong(reg);
        } else {
            return regToValue.get(reg);
        }
    }

    private long getFirstVal(final String line, final Map<String, Long> regToValue) {
        final String reg = getTargetRegister(line);
        if (isNumeric(reg)) {
            return Long.parseLong(reg);
        } else {
            return regToValue.get(reg);
        }
    }

    private String getTargetRegister(final String line) {
        return hasSingleParam(line) ? line.substring(4) : line.substring(4, line.indexOf(" ", 4));
    }

    private long getJmp(final String line, final Map<String, Long> regToValue) {
        final long val = getFirstVal(line, regToValue);
        if (val != 0) {
            return getSecondVal(line, regToValue);
        } else {
            return 1;
        }
    }

    public long runProgram() {

        while (ptr < lines.size()) {
            final String line = lines.get(ptr);
            final String reg = getTargetRegister(line);
            if (!regToValue.containsKey(reg)) {
                regToValue.put(reg, 0L);
            }
            if (line.startsWith("jnz")) {
                ptr += getJmp(line, regToValue);
            } else {
                if (line.startsWith("set")) {
                    regToValue.put(getTargetRegister(line), getSecondVal(line, regToValue));
                } else if (line.startsWith("sub")) {
                    regToValue.put(getTargetRegister(line),
                            getFirstVal(line, regToValue) - getSecondVal(line, regToValue));
                } else if (line.startsWith("mul")) {
                    regToValue.put(getTargetRegister(line),
                            getFirstVal(line, regToValue) * getSecondVal(line, regToValue));
                    timesMul++;
                } else {
                    throw new IllegalArgumentException("Unknown instruction '" + line + "'");
                }
                ptr++;
            }
        }

        return timesMul;
    }
}