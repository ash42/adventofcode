package nl.michielgraat.adventofcode2024.day17;

import java.util.Arrays;
import java.util.List;

public class Computer {
    private long a = 0;
    private long b = 0;
    private long c = 0;
    private final int[] program;
    private int ptr = 0;
    private String output = new String();

    public Computer(final List<String> input) {
        a = Long.parseLong(input.get(0).split(": ")[1]);
        b = Long.parseLong(input.get(1).split(": ")[1]);
        c = Long.parseLong(input.get(2).split(": ")[1]);
        program = Arrays.stream(input.get(4).split(": ")[1].split(",")).mapToInt(Integer::parseInt).toArray();
        ptr = 0;
    }

    private long getComboVal(final int operand) {
        switch (operand) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return a;
            case 5:
                return b;
            case 6:
                return c;
            case 7:
                throw new IllegalArgumentException("Reserved combo operand 7 found, program not valid");
            default:
                throw new IllegalArgumentException("Unknown combo operand " + operand);
        }
    }

    private void cdv(final int operand) {
        c = (long) (a / Math.pow(2, getComboVal(operand)));
        ptr += 2;
    }

    private void bdv(final int operand) {
        b = (long) (a / Math.pow(2, getComboVal(operand)));
        ptr += 2;
    }

    private void out(final int operand) {
        String result = String.valueOf(getComboVal(operand) % 8);
        if (!output.isEmpty()) {
            result = "," + result;
        }
        output += result;
        ptr += 2;
    }

    private void bxc() {
        b = b ^ c;
        ptr += 2;
    }

    private void jnz(final int operand) {
        ptr = a == 0 ? ptr + 2 : operand;
    }

    private void bst(final int operand) {
        b = getComboVal(operand) % 8;
        ptr += 2;
    }

    private void bxl(final int operand) {
        b = b ^ operand;
        ptr += 2;
    }

    private void adv(final int operand) {
        a = (long) (a / Math.pow(2, getComboVal(operand)));
        ptr += 2;
    }

    private void runInstruction(final int opcode, final int operand) {
        switch (opcode) {
            case 0:
                adv(operand);
                break;
            case 1:
                bxl(operand);
                break;
            case 2:
                bst(operand);
                break;
            case 3:
                jnz(operand);
                break;
            case 4:
                bxc();
                break;
            case 5:
                out(operand);
                break;
            case 6:
                bdv(operand);
                break;
            case 7:
                cdv(operand);
                break;
            default:
                throw new IllegalArgumentException("Unknown opcode: " + opcode);
        }
    }

    public void run() {
        while (ptr < program.length) {
            runInstruction(program[ptr], program[ptr + 1]);
        }
    }

    public void setA(final long a) {
        this.a = a;
    }

    public int[] getOutput() {
        return Arrays.stream(output.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public int[] getProgram() {
        return program;
    }

    @Override
    public String toString() {
        return "Computer [a=" + a + ", b=" + b + ", c=" + c + ", program=" + Arrays.toString(program) + ", ptr=" + ptr
                + "]";
    }
}
