package nl.michielgraat.adventofcode2018.day09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day09 {

    private static final String FILENAME = "day09.txt";

    private long getHighscore(int nrOfPlayers, int maxValue) {
        Circle<Integer> circle = new Circle<>();
        circle.add(0);
        long[] scores = new long[nrOfPlayers];
        for (int i = 1; i <= maxValue; i++) {
            int curPlayer = i % nrOfPlayers;
            if (i % 23 == 0) {
                scores[curPlayer] += i;
                circle.rotate(-7);
                scores[curPlayer] += circle.pop();
            } else {
                circle.rotate(2);
                circle.addLast(i);
            }
        }
        return Arrays.stream(scores).max().getAsLong();
    }

    private long runPart2(List<String> lines) {
        int nrOfPlayers = Integer.parseInt(lines.get(0).split(" ")[0]);
        int maxValue = Integer.parseInt(lines.get(0).split(" ")[6]) * 100;
        return getHighscore(nrOfPlayers, maxValue);
    }

    private long runPart1(List<String> lines) {
        int nrOfPlayers = Integer.parseInt(lines.get(0).split(" ")[0]);
        int maxValue = Integer.parseInt(lines.get(0).split(" ")[6]);
        return getHighscore(nrOfPlayers, maxValue);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day09().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day09().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
