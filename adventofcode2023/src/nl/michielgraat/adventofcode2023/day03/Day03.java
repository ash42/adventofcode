package nl.michielgraat.adventofcode2023.day03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day03 extends AocSolver {

    protected Day03(String filename) {
        super(filename);
    }

    private char[][] getGrid(List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];
        int i = 0;
        for (String line : input) {
            grid[i++] = line.toCharArray();
        }
        return grid;
    }

    private boolean hasSymbolAdjacent(int x, int y, String nr, char[][] grid) {
        for (int i = x; i < x + nr.length(); i++) {
            // Left
            if (i > 0 && !Character.isDigit(grid[y][i - 1]) && grid[y][i - 1] != '.') {
                return true;
            }
            // Left-up
            if (i > 0 && y > 0 && !Character.isDigit(grid[y - 1][i - 1]) && grid[y - 1][i - 1] != '.') {
                return true;
            }
            // Up
            if (y > 0 && !Character.isDigit(grid[y - 1][i]) && grid[y - 1][i] != '.') {
                return true;
            }
            // Right-up
            if (i < grid[y].length - 1 && y > 0 && !Character.isDigit(grid[y - 1][i + 1])
                    && grid[y - 1][i + 1] != '.') {
                return true;
            }
            // Right
            if (i < grid[y].length - 1 && !Character.isDigit(grid[y][i + 1]) && grid[y][i + 1] != '.') {
                return true;
            }
            // Right-down
            if (i < grid[y].length - 1 && y < grid.length - 1 && !Character.isDigit(grid[y + 1][i + 1])
                    && grid[y + 1][i + 1] != '.') {
                return true;
            }
            // Down
            if (y < grid.length - 1 && !Character.isDigit(grid[y + 1][i]) && grid[y + 1][i] != '.') {
                return true;
            }
            // Left-down
            if (i > 0 && y < grid.length - 1 && !Character.isDigit(grid[y + 1][i - 1]) && grid[y + 1][i - 1] != '.') {
                return true;
            }
        }
        return false;
    }

    private Set<GearPos> getAdjacentGearPos(int x, int y, String nr, char[][] grid) {
        Set<GearPos> gearpos = new HashSet<>();
        for (int i = x; i < x + nr.length(); i++) {
            // Left
            if (i > 0 && grid[y][i - 1] == '*') {
                gearpos.add(new GearPos(i - 1, y));
            }
            // Left-up
            if (i > 0 && y > 0 && grid[y - 1][i - 1] == '*') {
                gearpos.add(new GearPos(i - 1, y - 1));
            }
            // Up
            if (y > 0 && grid[y - 1][i] == '*') {
                gearpos.add(new GearPos(i, y - 1));
            }
            // Right-up
            if (i < grid[y].length - 1 && y > 0 && grid[y - 1][i + 1] == '*') {
                gearpos.add(new GearPos(i + 1, y - 1));
            }
            // Right
            if (i < grid[y].length - 1 && grid[y][i + 1] == '*') {
                gearpos.add(new GearPos(i + 1, y));
            }
            // Right-down
            if (i < grid[y].length - 1 && y < grid.length - 1 && grid[y + 1][i + 1] == '*') {
                gearpos.add(new GearPos(i + 1, y + 1));
            }
            // Down
            if (y < grid.length - 1 && grid[y + 1][i] == '*') {
                gearpos.add(new GearPos(i, y + 1));
            }
            // Left-down
            if (i > 0 && y < grid.length - 1 && grid[y + 1][i - 1] == '*') {
                gearpos.add(new GearPos(i - 1, y + 1));
            }
        }
        return gearpos;
    }

    private List<Integer> getPartNrs(char[][] grid) {
        List<Integer> nrs = new ArrayList<>();
        for (int y = 0; y < grid.length; y++) {
            String row = new String(grid[y]);
            Matcher matcher = Pattern.compile("\\d+").matcher(row);
            while (matcher.find()) {
                String curNr = matcher.group();
                int idx = matcher.start();
                if (hasSymbolAdjacent(idx, y, curNr, grid)) {
                    nrs.add(Integer.parseInt(curNr));
                }
            }
        }
        return nrs;
    }

    private Map<PartNrPos, List<GearPos>> getNrsWithGearAdjacent(char[][] grid) {
        Map<PartNrPos, List<GearPos>> map = new HashMap<>();
        for (int y = 0; y < grid.length; y++) {
            String row = new String(grid[y]);
            Matcher matcher = Pattern.compile("\\d+").matcher(row);
            while (matcher.find()) {
                String curNr = matcher.group();
                int idx = matcher.start();
                Set<GearPos> gearpos = getAdjacentGearPos(idx, y, curNr, grid);
                if (!gearpos.isEmpty()) {
                    map.put(new PartNrPos(idx, y, Integer.valueOf(curNr)), gearpos.stream().toList());
                }
            }
        }
        return map;
    }

    private Map<GearPos, List<PartNrPos>> getPartsWithAdjacentGears(Map<PartNrPos, List<GearPos>> nrs) {
        Map<GearPos, List<PartNrPos>> gpToNrs = new HashMap<>();
        for (Entry<PartNrPos, List<GearPos>> entry : nrs.entrySet()) {
            PartNrPos nr = entry.getKey();
            List<GearPos> gears = entry.getValue();
            for (GearPos gear : gears) {
                gpToNrs.putIfAbsent(gear, new ArrayList<>());
                List<PartNrPos> ops = gpToNrs.get(gear);
                ops.add(nr);
                gpToNrs.put(gear, ops);
            }
        }
        return gpToNrs;
    }

    private int getTotalGearRatio(char[][] grid) {
        // Getting all numbers with gears adjacent first seems easier than getting all
        // gears with number adjacent.
        Map<PartNrPos, List<GearPos>> nrs = getNrsWithGearAdjacent(grid);
        // Based upon the map above we can no make a list of all gears with their
        // corresponding adjacent numbers.
        Map<GearPos, List<PartNrPos>> gpToNrs = getPartsWithAdjacentGears(nrs);

        // Finally, calculate the total gear ratio, of course only for gears with
        // exactly two adjacent numbers.
        int total = 0;
        for (Entry<GearPos, List<PartNrPos>> entry : gpToNrs.entrySet()) {
            if (entry.getValue().size() == 2) {
                total += (entry.getValue().get(0).nr * entry.getValue().get(1).nr);
            }
        }
        return total;
    }

    @Override
    protected String runPart2(final List<String> input) {
        char[][] grid = getGrid(input);
        return String.valueOf(getTotalGearRatio(grid));
    }

    @Override
    protected String runPart1(final List<String> input) {
        char[][] grid = getGrid(input);
        List<Integer> partsNrs = getPartNrs(grid);

        return String.valueOf(partsNrs.stream().mapToInt(i -> i).sum());
    }

    public static void main(String... args) {
        new Day03("day03.txt");
    }
}

class GearPos {
    int x;
    int y;

    GearPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GearPos other = (GearPos) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}

class PartNrPos {
    int nr;
    int x;
    int y;

    PartNrPos(int x, int y, int nr) {
        this.nr = nr;
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + nr;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PartNrPos other = (PartNrPos) obj;
        if (nr != other.nr)
            return false;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

}