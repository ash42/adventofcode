package nl.michielgraat.adventofcode2016.day08;

import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day08 {

    private static final String FILENAME = "day08.txt";
    private static final int WIDTH = 50;
    private static final int HEIGHT = 6;
    private int[][] grid = new int[WIDTH][HEIGHT];

    private int nrOfOnPixels() {
        int total = 0;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                total += grid[i][j];
            }
        }
        return total;
    }

    private void printGrid() {
        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                switch(grid[i][j]) {
                    case 0: System.out.print(" "); break;
                    default: System.out.print("*"); break;
                }
            }
            System.out.println();
        }
    }

    private void handleRotateColumn(String command) {
        //System.out.println(command);
        String arguments = command.substring(command.indexOf("=") + 1);
        int columnNr = Integer.parseInt(arguments.substring(0, arguments.indexOf(" ")));
        int nr = Integer.parseInt(arguments.substring(arguments.lastIndexOf(" ") + 1));
        int[] result = new int[HEIGHT];
        for (int i = 0; i < HEIGHT; i++) {
            int newIndex = (i + nr) % HEIGHT;
            result[newIndex] = grid[columnNr][i];
        }
        for (int i = 0; i < HEIGHT; i++) {
            grid[columnNr][i] = result[i];
        }
    }

    private void handleRotateRow(String command) {
        //System.out.println(command);
        String arguments = command.substring(command.indexOf("=") + 1);
        int rowNr = Integer.parseInt(arguments.substring(0, arguments.indexOf(" ")));
        int nr = Integer.parseInt(arguments.substring(arguments.lastIndexOf(" ") + 1));
        int[] result = new int[WIDTH];
        for (int i = 0; i < WIDTH; i++) {
            int newIndex = (i + nr) % WIDTH;
            result[newIndex] = grid[i][rowNr];
        }
        for (int i = 0; i < WIDTH; i++) {
            grid[i][rowNr] = result[i];
        }
    }

    private void handleRect(String command) {
        //System.out.println(command);
        String arguments = command.substring(command.indexOf(" ") + 1);
        int width = Integer.parseInt(arguments.substring(0, arguments.indexOf("x")));
        int height = Integer.parseInt(arguments.substring(arguments.indexOf("x") + 1));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = 1;
            }
        }
    }

    private void handleCommand(String command) {
        if (command.startsWith("rect")) {
            handleRect(command);
        } else if (command.startsWith("rotate row")) {
            handleRotateRow(command);
        } else {
            handleRotateColumn(command);
        }
    }

    private void runPart2(List<String> lines) {
        for (String line : lines) {
            handleCommand(line);
        }
        printGrid();
    }

    private int runPart1(List<String> lines) {
        for (String line : lines) {
            handleCommand(line);
        }
        return nrOfOnPixels();
    }

    public static void main(String... args) {
        List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day08().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        new Day08().runPart2(lines);
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }

}
