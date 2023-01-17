package nl.michielgraat.adventofcode2019.day17;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import nl.michielgraat.adventofcode2019.AocSolver;
import nl.michielgraat.adventofcode2019.intcode.IntcodeComputer;

public class Day17 extends AocSolver {

    protected Day17(final String filename) {
        super(filename);
    }

    private void printView(final Map<Position, SpaceType> cameraView) {
        final int minX = cameraView.keySet().stream().mapToInt(Position::x).min().getAsInt();
        final int maxX = cameraView.keySet().stream().mapToInt(Position::x).max().getAsInt();
        final int minY = cameraView.keySet().stream().mapToInt(Position::y).min().getAsInt();
        final int maxY = cameraView.keySet().stream().mapToInt(Position::y).max().getAsInt();

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                System.out.print(cameraView.get(new Position(x, y)).getPrintable());
            }
            System.out.println();
        }
    }

    private Map<Position, SpaceType> getCameraView(final List<String> input) {
        final IntcodeComputer computer = new IntcodeComputer(input);
        final Map<Position, SpaceType> cameraView = new HashMap<>();
        computer.run();
        int x = 0;
        int y = 0;
        while (computer.hasOutput()) {
            final long output = computer.readFirstOutput();
            if (output == 10) {
                y++;
                x = 0;
            } else {
                cameraView.put(new Position(x, y), SpaceType.toSpaceType(output));
                x++;
            }
        }
        return cameraView;
    }

    private int nrOfStepsToMove(Position current, final int direction, final Map<Position, SpaceType> scaffolds) {
        int total = 0;
        while (scaffolds.keySet().contains(current.getNeighbour(direction))) {
            total++;
            current = current.getNeighbour(direction);
        }
        return total;
    }

    private Position getNextRobotPosition(final Position robot, final int robotDirection, final int nrOfSteps) {
        switch (robotDirection) {
            case 0:
                return new Position(robot.x() + nrOfSteps, robot.y());
            case 1:
                return new Position(robot.x(), robot.y() + nrOfSteps);
            case 2:
                return new Position(robot.x() - nrOfSteps, robot.y());
            default:
                return new Position(robot.x(), robot.y() - nrOfSteps);
        }
    }

    private int getNextRobotDirection(final Position robot, final int robotDirection,
            final Map<Position, SpaceType> scaffolds) {
        final int left = Math.floorMod(robotDirection - 1, 4);
        final int right = Math.floorMod(robotDirection + 1, 4);
        if (scaffolds.keySet().contains(robot.getNeighbour(left))) {
            return left;
        } else if (scaffolds.keySet().contains(robot.getNeighbour(right))) {
            return right;
        } else {
            return -1;
        }
    }

    private List<String> getMoves(final List<String> input) {
        final Map<Position, SpaceType> view = getCameraView(input);
        final Map<Position, SpaceType> scaffolds = view.entrySet().stream().filter(e -> e.getValue() != SpaceType.EMPTY)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        Position robot = view.entrySet().stream()
                .filter(e -> e.getValue() != SpaceType.EMPTY && e.getValue() != SpaceType.SCAFFOLD).map(Entry::getKey)
                .findAny().orElseThrow(NoSuchElementException::new);
        int robotDirection = view.get(robot).getRobotDirection();
        final List<String> instructions = new ArrayList<>();

        while (nrOfStepsToMove(robot, robotDirection, scaffolds) != 0
                || getNextRobotDirection(robot, robotDirection, scaffolds) != -1) {
            final int nrOfSteps = nrOfStepsToMove(robot, robotDirection, scaffolds);
            if (nrOfSteps != 0) {
                instructions.add(String.valueOf(nrOfSteps));
                robot = getNextRobotPosition(robot, robotDirection, nrOfSteps);
            } else {
                final int nextDir = getNextRobotDirection(robot, robotDirection, scaffolds);
                if (nextDir == Math.floorMod(robotDirection - 1, 4)) {
                    instructions.add("L");
                } else {
                    instructions.add("R");
                }
                robotDirection = nextDir;
            }
        }
        return instructions;
    }

    public boolean containsSubList(final List<String> subList, final Set<List<String>> listSet) {
        for (final List<String> list : listSet) {
            if (Collections.indexOfSubList(list, subList) != -1) {
                return true;
            }
        }
        return false;
    }

    public List<List<String>> findPatterns(final List<String> moves) {
        final List<List<String>> patterns = new ArrayList<>();
        for (int size = 20; size >= 5; size--) {
            for (int i = 0; i < moves.size() - (size - 1); i++) {
                final List<String> pattern = new ArrayList<>();
                for (int j = i; j < i + size; j++) {
                    pattern.add(moves.get(j));
                }
                if (pattern.contains("A") || pattern.contains("B") || pattern.contains("C")) {
                    continue;
                }
                int totalFound = 1;
                for (int k = i + size; k < moves.size() - (size - 1); k++) {
                    boolean matches = true;
                    for (int l = 0; l < size; l++) {
                        if (!pattern.get(l).equals(moves.get(k + l))) {
                            matches = false;
                            break;
                        }
                    }
                    if (matches) {
                        totalFound++;
                        if (totalFound > 1) {
                            patterns.add(pattern);
                            break;
                        }
                    }
                }
            }
        }
        return patterns;
    }

    private List<String> convertToAscii(final List<String> moves, final boolean addCommas) {
        final List<String> result = new ArrayList<>();
        for (int i = 0; i < moves.size(); i++) {
            final String instruction = moves.get(i);
            for (final char c : instruction.toCharArray()) {
                result.add(String.valueOf((int) c));
            }
            if (addCommas && i != moves.size() - 1) {
                result.add("44");
            }
        }
        return result;
    }

    private List<String> replace(final String replacement, final List<String> pattern,
            final List<String> moves) {
        final List<String> result = moves.stream().collect(Collectors.toList());
        while (Collections.indexOfSubList(result, pattern) != -1) {
            final int idx = Collections.indexOfSubList(result, pattern);
            result.subList(idx, idx + pattern.size()).clear();
            result.add(idx, replacement);
        }
        return result;
    }

    private int getMaxSaving(final List<String> pattern, final List<String> moves, final int nr) {
        final int sizeBefore = convertToAscii(moves, false).size();
        final List<String> replaced = replace("A", pattern, moves); // Do not care for the correct letter here
        final int saving = sizeBefore - convertToAscii(replaced, false).size();
        if (nr == 3) {
            return saving;
        } else {
            final List<List<String>> nextPatterns = findPatterns(replaced);
            int max = 0;
            for (final List<String> nextPattern : nextPatterns) {
                max = Math.max(max, getMaxSaving(nextPattern, replaced, nr + 1));
            }
            return saving + max;
        }
    }

    private List<String> getNextPattern(final int nr, final List<String> moves) {
        final List<List<String>> patterns = findPatterns(moves);
        int max = 0;
        List<String> pattern = new ArrayList<>();
        for (final List<String> nextPattern : patterns) {
            final int saving = getMaxSaving(nextPattern, moves.stream().toList(), nr);
            if (saving > max) {
                max = saving;
                pattern = nextPattern;
            }
        }
        return pattern;
    }

    private long getDustCollected(final IntcodeComputer robot, final List<Integer> mainRoutine, final List<Integer> a,
            final List<Integer> b, final List<Integer> c) {
        robot.setValueAtAddress(0, 2);
        mainRoutine.forEach(robot::addInput);
        robot.addInput(10);
        a.forEach(robot::addInput);
        robot.addInput(10);
        b.forEach(robot::addInput);
        robot.addInput(10);
        c.forEach(robot::addInput);
        robot.addInput(10);
        robot.addInput(110);
        robot.addInput(10);
        robot.run();
        return robot.readOutput();
    }

    @Override
    protected String runPart2(final List<String> input) {
        List<String> moves = getMoves(input);

        final List<String> a = getNextPattern(1, moves.stream().toList());
        moves = replace("A", a, moves);
        final List<String> b = getNextPattern(2, moves.stream().toList());
        moves = replace("B", b, moves);
        final List<String> c = getNextPattern(3, moves.stream().toList());
        moves = replace("C", c, moves);

        final List<Integer> aAscii = convertToAscii(a, true).stream().map(Integer::valueOf).toList();
        final List<Integer> bAscii = convertToAscii(b, true).stream().map(Integer::valueOf).toList();
        final List<Integer> cAscii = convertToAscii(c, true).stream().map(Integer::valueOf).toList();
        final List<Integer> mainRoutine = convertToAscii(moves, true).stream().map(Integer::valueOf).toList();

        return String.valueOf(getDustCollected(new IntcodeComputer(input), mainRoutine, aAscii, bAscii, cAscii));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final Map<Position, SpaceType> view = getCameraView(input);
        final Map<Position, SpaceType> scaffolds = view.entrySet().stream().filter(e -> e.getValue() != SpaceType.EMPTY)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        // Intersections have four neighbours which also are scaffolds.
        return String.valueOf(scaffolds.keySet().stream().filter(p -> scaffolds.keySet().containsAll(p.getNeighbours()))
                .mapToInt(p -> p.x() * p.y()).sum());
    }

    public static void main(final String... args) {
        new Day17("day17.txt");
    }
}
