package nl.michielgraat.adventofcode2019.intcode.instructions;

import java.util.Deque;
import java.util.List;

import nl.michielgraat.adventofcode2019.intcode.RelativeBase;

public abstract class Instruction {

    static final int POSITION_MODE = 0;
    static final int IMMEDIATE_MODE = 1;
    static final int RELATIVE_MODE = 2;

    List<Long> memory;
    int ptr;
    int modes;
    RelativeBase relativeBase;
    Deque<Long> input;
    Deque<Long> output;

    Instruction(final List<Long> memory, final int ptr, final int modes, final RelativeBase relativeBase,
            final Deque<Long> input,
            final Deque<Long> output) {
        this.memory = memory;
        this.ptr = ptr;
        this.modes = modes;
        this.input = input;
        this.relativeBase = relativeBase;
        this.output = output;
    }

    Instruction(final List<Long> memory, final int ptr, final int modes, final RelativeBase relativeBase,
            final Deque<Long> input) {
        this.memory = memory;
        this.ptr = ptr;
        this.modes = modes;
        this.relativeBase = relativeBase;
        this.input = input;
    }

    Instruction(final List<Long> memory, final int ptr, final int modes, final RelativeBase relativeBase) {
        this.memory = memory;
        this.modes = modes;
        this.ptr = ptr;
        this.relativeBase = relativeBase;
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

    void writeToMemory(final int pos, final long val) {
        increaseMemoryTo(pos);
        memory.set(pos, val);
    }

    boolean isPositionMode(int mode) {
        return mode <= POSITION_MODE;
    }

    private void increaseMemoryTo(final int address) {
        while (memory.size() < address) {
            memory.add(0L);
        }
    }

    int getOutputPosition(final int mode) {
        final int value = memory.get(getOutputPosition()).intValue();
        return mode == RELATIVE_MODE ? value + relativeBase.intValue() : value;
    }

    long getVar(final int mode, final int position) {
        final long param = memory.get(position);
        if (isPositionMode(mode)) {
            return memory.get((int) param);
        } else {
            return mode == IMMEDIATE_MODE ? param : memory.get((int) (param + relativeBase.intValue())).longValue();
        }
    }

    public abstract String getName();

    public abstract int getOpcode();

    public abstract int getNrParameters();

    public abstract List<Long> getInputPositions();

    public abstract int getOutputPosition();

    public abstract int getPtrIncrease();

    public abstract void execute();
}
