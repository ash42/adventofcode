package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.List;

import nl.michielgraat.adventofcode2019.intcode.RelativeBase;

public class AdjustRelativeBase extends Instruction {

    public AdjustRelativeBase(final List<Long> memory, final int ptr, final int modes,
            final RelativeBase relativeBase) {
        super(memory, ptr, modes, relativeBase);
    }

    @Override
    public String getName() {
        return "Adjust relative base";
    }

    @Override
    public int getOpcode() {
        return 9;
    }

    @Override
    public int getNrParameters() {
        return 1;
    }

    @Override
    public List<Long> getInputPositions() {
        return memory.subList(ptr + 1, ptr + 2);
    }

    @Override
    public int getOutputPosition() {
        return -1;
    }

    @Override
    public int getPtrIncrease() {
        return 2;
    }

    @Override
    public void execute() {
        relativeBase.increaseBy(getVar(modes % 10, ptr + 1));
    }
}
