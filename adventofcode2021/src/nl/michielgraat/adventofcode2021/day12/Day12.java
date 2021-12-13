package nl.michielgraat.adventofcode2021.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day12 {

    private static final String FILENAME = "day12.txt";

    private List<Cave> parseCaves(final List<String> lines) {
        final List<Cave> caves = new ArrayList<>();
        for (final String line : lines) {
            final String[] parts = line.split("-");
            Cave start = new Cave(parts[0]);
            Cave end = new Cave(parts[1]);
            if (caves.contains(start)) {
                start = caves.remove(caves.indexOf(start));
            }
            if (caves.contains(end)) {
                end = caves.remove(caves.indexOf(end));
            }
            if (!start.isEnd() && !end.isStart() && !start.getExits().contains(end)) {
                start.addExit(end);
            }
            caves.add(start);
            if (!start.isStart() && !end.isEnd()) {
                end.addExit(start);
            }
            caves.add(end);
        }
        return caves;
    }

    private Cave getStartCave(final List<Cave> caves) {
        return caves.stream().filter(c -> c.isStart()).collect(Collectors.toList()).get(0);
    }

    private boolean isLowerCase(final String s) {
        return !s.chars().anyMatch(Character::isUpperCase);
    }

    private boolean mayVisit(final Cave caveToTest, final String path) {
        if (!caveToTest.isSmall()) {
            return true;
        }
        final List<String> visited = Arrays.asList(path.split(","));

        final Map<String, Long> counts = visited.stream().filter(this::isLowerCase).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        final long caveToTestCount = (counts.keySet().contains(caveToTest.getName())) ? counts.get(caveToTest.getName()) + 1 : 1L;
        counts.put(caveToTest.getName(), caveToTestCount);

        if (counts.values().stream().filter(c -> c > 2).count() != 0) {
            return false;
        } else {
            return counts.values().stream().filter(c -> c == 2L).count() <= 1;
        }
    }

    private void getPaths2(final Cave startCave, String currentPath, final List<String> paths) {
        if (!mayVisit(startCave, currentPath)) {
            return;
        }
        String postfix = (currentPath.length() == 0) ? "" : ",";
        postfix += startCave.getName();
        currentPath += postfix;
        if (startCave.isEnd()) {
            paths.add(currentPath);
            return;
        }
        for (final Cave exit : startCave.getExits()) {
            getPaths2(exit, currentPath, paths);
        }
    }

    private int getNrOfPaths(final Cave startCave) {
        if (startCave.isEnd()) {
            startCave.setVisited(false);
            return 1;
        }
        if (startCave.isVisited()) {
            return 0;
        }
        if (startCave.isSmall()) {
            startCave.setVisited(true);
        }
        int total = 0;
        for (final Cave exit : startCave.getExits()) {
            total += getNrOfPaths(exit);
        }
        startCave.setVisited(false);
        return total;
    }

    private int runPart2(final List<String> lines) {
        final List<Cave> caves = parseCaves(lines);
        final List<String> paths = new ArrayList<>();
        getPaths2(getStartCave(caves), "", paths);
        return paths.size();
    }

    private int runPart1(final List<String> lines) {
        final List<Cave> caves = parseCaves(lines);
        return getNrOfPaths(getStartCave(caves));
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day12().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day12().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}

class Cave {
    String name;
    List<Cave> exits = new ArrayList<>();
    boolean visited = false;

    Cave(final String name) {
        this.name = name;
    }

    void addExit(final Cave cave) {
        exits.add(cave);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Cave> getExits() {
        return exits;
    }

    public void setExits(final List<Cave> exits) {
        this.exits = exits;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(final boolean visited) {
        this.visited = visited;
    }

    public boolean isSmall() {
        if (isStart() || isEnd()) {
            return false;
        }
        for (final char c : name.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return false;
            }
        }
        return true;
    }

    public boolean isStart() {
        return name.equals("start");
    }

    public boolean isEnd() {
        return name.equals("end");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Cave other = (Cave) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        exits.forEach(e -> sb.append(e.getName() + " "));
        return "Cave '" + name + "' has exits: " + sb.toString();
    }
}