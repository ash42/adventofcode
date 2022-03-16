package nl.michielgraat.adventofcode2017.day18;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Pattern;

public class Program {

    private final Map<String, Long> regToValue = new HashMap<>();
    private final List<String> lines;
    private final Deque<Long> input;
    private final Deque<Long> output;
    private final int id;
    private int ptr = 0;
    private int timesSend = 0;

    public Program(final int id, final List<String> lines) {
        this.id = id;
        this.lines = lines;
        input = new ArrayDeque<>();
        output = new ArrayDeque<>();
    }

    public Program(final int id, final List<String> lines, final Deque<Long> input, final Deque<Long> output) {
        this.id = id;
        this.lines = lines;
        this.input = input;
        this.output = output;
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
        if (val > 0) {
            return getSecondVal(line, regToValue);
        } else {
            return 1;
        }
    }

    public long runProgram(final boolean part1) {
        long lastFreq = -1;

        while (ptr < lines.size()) {
            final String line = lines.get(ptr);
            final String reg = getTargetRegister(line);
            if (!regToValue.containsKey(reg)) {
                regToValue.put(reg, ("p".equals(reg) ? (long) id : 0L));
            }
            if (line.startsWith("jgz")) {
                ptr += getJmp(line, regToValue);
            } else {
                if (line.startsWith("snd")) {
                    lastFreq = getFirstVal(line, regToValue);
                    if (!part1) {
                        output.add(lastFreq);
                        timesSend++;
                    }
                } else if (line.startsWith("set")) {
                    regToValue.put(getTargetRegister(line), getSecondVal(line, regToValue));
                } else if (line.startsWith("add")) {
                    regToValue.put(getTargetRegister(line),
                            getFirstVal(line, regToValue) + getSecondVal(line, regToValue));
                } else if (line.startsWith("mul")) {
                    regToValue.put(getTargetRegister(line),
                            getFirstVal(line, regToValue) * getSecondVal(line, regToValue));
                } else if (line.startsWith("mod")) {
                    regToValue.put(getTargetRegister(line),
                            getFirstVal(line, regToValue) % getSecondVal(line, regToValue));
                } else if (line.startsWith("rcv")) {
                    if (part1) {
                        if (getFirstVal(line, regToValue) != 0) {
                            return lastFreq;
                        }
                    } else {
                        if (input.isEmpty()) {
                            return -1;
                        } else {
                            regToValue.put(getTargetRegister(line), input.pop());
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Unknown instruction '" + line + "'");
                }
                ptr++;
            }
        }

        return 0;
    }

    public boolean isWaiting() {
        return input.isEmpty() && lines.get(ptr).startsWith("rcv");
    }

    public boolean isHalted() {
        return ptr < 0 || ptr >= lines.size();
    }

    public int getTimesSend() {
        return timesSend;
    }

}
