package nl.michielgraat.adventofcode2017.day12;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day12 {

    private static final String FILENAME = "day12.txt";
    private static final String SPLITTER = "<->";

    private int countNeighbours(final Program program, final List<Program> programs, final List<Program> visited) {
        visited.add(program);
        int total = 1;
        for (final Program neighbour : program.getNeighbours()) {
            if (!visited.contains(neighbour)) {
                final Program n = programs.get(programs.indexOf(neighbour));
                visited.add(n);
                total += countNeighbours(n, programs, visited);

            }
        }
        return total;
    }

    private List<Program> getProgramList(final List<String> lines) {
        final List<Program> programs = new ArrayList<>();
        for (final String line : lines) {
            final Program p = new Program(Integer.parseInt(line.split(SPLITTER)[0].trim()));
            final String[] neighbours = line.split(SPLITTER)[1].split(",");
            final List<Program> pNeighbours = new ArrayList<>();
            for (final String neighbour : neighbours) {
                final Program pNeighbour = new Program(Integer.parseInt(neighbour.trim()));
                pNeighbours.add(pNeighbour);
            }
            p.setNeighbours(pNeighbours);
            programs.add(p);
        }
        return programs;
    }

    public int runPart2(final List<String> lines) {
        final List<Program> programs = getProgramList(lines);
        int totalFound = 0;
        while (!programs.isEmpty()) {
            final List<Program> visited = new ArrayList<>();
            countNeighbours(programs.get(0), programs, visited);
            programs.removeAll(visited);
            totalFound++;
        }
        return totalFound;
    }

    public int runPart1(final List<String> lines) {
        final List<Program> programs = getProgramList(lines);
        return countNeighbours(programs.get(programs.indexOf(new Program(0))), programs, new ArrayList<>());
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day12().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day12().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}