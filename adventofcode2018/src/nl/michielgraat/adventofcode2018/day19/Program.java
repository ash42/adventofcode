package nl.michielgraat.adventofcode2018.day19;

import java.util.ArrayList;
import java.util.List;

public class Program {
    List<Instruction> instructions = new ArrayList<>();
    long insPtr = 0;
    int ipReg = 0;
    long[] registers = new long[6];

    public Program(final List<String> lines) {
        parseProgram(lines);
    }

    private String toString(final long[] input) {
        final StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < input.length; i++) {
            if (i < input.length - 1) {
                s.append(input[i] + " ");
            } else {
                s.append(input[i] + "]");
            }
        }
        return s.toString();
    }

    private void parseProgram(final List<String> lines) {
        final List<Instruction> program = new ArrayList<>();
        for (final String line : lines) {
            if (line.startsWith("#")) {
                ipReg = Integer.parseInt(line.split(" ")[1]);
            } else {
                instructions.add(new Instruction(line));
            }
        }
    }

    public long run() {
        while (insPtr < instructions.size()) {
            registers[ipReg] = insPtr;
            final Instruction current = instructions.get((int) registers[ipReg]);
            registers = current.run(registers);
            insPtr = registers[ipReg];
            insPtr++;
        }
        return registers[0];
    }
}
