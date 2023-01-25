package nl.michielgraat.adventofcode2019.day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2019.AocSolver;
/**
 * Getting neighbours can be done a lot quicker by looking directly at the maze
 * in a char[][] form. This requires more thorough handling of edge cases though
 * and I am just happy to have something that works ;-) Runtime is about 2,5 secs
 * for part 1, 60 secs for part 2.
 */
public class Day20 extends AocSolver {

    protected Day20(final String filename) {
        super(filename);
    }

    private Map<Integer, List<Position>> getMazeCache(final int maxDepth, final List<Position> maze) {
        final Map<Integer, List<Position>> cache = new HashMap<>();
        for (int i = 0; i <= maxDepth; i++) {
            final List<Position> newMaze = new ArrayList<>();
            for (final Position m : maze) {
                newMaze.add(new Position(m.x(), m.y(), new Portal(m.portal().name(), m.portal().inner(), i)));
            }
            cache.put(i, newMaze);
        }
        return cache;
    }

    private char[][] getCharMaze(final List<String> input) {
        final char[][] charMaze = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            charMaze[y] = input.get(y).toCharArray();
        }
        return charMaze;
    }

    private List<Position> getMaze(final List<String> input) {
        final List<Position> maze = new ArrayList<>();
        final char[][] charMaze = getCharMaze(input);
        for (int y = 0; y < charMaze.length; y++) {
            for (int x = 0; x < charMaze[y].length; x++) {
                if (charMaze[y][x] == '.') {
                    boolean inner = false;
                    String portal = "";
                    if (Character.isLetter(charMaze[y][x - 1])) {
                        portal += charMaze[y][x - 2];
                        portal += charMaze[y][x - 1];
                        inner = (x - 2) != 0;
                    } else if (Character.isLetter(charMaze[y][x + 1])) {
                        portal += charMaze[y][x + 1];
                        portal += charMaze[y][x + 2];
                        inner = (x + 2) != charMaze[y].length - 1;
                    } else if (Character.isLetter(charMaze[y - 1][x])) {
                        portal += charMaze[y - 2][x];
                        portal += charMaze[y - 1][x];
                        inner = (y - 2) != 0;
                    } else if (Character.isLetter(charMaze[y + 1][x])) {
                        portal += charMaze[y + 1][x];
                        portal += charMaze[y + 2][x];
                        inner = (y + 2) != charMaze.length - 1;
                    }
                    maze.add(new Position(x, y, new Portal(portal, inner, 0)));
                }
            }
        }
        return maze;
    }

    private int dijkstra(final Position start, final Position end, List<Position> positions, final boolean part1,
            final int maxDepth) {
        final PriorityQueue<Position> queue = new PriorityQueue<>();
        queue.offer(start);
        final Map<Position, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        int currentLevel = 0;
        final Map<Integer, List<Position>> mazeCache = getMazeCache(maxDepth, positions);
        while (!queue.isEmpty()) {
            final Position current = queue.poll();
            final int dist = distances.get(current);
            if (current.portal().level() != currentLevel) {
                positions = mazeCache.get(current.portal().level());
                currentLevel = current.portal().level();
            }
            final List<Position> neighbours = part1 ? current.getNeighbours(positions)
                    : current.getNeighbours2(positions, maxDepth);
            for (final Position n : neighbours) {
                int ndist = dist;
                ndist++;
                if (ndist < distances.getOrDefault(n, Integer.MAX_VALUE)) {
                    distances.put(n, ndist);
                    queue.add(n);
                }
            }
        }

        return distances.entrySet().stream().filter(e -> e.getKey().equals(end)).collect(Collectors.toList()).stream()
                .map(Entry::getValue).mapToInt(d -> d).min().orElse(Integer.MAX_VALUE);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Position> maze = getMaze(input);
        final Position start = maze.stream().filter(Position::isStart).findAny()
                .orElseThrow(NoSuchElementException::new);
        final Position end = maze.stream().filter(Position::isEnd).findAny().orElseThrow(NoSuchElementException::new);

        return String.valueOf(
                dijkstra(start, end, maze, false, (int) maze.stream().filter(p -> p.portal().inner()).count()));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Position> maze = getMaze(input);
        final Position start = maze.stream().filter(Position::isStart).findAny()
                .orElseThrow(NoSuchElementException::new);
        final Position end = maze.stream().filter(Position::isEnd).findAny().orElseThrow(NoSuchElementException::new);

        return String.valueOf(dijkstra(start, end, maze, true, 0));
    }

    public static void main(final String... args) {
        new Day20("day20.txt");
    }
}
