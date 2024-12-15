package nl.michielgraat.adventofcode2024.day15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day15 extends AocSolver {

    protected Day15(final String filename) {
        super(filename);
    }

    private void printGrid(final char[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
    }

    private char[][] parseGrid(final List<String> input) {
        int size = 0;
        for (; size < input.size(); size++) {
            if (input.get(size).isBlank()) {
                break;
            }
        }
        final char[][] grid = new char[size][input.get(0).length()];
        for (int y = 0; y < size; y++) {
            final String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                grid[y][x] = line.charAt(x);
            }
        }
        return grid;
    }

    private String parseInstructions(final List<String> input) {
        final StringBuilder sb = new StringBuilder();
        boolean insStart = false;
        for (final String line : input) {
            if (insStart) {
                sb.append(line);
            } else if (line.isBlank()) {
                insStart = true;
            }
        }
        return sb.toString();
    }

    private Optional<Position> getRobotPosition(final char[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == '@') {
                    return Optional.of(new Position(x, y));
                }
            }
        }
        return Optional.empty();
    }

    private boolean isInBounds(final int x, final int y, final char[][] grid) {
        return x >= 0 && y >= 0 && y < grid.length && x < grid[y].length;
    }

    private boolean moveDown(final int x, final int y, final char[][] grid) {
        if (isInBounds(x, y + 1, grid)) {
            if (grid[y + 1][x] == '.') {
                grid[y][x] = '.';
                grid[y + 1][x] = '@';
                return true;
            } else if (grid[y + 1][x] == 'O') {
                int destinationY = y + 2;
                boolean foundEmptySpot = false;
                while (destinationY < grid.length) {
                    if (grid[destinationY][x] == '.') {
                        foundEmptySpot = true;
                        break;
                    }
                    if (grid[destinationY][x] == '#') {
                        break;
                    } else {
                        destinationY++;
                    }
                }
                if (foundEmptySpot) {
                    for (int toMoveY = y + 2; toMoveY <= destinationY; toMoveY++) {
                        grid[toMoveY][x] = 'O';
                    }
                    grid[y + 1][x] = '@';
                    grid[y][x] = '.';
                    return true;
                }
            }
        }
        return false;
    }

    private boolean moveUp(final int x, final int y, final char[][] grid) {
        if (isInBounds(x, y - 1, grid)) {
            if (grid[y - 1][x] == '.') {
                grid[y][x] = '.';
                grid[y - 1][x] = '@';
                return true;
            } else if (grid[y - 1][x] == 'O') {
                int destinationY = y - 2;
                boolean foundEmptySpot = false;
                while (destinationY > 0) {
                    if (grid[destinationY][x] == '.') {
                        foundEmptySpot = true;
                        break;
                    }
                    if (grid[destinationY][x] == '#') {
                        break;
                    } else {
                        destinationY--;
                    }
                }
                if (foundEmptySpot) {
                    for (int toMoveY = y - 2; toMoveY >= destinationY; toMoveY--) {
                        grid[toMoveY][x] = 'O';
                    }
                    grid[y - 1][x] = '@';
                    grid[y][x] = '.';
                    return true;
                }
            }
        }
        return false;
    }

    private boolean moveRight(final int x, final int y, final char[][] grid) {
        if (isInBounds(x + 1, y, grid)) {
            if (grid[y][x + 1] == '.') {
                grid[y][x] = '.';
                grid[y][x + 1] = '@';
                return true;
            } else if (grid[y][x + 1] == 'O') {
                int destinationX = x + 2;
                boolean foundEmptySpot = false;
                while (destinationX < grid[y].length) {
                    if (grid[y][destinationX] == '.') {
                        foundEmptySpot = true;
                        break;
                    } else if (grid[y][destinationX] == '#') {
                        break;
                    } else {
                        destinationX++;
                    }
                }
                if (foundEmptySpot) {
                    for (int toMoveX = x + 2; toMoveX <= destinationX; toMoveX++) {
                        grid[y][toMoveX] = 'O';
                    }
                    grid[y][x + 1] = '@';
                    grid[y][x] = '.';
                    return true;
                }
            }
        }
        return false;
    }

    private boolean moveLeft(final int x, final int y, final char[][] grid) {
        if (isInBounds(x - 1, y, grid)) {
            if (grid[y][x - 1] == '.') {
                grid[y][x] = '.';
                grid[y][x - 1] = '@';
                return true;
            } else if (grid[y][x - 1] == 'O') {
                int destinationX = x - 2;
                boolean foundEmptySpot = false;
                while (destinationX > 0) {
                    if (grid[y][destinationX] == '.') {
                        foundEmptySpot = true;
                        break;
                    } else if (grid[y][destinationX] == '#') {
                        break;
                    } else {
                        destinationX--;
                    }
                }
                if (foundEmptySpot) {
                    for (int toMoveX = x - 2; toMoveX >= destinationX; toMoveX--) {
                        grid[y][toMoveX] = 'O';
                    }
                    grid[y][x - 1] = '@';
                    grid[y][x] = '.';
                    return true;
                }
            }
        }
        return false;
    }

    private boolean moveDown2(final int x, final int y, final char[][] grid) {
        if (isInBounds(x, y + 1, grid)) {
            if (grid[y + 1][x] == '.') {
                grid[y][x] = '.';
                grid[y + 1][x] = '@';
                return true;
            } else if (grid[y + 1][x] == '[' || grid[y + 1][x] == ']') {
                boolean foundEmptySpot = true;

                final Map<Integer, List<Box>> involvedBoxes = findInvolvedBoxes(grid, x, y, false);
                final int maxY = involvedBoxes.keySet().stream().mapToInt(k -> k).max().getAsInt();
                if (isInBounds(x, maxY + 1, grid)) {
                    for (final Box box : involvedBoxes.values().stream().flatMap(List::stream)
                            .collect(Collectors.toList())) {
                        if ((grid[box.left().y() + 1][box.left().x()] == '#'
                                || grid[box.right().y() + 1][box.right().x()] == '#')) {
                            foundEmptySpot = false;
                            break;
                        }
                    }
                    if (foundEmptySpot) {
                        for (int currentY = maxY; currentY > y; currentY--) {
                            final List<Box> boxesToMove = involvedBoxes.get(currentY);
                            for (final Box box : boxesToMove) {
                                grid[currentY + 1][box.left().x()] = '[';
                                grid[currentY + 1][box.right().x()] = ']';
                                grid[currentY][box.left().x()] = '.';
                                grid[currentY][box.right().x()] = '.';
                            }
                        }
                        grid[y + 1][x] = '@';
                        grid[y][x] = '.';
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean moveUp2(final int x, final int y, final char[][] grid) {
        if (isInBounds(x, y - 1, grid)) {
            if (grid[y - 1][x] == '.') {
                grid[y][x] = '.';
                grid[y - 1][x] = '@';
                return true;
            } else if (grid[y - 1][x] == '[' || grid[y - 1][x] == ']') {
                boolean foundEmptySpot = true;

                final Map<Integer, List<Box>> involvedBoxes = findInvolvedBoxes(grid, x, y, true);
                final int minY = involvedBoxes.keySet().stream().mapToInt(k -> k).min().getAsInt();
                if (isInBounds(x, minY - 1, grid)) {
                    for (final Box box : involvedBoxes.values().stream().flatMap(List::stream)
                            .collect(Collectors.toList())) {
                        if ((grid[box.left().y() - 1][box.left().x()] == '#'
                                || grid[box.right().y() - 1][box.right().x()] == '#')) {
                            foundEmptySpot = false;
                            break;
                        }
                    }

                    if (foundEmptySpot) {
                        for (int currentY = minY; currentY < y; currentY++) {
                            final List<Box> boxesToMove = involvedBoxes.get(currentY);
                            for (final Box box : boxesToMove) {
                                grid[currentY - 1][box.left().x()] = '[';
                                grid[currentY - 1][box.right().x()] = ']';
                                grid[currentY][box.left().x()] = '.';
                                grid[currentY][box.right().x()] = '.';
                            }
                        }
                        grid[y - 1][x] = '@';
                        grid[y][x] = '.';
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean moveRight2(final int x, final int y, final char[][] grid) {
        if (isInBounds(x + 1, y, grid)) {
            if (grid[y][x + 1] == '.') {
                grid[y][x] = '.';
                grid[y][x + 1] = '@';
                return true;
            } else if (grid[y][x + 1] == '[') {
                int destinationX = x + 3;
                boolean foundEmptySpot = false;
                while (destinationX < grid[y].length) {
                    if (grid[y][destinationX] == '.') {
                        foundEmptySpot = true;
                        break;
                    } else if (grid[y][destinationX] == '#') {
                        break;
                    } else {
                        destinationX++;
                    }
                }
                if (foundEmptySpot) {
                    for (int toMoveX = x + 2; toMoveX <= destinationX; toMoveX += 2) {
                        grid[y][toMoveX] = '[';
                        grid[y][toMoveX + 1] = ']';
                    }
                    grid[y][x + 1] = '@';
                    grid[y][x] = '.';
                    return true;
                }
            }
        }
        return false;
    }

    private boolean moveLeft2(final int x, final int y, final char[][] grid) {
        if (isInBounds(x - 1, y, grid)) {
            if (grid[y][x - 1] == '.') {
                grid[y][x] = '.';
                grid[y][x - 1] = '@';
                return true;
            } else if (grid[y][x - 1] == ']') {
                int destinationX = x - 3;
                boolean foundEmptySpot = false;
                while (destinationX > 0) {
                    if (grid[y][destinationX] == '.') {
                        foundEmptySpot = true;
                        break;
                    } else if (grid[y][destinationX] == '#') {
                        break;
                    } else {
                        destinationX--;
                    }
                }
                if (foundEmptySpot) {
                    for (int toMoveX = x - 2; toMoveX >= destinationX; toMoveX -= 2) {
                        grid[y][toMoveX] = ']';
                        grid[y][toMoveX - 1] = '[';
                    }
                    grid[y][x - 1] = '@';
                    grid[y][x] = '.';
                    return true;
                }
            }
        }
        return false;
    }

    private Map<Integer, List<Box>> findInvolvedBoxes(final char[][] grid, final int x, int y, final boolean up) {
        final Map<Integer, List<Box>> involvedBoxes = new HashMap<>();
        y = up ? y - 1 : y + 1;
        final Box start = grid[y][x] == '[' ? new Box(new Position(x, y), new Position(x + 1, y))
                : new Box(new Position(x - 1, y), new Position(x, y));
        final List<Box> boxes = new ArrayList<>();
        boxes.add(start);
        involvedBoxes.put(y, boxes);
        while (true) {
            final List<Box> boxesToAdd = new ArrayList<>();
            if (y > 0 && y < grid.length - 1) {
                final int nextY = up ? y - 1 : y + 1;
                for (final Box box : involvedBoxes.getOrDefault(y, new ArrayList<>())) {
                    if (grid[nextY][box.left().x()] == '[') {
                        boxesToAdd.add(
                                new Box(new Position(box.left().x(), nextY), new Position(box.right().x(), nextY)));
                    }
                    if (grid[nextY][box.left().x()] == ']') {
                        boxesToAdd.add(
                                new Box(new Position(box.left().x() - 1, nextY), new Position(box.left().x(), nextY)));
                    }
                    if (grid[nextY][box.right().x()] == '[') {
                        boxesToAdd.add(new Box(new Position(box.right().x(), nextY),
                                new Position(box.right().x() + 1, nextY)));
                    }
                }
                if (boxesToAdd.isEmpty()) {
                    break;
                } else {
                    involvedBoxes.put(nextY, boxesToAdd);
                    y = up ? y - 1 : y + 1;
                }
            } else {
                break;
            }
        }
        return involvedBoxes;
    }

    private void move(final String instructions, final char[][] grid, int x, int y, final boolean part1) {
        for (int i = 0; i < instructions.length(); i++) {
            final char instruction = instructions.charAt(i);
            switch (instruction) {
                case '<':
                    x = (part1 ? moveLeft(x, y, grid) : moveLeft2(x, y, grid)) ? x - 1 : x;
                    break;
                case '>':
                    x = (part1 ? moveRight(x, y, grid) : moveRight2(x, y, grid)) ? x + 1 : x;
                    break;
                case '^':
                    y = (part1 ? moveUp(x, y, grid) : moveUp2(x, y, grid)) ? y - 1 : y;
                    break;
                default:
                    y = (part1 ? moveDown(x, y, grid) : moveDown2(x, y, grid)) ? y + 1 : y;
            }
        }
    }

    private long calculateGPS(final char[][] grid) {
        long total = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 'O' || grid[y][x] == '[') {
                    total += 100 * y + x;
                }
            }
        }
        return total;
    }

    private char[][] enlargeGrid(final char[][] grid) {
        final char[][] newGrid = new char[grid.length][grid[0].length * 2];

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                switch (grid[y][x]) {
                    case 'O':
                        newGrid[y][2 * x] = '[';
                        newGrid[y][2 * x + 1] = ']';
                        break;
                    case '@':
                        newGrid[y][2 * x] = '@';
                        newGrid[y][2 * x + 1] = '.';
                        break;
                    default:
                        newGrid[y][2 * x] = grid[y][x];
                        newGrid[y][2 * x + 1] = grid[y][x];
                }
            }
        }
        return newGrid;
    }

    @Override
    protected String runPart2(final List<String> input) {
        char[][] grid = parseGrid(input);
        grid = enlargeGrid(grid);
        final String instructions = parseInstructions(input);
        final Position robotPosition = getRobotPosition(grid).get();
        move(instructions, grid, robotPosition.x(), robotPosition.y(), false);
        return String.valueOf(calculateGPS(grid));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final char[][] grid = parseGrid(input);
        final String instructions = parseInstructions(input);
        final Position robotPosition = getRobotPosition(grid).get();
        move(instructions, grid, robotPosition.x(), robotPosition.y(), true);
        return String.valueOf(calculateGPS(grid));
    }

    public static void main(final String... args) {
        new Day15("day15.txt");
    }
}

record Position(int x, int y) {
}

record Box(Position left, Position right) {
}