package nl.michielgraat.adventofcode2018.day15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day15 {
    private static final String FILENAME = "day15.txt";

    private char[][] parseGrid(final List<String> lines) {
        final char[][] grid = new char[lines.get(0).length()][lines.size()];
        for (int y = 0; y < lines.size(); y++) {
            final String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                grid[x][y] = line.charAt(x);
            }
        }
        return grid;
    }

    private List<Unit> getUnits(final char[][] grid, final int attackPower) {
        final List<Unit> units = new ArrayList<>();
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] == 'E') {
                    units.add(new Unit(x, y, Race.ELF, attackPower));
                } else if (grid[x][y] == 'G') {
                    units.add(new Unit(x, y, Race.GOBLIN, 3));
                }
            }
        }
        return units;
    }

    private char[][] updateGrid(final char[][] prev, final List<Unit> units) {
        final char[][] newGrid = new char[prev.length][prev[0].length];
        for (int y = 0; y < prev[0].length; y++) {
            for (int x = 0; x < prev.length; x++) {
                if (prev[x][y] == '.' || prev[x][y] == 'E' || prev[x][y] == 'G') {
                    newGrid[x][y] = '.';
                } else {
                    newGrid[x][y] = prev[x][y];
                }
            }
        }
        for (final Unit u : units) {
            if (u.hitpoints > 0) {
                newGrid[u.p.x][u.p.y] = (u.race == Race.ELF) ? 'E' : 'G';
            }
        }

        return newGrid;
    }

    public int runPart2(final List<String> lines) {
        boolean found = false;
        int attackPower = 4;
        int totalHp = 0;
        int rounds = 0;
        while (!found) {
            char[][] grid = parseGrid(lines);
            List<Unit> units = getUnits(grid, attackPower++);
            final long nrOfElves = units.stream().filter(u -> u.race == Race.ELF).count();
            Collections.sort(units);
            boolean done = false;
            rounds = 0;
            while (!done) {
                rounds++;
                units = units.stream().filter(u -> u.hitpoints > 0).collect(Collectors.toList());
                final long newNrOfElves = units.stream().filter(u -> u.race == Race.ELF).count();
                done = newNrOfElves != nrOfElves;
                if (!done) {
                    Collections.sort(units);
                    for (final Unit u : units) {
                        if (!done && u.hitpoints > 0) {
                            done = u.takeTurn(grid, units);
                        }
                        grid = updateGrid(grid, units);
                    }
                    Collections.sort(units);
                }
            }
            units = units.stream().filter(u -> u.hitpoints > 0).collect(Collectors.toList());
            final long finalNrOfElves = units.stream().filter(u -> u.race == Race.ELF).count();
            found = nrOfElves == finalNrOfElves;
            totalHp = units.stream().mapToInt(u -> u.hitpoints).sum();
        }

        return (rounds - 1) * totalHp;
    }

    public int runPart1(final List<String> lines) {
        char[][] grid = parseGrid(lines);
        List<Unit> units = getUnits(grid, 3);
        Collections.sort(units);
        boolean done = false;
        int rounds = 0;

        while (!done) {
            rounds++;
            units = units.stream().filter(u -> u.hitpoints > 0).collect(Collectors.toList());
            Collections.sort(units);
            for (final Unit u : units) {
                if (!done && u.hitpoints > 0) {
                    done = u.takeTurn(grid, units);
                }
                grid = updateGrid(grid, units);
            }
            Collections.sort(units);
        }
        units = units.stream().filter(u -> u.hitpoints > 0).collect(Collectors.toList());
        final int totalHp = units.stream().mapToInt(u -> u.hitpoints).sum();

        return (rounds - 1) * totalHp;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day15().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day15().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}