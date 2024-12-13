package nl.michielgraat.adventofcode2024.day13;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day13 extends AocSolver {

    protected Day13(final String filename) {
        super(filename);
    }

    private Equation parse(final List<String> input) {
        final String line1 = input.get(0);
        final String line2 = input.get(1);
        final String line3 = input.get(2);

        final int xa = Integer.parseInt(line1.split("X+")[1].split(",")[0]);
        final int ya = Integer.parseInt(line1.split("Y+")[1]);
        final int xb = Integer.parseInt(line2.split("X+")[1].split(",")[0]);
        final int yb = Integer.parseInt(line2.split("Y+")[1]);
        final int xPrize = Integer.parseInt(line3.split("X=")[1].split(",")[0]);
        final int yPrize = Integer.parseInt(line3.split("Y=")[1]);

        return new Equation(xa, xb, xPrize, ya, yb, yPrize);
    }

    private List<Equation> parseInput(final List<String> input) {
        final List<Equation> result = new ArrayList<>();
        List<String> current = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            final String line = input.get(i);
            if (line.isBlank()) {
                result.add(parse(current));
                current = new ArrayList<>();
            } else {
                current.add(input.get(i));
                if (i == input.size() - 1) {
                    result.add(parse(current));
                }
            }
        }
        return result;
    }

    private void gaussianElimination(final double[][] coefficients, final double rhs[]) {
        final int nrVariables = coefficients.length;
        for (int i = 0; i < nrVariables; i++) {
            // Select pivot
            final double pivot = coefficients[i][i];
            // Normalize row i
            for (int j = 0; j < nrVariables; j++) {
                coefficients[i][j] = coefficients[i][j] / pivot;
            }
            rhs[i] = rhs[i] / pivot;
            // Sweep using row i
            for (int k = 0; k < nrVariables; k++) {
                if (k != i) {
                    final double factor = coefficients[k][i];
                    for (int j = 0; j < nrVariables; j++) {
                        coefficients[k][j] = coefficients[k][j] - factor * coefficients[i][j];
                    }
                    rhs[k] = rhs[k] - factor * rhs[i];
                }
            }
        }
    }

    private long solveEquation(final Equation e, final long prizeIncrement) {
        final double[][] coefficients = new double[2][2];
        final double[] rhs = new double[2];
        final long xPrize = e.xPrize() + prizeIncrement;
        final long yPrize = e.yPrize() + prizeIncrement;
        coefficients[0][0] = e.xa();
        coefficients[0][1] = e.xb();
        coefficients[1][0] = e.ya();
        coefficients[1][1] = e.yb();
        rhs[0] = xPrize;
        rhs[1] = yPrize;
        gaussianElimination(coefficients, rhs);
        final long a = Math.round(rhs[0]);
        final long b = Math.round(rhs[1]);
        // Double-check after rounding
        if (a * e.xa() + b * e.xb() == xPrize && a * e.ya() + b * e.yb() == yPrize) {
            return a * 3 + b;
        } else {
            return 0;
        }
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Equation> equations = parseInput(input);
        return String.valueOf(equations.stream().mapToLong(e -> solveEquation(e, 10000000000000L)).sum());
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Equation> equations = parseInput(input);
        return String.valueOf(equations.stream().mapToLong(e -> solveEquation(e, 0)).sum());
    }

    public static void main(final String... args) {
        new Day13("day13.txt");
    }
}

record Equation(int xa, int xb, int xPrize, int ya, int yb, int yPrize) {
}