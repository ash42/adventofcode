package nl.michielgraat.adventofcode2019.day23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.michielgraat.adventofcode2019.AocSolver;
import nl.michielgraat.adventofcode2019.intcode.IntcodeComputer;

public class Day23 extends AocSolver {

    protected Day23(final String filename) {
        super(filename);
    }

    private List<IntcodeComputer> getComputers(final List<String> input) {
        final List<IntcodeComputer> computers = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            final IntcodeComputer computer = new IntcodeComputer(input);
            computer.addInput(i);
            computers.add(computer);
        }
        return computers;
    }

    private Map<Integer, List<Long>> getQueues() {
        final Map<Integer, List<Long>> queues = new HashMap<>();
        for (int i = 0; i < 50; i++) {
            queues.put(i, new ArrayList<>());
        }
        return queues;
    }

    private Packet getPacket(final IntcodeComputer computer) {
        return new Packet(computer.readFirstOutput(), computer.readFirstOutput(), computer.readFirstOutput());
    }

    private void addInput(final Packet p, final Map<Integer, List<Long>> queues) {
        if (p.destination() != 255L) {
            final List<Long> inputs = new ArrayList<>();
            inputs.add(p.x());
            inputs.add(p.y());
            queues.get((int) p.destination()).addAll(inputs);
        }
    }

    private boolean addInputAndRun(final List<IntcodeComputer> computers, final Map<Integer, List<Long>> queues) {
        boolean idle = true;
        for (final Entry<Integer, List<Long>> entry : queues.entrySet()) {
            final IntcodeComputer destination = computers.get(entry.getKey());
            if (!entry.getValue().isEmpty()) {
                entry.getValue().forEach(destination::addInput);
                idle = false;
            } else {
                destination.addInput(-1);
            }
            destination.run();
        }
        return idle;
    }

    private long sendNatPacket(final Packet natPacket, final List<IntcodeComputer> computers) {
        final IntcodeComputer first = computers.get(0);
        first.addInput(natPacket.x());
        first.addInput(natPacket.y());
        first.run();
        return natPacket.y();
    }

    private Packet runComputers2(final List<IntcodeComputer> computers, final Map<Integer, List<Long>> queues) {
        Packet natPacket = null;
        for (final IntcodeComputer computer : computers) {
            if (computer.hasOutput()) {
                final Packet p = getPacket(computer);
                addInput(p, queues);
                if (p.destination() == 255L) {
                    natPacket = p;
                }
            }
        }
        return natPacket;
    }

    private String runComputers1(final List<IntcodeComputer> computers, final Map<Integer, List<Long>> queues) {
        for (final IntcodeComputer computer : computers) {
            if (computer.hasOutput()) {
                final Packet p = getPacket(computer);
                if (p.destination() == 255L) {
                    return String.valueOf(p.y());
                }
                addInput(p, queues);
            }
        }
        return "";
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<IntcodeComputer> computers = getComputers(input);
        computers.forEach(c -> c.run());

        long prevNatY = Long.MIN_VALUE;
        Packet natPacket = null;

        while (true) {
            final Map<Integer, List<Long>> queues = getQueues();
            natPacket = runComputers2(computers, queues);
            final boolean idle = addInputAndRun(computers, queues);
            if (idle && natPacket != null) {
                if (natPacket.y() == prevNatY) {
                    return String.valueOf(prevNatY);
                }
                prevNatY = sendNatPacket(natPacket, computers);
            }
        }
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<IntcodeComputer> computers = getComputers(input);
        computers.forEach(c -> c.run());

        while (true) {
            final Map<Integer, List<Long>> queues = getQueues();
            final String result = runComputers1(computers, queues);
            if (!result.isEmpty()) {
                return result;
            }
            addInputAndRun(computers, queues);
        }
    }

    public static void main(final String... args) {
        new Day23("day23.txt");
    }
}
