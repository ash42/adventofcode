package nl.michielgraat.adventofcode2022.day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day22 extends AocSolver {

    private static final char OPEN = '.';
    private static final char WALL = '#';
    private static final int RIGHT = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int UP = 3;

    protected Day22(final String filename) {
        super(filename);
    }

    private void fold(final Map<Integer, List<Position>> map, final int faceSize) {

        final List<Position> face1 = map.get(1);
        final List<Position> face2 = map.get(2);
        final List<Position> face3 = map.get(3);
        final List<Position> face4 = map.get(4);
        final List<Position> face5 = map.get(5);
        final List<Position> face6 = map.get(6);

        for (final Position p : face1.stream().filter(p -> p.y() == 0).toList()) {
            final Position neighbour = face6.stream().filter(n -> n.x() == 0).filter(n -> n.y() == p.x() + 2 * faceSize)
                    .findFirst().orElseThrow(NoSuchElementException::new);
            p.setNeighbour(neighbour, UP);
            p.setAdjustment(3, getFace(neighbour, map));
            neighbour.setNeighbour(p, LEFT);
            neighbour.setAdjustment(1, getFace(p, map));
        }
        for (final Position p : face1.stream().filter(p -> p.x() == faceSize).toList()) {
            final Position neighbour = face5.stream().filter(n -> n.y() == (3 * faceSize - 1) - p.y())
                    .filter(n -> n.x() == 0)
                    .findFirst().orElseThrow(NoSuchElementException::new);
            p.setNeighbour(neighbour, LEFT);
            p.setAdjustment(2, getFace(neighbour, map));
            neighbour.setNeighbour(p, LEFT);
            neighbour.setAdjustment(2, getFace(p, map));
        }

        for (final Position p : face2.stream().filter(p -> p.y() == 0).toList()) {
            final Position neighbour = face6.stream().filter(n -> n.y() == 4 * faceSize - 1)
                    .filter(n -> n.x() == p.x() - 2 * faceSize).findFirst().orElseThrow(NoSuchElementException::new);
            p.setNeighbour(neighbour, UP);
            p.setAdjustment(0, getFace(neighbour, map));
            neighbour.setNeighbour(p, DOWN);
            neighbour.setAdjustment(0, getFace(p, map));
        }
        for (final Position p : face2.stream().filter(p -> p.x() == 3 * faceSize - 1).toList()) {
            final Position neighbour = face4.stream().filter(n -> n.x() == 2 * faceSize - 1)
                    .filter(n -> n.y() == (3 * faceSize - 1) - p.y()).findFirst()
                    .orElseThrow(NoSuchElementException::new);
            p.setNeighbour(neighbour, RIGHT);
            p.setAdjustment(2, getFace(neighbour, map));
            neighbour.setNeighbour(p, RIGHT);
            neighbour.setAdjustment(2, getFace(p, map));
        }
        for (final Position p : face2.stream().filter(p -> p.y() == faceSize - 1).toList()) {
            final Position neighbour = face3.stream().filter(n -> n.x() == 2 * faceSize - 1)
                    .filter(n -> n.y() == (p.x() - faceSize)).findFirst().orElseThrow(NoSuchElementException::new);
            p.setNeighbour(neighbour, DOWN);
            p.setAdjustment(3, getFace(neighbour, map));
            neighbour.setNeighbour(p, RIGHT);
            neighbour.setAdjustment(1, getFace(p, map));
        }

        for (final Position p : face3.stream().filter(p -> p.x() == faceSize).toList()) {
            final Position neighbour = face5.stream().filter(n -> n.y() == 2 * faceSize)
                    .filter(n -> n.x() == (p.y() - faceSize)).findFirst().orElseThrow(NoSuchElementException::new);
            p.setNeighbour(neighbour, LEFT);
            p.setAdjustment(1, getFace(neighbour, map));
            neighbour.setNeighbour(p, UP);
            neighbour.setAdjustment(3, getFace(p, map));
        }

        for (final Position p : face4.stream().filter(p -> p.y() == 3 * faceSize - 1).toList()) {
            final Position neighbour = face6.stream().filter(n -> n.x() == faceSize - 1)
                    .filter(n -> n.y() == (p.x() + 2 * faceSize)).findFirst().orElseThrow(NoSuchElementException::new);
            p.setNeighbour(neighbour, DOWN);
            p.setAdjustment(3, getFace(neighbour, map));
            neighbour.setNeighbour(p, RIGHT);
            neighbour.setAdjustment(1, getFace(p, map));
        }
    }

    private void addNeighbours(List<String> input, final Position p) {
        input = input.subList(0, input.size() - 2);

        // Right
        Position n = new Position(p.x() + 1, p.y());
        if (n.x() >= input.get(n.y()).length()) { // Wrap around
            int start = 0;
            for (; start < input.get(n.y()).length(); start++) {
                if (input.get(n.y()).charAt(start) == OPEN || input.get(n.y()).charAt(start) == WALL) {
                    break;
                }
            }
            n = new Position(start, n.y());
        }
        if (input.get(n.y()).charAt(n.x()) != WALL) {
            p.setNeighbour(n, RIGHT);
        }

        // Down
        n = new Position(p.x(), p.y() + 1);
        if (n.y() >= input.size() || (n.y() != input.size() - 1 && n.x() >= input.get(n.y()).length())) { // Wrap around
            int start = 0;
            for (; start <= input.size(); start++) {
                if (input.get(start).charAt(n.x()) == OPEN || input.get(start).charAt(n.x()) == WALL) {
                    break;
                }
            }
            n = new Position(n.x(), start);
        }
        if (input.get(n.y()).charAt(n.x()) != WALL) {
            p.setNeighbour(n, DOWN);
        }

        // Left
        n = new Position(p.x() - 1, p.y());
        if (n.x() < 0 || input.get(n.y()).charAt(n.x()) == ' ') { // Wrap around
            final int start = Math.max(input.get(n.y()).lastIndexOf(OPEN), input.get(n.y()).lastIndexOf(WALL));
            n = new Position(start, n.y());
        }
        if (input.get(n.y()).charAt(n.x()) != WALL) {
            p.setNeighbour(n, LEFT);
        }

        // Up
        n = new Position(p.x(), p.y() - 1);
        if (n.y() < 0 || input.get(n.y()).charAt(n.x()) == ' ') { // Wrap around
            int start = input.size() - 1;
            for (; start > 0; start--) {
                if (input.get(start).length() > n.x()
                        && (input.get(start).charAt(n.x()) == OPEN || input.get(start).charAt(n.x()) == WALL)) {
                    break;
                }
            }
            n = new Position(n.x(), start);
        }
        if (input.get(n.y()).charAt(n.x()) != WALL) {
            p.setNeighbour(n, UP);
        }
    }

    private List<Position> getPositions(final List<String> input) {
        final List<Position> p = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            final String line = input.get(y);
            if (line.trim().isEmpty()) {
                break;
            }
            final char[] chars = line.toCharArray();
            for (int x = 0; x < chars.length; x++) {
                if (chars[x] != ' ') {
                    final Position pos = new Position(x, y);
                    addNeighbours(input, pos);
                    p.add(pos);
                }
            }
        }
        return p;
    }

    private Map<Integer, List<Position>> getFaceToPositions(final List<Position> positions, final int faceSize) {
        final Map<Integer, List<Position>> map = new HashMap<>();
        // Specific for input:
        // 12
        // 3
        // 54
        // 6
        map.put(1, positions.stream().filter(p -> p.x() >= faceSize && p.x() < 2 * faceSize)
                .filter(p -> p.y() < faceSize).collect(Collectors.toList()));
        map.put(2, positions.stream().filter(p -> p.x() >= 2 * faceSize && p.x() < 3 * faceSize)
                .filter(p -> p.y() < faceSize).collect(Collectors.toList()));
        map.put(3, positions.stream().filter(p -> p.x() >= faceSize && p.x() < 2 * faceSize)
                .filter(p -> p.y() >= faceSize && p.y() < 2 * faceSize).collect(Collectors.toList()));
        map.put(4, positions.stream().filter(p -> p.x() >= faceSize && p.x() < 2 * faceSize)
                .filter(p -> p.y() >= 2 * faceSize && p.y() < 3 * faceSize).collect(Collectors.toList()));
        map.put(5, positions.stream().filter(p -> p.x() < faceSize)
                .filter(p -> p.y() >= 2 * faceSize && p.y() < 3 * faceSize).collect(Collectors.toList()));
        map.put(6, positions.stream().filter(p -> p.x() < faceSize)
                .filter(p -> p.y() >= 3 * faceSize && p.y() < 4 * faceSize).collect(Collectors.toList()));
        return map;
    }

    private int getFace(final Position p, final Map<Integer, List<Position>> faceMap) {
        return faceMap.entrySet().stream().filter(e -> e.getValue().contains(p)).map(Map.Entry::getKey).findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    private Map<Position, Character> getTypeMap(final List<Position> positions, final List<String> input) {
        final Map<Position, Character> typeMap = new HashMap<>();
        for (final Position p : positions) {
            typeMap.put(p, input.get(p.y()).charAt(p.x()));
        }
        return typeMap;
    }

    private String getInstruction(final List<String> input) {
        boolean read = false;
        for (final String line : input) {
            if (read) {
                return line;
            }
            if (line.isEmpty()) {
                read = true;
            }
        }
        throw new NoSuchElementException();
    }

    private Position getStart(final List<Position> positions) {
        final int startX = positions.stream().filter(p -> p.y() == 0).mapToInt(Position::x).min()
                .orElseThrow(NoSuchElementException::new);
        return positions.stream().filter(p -> p.y() == 0).filter(p -> p.x() == startX).findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    private int getNextDirection(final int current, final char turnTo) {
        return turnTo == 'L' ? Math.floorMod(current - 1, 4) : Math.floorMod(current + 1, 4);
    }

    private int walk(final String instructions, final List<Position> positions, final Map<Position, Character> typeMap,
            final Map<Integer, List<Position>> faceToPositions) {
        Position current = getStart(positions);
        int currentDirection = RIGHT;

        final Pattern p = Pattern.compile("(\\d+)(\\D)?");
        final Matcher m = p.matcher(instructions);

        while (m.find()) {
            final int nrSteps = Integer.parseInt(m.group(1));
            for (int i = 0; i < nrSteps; i++) {
                final Position newPos = current.neighbours()[currentDirection];
                if (newPos == null || (typeMap.get(newPos) != null && typeMap.get(newPos) == WALL)) {
                    // Hit wall, stop moving
                    break;
                } else {
                    final int face = getFace(current, faceToPositions);
                    current = positions.get(positions.indexOf(newPos));
                    currentDirection = Math.floorMod(currentDirection + current.getAdjustment(face), 4);
                }
            }

            if (m.group(2) != null) {
                currentDirection = getNextDirection(currentDirection, m.group(2).charAt(0));
            }
        }

        return 1000 * (current.y() + 1) + 4 * (current.x() + 1) + currentDirection;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Position> positions = getPositions(input);
        final Map<Integer, List<Position>> faceToPositions = getFaceToPositions(positions, input.size() / 4);
        final Map<Position, Character> typeMap = getTypeMap(positions, input);
        fold(faceToPositions, input.size() / 4);
        final int password = walk(getInstruction(input), positions, typeMap, faceToPositions);
        return String.valueOf(password);
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Position> positions = getPositions(input);
        final Map<Integer, List<Position>> faceToPositions = getFaceToPositions(positions, input.size() / 4);
        final Map<Position, Character> typeMap = getTypeMap(positions, input);
        final int password = walk(getInstruction(input), positions, typeMap, faceToPositions);

        return String.valueOf(password);
    }

    public static void main(final String... args) {
        new Day22("day22.txt");
    }
}
