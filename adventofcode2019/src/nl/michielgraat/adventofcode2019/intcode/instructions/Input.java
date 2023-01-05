package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.Deque;
import java.util.List;

import nl.michielgraat.adventofcode2019.intcode.RelativeBase;

public class Input extends Instruction {

    public Input(final List<Long> memory, final int ptr, final int modes, final RelativeBase relativeBase, final Deque<Long> input) {
        super(memory, ptr, modes, relativeBase, input);
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
        final long input = memory.get(ptr+1);
        final int mode1 = modes % 10;
        final long value = readInput();
        final int address = (int) (mode1 == RELATIVE_MODE ? input + relativeBase.intValue() : input);
        writeToMemory(address, value);
    }

}
