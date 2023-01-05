package nl.michielgraat.adventofcode2019.intcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nl.michielgraat.adventofcode2019.intcode.instructions.Add;
import nl.michielgraat.adventofcode2019.intcode.instructions.AdjustRelativeBase;
import nl.michielgraat.adventofcode2019.intcode.instructions.Equals;
import nl.michielgraat.adventofcode2019.intcode.instructions.Halt;
import nl.michielgraat.adventofcode2019.intcode.instructions.Input;
import nl.michielgraat.adventofcode2019.intcode.instructions.Instruction;
import nl.michielgraat.adventofcode2019.intcode.instructions.JumpIfFalse;
import nl.michielgraat.adventofcode2019.intcode.instructions.JumpIfTrue;
import nl.michielgraat.adventofcode2019.intcode.instructions.LessThan;
import nl.michielgraat.adventofcode2019.intcode.instructions.Multiply;
import nl.michielgraat.adventofcode2019.intcode.instructions.Output;

public class IntcodeComputer {
    private final List<Long> program;
    private List<Long> memory;
    private Deque<Long> input;
    private Deque<Long> output;
    private int ptr = 0;
    private boolean waitingForInput = false;
    private boolean halted = false;
    private RelativeBase relativeBase = new RelativeBase();

    public IntcodeComputer(final List<String> input) {
        final String programString = input.get(0);
        this.program = Stream.of(programString.split(",")).map(String::trim).map(Long::parseLong).toList();
        reset();
    }

    private Instruction getInstruction(final int opcode, final int modes) {
        switch (opcode) {
            case 1:
                return new Add(memory, ptr, modes, relativeBase);
            case 2:
                return new Multiply(memory, ptr, modes, relativeBase);
            case 3:
                return new Input(memory, ptr, modes, relativeBase, input);
            case 4:
                return new Output(memory, ptr, modes, relativeBase, input, output);
            case 5:
                return new JumpIfTrue(memory, ptr, modes, relativeBase);
            case 6:
                return new JumpIfFalse(memory, ptr, modes, relativeBase);
            case 7:
                return new LessThan(memory, ptr, modes, relativeBase);
            case 8:
                return new Equals(memory, ptr, modes, relativeBase);
            case 9:
                return new AdjustRelativeBase(memory, ptr, modes, relativeBase);
            case 99:
                return new Halt(memory, ptr, modes, relativeBase);
            default:
                throw new IllegalArgumentException("Unknown opcode: [" + opcode + "]");
        }
    }

    public void run() {
        int current = 0;
        waitingForInput = false;
        while (!waitingForInput && !halted) {
            current = memory.get(ptr).intValue();
            int opcode = current;
            int modes = -1;
            if (opcode >= 100) {
                opcode = current % 100;
                modes = current / 100;
            }
            final Instruction i = getInstruction(opcode, modes);
            if (!(i instanceof Halt)) {
                waitingForInput = i.waitingForInput();
                if (!waitingForInput) {
                    waitingForInput = false;
                    i.execute();
                    ptr += i.getPtrIncrease();
                }
            } else {
                halted = true;
            }
        }
    }

    public void reset() {
        this.memory = this.program.stream().collect(Collectors.toList());
        // Initially set memory size to 10 times program size
        increaseMemoryTo(memory.size() * 10);
        this.ptr = 0;
        this.input = new ArrayDeque<>();
        this.output = new ArrayDeque<>();
        this.halted = false;
        this.waitingForInput = false;
        this.relativeBase = new RelativeBase();
    }

    private void increaseMemoryTo(final int address) {
        while (memory.size() < address) {
            memory.add(0L);
        }
    }

    public long getValueAtAddress(final int address) {
        return this.memory.get(address);
    }

    public void setValueAtAddress(final int address, final long value) {
        this.memory.set(address, value);
    }

    public void addInput(final long nr) {
        this.input.push(nr);
    }

    public long readOutput() {
        return this.output.pop();
    }

    public String printAllOutput() {
        final StringBuilder sb = new StringBuilder();
        while (!this.output.isEmpty()) {
            sb.append(this.output.removeLast());
            sb.append(" ");
        }
        return sb.toString();
    }

    public boolean isHalted() {
        return this.halted;
    }

    public boolean isWaitingForInput() {
        return this.waitingForInput;
    }
}
