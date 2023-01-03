package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.Deque;
import java.util.List;

public class Input extends Instruction {

    public Input(final List<Long> memory, final int ptr, final int modes, final Deque<Long> input) {
        super(memory, ptr, modes, input);
    }

    @Override
    public String getName() {
        return "Input";
    }

    @Override
    public int getOpcode() {
        return 3;
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
        memory.set(memory.get(ptr + 1).intValue(), readInput());
    }

}
