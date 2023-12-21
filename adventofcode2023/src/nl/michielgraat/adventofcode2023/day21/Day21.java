package nl.michielgraat.adventofcode2023.day21;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day21 extends AocSolver {

    protected Day21(String filename) {
        super(filename);
    }

    private Coordinate getStart(List<String> input) {
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == 'S') {
                    return new Coordinate(x, y, '.');
                }
            }
        }
        throw new IllegalArgumentException("No starting coordinate found in input");
    }

    private List<Coordinate> readCoordinates(List<String> input) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                coordinates.add(new Coordinate(x, y, line.charAt(x) == 'S' ? '.' : line.charAt(x)));
            }
        }
        return coordinates;
    }

    private Set<Coordinate> getCoordinatesWithStepLength(Coordinate current, int curDist, int maxDist, final List<Coordinate> coordinates, Set<CoordinateDistance> visited) {
        Set<Coordinate> result = new HashSet<>();
        if (!visited.contains(new CoordinateDistance(current, curDist))) {
            if (curDist == maxDist) {
                result.add(current);
            } else {
                visited.add(new CoordinateDistance(current, curDist));
                
                List<Coordinate> neighbours = current.getNeighbours(coordinates);
                for (Coordinate neighbour : neighbours) {
                    result.addAll(getCoordinatesWithStepLength(neighbour, curDist+1, maxDist, coordinates, visited));
                }
            }
        }
        return result;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return "part 2";
    }

    @Override
    protected String runPart1(final List<String> input) {
        Set<Coordinate> cs = getCoordinatesWithStepLength(getStart(input), 0, 64, readCoordinates(input), new HashSet<>());
        return String.valueOf(cs.size());
    }

    public static void main(String... args) {
        new Day21("day21.txt");
    }
}
