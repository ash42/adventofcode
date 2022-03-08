package nl.michielgraat.adventofcode2016.day22;

import java.util.List;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day22 {

    private static final String FILENAME = "day22.txt";

    public int runPart2(final List<String> lines) {
        final String lastLine = lines.get(lines.size() - 1);
        final int maxX = Integer.parseInt(
                lastLine.substring(lastLine.indexOf("-") + 2, lastLine.indexOf("-", lastLine.indexOf("-") + 1)));
        int emptyX = -1;
        int emptyY = -1;
        int minWallX = Integer.MAX_VALUE;
        for (int i = 2; i < lines.size(); i++) {
            final String a = lines.get(i);
            final String[] elementsA = a.split("\\s+");
            final int x = Integer.parseInt(a.substring(a.indexOf("-") + 2, a.indexOf("-", a.indexOf("-") + 1)));
            final int y = Integer.parseInt(a.substring(a.indexOf("-", a.indexOf("-") + 1) + 2, a.indexOf(" ")));
            final int sizeA = Integer.parseInt(elementsA[1].substring(0, elementsA[1].length() - 1));
            final int usedA = Integer.parseInt(elementsA[2].substring(0, elementsA[2].length() - 1));
            if (sizeA > 100 && x < minWallX) {
                minWallX = x;
            }
            if (usedA == 0) {
                emptyX = x;
                emptyY = y;
            }
        }
        /*
         * To understand this better, try to print the input as described in the
         * assignment.
         * 
         * We need to get the empty space from it's original position to right beside
         * the goal data.
         * For y this is just the y-coordinate of the empty space (as the goal data is
         * at y = 0).
         * 
         * So this is emptyY.
         * 
         * For x this is slightly more complicated, because there is a "wall" with
         * imprenetable data (very large, very full nodes), so the empty data has
         * to move around this wall.
         * So we take difference of the x-coordinate of the empty space and the minimum
         * x-coordinate of the wall. From this we subtract 1 because we have to move
         * past the wall.
         * 
         * So this is emptyX - minWallX - 1.
         * 
         * Now the empty space is at (minWallX-1,0). We need to get to (maxX-1,0) - just
         * beside the goal data.
         * 
         * So this is maxX-1 - minWallX-1.
         * 
         * Finally we have to move the data to the empty space. This leaves the goal
         * data one step closer to the finish at (0,0), but the empty space is now at
         * the wrong side of the goal data. So to move the goal one step closer to the
         * finish again, we have to move the empty space around the goal data. This
         * takes 4 steps. So in total this is 5 steps: 1 time to move the goal data
         * into the empty space and 4 times to move the empty space around the new
         * position of the goal data.
         * As we start with the empty space at maxX-1, we have to repeat this procedure
         * maxX-1 times.
         * 
         * So this is (maxX-1)*5.
         * 
         * Finally the empty space is at the finish, with the goal data just beside it.
         * So, to finish the task, we have to take one final step, moving the goal data
         * to the finish.
         * 
         * So this is + 1.
         * 
         * If we combine all this, we get:
         */
        return emptyY + (emptyX - (minWallX - 1)) + (maxX - 1 - (minWallX - 1)) + (maxX - 1) * 5 + 1;
    }

    public int runPart1(final List<String> lines) {
        int total = 0;
        for (int i = 2; i < lines.size(); i++) {
            final String a = lines.get(i);
            final String[] elementsA = a.split("\\s+");
            final int usedA = Integer.parseInt(elementsA[2].substring(0, elementsA[2].length() - 1));
            final int availA = Integer.parseInt(elementsA[3].substring(0, elementsA[3].length() - 1));
            for (int j = i + 1; j < lines.size(); j++) {
                final String b = lines.get(j);
                final String[] elementsB = b.split("\\s+");
                final int usedB = Integer.parseInt(elementsB[2].substring(0, elementsB[2].length() - 1));
                final int availB = Integer.parseInt(elementsB[3].substring(0, elementsB[3].length() - 1));
                if ((usedA != 0 && usedA <= availB) || (usedB != 0 && usedB <= availA)) {
                    total++;
                }
            }
        }
        return total;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day22().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day22().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}