package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.List;

public class JumpIfTrue extends Instruction {

    int ptrIncr;

    public JumpIfTrue(final List<Integer> memory, final int ptr, final int modes) {
        super(memory, ptr, modes);
        this.ptrIncr = 3;
    }

    @Override
    public String getName() {
        return "Jump-if-true";
    }

    @Override
    public int getOpcode() {
        return 5;
    }

    @Override
    public int getNrParameters() {
        return 2;
    }

    @Override
    public List<Integer> getInputPositions() {
        return memory.subList(ptr + 1, ptr + 3);
    }

    @Override
    public int getOutputPosition() {
        return -1;
    }

    @Override
    public int getPtrIncrease() {
        return ptrIncr;
    }

    @Override
    public void execute() {
        int var1 = 0;
        int var2 = 0;
        if (modes > -1) {
            final int mode1 = modes % 10;
            final int mode2 = (modes / 10) % 10;
            var1 = mode1 == 0 ? memory.get(memory.get(ptr + 1)) : memory.get(ptr + 1);
            var2 = mode2 == 0 ? memory.get(memory.get(ptr + 2)) : memory.get(ptr + 2);
        } else {
            var1 = memory.get(memory.get(ptr + 1));
            var2 = memory.get(memory.get(ptr + 2));
        }
        if (var1 != 0) {
            ptrIncr = var2 - ptr;
        }
    }

}
