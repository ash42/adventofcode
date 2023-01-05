package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.List;

import nl.michielgraat.adventofcode2019.intcode.RelativeBase;

public class JumpIfFalse extends Instruction {

    int ptrIncr;

    public JumpIfFalse(final List<Long> memory, final int ptr, final int modes, final RelativeBase relativeBase) {
        super(memory, ptr, modes, relativeBase);
        this.ptrIncr = 3;
    }

    @Override
    public String getName() {
        return "Jump-if-false";
    }

    @Override
    public int getOpcode() {
        return 6;
    }

    @Override
    public int getNrParameters() {
        return 2;
    }

    @Override
    public List<Long> getInputPositions() {
        return memory.subList(ptr + 1, ptr + 3);
    }

    @Override
    public int getOutputPosition() {
        return -1;
    }

    @Override
    public int getPtrIncrease() {
        return ptrIncr;
    }

    @Override
    public void execute() {
        final long var1 = getVar(modes % 10, ptr + 1);
        final long var2 = getVar((modes / 10) % 10, ptr + 2);
        if (var1 == 0) {
            ptrIncr = (int) var2 - ptr;
        }
    }

}
