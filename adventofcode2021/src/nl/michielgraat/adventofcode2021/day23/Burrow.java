package nl.michielgraat.adventofcode2021.day23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Burrow implements Comparable<Burrow> {
    char[][] grid;
    long energy = Long.MAX_VALUE;

    Burrow(final List<String> lines) {
        parseGrid(lines);
        this.energy = 0;
    }

    Burrow(final char[][] grid, final long energy) {
        this.grid = grid;
        this.energy = energy;
    }

    private void parseGrid(final List<String> lines) {
        grid = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                grid[i][j] = line.charAt(j);
            }
        }
    }

    void print() {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
        System.out.println("Energy: " + this.energy);
        System.out.println();
    }

    private boolean podFinished(final char pod) {
        final int x = getCorrectRoomX(pod);
        for (int y = 2; y < grid.length - 1; y++) {
            if (grid[y][x] != pod)
                return false;
        }
        return true;
    }

    private List<Position> findPositionsForMoveablePods(final char pod) {
        final List<Position> positions = new ArrayList<>();
        if (!podFinished(pod)) {
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[0].length; x++) {
                    if (grid[y][x] == pod) {
                        final Position p = new Position(pod, x, y);
                        if (!positions.contains(p)) {
                            positions.add(p);
                        }
                    }
                }
            }
        }
        return positions;
    }

    private List<Coordinate> getHallwayCoordinates() {
        final List<Coordinate> hw = new ArrayList<>();
        hw.add(new Coordinate(1, 1));
        hw.add(new Coordinate(2, 1));
        hw.add(new Coordinate(4, 1));
        hw.add(new Coordinate(6, 1));
        hw.add(new Coordinate(8, 1));
        hw.add(new Coordinate(10, 1));
        hw.add(new Coordinate(11, 1));
        return hw;
    }

    private int getCorrectRoomX(final char pod) {
        if (pod == 'A') {
            return 3;
        } else if (pod == 'B') {
            return 5;
        } else if (pod == 'C') {
            return 7;
        } else {
            return 9;
        }
    }

    private List<Coordinate> getLowestRoomCoordinate(final char pod) {
        final List<Coordinate> r = new ArrayList<>();
        final int x = getCorrectRoomX(pod);
        // We are only interested in the 'lowest' (highest Y) position which is
        // accessible, we do not stop at in-between coordinates (pod goes to 'lowest'
        // position possible.)
        if (roomEmpty(x) || roomContainsPod(x, pod)) {
            final int highestY = getHighestY(x);
            r.add(new Coordinate(x, highestY));
        }
        return r;
    }

    private int reachable(final int x, final int y, final int prevy, final char pod, final Coordinate goal, final Coordinate start, int nr) {
        if (goal.x == x && goal.y == y) {
            if (grid[y][x] == '.') {
                return nr;
            } else {
                return -1;
            }
        } else {
            if (grid[y][x] == '.' || (x == start.x && y == start.y)) {

                if (y >= 2 && prevy != y - 1) {
                    if ((y == grid.length - 2 && x != getCorrectRoomX(pod)) || (y >= 2 && y <= grid.length - 3)) {
                        return reachable(x, y - 1, y, pod, goal, start, ++nr);
                    }
                } else if (y == 1) {
                    if (goal.x < x) {
                        return reachable(x - 1, y, y, pod, goal, start, ++nr);
                    } else if (goal.x > x) {
                        return reachable(x + 1, y, y, pod, goal, start, ++nr);
                    } else if (x == getCorrectRoomX(pod)) {
                        // Dive to the lowest position in the goal-room.
                        final int highestY = getHighestY(x);
                        nr += highestY - y;
                        return reachable(x, highestY, y, pod, goal, start, nr);
                    }
                }
            }
        }
        return -1;
    }

    private int getHighestY(final int x) {
        int highest = 2;
        for (int y = 2; y < grid.length - 1; y++) {
            if (grid[y][x] == '.') {
                highest = y;
            }
        }
        return highest;
    }

    private boolean roomEmpty(final int x) {
        for (int y = 2; y < grid.length - 1; y++) {
            if (grid[y][x] != '.') {
                return false;
            }
        }
        return true;
    }

    private boolean roomContainsPod(final int x, final char pod) {
        if (grid[2][x] != '.') {
            return false;
        } else {
            boolean contains = false;
            for (int y = 2; y < grid.length - 1; y++) {
                if (grid[y][x] != '.' && grid[y][x] != pod) {
                    return false;
                }
                if (grid[y][x] == pod) {
                    contains = true;
                }
            }
            return contains;
        }
    }

    private List<Move> getMovesForPod(final int x, final int y, final char pod) {
        final List<Move> moves = new ArrayList<>();
        if (!podFinished(pod)) {
            final Coordinate current = new Coordinate(x, y);
            final List<Coordinate> possibleEnds = new ArrayList<>();
            if (y != 1) {
                // We do not start in the hallway, so all hallway coordinates need to be
                // evaluated
                possibleEnds.addAll(getHallwayCoordinates());
            }
            // Add the end-room for this pod
            possibleEnds.addAll(getLowestRoomCoordinate(pod));
            // Of course we do not move to the current position, because we are already
            // there.
            possibleEnds.remove(current);

            for (final Coordinate c : possibleEnds) {
                final int nrOfSteps = reachable(x, y, y, pod, c, new Coordinate(x, y), 0);
                if (nrOfSteps >= 0) {
                    moves.add(new Move(x, y, c.x, c.y, pod, nrOfSteps));
                }
            }
        }
        return moves;
    }

    private List<Position> getPodPositions() {
        final List<Position> positions = new ArrayList<>();
        positions.addAll(findPositionsForMoveablePods('A'));
        positions.addAll(findPositionsForMoveablePods('B'));
        positions.addAll(findPositionsForMoveablePods('C'));
        positions.addAll(findPositionsForMoveablePods('D'));
        return positions;
    }

    private char[][] copyGrid(final char[][] grid) {
        final char[][] newGrid = new char[grid.length][grid[0].length];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                newGrid[y][x] = Character.valueOf(grid[y][x]);
            }
        }
        return newGrid;
    }

    List<Burrow> getNeighbours() {
        final List<Burrow> neighbours = new ArrayList<>();
        final List<Position> podPositions = getPodPositions();
        final List<Move> moves = new ArrayList<>();
        for (final Position p : podPositions) {
            moves.addAll(getMovesForPod(p.x, p.y, p.pod));
        }
        for (final Move move : moves) {
            final char[][] newGrid = copyGrid(grid);
            newGrid[move.eY][move.eX] = move.pod;
            newGrid[move.sY][move.sX] = '.';
            neighbours.add(new Burrow(newGrid, move.energy));
        }
        return neighbours;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(grid);
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
        final Burrow other = (Burrow) obj;
        if (!Arrays.deepEquals(grid, other.grid))
            return false;
        return true;
    }

    @Override
    public int compareTo(final Burrow o) {
        return Long.valueOf(this.energy).compareTo(Long.valueOf(o.energy));
    }
}