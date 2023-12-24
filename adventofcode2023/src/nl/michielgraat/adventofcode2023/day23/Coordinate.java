package nl.michielgraat.adventofcode2023.day23;

import java.util.ArrayList;
import java.util.List;

public record Coordinate(int x, int y) {

    public List<Coordinate> getNeighbours(char[][] grid, boolean part1) {
        List<Coordinate> neighbours = new ArrayList<>();

        Coordinate north = new Coordinate(x, y - 1);
        Coordinate east = new Coordinate(x + 1, y);
        Coordinate south = new Coordinate(x, y + 1);
        Coordinate west = new Coordinate(x - 1, y);

        if (part1 && grid[y][x] == '>') {
            if (x + 1 < grid[y].length && grid[y][x + 1] != '#' ) {
                neighbours.add(east);
            }
        } else if (part1 && grid[y][x] == '<') {
            if (x - 1 >= 0 && grid[y][x - 1] != '#') {
                neighbours.add(west);
            }
        } else if (part1 && grid[y][x] == '^') {
            if (y - 1 >= 0 && grid[y - 1][x] != '#') {
                neighbours.add(north);
            }
        } else if (part1 && grid[y][x] == 'v') {
            if (y + 1 < grid.length && grid[y + 1][x] != '#') {
                neighbours.add(south);
            }
        } else {
            if (y - 1 >= 0 && grid[y - 1][x] != '#') {
                neighbours.add(north);
            }
            if (x + 1 < grid[y].length && grid[y][x + 1] != '#') {
                neighbours.add(east);
            }
            if (y + 1 < grid.length && grid[y + 1][x] != '#') {
                neighbours.add(south);
            }
            if (x - 1 >= 0 && grid[y][x - 1] != '#') {
                neighbours.add(west);
            }
        }
        return neighbours;
    }

}
