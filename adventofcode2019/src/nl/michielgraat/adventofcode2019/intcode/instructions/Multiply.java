package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.List;

public class Multiply extends Instruction {

    public Multiply(final List<Integer> memory, final int ptr) {
        super(memory, ptr);
    }

    @Override
    public String getName() {
        return "Multiply";
    }

    @Override
    public int getOpcode() {
        return 2;
    }

    @Override
    public int getNrParameters() {
        return 3;
    }

    @Override
    public List<Integer> getInputPositions() {
        return memory.subList(ptr + 1, ptr + 3);
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
    public int execute() {
        return memory.get(memory.get(ptr + 1)) * memory.get(memory.get(ptr + 2));
    }

}
