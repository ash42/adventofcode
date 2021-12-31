package nl.michielgraat.adventofcode2021.day23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day23 {

    private static final String FILENAME = "day23.txt";
    private static final String FILENAME2 = "day23-2.txt";

    private Burrow getGoal() {
        final List<String> lines = new ArrayList<>();
        lines.add("#############");
        lines.add("#...........#");
        lines.add("###A#B#C#D###");
        lines.add("  #A#B#C#D#");
        lines.add("  #########");
        return new Burrow(lines);
    }

    private Burrow getGoal2() {
        final List<String> lines = new ArrayList<>();
        lines.add("#############");
        lines.add("#...........#");
        lines.add("###A#B#C#D###");
        lines.add("  #A#B#C#D#");
        lines.add("  #A#B#C#D#");
        lines.add("  #A#B#C#D#");
        lines.add("  #########");
        return new Burrow(lines);
    }

    private long dijkstra(final Burrow start, final Burrow goal) {
        final PriorityQueue<Burrow> queue = new PriorityQueue<>();
        queue.add(start);
        final Map<Burrow, Long> energies = new HashMap<>();
        energies.put(start, 0L);
        long lowest = Long.MAX_VALUE;
        while (!queue.isEmpty()) {
            final Burrow current = queue.poll();
            final long currentEnergy = current.energy;
            final List<Burrow> neighbours = current.getNeighbours();
            for (final Burrow n : neighbours) {
                final long newEnergy = currentEnergy + n.energy;
                if (newEnergy < energies.getOrDefault(n, Long.MAX_VALUE)) {
                    if (n.equals(goal) && newEnergy < lowest) {
                        // We should be able to just get the lowest value from the energies map, but
                        // somehow getting the goal from that map often returns null. The hashCode()
                        // method in the Burrow class seems correct, so I am probably overlooking 
                        // something. This works too though.
                        lowest = newEnergy;
                    }
                    energies.put(n, newEnergy);
                    n.energy = newEnergy;
                    queue.add(n);
                }
            }
        }
        return lowest;
    }

    private long runPart2(final List<String> lines) {
        return dijkstra(new Burrow(lines), getGoal2());
    }

    private long runPart1(final List<String> lines) {
        return dijkstra(new Burrow(lines), getGoal());
    }

    public static void main(final String[] args) {
        List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day23().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        lines = FileReader.getStringList(FILENAME2);
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day23().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}