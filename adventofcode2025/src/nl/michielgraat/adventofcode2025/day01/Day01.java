package nl.michielgraat.adventofcode2025.day01;

import java.util.List;

import nl.michielgraat.adventofcode2025.AocSolver;

public class Day01 extends AocSolver {

    protected Day01(final String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        int password = 0;
        int dial = 50;

        for (final String line : input) {
            final String direction = line.substring(0, 1);
            final int rotations = Integer.parseInt(line.substring(1));

            // The absolute value, so not adjusted to the allowed values of 0-99.
            int unadjusted = dial + (direction.equals("L") ? -rotations : rotations);

            if (unadjusted < 0) {
                if (dial > 0) {
                    // The dial before this specific instruction was above 0 and now we are below 0,
                    // so we must have passed 0. Add 1 to the password.
                    password++;
                }
                // We are below 0. If the unadjusted value is <= -100, add 1 extra to the
                // password for every hundred.
                password += Math.abs(unadjusted) / 100;
                // Adjust to correct dial values. The final mod 100 is for the case in which
                // unadjusted is -100. In that case 100 + (-100 % 100) = 100 + 0 = 100. To
                // adjust this to the values on the dial, we should mod 100 one final time.
                unadjusted = (100 + (unadjusted % 100)) % 100;
            } else if (unadjusted > 0) {
                // Add 1 to the password for every hundred.
                password += unadjusted / 100;
                // Adjust to allowed dial values.
                unadjusted = unadjusted % 100;
            } else if (unadjusted == 0) {
                // We are at 0, so add 1 to the password.
                password++;
            }
            dial = unadjusted;
        }

        return String.valueOf(password);
    }

    @Override
    protected String runPart1(final List<String> input) {
        int password = 0;
        int dial = 50;
        for (final String line : input) {
            final String direction = line.substring(0, 1);
            final int rotations = Integer.parseInt(line.substring(1));

            // Since we work with a scale of 100, it is not necessary to adjust the dial to
            // the allowed values 0-99. Just mod 100 and we will find the answer.
            dial += direction.equals("L") ? -rotations : rotations;
            if (dial % 100 == 0) {
                password++;
            }
        }
        return String.valueOf(password);
    }

    public static void main(final String... args) {
        new Day01("day01.txt");
    }
}
