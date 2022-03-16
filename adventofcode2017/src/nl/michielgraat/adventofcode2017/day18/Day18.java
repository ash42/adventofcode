package nl.michielgraat.adventofcode2017.day18;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day18 {

    private static final String FILENAME = "day18.txt";

    public int runPart2(final List<String> lines) {
        final Deque<Long> input0 = new ArrayDeque<>();
        final Deque<Long> input1 = new ArrayDeque<>();
        final Program p0 = new Program(0, lines, input0, input1);
        final Program p1 = new Program(1, lines, input1, input0);
        while (!(p0.isHalted() && p1.isHalted()) && !(p0.isWaiting() && p1.isWaiting())) {
            p0.runProgram(false);
            p1.runProgram(false);
        }
        return p1.getTimesSend();
    }

    public long runPart1(final List<String> lines) {
        return new Program(0, lines).runProgram(true);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day18().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day18().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
