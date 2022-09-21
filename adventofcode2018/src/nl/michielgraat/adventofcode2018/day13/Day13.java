package nl.michielgraat.adventofcode2018.day13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day13 {
    private static final String FILENAME = "day13.txt";

    private char[][] buildGrid(List<String> lines, List<Cart> carts) {
        char[][] grid = new char[lines.get(0).length()][lines.size()];
        int cartId = 0;
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == '^') {
                    carts.add(new Cart(cartId++, x, y, Direction.UP));
                    grid[x][y] = 's';
                } else if (c == '>') {
                    carts.add(new Cart(cartId++, x, y, Direction.RIGHT));
                    grid[x][y] = 's';
                } else if (c == 'v') {
                    carts.add(new Cart(cartId++, x, y, Direction.DOWN));
                    grid[x][y] = 's';
                } else if (c == '<') {
                    carts.add(new Cart(cartId++, x, y, Direction.LEFT));
                    grid[x][y] = 's';
                } else {
                    grid[x][y] = c;
                }
            }
        }
        Collections.sort(carts);
        return grid;
    }

    private String tick(List<Cart> carts, char[][] grid) {
        String crash = null;
        for (Cart c : carts) {
            c.move(grid);
            if (crash == null) {
                for (Cart c2 : carts) {
                    if (!c.equals(c2) && c.x == c2.x && c.y == c2.y) {
                        crash = c.x + "," + c.y;
                    }
                }
            }
        }
        Collections.sort(carts);
        return crash;
    }

    private List<Cart> tickAndRemove(List<Cart> carts, char[][] grid) {
        List<Cart> newCarts = new ArrayList<>();
        for (Cart c : carts) {
            if (!c.toRemove) {
                c.move(grid);
                for (Cart c2 : carts) {
                    if (!c.equals(c2) && c.x == c2.x && c.y == c2.y) {
                        c.setToRemove(true);
                        c2.setToRemove(true);
                    }
                }
            }
        }
        for (Cart c : carts) {
            if (!c.isToRemove()) {
                newCarts.add(c);
            }
        }
        Collections.sort(newCarts);
        return newCarts;
    }

    private String runPart2(List<String> lines) {
        List<Cart> carts = new ArrayList<>();
        char[][] grid = buildGrid(lines, carts);
        while (carts.size() > 1) {
            carts = tickAndRemove(carts, grid);
        }
        return carts.get(0).x + "," + carts.get(0).y;
    }

    private String runPart1(List<String> lines) {
        List<Cart> carts = new ArrayList<>();
        char[][] grid = buildGrid(lines, carts);
        String crash = null;
        while (crash == null) {
            crash = tick(carts, grid);
        }
        return crash;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day13().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day13().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
