package nl.michielgraat.adventofcode2018.day16;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Instruction {

    private final int opcode;
    private final int a;
    private final int b;
    private final int c;

    public int[] addr(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] + input[b];
        return output;
    }

    public int[] addi(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] + b;
        return output;
    }

    public int[] mulr(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] * input[b];
        return output;
    }

    public int[] muli(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] * b;
        return output;
    }

    public int[] banr(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] & input[b];
        return output;
    }

    public int[] bani(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] & b;
        return output;
    }

    public int[] borr(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] | input[b];
        return output;
    }

    public int[] bori(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] | b;
        return output;
    }

    public int[] setr(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a];
        return output;
    }

    public int[] seti(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = a;
        return output;
    }

    public int[] gtir(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = (a > input[b]) ? 1 : 0;
        return output;
    }

    public int[] gtri(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = (input[a] > b) ? 1 : 0;
        return output;
    }

    public int[] gtrr(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = (input[a] > input[b]) ? 1 : 0;
        return output;
    }

    public int[] eqir(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = (a == input[b]) ? 1 : 0;
        return output;
    }

    public int[] eqri(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = (input[a] == b) ? 1 : 0;
        return output;
    }

    public int[] eqrr(final int[] input) {
        final int[] output = Arrays.copyOf(input, input.length);
        output[c] = (input[a] == input[b]) ? 1 : 0;
        return output;
    }

    public Instruction(final String instruction) {
        final String[] insArray = instruction.split(" ");
        this.opcode = Integer.parseInt(insArray[0]);
        this.a = Integer.parseInt(insArray[1]);
        this.b = Integer.parseInt(insArray[2]);
        this.c = Integer.parseInt(insArray[3]);
    }

    public int[] run(final int[] input, final Map<Integer, List<Opcode>> mapping) {
        final Opcode op = mapping.get(opcode).get(0);
        if (op == Opcode.ADDI)
            return addi(input);
        if (op == Opcode.ADDR)
            return addr(input);
        if (op == Opcode.MULI)
            return muli(input);
        if (op == Opcode.MULR)
            return mulr(input);
        if (op == Opcode.BANR)
            return banr(input);
        if (op == Opcode.BANI)
            return bani(input);
        if (op == Opcode.BORR)
            return borr(input);
        if (op == Opcode.BORI)
            return bori(input);
        if (op == Opcode.SETR)
            return setr(input);
        if (op == Opcode.SETI)
            return seti(input);
        if (op == Opcode.GTIR)
            return gtir(input);
        if (op == Opcode.GTRI)
            return gtri(input);
        if (op == Opcode.GTRR)
            return gtrr(input);
        if (op == Opcode.EQIR)
            return eqir(input);
        if (op == Opcode.EQRI)
            return eqri(input);
        if (op == Opcode.EQRR)
            return eqrr(input);
        throw new IllegalArgumentException("Unknown opcode: " + opcode);
    }

    public int getOpcode() {
        return opcode;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    @Override
    public String toString() {
        return "Instruction [a=" + a + ", b=" + b + ", c=" + c + ", opcode=" + opcode + "]";
    }

}
