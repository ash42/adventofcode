package nl.michielgraat.adventofcode2018.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day16 {

    private static final String FILENAME = "day16.txt";

    private int[] parseArray(final String s) {
        final String arrayString = s.substring(s.indexOf("[") + 1, s.indexOf("]"));
        final String[] values = arrayString.split(", ");
        final int[] result = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = Integer.parseInt(values[i]);
        }
        return result;
    }

    private List<Opcode> geMatchingOpcodes(final Instruction ins, final int[] before, final int[] after) {
        final List<Opcode> matching = new ArrayList<>();
        int[] result = ins.addi(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.ADDI);

        result = ins.addr(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.ADDR);

        result = ins.mulr(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.MULR);

        result = ins.muli(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.MULI);

        result = ins.banr(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.BANR);

        result = ins.bani(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.BANI);

        result = ins.borr(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.BORR);

        result = ins.bori(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.BORI);

        result = ins.setr(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.SETR);

        result = ins.seti(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.SETI);

        result = ins.gtir(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.GTIR);

        result = ins.gtri(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.GTRI);

        result = ins.gtrr(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.GTRR);

        result = ins.eqir(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.EQIR);

        result = ins.eqri(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.EQRI);

        result = ins.eqrr(before);
        if (Arrays.equals(after, result))
            matching.add(Opcode.EQRR);

        return matching;
    }

    private List<Opcode> copyWithout(final List<Opcode> toRemove, final List<Opcode> original) {
        final List<Opcode> opcodes = new ArrayList<>();
        for (final Opcode o : original) {
            opcodes.add(o);
        }
        opcodes.removeAll(toRemove);
        return opcodes;
    }

    private Map<Integer, List<Opcode>> calculateOpcodes(Map<Integer, List<Opcode>> nrToOpcode) {
        boolean changed = true;

        Map<Integer, List<Opcode>> result = new HashMap<>();
        while (changed) {
            changed = false;
            result = new HashMap<>();
            final List<Opcode> valuesToRemove = new ArrayList<>();
            // Find all entries with exactly one opcode, that opcode can be
            // removed from all other entries (because we know to which number
            // it belongs).
            for (final Entry<Integer, List<Opcode>> entry : nrToOpcode.entrySet()) {
                final List<Opcode> values = entry.getValue();
                if (values.size() == 1) {
                    valuesToRemove.add(values.get(0));
                }
            }
            // Remove the opcode from all entries where it isn't unique.
            for (final Entry<Integer, List<Opcode>> entry : nrToOpcode.entrySet()) {
                final int opcode = entry.getKey();
                final int size = entry.getValue().size();
                if (size == 1) {
                    result.put(opcode, entry.getValue());
                } else {
                    final List<Opcode> remaining = copyWithout(valuesToRemove, entry.getValue());
                    result.put(opcode, remaining);
                    if (remaining.size() < size) {
                        changed = true;
                    }
                }
            }
            nrToOpcode = result;
        }
        return result;
    }

    public int runPart2(final List<String> lines) {
        final Map<Integer, List<Opcode>> nrToOpcode = new HashMap<>();
        // Build mapping from number to all possible opcodes.
        for (int i = 0; i < lines.size(); i += 4) {
            if (lines.get(i).isEmpty() && lines.get(i+1).isEmpty()) {
                break;
            }
            final String before = lines.get(i);
            final String instruction = lines.get(i + 1);
            final String after = lines.get(i + 2);
            final Instruction ins = new Instruction(instruction);
            nrToOpcode.put(ins.getOpcode(), geMatchingOpcodes(ins, parseArray(before), parseArray(after)));
        }
        // Find out which opcode belongs to which number.
        final Map<Integer, List<Opcode>> mapping = calculateOpcodes(nrToOpcode);
        // Run the program.
        int[] registers = new int[4];
        boolean prevBefore = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isEmpty() || line.startsWith("Before") || line.startsWith("After") || prevBefore) {
                prevBefore = line.startsWith("Before");
            } else {
                final Instruction ins = new Instruction(line);
                registers = ins.run(registers, mapping);
            }
        }
        return registers[0];
    }

    public int runPart1(final List<String> lines) {
        int total = 0;
        for (int i = 0; i < lines.size(); i += 4) {
            if (lines.get(i).isEmpty() && lines.get(i+1).isEmpty()) {
                break;
            }
            final String before = lines.get(i);
            final String instruction = lines.get(i + 1);
            final String after = lines.get(i + 2);
            final Instruction ins = new Instruction(instruction);
            final int nrOfMatching = geMatchingOpcodes(ins, parseArray(before), parseArray(after)).size();
            if (nrOfMatching >= 3) {
                total++;
            }
        }
        return total;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day16().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day16().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
