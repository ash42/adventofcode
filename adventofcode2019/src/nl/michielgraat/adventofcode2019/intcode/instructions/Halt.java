package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.ArrayList;
import java.util.List;

public class Halt extends Instruction {

    public Halt(final List<Long> memory, final int ptr, final int modes) {
        super(memory, ptr, modes);
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
    public List<Long> getInputPositions() {
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
    public void execute() {
    }

}
