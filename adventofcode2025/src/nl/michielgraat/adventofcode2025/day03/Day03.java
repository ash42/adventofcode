package nl.michielgraat.adventofcode2025.day03;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import nl.michielgraat.adventofcode2025.AocSolver;

public class Day03 extends AocSolver {

    protected Day03(String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        long totalJoltage = 0;
        for (String bank : input) {
            int[] batteries = Arrays.stream(bank.split("")).mapToInt(Integer::parseInt).toArray();
            int startIdx = 0;
            int power = 11;
            int maxIdx = batteries.length - power;
            while (power >= 0) {
                // We first select the range of the numbers array in which we search for the
                // next battery with the highest value. At first, because we are looking for 12
                // batteries, this is from the start of the bank until bank.length - 11.
                //
                // After the first iteration the startIdx is defined as the index of the battery
                // found in the previous iteration + 1. The maximum index is defined as
                // bank.length - (remaining length of number + 1).
                int[] sub = Arrays.copyOfRange(batteries, startIdx, maxIdx);
                Arrays.sort(sub);
                int greatest = sub[sub.length - 1];

                totalJoltage += (Math.pow(10, power) * greatest);

                startIdx = IntStream.range(startIdx, batteries.length).filter(i -> batteries[i] == greatest).findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No greatest found?!")) + 1;
                power--;
                maxIdx = batteries.length - power;
            }
        }
        return String.valueOf(totalJoltage);
    }

    @Override
    protected String runPart1(final List<String> input) {
        int totalJoltage = 0;
        for (String bank : input) {
            // Convert the bank to an integer array.
            int[] converted = Arrays.stream(bank.substring(0, bank.length()).split("")).mapToInt(Integer::parseInt)
                    .toArray();
            // Now get all batteries, but omit the last one (because that may be the battery
            // with the highest joltage but there is no battery after the last one).
            int[] batteries = Arrays.copyOf(converted, converted.length - 1);
            // Sort the array and get the battery with the highest joltage
            Arrays.sort(batteries);
            int highest = batteries[batteries.length - 1];

            // Now get the integer array of all batteries after the battery with the highest
            // joltage...
            int index = IntStream.range(0, converted.length).filter(i -> converted[i] == highest).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No highest found?!"));
            int[] remainingBatteries = Arrays.copyOfRange(converted, index + 1, converted.length);
            // ...and pick the battery with the highest joltage from that.
            Arrays.sort(remainingBatteries);
            int second = remainingBatteries[remainingBatteries.length - 1];
            totalJoltage += 10 * highest + second;
        }
        return String.valueOf(totalJoltage);
    }

    public static void main(String... args) {
        new Day03("day03.txt");
    }
}
