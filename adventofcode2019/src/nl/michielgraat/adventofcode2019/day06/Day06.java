package nl.michielgraat.adventofcode2019.day06;

import java.util.HashMap;
import java.util.List;

import nl.michielgraat.adventofcode2019.AocSolver;

public class Day06 extends AocSolver {

    protected Day06(final String filename) {
        super(filename);
    }

    PlanetarySystem getPlanetarySystem(final List<String> input) {
        final PlanetarySystem system = new PlanetarySystem(new HashMap<>());
        for (final String line : input) {
            final String[] bodies = line.split("\\)");
            system.addCelestialBody(bodies[0]);
            system.addCelestialBody(bodies[1]);
            system.addOrbit(bodies[0], bodies[1]);
        }
        return system;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final PlanetarySystem system = getPlanetarySystem(input);
        return String.valueOf(system.getOrbitalTransfersToSanta());
    }

    @Override
    protected String runPart1(final List<String> input) {
        final PlanetarySystem system = getPlanetarySystem(input);
        return String.valueOf(system.getNrOrbits());
    }

    public static void main(final String... args) {
        new Day06("day06.txt");
    }
}
