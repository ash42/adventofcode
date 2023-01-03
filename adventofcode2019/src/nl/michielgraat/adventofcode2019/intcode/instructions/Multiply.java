package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.List;

public class Multiply extends Instruction {

    public Multiply(final List<Long> memory, final int ptr, final int modes) {
        super(memory, ptr, modes);
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
        if (modes > -1) {
            final int mode1 = modes % 10;
            final int mode2 = (modes / 10) % 10;
            final long var1 = mode1 == 0 ? memory.get(memory.get(ptr + 1).intValue()) : memory.get(ptr + 1);
            final long var2 = mode2 == 0 ? memory.get(memory.get(ptr + 2).intValue()) : memory.get(ptr + 2);
            memory.set(memory.get(getOutputPosition()).intValue(), var1 * var2);
        } else {
            memory.set(memory.get(getOutputPosition()).intValue(),
                    memory.get(memory.get(ptr + 1).intValue()) * memory.get(memory.get(ptr + 2).intValue()));
        }
    }

}
