package nl.michielgraat.adventofcode2022.day13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day13 extends AocSolver {

    protected Day13(final String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Packet> packets = new ArrayList<>();
        final Packet divider1 = new Packet("[[2]]");
        final Packet divider2 = new Packet("[[6]]");
        packets.add(divider1);
        packets.add(divider2);
        for (int i = 0; i < input.size(); i += 3) {
            packets.add(new Packet(input.get(i)));
            packets.add(new Packet(input.get(i + 1)));
        }
        Collections.sort(packets);
        return String.valueOf((packets.indexOf(divider1) + 1) * (packets.indexOf(divider2) + 1));
    }

    @Override
    protected String runPart1(final List<String> input) {
        int total = 0;
        for (int i = 0, pairNr = 1; i < input.size(); i += 3, pairNr++) {
            final Packet left = new Packet(input.get(i));
            final Packet right = new Packet(input.get(i + 1));
            total += left.compareTo(right) < 0 ? pairNr : 0;
        }
        return String.valueOf(total);
    }

    public static void main(final String... args) {
        new Day13("day13.txt");
    }
}
