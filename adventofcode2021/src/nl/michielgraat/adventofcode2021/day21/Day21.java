package nl.michielgraat.adventofcode2021.day21;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day21 {

    private static final String FILENAME = "day21.txt";

    private final int[] diracDie = { 1, 2, 3 };
    private final int[][] combos = Arrays.stream(diracDie).boxed()
            .flatMap(a -> Arrays.stream(diracDie).boxed()
                    .flatMap(b -> Arrays.stream(diracDie).boxed().map(c -> new int[] { a, b, c })))
            .toArray(int[][]::new);
    private final Map<Input, long[]> cache = new HashMap<>();

    private long[] playRound(final int curPos, final int curScore, final int otherPos, final int otherScore) {
        if (curScore >= 21) {
            return new long[] { 1L, 0 };
        }
        if (otherScore >= 21) {
            return new long[] { 0, 1L };
        }
        final long[] result = new long[] { 0, 0 };
        for (final int[] combo : combos) {
            int newPos = (curPos + combo[0] + combo[1] + combo[2]) % 10;
            if (newPos == 0)
                newPos = 10;
            final int newScore = curScore + newPos;

            // In the next round the other player becomes the current player (i.e. the
            // player whose turn it is), so flip the input.
            final Input key = new Input(otherPos, otherScore, newPos, newScore);
            final boolean inCache = cache.keySet().contains(key);
            final long[] recursiveResult = (!inCache) ? playRound(otherPos, otherScore, newPos, newScore) : cache.get(key);
            if (!inCache)
                cache.put(key, recursiveResult);
            result[0] = result[0] + recursiveResult[1];
            result[1] = result[1] + recursiveResult[0];

        }
        return result;
    }

    private int runGame(final int startP1, final int startP2) {
        int nrOfRolls = 0;
        final Player p1 = new Player(startP1);
        final Player p2 = new Player(startP2);
        final Die die = new Die();
        while (true) {
            p1.rollThreeTimes(die);
            nrOfRolls += 3;
            if (p1.score >= 1000) {
                return p2.score * nrOfRolls;
            }
            p2.rollThreeTimes(die);
            nrOfRolls += 3;
            if (p2.score >= 1000) {
                return p1.score * nrOfRolls;
            }
        }
    }

    private long runPart2(final List<String> lines) {
        final int startP1 = Integer.parseInt(lines.get(0).substring(lines.get(0).indexOf(":") + 2));
        final int startP2 = Integer.parseInt(lines.get(1).substring(lines.get(1).indexOf(":") + 2));
        final long[] result = playRound(startP1, 0, startP2, 0);
        return Math.max(result[0], result[1]);
    }

    private int runPart1(final List<String> lines) {
        final int startP1 = Integer.parseInt(lines.get(0).substring(lines.get(0).indexOf(":") + 2));
        final int startP2 = Integer.parseInt(lines.get(1).substring(lines.get(1).indexOf(":") + 2));
        return runGame(startP1, startP2);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day21().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day21().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}

class Input {
    int currentPos;
    int curScore;
    int otherPos;
    int otherScore;

    public Input(final int p1pos, final int p1score, final int p2pos, final int p2score) {
        this.currentPos = p1pos;
        this.curScore = p1score;
        this.otherPos = p2pos;
        this.otherScore = p2score;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + currentPos;
        result = prime * result + curScore;
        result = prime * result + otherPos;
        result = prime * result + otherScore;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Input other = (Input) obj;
        if (currentPos != other.currentPos)
            return false;
        if (curScore != other.curScore)
            return false;
        if (otherPos != other.otherPos)
            return false;
        if (otherScore != other.otherScore)
            return false;
        return true;
    }

}

class Player {
    long pos = 0;
    long nrOfRolls = 0;
    int score = 0;

    Player(final long val) {
        pos = val;
    }

    void rollThreeTimes(final Die d) {
        for (int i = 1; i <= 3; i++) {
            d.incr();
            final int incr = d.val;
            pos = (pos + incr) % 10;
            if (pos == 0) {
                pos = 10;
            }
        }
        score += pos;
    }
}

class Die {
    int val = 0;

    void incr() {
        val++;
        if (val > 100) {
            val %= 100;
        }
    }
}