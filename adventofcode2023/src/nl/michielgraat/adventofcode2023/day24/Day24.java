package nl.michielgraat.adventofcode2023.day24;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day24 extends AocSolver {

    protected Day24(String filename) {
        super(filename);
    }

    private List<Line> readLines(final List<String> input) {
        List<Line> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(
                "(-{0,1}(?!0)\\d+),\\s+(-{0,1}(?!0)\\d+),\\s+(-{0,1}(?!0)\\d+)\\s+@\\s+(-{0,1}(?!0)\\d+),\\s+(-{0,1}(?!0)\\d+),\\s+(-{0,1}(?!0)\\d+)");
        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            Line l = new Line(Long.valueOf(matcher.group(1)), Long.valueOf(matcher.group(2)),
                    Long.valueOf(matcher.group(3)), Long.valueOf(matcher.group(4)),
                    Long.valueOf(matcher.group(5)), Long.valueOf(matcher.group(6)));
            result.add(l);
        }
        return result;
    }

    private int getNrCollisionsIn(long start, long end, List<Line> lines) {
        int total = 0;
        for (int i = 0; i < lines.size() - 1; i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                Line l1 = lines.get(i);
                Line l2 = lines.get(j);
                Coordinate c = l1.crossesAt(l2);
                // Could condense the logic below into one if-statement to get the collision
                // inside the test area, but this is a bit more clear I think.
                if ((l1.velX() > 0 && c.x() < l1.x()) || (l1.velX() < 0 && c.x() > l1.x())
                        || (l1.velY() > 0 && c.y() < l1.y()) || (l1.velY() < 0 && c.y() > l1.y())) {
                    // Hailstones crossed in the past for hailstone A
                } else if ((l2.velX() > 0 && c.x() < l2.x()) || (l2.velX() < 0 && c.x() > l2.x())
                        || (l2.velY() > 0 && c.y() < l2.y()) || (l2.velY() < 0 && c.y() > l2.y())) {
                    // Hailstones crossed in the past for hailstone B
                } else if (c.x() >= start && c.x() <= end && c.y() >= start && c.y() <= end) {
                    total++;
                } else {
                    // Hailstones' paths will cross outside the test area
                }
            }
        }
        return total;
    }

    private void gaussianElimination(double[][] coefficients, double rhs[]) {
        int nrVariables = coefficients.length;
        for (int i = 0; i < nrVariables; i++) {
            // Select pivot
            double pivot = coefficients[i][i];
            // Normalize row i
            for (int j = 0; j < nrVariables; j++) {
                coefficients[i][j] = coefficients[i][j] / pivot;
            }
            rhs[i] = rhs[i] / pivot;
            // Sweep using row i
            for (int k = 0; k < nrVariables; k++) {
                if (k != i) {
                    double factor = coefficients[k][i];
                    for (int j = 0; j < nrVariables; j++) {
                        coefficients[k][j] = coefficients[k][j] - factor * coefficients[i][j];
                    }
                    rhs[k] = rhs[k] - factor * rhs[i];
                }
            }
        }

    }

    @Override
    protected String runPart2(final List<String> input) {
        // We define our desired rock as X,Y,Z,VX,VY,VZ.
        // Now lets look at X.
        // If we take any hailstone x,y,z,vx,vy,vz, then (t = nanosecond) X + tVX = x +
        // tvx.
        // We can rewrite this as t = (X-x)/(vx-VX).
        //
        // The same holds for y and z, so:
        // (X-x)/(vx-VX) = (Y-y)/(vy-VY) = (Z-z)/(vz-VZ)
        //
        // Lets rewrite that a bit:
        // (X-x)/(vx-VX) = (Y-y)/(vy-VY)
        // (X-x)(vy-VY) = (Y-y)(vx-VX)
        // Xvy - XVY - xvy + xVY = Yvx - YVX - yvx + yVX
        // YVX - XVY = -Xvy + xvy - xVY + Yvx - yvx + yVX
        //
        // YVX - XVY is the same for each hailstone (as these variables are defined by
        // our desired position).
        //
        // Now take another hailstone x',y',z',vx',vy',vz'
        // Then:
        // YVX - XVY = -Xvy' + x'vy' - x'VY + Yvx' - y'vx' + y'VX
        //
        // And (as we said YVX - VXY is the same for all haildstones):
        //
        // -Xvy + xvy - xVY + Yvx - yvx + yVX = -Xvy' + x'vy' - x'VY + Yvx' - y'vx' +
        // y'VX
        //
        // Or (put into order of X,Y,VX,VY):
        //
        // (vy'-vy)X + (vx-vx')Y + (y-y')VX + (x'-x)VY = - xvy + yvx + x'vy' - y'vx'
        //
        // Everything except for X,Y,VX,VY is known, so we have a linear equation with 4
        // variables. We can solve this with Gaussian elimination using a couple of
        // hailstones.
        //
        // For z:
        //
        // (vz'-vz)X + (vx-vx')Z + (z-z')VX + (x'-x)VZ = - xvz + zvx + x'vz' - z'vx'
        //
        // Or, if you already know X and VX from part solving the equation above:
        //
        // (vx-vx')Z + (x'-x)VZ = - xvz + zvx + x'vz' - z'vx' - (vz'-vz)X - (z-z')VX

        List<Line> lines = readLines(input);
        double coefficients[][] = new double[4][4];
        double rhs[] = new double[4];

        // Get X,Y,VX,VY
        for (int i = 0; i < 4; i++) {
            Line l1 = lines.get(i);
            Line l2 = lines.get(i + 1);
            coefficients[i][0] = l2.velY() - l1.velY();
            coefficients[i][1] = l1.velX() - l2.velX();
            coefficients[i][2] = l1.y() - l2.y();
            coefficients[i][3] = l2.x() - l1.x();
            rhs[i] = -l1.x() * l1.velY() + l1.y() * l1.velX() + l2.x() * l2.velY() - l2.y() * l2.velX();
        }

        gaussianElimination(coefficients, rhs);

        long x = Math.round(rhs[0]);
        long y = Math.round(rhs[1]);
        long vx = Math.round(rhs[2]);
        long vy = Math.round(rhs[3]);

        // Get X,VZ
        coefficients = new double[2][2];
        rhs = new double[2];
        for (int i = 0; i < 2; i++) {
            Line l1 = lines.get(i);
            Line l2 = lines.get(i + 1);
            coefficients[i][0] = l1.velX() - l2.velX();
            coefficients[i][1] = l2.x() - l1.x();
            rhs[i] = -l1.x() * l1.velZ() + l1.z() * l1.velX() + l2.x() * l2.velZ() - l2.z() * l2.velX()
                    - ((l2.velZ() - l1.velZ()) * x) - ((l1.z() - l2.z()) * vx);
        }

        gaussianElimination(coefficients, rhs);

        long z = Math.round(rhs[0]);
        long vz = Math.round(rhs[1]);

        Line rock = new Line(x, y, z, vx, vy, vz);

        return String.valueOf(rock.x() + rock.y() + rock.z());
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getNrCollisionsIn(200000000000000L, 400000000000000L, readLines(input)));
    }

    public static void main(String... args) {
        new Day24("day24.txt");
    }
}
