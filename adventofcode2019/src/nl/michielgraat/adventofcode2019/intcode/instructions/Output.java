package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import nl.michielgraat.adventofcode2019.intcode.RelativeBase;

public class Output extends Instruction {

    public Output(final List<Long> memory, final int ptr, final int modes, final RelativeBase relativeBase,
            final Deque<Long> input,
            final Deque<Long> output) {
        super(memory, ptr, modes, relativeBase, input, output);
    }

    @Override
    public String getName() {
        return "Output";
    }

    @Override
    public int getOpcode() {
        return 4;
    }

    @Override
    public int getNrParameters() {
        return 1;
    }

    @Override
    public List<Long> getInputPositions() {
        return new ArrayList<>();
    }

    @Override
    public int getOutputPosition() {
        return ptr + 1;
    }

    @Override
    public int getPtrIncrease() {
        return 2;
    }

    @Override
    public void execute() {
        saveOutput(getVar(modes % 10, ptr + 1));
    }

}
