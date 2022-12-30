package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.Deque;
import java.util.List;

public abstract class Instruction {

    List<Integer> memory;
    int ptr;
    int modes;
    Deque<Integer> input;
    Deque<Integer> output;

    Instruction(final List<Integer> memory, final int ptr, final int modes, final Deque<Integer> input,
            final Deque<Integer> output) {
        this.memory = memory;
        this.ptr = ptr;
        this.modes = modes;
        this.input = input;
        this.output = output;
    }

    Instruction(final List<Integer> memory, final int ptr, final int modes, final Deque<Integer> input) {
        this.memory = memory;
        this.ptr = ptr;
        this.modes = modes;
        this.input = input;
    }

    Instruction(final List<Integer> memory, final int ptr, final int modes) {
        this.memory = memory;
        this.modes = modes;
        this.ptr = ptr;
    }

    int readInput() {
        return this.input.removeLast();
    }

    void saveOutput(final int nr) {
        this.output.push(nr);
    }

    public abstract String getName();

    public abstract int getOpcode();

    public abstract int getNrParameters();

    public abstract List<Integer> getInputPositions();

    public abstract int getOutputPosition();

    public abstract int getPtrIncrease();

    public abstract void execute();
}
