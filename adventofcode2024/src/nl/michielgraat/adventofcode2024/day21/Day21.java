package nl.michielgraat.adventofcode2024.day21;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day21 extends AocSolver {

    private static final char[][] numPad = {
            { '7', '8', '9' },
            { '4', '5', '6' },
            { '1', '2', '3' },
            { ' ', '0', 'A' }
    };

    private static final char[][] dirPad = {
            { ' ', '^', 'A' },
            { '<', 'v', '>' }
    };

    protected Day21(final String filename) {
        super(filename);
    }

    private Map<Move, String> generateNumPadMoves(final Map<Character, Position> positionMap) {
        final Map<Move, String> moves = new HashMap<>();

        for (final Entry<Character, Position> startEntry : positionMap.entrySet()) {
            for (final Entry<Character, Position> endEntry : positionMap.entrySet()) {
                if (startEntry.getKey() != ' ' && endEntry.getKey() != ' ') {
                    moves.put(new Move(startEntry.getKey(), endEntry.getKey()),
                            generatePadMove(startEntry, endEntry, true));
                }
            }
        }

        return moves;
    }

    private Map<Move, String> generateDirectionalPadMoves(final Map<Character, Position> positionMap) {
        final Map<Move, String> moves = new HashMap<>();
        for (final Entry<Character, Position> startEntry : positionMap.entrySet()) {
            for (final Entry<Character, Position> endEntry : positionMap.entrySet()) {
                if (startEntry.getKey() != ' ' && endEntry.getKey() != ' ') {
                    moves.put(new Move(startEntry.getKey(), endEntry.getKey()),
                            generatePadMove(startEntry, endEntry, false));
                }
            }
        }

        return moves;
    }

    private String generatePadMove(final Entry<Character, Position> startEntry,
            final Entry<Character, Position> endEntry,
            final boolean numPadMove) {
        final StringBuilder sb = new StringBuilder();
        final Position start = startEntry.getValue();
        final Position end = endEntry.getValue();
        final int diffX = end.x() - start.x();
        final int diffY = end.y() - start.y();

        if (numPadMove && start.y() == numPad.length - 1 && end.x() == 0) {
            // Bottom row, going to left, vertical first to avoid empty space
            sb.append(getVerticalMovement(diffY));
            sb.append(getHorizontalMovement(diffX));
            return sb.toString();
        } else if (numPadMove && start.x() == 0 && end.y() == numPad.length - 1) {
            // Left column, going to bottom row, right first to avoid empty space.
            sb.append(getHorizontalMovement(diffX));
            sb.append(getVerticalMovement(diffY));
            return sb.toString();
        } else if (!numPadMove && start.x() == 0) {
            // Directional pad, starting on <, go horizontal first to avoid empty space.
            sb.append(getHorizontalMovement(diffX));
            sb.append(getVerticalMovement(diffY));
            return sb.toString();
        } else if (!numPadMove && end.x() == 0) {
            // Directional pad, ending on <, go vertical first to avoid empty space.
            sb.append(getVerticalMovement(diffY));
            sb.append(getHorizontalMovement(diffX));
            return sb.toString();
        }

        if (diffY < 0 && diffX < 0) {
            // Going up/left, horizontal first.
            sb.append(getHorizontalMovement(diffX));
            sb.append(getVerticalMovement(diffY));
            return sb.toString();
        } else if (diffY > 0 && diffX < 0) {
            // Going down/left, horizontal first.
            sb.append(getHorizontalMovement(diffX));
            sb.append(getVerticalMovement(diffY));
            return sb.toString();
        } else if (diffY > 0 && diffX > 0) {
            // Going down/right, vertical first.
            sb.append(getVerticalMovement(diffY));
            sb.append(getHorizontalMovement(diffX));
            return sb.toString();
        } else if (diffY < 0 && diffX > 0) {
            // Going up/right, vertical first
            sb.append(getVerticalMovement(diffY));
            sb.append(getHorizontalMovement(diffX));
            return sb.toString();
        } else {
            // If all previous conditions do not apply, one of diffX/diffY == 0, so order
            // does not matter.
            sb.append(getHorizontalMovement(diffX));
            sb.append(getVerticalMovement(diffY));
            return sb.toString();
        }
    }

    private long countChars(final String code, final int depth, final Map<Move, String> dirPadMoves,
            final Map<Key, Long> memoMap) {
        if (depth == 0) {
            return code.length();
        }
        if (code.equals("A")) {
            return 1;
        }
        final Key key = new Key(code, depth);
        if (memoMap.containsKey(key)) {
            return memoMap.get(key);
        }

        long total = 0;
        for (final String move : code.split("A")) {
            // We process every move, which is defined by ending in an A
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= move.length(); i++) {
                final Move m = new Move(i == 0 ? 'A' : move.charAt(i - 1), i == move.length() ? 'A' : move.charAt(i));
                sb.append(dirPadMoves.get(m));
                sb.append("A");
            }
            total += countChars(sb.toString(), depth - 1, dirPadMoves, memoMap);
        }
        memoMap.put(key, total);
        return total;
    }

    private long getNrChars(final String code, final Map<Move, String> numPadMoves, final Map<Move, String> dirPadMoves,
            final Map<Key, Long> memoMap, final int depth) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < code.length(); i++) {
            final Move move = new Move(i == 0 ? 'A' : code.charAt(i - 1), code.charAt(i));
            sb.append(numPadMoves.get(move));
            sb.append("A");
        }
        final String start = sb.toString();

        return countChars(start, depth, dirPadMoves, memoMap);
    }

    private Map<Character, Position> getPositionMap(final char[][] pad) {
        final Map<Character, Position> map = new HashMap<>();
        for (int y = 0; y < pad.length; y++) {
            for (int x = 0; x < pad[y].length; x++) {
                map.put(pad[y][x], new Position(x, y));
            }
        }
        return map;
    }

    private String getHorizontalMovement(final int diffX) {
        String s = new String();
        if (diffX > 0) {
            for (int i = 0; i < diffX; i++) {
                s = s + (">");
            }
        } else if (diffX < 0) {
            for (int i = diffX; i < 0; i++) {
                s = s + ("<");
            }
        }
        return s;
    }

    private String getVerticalMovement(final int diffY) {
        String s = new String();
        if (diffY > 0) {
            for (int i = 0; i < diffY; i++) {
                s = s + ("v");
            }
        } else if (diffY < 0) {
            for (int i = diffY; i < 0; i++) {
                s = s + ("^");
            }
        }
        return s;
    }

    private int getNumber(final String s) {
        return Integer.parseInt(s.substring(0, s.length() - 1));
    }

    @Override
    protected String runPart2(final List<String> input) {
        final Map<Character, Position> numPadPositions = getPositionMap(numPad);
        final Map<Character, Position> dirPadPositions = getPositionMap(dirPad);
        final Map<Move, String> numPadMoves = generateNumPadMoves(numPadPositions);
        final Map<Move, String> dirPadMoves = generateDirectionalPadMoves(dirPadPositions);
        long total = 0;
        final Map<Key, Long> moveToLength = new HashMap<>();
        for (final String code : input) {
            final long nr = getNumber(code);
            total += nr * getNrChars(code, numPadMoves, dirPadMoves, moveToLength, 25);
        }

        return String.valueOf(total);
    }

    @Override
    protected String runPart1(final List<String> input) {
        final Map<Character, Position> numPadPositions = getPositionMap(numPad);
        final Map<Character, Position> dirPadPositions = getPositionMap(dirPad);
        final Map<Move, String> numPadMoves = generateNumPadMoves(numPadPositions);
        final Map<Move, String> dirPadMoves = generateDirectionalPadMoves(dirPadPositions);
        long total = 0;
        final Map<Key, Long> moveToLength = new HashMap<>();
        for (final String code : input) {
            final long nr = getNumber(code);
            total += nr * getNrChars(code, numPadMoves, dirPadMoves, moveToLength, 2);
        }

        return String.valueOf(total);
    }

    public static void main(final String... args) {
        new Day21("day21.txt");
    }
}

record Position(int x, int y) {
}

record Move(char start, char end) {
};

record Key(String code, int depth) {
};