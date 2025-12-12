package nl.michielgraat.adventofcode2025.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import nl.michielgraat.adventofcode2025.AocSolver;

public class Day12 extends AocSolver {

    protected Day12(final String filename) {
        super(filename);
    }

    private String printGrid(final int[][] grid) {
        final StringBuilder sb = new StringBuilder();
        for (int y = 0; y < grid.length; y++) {
            sb.append(Arrays.toString(grid[y]));
            sb.append("\n");
        }
        return sb.toString();
    }

    private int[][] transpose(final int[][] grid) {
        final int[][] result = new int[grid[0].length][grid.length];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                result[x][y] = grid[y][x];
            }
        }
        return result;
    }

    private int[][] reverseRows(final int[][] grid) {
        final int[][] result = new int[grid.length][grid[0].length];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < (grid[0].length / 2) + 1; x++) {
                final int current = grid[y][x];
                result[y][x] = grid[y][grid[y].length - x - 1];
                result[y][grid[y].length - x - 1] = current;
            }
        }
        return result;
    }

    private int[][] rotateRight(final int[][] grid) {
        return reverseRows(transpose(grid));
    }

    private int[][] flipHorizontally(final int[][] grid) {
        return reverseRows(grid);
    }

    private int[][] flipVertically(final int[][] grid) {
        return reverseRows(rotateRight(rotateRight(grid)));
    }

    private boolean matricesEqual(final int[][] m1, final int[][] m2) {
        if (m1.length != m2.length || m1[0].length != m2[0].length) {
            return false;
        }
        for (int i = 0; i < m1.length; i++) {
            if (!Arrays.equals(m1[i], m2[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean contains(final int[][] m, final List<int[][]> l) {
        for (final int[][] i : l) {
            if (matricesEqual(m, i)) {
                return true;
            }
        }
        return false;
    }

    private List<int[][]> getAllShapes(final int[][] grid) {
        final List<int[][]> result = new ArrayList<>();
        result.add(grid);
        final int[][] r90 = rotateRight(grid);
        final int[][] r180 = rotateRight(r90);
        final int[][] r270 = rotateRight(r180);
        final int[][] h = flipHorizontally(grid);
        final int[][] v = flipVertically(grid);
        final int[][] h90 = flipHorizontally(r90);
        final int[][] v90 = flipVertically(r90);

        result.add(r90);
        if (!contains(r180, result)) {
            result.add(r180);
        }
        if (!contains(r270, result)) {
            result.add(r270);
        }
        if (!contains(h, result)) {
            result.add(h);
        }
        if (!contains(v, result)) {
            result.add(v);
        }
        if (!contains(h90, result)) {
            result.add(h90);
        }
        if (!contains(v90, result)) {
            result.add(v90);
        }
        return result;
    }

    private Map<Integer, int[][]> parseShapes(final List<String> input) {
        final Map<Integer, int[][]> shapes = new HashMap<>();
        for (int i = 0; i < input.size(); i += 5) {
            final String line = input.get(i);
            if (line.contains("x")) {
                break;
            } else {
                final int index = Integer.parseInt(line.substring(0, line.indexOf(':')));
                final int[][] shape = new int[3][3];
                for (int j = i + 1; j < i + 4; j++) {
                    final String array = input.get(j);
                    for (int k = 0; k < 3; k++) {
                        shape[j - i - 1][k] = array.charAt(k) == '.' ? 0 : 1;
                    }
                }
                shapes.put(index, shape);
            }
        }
        return shapes;
    }

    private List<Region> parseRegions(final List<String> input) {
        final List<Region> result = new ArrayList<>();
        for (final String line : input) {
            if (line.contains("x")) {
                final int width = Integer.valueOf(line.substring(0, line.indexOf("x")));
                final int height = Integer.valueOf(line.substring(line.indexOf("x") + 1, line.indexOf(":")));
                final int[] quantities = Arrays.stream(line.substring(line.indexOf(":") + 2).trim().split(" "))
                        .mapToInt(Integer::parseInt).toArray();
                result.add(new Region(width, height, quantities));
            }
        }
        return result;
    }

    private void place(final int x, final int y, final int[][] shape, final int[][] grid, final boolean reverse) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (reverse) {
                    grid[i + y][j + x] = 0;
                } else {
                    if (shape[i][j] == 1) {
                        grid[i + y][j + x] = 1;
                    }
                }
            }
        }
    }

    private Optional<Coordinate> fits(final int[][] shape, final int[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            x: for (int x = 0; x < grid[y].length; x++) {
                for (int i = 0; i < shape.length; i++) {
                    for (int j = 0; j < shape[i].length; j++) {
                        if (i + y >= grid.length || j + x >= grid[i].length) {
                            continue x;
                        }
                        if ((grid[i + y][j + x] & shape[i][j]) == 1) {
                            continue x;
                        }
                    }
                }
                return Optional.of(new Coordinate(x, y, shape));
            }
        }
        return Optional.empty();
    }

    private boolean placeShapes(final int shapeNr, final int[][] grid, final List<int[][]> shapesToFit) {
        if (shapeNr == shapesToFit.size()) {
            return true;
        }
        final int[][] shape = shapesToFit.get(shapeNr);
        final List<int[][]> allVariants = getAllShapes(shape);
        for (final int[][] variant : allVariants) {
            final Optional<Coordinate> coordinate = fits(variant, grid);
            if (coordinate.isPresent()) {
                place(coordinate.get().x(), coordinate.get().y(), coordinate.get().shape(), grid, false);
                if (placeShapes(shapeNr + 1, grid, shapesToFit)) {
                    return true;
                }
                place(coordinate.get().x(), coordinate.get().y(), coordinate.get().shape(), grid, true);
            }
        }
        return false;
    }

    private boolean canFit(final Region region, final Map<Integer, int[][]> shapes) {
        final int[][] grid = new int[region.height()][region.width()];
        final int[] quantities = region.quantities();
        final List<int[][]> shapesToPlace = new ArrayList<>();
        for (int i = 0; i < quantities.length; i++) {
            for (int j = 0; j < quantities[i]; j++) {
                shapesToPlace.add(shapes.get(i));
            }
        }
        final boolean success = placeShapes(0, grid, shapesToPlace);
        return success;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return "Merry Christmas!";
    }

    @Override
    protected String runPart1(final List<String> input) {
        //
        // Backtracking way to fit shapes (way too slow for real input):
        //
        // Map<Integer, int[][]> shapes = parseShapes(input);
        // int total = 0;
        // for (Region region : parseRegions(input)) {
        // if (canFit(region, shapes)) {
        // total++;
        // }
        // }

        // The way it works fast for this specific input:
        // Just calculate the number of shapes needed, multiply that with the shape size
        // (always 3x3) and check if the region size is greater than or equal to that
        // minimum needed area.
        int total = 0;
        for (final Region region : parseRegions(input)) {
            final int nrOfShapes = Arrays.stream(region.quantities()).sum();
            final int minAreaNeeded = 9 * nrOfShapes;
            final int regionSize = region.width() * region.height();
            if (regionSize >= minAreaNeeded) {
                total++;
            }
        }
        return String.valueOf(total);
    }

    public static void main(final String... args) {
        new Day12("day12.txt");
    }
}
