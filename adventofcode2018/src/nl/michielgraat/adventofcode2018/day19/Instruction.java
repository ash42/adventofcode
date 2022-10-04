package nl.michielgraat.adventofcode2018.day19;

import java.util.Arrays;

public class Instruction {

    private final Opcode opcode;
    private final int a;
    private final int b;
    private final int c;

    public long[] addr(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] + input[b];
        return output;
    }

    public long[] addi(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] + b;
        return output;
    }

    public long[] mulr(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] * input[b];
        return output;
    }

    public long[] muli(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] * b;
        return output;
    }

    public long[] banr(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] & input[b];
        return output;
    }

    public long[] bani(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] & b;
        return output;
    }

    public long[] borr(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] | input[b];
        return output;
    }

    public long[] bori(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a] | b;
        return output;
    }

    public long[] setr(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = input[a];
        return output;
    }

    public long[] seti(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = a;
        return output;
    }

    public long[] gtir(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = (a > input[b]) ? 1 : 0;
        return output;
    }

    public long[] gtri(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = (input[a] > b) ? 1 : 0;
        return output;
    }

    public long[] gtrr(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = (input[a] > input[b]) ? 1 : 0;
        return output;
    }

    public long[] eqir(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = (a == input[b]) ? 1 : 0;
        return output;
    }

    public long[] eqri(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = (input[a] == b) ? 1 : 0;
        return output;
    }

    public long[] eqrr(final long[] input) {
        final long[] output = Arrays.copyOf(input, input.length);
        output[c] = (input[a] == input[b]) ? 1 : 0;
        return output;
    }

    public Instruction(final String instruction) {
        final String[] insArray = instruction.split(" ");
        this.opcode = Opcode.fromString(insArray[0]);
        this.a = Integer.parseInt(insArray[1]);
        this.b = Integer.parseInt(insArray[2]);
        this.c = Integer.parseInt(insArray[3]);
    }

    public long[] run(final long[] input) {
        if (opcode == Opcode.ADDI)
            return addi(input);
        if (opcode == Opcode.ADDR)
            return addr(input);
        if (opcode == Opcode.MULI)
            return muli(input);
        if (opcode == Opcode.MULR)
            return mulr(input);
        if (opcode == Opcode.BANR)
            return banr(input);
        if (opcode == Opcode.BANI)
            return bani(input);
        if (opcode == Opcode.BORR)
            return borr(input);
        if (opcode == Opcode.BORI)
            return bori(input);
        if (opcode == Opcode.SETR)
            return setr(input);
        if (opcode == Opcode.SETI)
            return seti(input);
        if (opcode == Opcode.GTIR)
            return gtir(input);
        if (opcode == Opcode.GTRI)
            return gtri(input);
        if (opcode == Opcode.GTRR)
            return gtrr(input);
        if (opcode == Opcode.EQIR)
            return eqir(input);
        if (opcode == Opcode.EQRI)
            return eqri(input);
        if (opcode == Opcode.EQRR)
            return eqrr(input);
        throw new IllegalArgumentException("Unknown opcode: " + opcode);
    }

    public Opcode getOpcode() {
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
        return opcode.name().toLowerCase() + " " + a + " " + b + " " + c;
    }

}
