package nl.michielgraat.adventofcode2019.day10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import nl.michielgraat.adventofcode2019.AocSolver;

public class Day10 extends AocSolver {

    protected Day10(final String filename) {
        super(filename);
    }

    private List<Asteroid> parseAsteroids(final List<String> input) {
        final List<Asteroid> asteroids = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            final String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') {
                    asteroids.add(new Asteroid(x, y));
                }
            }
        }
        return asteroids;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Asteroid> asteroids = parseAsteroids(input);
        final Asteroid base = Collections.max(asteroids, Comparator.comparing(a -> a.getNrInSight(asteroids)));
        final Asteroid lastDestroyed = base.getNthDestroyedAsteroid(200, asteroids);
        return String.valueOf((lastDestroyed.x() * 100 + lastDestroyed.y()));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Asteroid> asteroids = parseAsteroids(input);
        return String.valueOf(asteroids.stream().mapToInt(a -> a.getNrInSight(asteroids)).max()
                .orElseThrow(NoSuchElementException::new));
    }

    public static void main(final String... args) {
        new Day10("day10.txt");
    }
}
