package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.Deque;
import java.util.List;

public abstract class Instruction {

    List<Long> memory;
    int ptr;
    int modes;
    Deque<Long> input;
    Deque<Long> output;

    Instruction(final List<Long> memory, final int ptr, final int modes, final Deque<Long> input,
            final Deque<Long> output) {
        this.memory = memory;
        this.ptr = ptr;
        this.modes = modes;
        this.input = input;
        this.output = output;
    }

    Instruction(final List<Long> memory, final int ptr, final int modes, final Deque<Long> input) {
        this.memory = memory;
        this.ptr = ptr;
        this.modes = modes;
        this.input = input;
    }

    Instruction(final List<Long> memory, final int ptr, final int modes) {
        this.memory = memory;
        this.modes = modes;
        this.ptr = ptr;
    }

    public boolean waitingForInput() {
        return this instanceof Input && this.input.isEmpty();
    }

    long readInput() {
        return this.input.removeLast();
    }

    void saveOutput(final long nr) {
        this.output.push(nr);
    }

    public abstract String getName();

    public abstract int getOpcode();

    public abstract int getNrParameters();

    public abstract List<Long> getInputPositions();

    public abstract int getOutputPosition();

    public abstract int getPtrIncrease();

    public abstract void execute();
}
