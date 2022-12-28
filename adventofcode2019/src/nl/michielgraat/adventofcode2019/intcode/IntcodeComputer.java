package nl.michielgraat.adventofcode2019.intcode;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nl.michielgraat.adventofcode2019.intcode.instructions.Add;
import nl.michielgraat.adventofcode2019.intcode.instructions.Halt;
import nl.michielgraat.adventofcode2019.intcode.instructions.Instruction;
import nl.michielgraat.adventofcode2019.intcode.instructions.Multiply;

public class IntcodeComputer {
    private final List<Integer> program;
    private List<Integer> memory;
    private int ptr = 0;

    public IntcodeComputer(final List<String> input) {
        final String programString = input.get(0);
        this.program = Stream.of(programString.split(",")).map(String::trim).map(Integer::parseInt).toList();
        this.memory = Stream.of(programString.split(",")).map(String::trim).map(Integer::parseInt)
                .collect(Collectors.toList());
        ptr = 0;
    }

    private Instruction getInstruction(final int ptr) {
        switch (program.get(ptr)) {
            case 1:
                return new Add(memory, ptr);
            case 2:
                return new Multiply(memory, ptr);
            case 99:
                return new Halt(memory, ptr);
            default:
                throw new IllegalArgumentException("Unknown opcode: [" + program.get(ptr) + "]");
        }
    }

    public void run() {
        while (program.get(ptr) != 99) {
            final Instruction i = getInstruction(ptr);
            memory.set(memory.get(i.getOutputPosition()), i.execute());
            ptr += i.getPtrIncrease();
        }
    }

    public void reset() {
        this.memory = this.program.stream().collect(Collectors.toList());
        this.ptr = 0;
    }

    public int getValueAtAddress(final int address) {
        return memory.get(address);
    }

    public void setValueAtAddress(final int address, final int value) {
        this.memory.set(address, value);
    }
}
