package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.ArrayList;
import java.util.List;

public class Halt extends Instruction {

    public Halt(final List<Integer> memory, final int ptr) {
        super(memory, ptr);
    }

    @Override
    public String getName() {
        return "Halt";
    }

    @Override
    public int getOpcode() {
        return 99;
    }

    @Override
    public int getNrParameters() {
        return 0;
    }

    @Override
    public List<Integer> getInputPositions() {
        return new ArrayList<>();
    }

    @Override
    public int getOutputPosition() {
        return 0;
    }

    @Override
    public int getPtrIncrease() {
        return 0;
    }

    @Override
    public int execute() {
        return 0;
    }

}
