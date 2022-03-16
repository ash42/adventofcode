package nl.michielgraat.adventofcode2017.day17;

import java.util.ArrayList;
import java.util.List;

public class Day17 {

    public int runPart2(int input) {
        List<Integer> buffer = new ArrayList<>(50000000);
        buffer.add(0);
        int pos = 0;
        int result = 0;
        int size = buffer.size();
        for (int i = 1; i < 50000000; i++) {
            pos = (pos + input) % size;
            //0 is always at the start of the buffer, so we are only interested in values
            //inserted at index 1
            if (pos == 0) {
                result = i;
            }
            size++;
            pos = pos+1;
        }
        return result;
    }

    public int runPart1(int input) {
        List<Integer> buffer = new ArrayList<>(2018);
        buffer.add(0);
        int pos = 0;
        for (int i = 1; i < 2018; i++) {
            pos = (pos + input) % buffer.size();
            buffer.add(pos+1, i);
            pos = pos+1;
            
        }
        return buffer.get((pos+1)%buffer.size());
    }

    public static void main(final String[] args) {
        final int input = 335;
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day17().runPart1(input));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day17().runPart2(input));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
