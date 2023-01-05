package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.List;

import nl.michielgraat.adventofcode2019.intcode.RelativeBase;

public class LessThan extends Instruction {

    public LessThan(final List<Long> memory, final int ptr, final int modes, final RelativeBase relativeBase) {
        super(memory, ptr, modes, relativeBase);
    }

    @Override
    public String getName() {
        return "Less than";
    }

    @Override
    public int getOpcode() {
        return 7;
    }

    @Override
    public int getNrParameters() {
        return 3;
    }

    @Override
    public List<Long> getInputPositions() {
        return memory.subList(ptr + 1, ptr + 4);
    }

    @Override
    public int getOutputPosition() {
        return ptr + 3;
    }

    @Override
    public int getPtrIncrease() {
        return 4;
    }

    @Override
    public void execute() {
        final long var1 = getVar(modes % 10, ptr + 1);
        final long var2 = getVar((modes / 10) % 10, ptr + 2);
        final int outputPosition = getOutputPosition((modes / 100) % 10);
        writeToMemory(outputPosition, var1 < var2 ? 1L : 0L);
    }

}
