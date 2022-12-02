package nl.michielgraat.adventofcode2022.day02;

import java.util.List;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day02 extends AocSolver {

    private static final int ROCK = 1;
    private static final int PAPER = 2;
    private static final int SCISSORS = 3;

    private static final String LOSE = "X";
    private static final String DRAW = "Y";
    private static final String WIN = "Z";

    protected Day02(final String filename) {
        super(filename);
    }

    private int getShape(final String input) {
        switch (input) {
            case "A":
            case "X":
                return ROCK;
            case "B":
            case "Y":
                return PAPER;
            case "C":
            case "Z":
                return SCISSORS;
            default:
                throw new IllegalArgumentException("Unknown shape '" + input + "'");
        }
    }

    private int getOutcomeScore(final int shape1, final int shape2) {
        switch (shape1) {
            case ROCK:
                if (shape2 == ROCK)
                    return 3;
                return shape2 == PAPER ? 6 : 0;
            case PAPER:
                if (shape2 == ROCK)
                    return 0;
                return shape2 == PAPER ? 3 : 6;
            case SCISSORS:
                if (shape2 == ROCK)
                    return 6;
                return shape2 == PAPER ? 0 : 3;
            default:
                throw new IllegalArgumentException(
                        "Cannot calculate outcome score for '" + shape1 + "' vs '" + shape2 + "'");
        }
    }

    private int findShape(final int shape1, final String outcome) {
        switch (outcome) {
            case LOSE:
                if (shape1 == ROCK)
                    return SCISSORS;
                return shape1 == PAPER ? ROCK : PAPER;
            case DRAW:
                return shape1;
            case WIN:
                if (shape1 == ROCK)
                    return PAPER;
                return shape1 == PAPER ? SCISSORS : ROCK;
            default:
                throw new IllegalArgumentException(
                        "Cannot find a shape which achieves '" + outcome + "' for input '" + shape1 + "'");
        }
    }

    private int getScore(final int shape1, final int shape2) {
        return shape2 + getOutcomeScore(shape1, shape2);
    }

    @Override
    protected String runPart2(final List<String> input) {
        int totalScore = 0;
        for (final String line : input) {
            final String[] parts = line.split(" ");
            final int shape1 = getShape(parts[0]);
            final int shape2 = findShape(shape1, parts[1]);
            totalScore += shape2 + getOutcomeScore(shape1, shape2);
        }
        return String.valueOf(totalScore);
    }

    @Override
    protected String runPart1(final List<String> input) {
        int totalScore = 0;
        for (final String line : input) {
            final String[] shapes = line.split(" ");
            totalScore += getScore(getShape(shapes[0]), getShape(shapes[1]));
        }
        return String.valueOf(totalScore);
    }

    public static void main(final String... args) {
        new Day02("day02.txt");
    }
}