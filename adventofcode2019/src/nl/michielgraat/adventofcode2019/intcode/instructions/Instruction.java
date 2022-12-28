package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.List;

public abstract class Instruction {

    List<Integer> memory;
    int ptr;

    Instruction(final List<Integer> memory, final int ptr) {
        this.memory = memory.stream().toList();
        this.ptr = ptr;
    }

    public abstract String getName();

    public abstract int getOpcode();

    public abstract int getNrParameters();

    public abstract List<Integer> getInputPositions();

    public abstract int getOutputPosition();

    public abstract int getPtrIncrease();

    public abstract int execute();
}
