package nl.michielgraat.adventofcode2019.day25;

import java.util.List;
import java.util.Scanner;

import nl.michielgraat.adventofcode2019.AocSolver;
import nl.michielgraat.adventofcode2019.intcode.IntcodeComputer;

public class Day25 extends AocSolver {

    protected Day25(String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        return "none";
    }

    /**
     * I solved this by playing it like the little adventure game it is.
     * 
     * First I explored (and drew) the map, and then tried to find the right
     * combination of items to pass the pressure sensor.
     * 
     * Main logic there: find the one item that is so heavy you can not pass without
     * it. In my case this was the tambourine. After that, lose all items which, in
     * combination with the first item, are too heavy to pass. Repeat until you get
     * the right combo.
     * 
     * In my case:
     * 
     * tambourine/easter egg/space heater/fixed point
     */
    @Override
    protected String runPart1(final List<String> input) {
        Scanner scanner = new Scanner(System.in);
        IntcodeComputer droid = new IntcodeComputer(input);
        droid.run();
        System.out.println(droid.readAllOutputAscii());
        while (scanner.hasNextLine()) {
            String userInput = scanner.nextLine();
            if (userInput.equals("exit") || userInput.equals("quit")) {
                break;
            }
            if (userInput.equals("n")) {
                userInput = "north";
            }
            if (userInput.equals("s")) {
                userInput = "south";
            }
            if (userInput.equals("e")) {
                userInput = "east";
            }
            if (userInput.equals("w")) {
                userInput = "west";
            }
            droid.addAsciiInput(userInput);
            droid.run();
            System.out.println(droid.readAllOutputAscii());
        }
        scanner.close();
        return "";
    }

    public static void main(String... args) {
        new Day25("day25.txt");
    }
}
