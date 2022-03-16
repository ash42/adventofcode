package nl.michielgraat.adventofcode2017.day16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day16 {

    private static final String FILENAME = "day16.txt";

    private List<Move> getMoves(final String[] moves) {
        final List<Move> result = new ArrayList<>();
        for (final String move : moves) {
            if (move.startsWith("s")) {
                final Move i = new Move(MoveType.SPIN);
                i.setNr(Integer.parseInt(move.substring(1)));
                result.add(i);
            } else if (move.startsWith("x")) {
                final Move i = new Move(MoveType.EXCHANGE);
                i.setIdx1(Integer.parseInt(move.substring(1, move.indexOf("/"))));
                i.setIdx2(Integer.parseInt(move.substring(move.indexOf("/") + 1)));
                result.add(i);
            } else {
                final Move i = new Move(MoveType.PARTNER);
                i.setLet1(move.charAt(1));
                i.setLet2(move.charAt(3));
                result.add(i);
            }
        }
        return result;
    }

    private void dance(final List<Move> moves, final char[] chars) {
        for (final Move move : moves) {
            if (move.getType() == MoveType.SPIN) {
                for (int i = 0; i < move.getNr(); i++) {
                    final char last = chars[chars.length - 1];
                    for (int j = chars.length - 2; j >= 0; j--) {
                        chars[j + 1] = chars[j];
                    }
                    chars[0] = last;
                }
            } else if (move.getType() == MoveType.EXCHANGE) {
                final int i1 = move.getIdx1();
                final int i2 = move.getIdx2();
                final char temp = chars[i1];
                chars[i1] = chars[i2];
                chars[i2] = temp;
            } else {
                final String s = new String(chars);
                final int i1 = s.indexOf(move.getLet1());
                final int i2 = s.indexOf(move.getLet2());
                final char temp = chars[i1];
                chars[i1] = chars[i2];
                chars[i2] = temp;
            }
        }
    }

    public String runPart2(final List<String> lines) {
        final List<Move> moves = getMoves(lines.get(0).split(","));
        char[] chars = IntStream.rangeClosed(97, 112).mapToObj(c -> Character.toString((char) c))
                .collect(Collectors.joining()).toCharArray();
        final Map<String, String> io = new HashMap<>();
        for (int i = 0; i < 1000000000; i++) {
            final String pre = new String(chars);
            if (io.containsKey(pre)) {
                chars = io.get(pre).toCharArray();
            } else {
                dance(moves, chars);
                io.put(pre, new String(chars));
            }
        }
        return new String(chars);
    }

    public String runPart1(final List<String> lines) {
        final char[] chars = IntStream.rangeClosed(97, 112).mapToObj(c -> Character.toString((char) c))
                .collect(Collectors.joining()).toCharArray();
        final List<Move> moves = getMoves(lines.get(0).split(","));
        dance(moves, chars);
        return new String(chars);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day16().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day16().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}

class Move {
    private MoveType type;
    private int nr;
    private int idx1;
    private int idx2;
    private int let1;
    private int let2;

    public Move(final MoveType type) {
        this.type = type;
    }

    public MoveType getType() {
        return type;
    }

    public void setType(final MoveType type) {
        this.type = type;
    }

    public int getIdx1() {
        return idx1;
    }

    public void setIdx1(final int idx1) {
        this.idx1 = idx1;
    }

    public int getIdx2() {
        return idx2;
    }

    public void setIdx2(final int idx2) {
        this.idx2 = idx2;
    }

    public int getLet1() {
        return let1;
    }

    public void setLet1(final int let1) {
        this.let1 = let1;
    }

    public int getLet2() {
        return let2;
    }

    public void setLet2(final int let2) {
        this.let2 = let2;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(final int nr) {
        this.nr = nr;
    }

}

enum MoveType {
    SPIN,
    EXCHANGE,
    PARTNER;
}