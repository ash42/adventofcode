package nl.michielgraat.adventofcode2022.day23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day23 extends AocSolver {

    private static final int N = 0;
    private static final int S = 1;
    private static final int W = 2;
    private static final int E = 3;

    private static final char ELF = '#';

    protected Day23(final String filename) {
        super(filename);
    }

    private void printGrove(final List<Position> grove) {
        final int minY = grove.stream().mapToInt(Position::y).min().getAsInt();
        final int maxY = grove.stream().mapToInt(Position::y).max().getAsInt();
        final int minX = grove.stream().mapToInt(Position::x).min().getAsInt();
        final int maxX = grove.stream().mapToInt(Position::x).max().getAsInt();

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                System.out.print(grove.contains(new Position(x, y)) ? ELF : '.');
            }
            System.out.println();
        }
    }

    private List<Position> getGrove(final List<String> input) {
        final List<Position> positions = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            final String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == ELF) {
                    positions.add(new Position(x, y));
                }
            }
        }
        return positions;
    }

    private int getDirection(final int round) {
        switch (round % 4) {
            case N:
                return N;
            case S:
                return S;
            case W:
                return W;
            default:
                return E;
        }
    }

    private boolean containsElf(final int direction, final List<Position> grove, final Set<Position> n,
            final Set<Position> s, final Set<Position> w, final Set<Position> e) {
        Set<Position> positions;
        switch (direction) {
            case N:
                positions = n;
                break;
            case S:
                positions = s;
                break;
            case W:
                positions = w;
                break;
            default:
                positions = e;
        }
        for (final Position pos : positions) {
            if (grove.indexOf(pos) != -1) {
                return true;
            }
        }
        return false;
    }

    private Map<Position, Position> consider(final int round, final List<Position> grove) {
        final Map<Position, Position> nextToCurrent = new HashMap<>();
        final Set<Position> seen = new HashSet<>();
        final int direction = getDirection(round);
        for (final Position elfPos : grove) {
            final Set<Position> nPositions = elfPos.getNorthPositions();
            final Set<Position> sPositions = elfPos.getSouthPositions();
            final Set<Position> wPositions = elfPos.getWestPositions();
            final Set<Position> ePositions = elfPos.getEastPositions();
            if (elfPos.isGoingToMove(grove)) {
                for (int add = 0; add < 4; add++) {
                    final int dir = (direction + add) % 4;
                    if (!containsElf(dir, grove, nPositions, sPositions, wPositions, ePositions)) {
                        final Position next = elfPos.getNextPosition(dir);
                        if (!seen.contains(next)) {
                            nextToCurrent.put(next, elfPos);
                            seen.add(next);
                        } else {
                            nextToCurrent.remove(next);
                        }
                        break;
                    }
                }
            }
        }
        return nextToCurrent;
    }

    private void move(final List<Position> oldElfPositions, final List<Position> newElfPositions,
            final List<Position> grove) {
        oldElfPositions.forEach(grove::remove);
        newElfPositions.forEach(grove::add);
    }

    private int countEmptyTiles(final List<Position> grove) {
        final int minY = grove.stream().mapToInt(Position::y).min().getAsInt();
        final int maxY = grove.stream().mapToInt(Position::y).max().getAsInt();
        final int minX = grove.stream().mapToInt(Position::x).min().getAsInt();
        final int maxX = grove.stream().mapToInt(Position::x).max().getAsInt();
        int total = 0;
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                final Position p = new Position(x, y);
                if (grove.indexOf(p) == -1) {
                    total++;
                }
            }
        }
        return total;
    }

    private int runRounds(final List<Position> grove, final int nrRounds) {
        for (int round = 0; round < nrRounds; round++) {
            final Map<Position, Position> proposed = consider(round, grove);
            final List<Position> oldElfPositions = proposed.entrySet().stream().map(Map.Entry::getValue)
                    .collect(Collectors.toList());
            final List<Position> newElfPositions = proposed.entrySet().stream().map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            move(oldElfPositions, newElfPositions, grove);
        }
        return countEmptyTiles(grove);
    }

    private int findRound(final List<Position> grove) {
        int round = 0;
        List<Position> before = new ArrayList<>();
        while (!grove.equals(before)) {
            before = grove.stream().toList();
            final Map<Position, Position> proposed = consider(round, grove);
            final List<Position> oldElfPositions = proposed.entrySet().stream().map(Map.Entry::getValue)
                    .collect(Collectors.toList());
            final List<Position> newElfPositions = proposed.entrySet().stream().map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            move(oldElfPositions, newElfPositions, grove);
            round++;
        }
        return round;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(findRound(getGrove(input)));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(runRounds(getGrove(input), 10));
    }

    public static void main(final String... args) {
        new Day23("day23.txt");
    }
}
