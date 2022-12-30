package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Output extends Instruction {

    public Output(final List<Integer> memory, final int ptr, final int modes, final Deque<Integer> input,
            final Deque<Integer> output) {
        super(memory, ptr, modes, input, output);
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
    public List<Integer> getInputPositions() {
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
        if (modes > 0) {
            saveOutput(memory.get(ptr + 1));
        } else {
            saveOutput(memory.get(memory.get(ptr + 1)));
        }
    }

}
