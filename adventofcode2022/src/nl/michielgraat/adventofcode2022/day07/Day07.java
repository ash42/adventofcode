package nl.michielgraat.adventofcode2022.day07;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day07 extends AocSolver {

    protected Day07(final String filename) {
        super(filename);
    }

    private Directory handleCd(final String command, Directory currentDirectory) {
        final String parameter = command.split(" ")[2];
        if ("/".equals(parameter)) {
            currentDirectory = currentDirectory.getRoot();
        } else if ("..".equals(parameter)) {
            currentDirectory = currentDirectory.moveOut();
        } else {
            currentDirectory = currentDirectory.moveIn(parameter);
        }
        return currentDirectory;
    }

    private Directory buildDirectoryTree(final List<String> input) {
        Directory currentDirectory = new Directory("/", null);
        for (final String line : input) {
            if (line.startsWith("$")) {
                if (line.startsWith("$ cd")) {
                    currentDirectory = handleCd(line, currentDirectory);
                }
            } else if (line.startsWith("dir")) {
                currentDirectory.addDirectory(line.split(" ")[1]);
            } else {
                currentDirectory.addFile(line.split(" ")[1], Integer.parseInt(line.split(" ")[0]));
            }
        }
        return currentDirectory.getRoot();
    }

    private List<Directory> getDirectoriesWithMaxSize(final Directory start, final int size) {
        final List<Directory> result = new ArrayList<>();
        if (start.getSize() <= size) {
            result.add(start);
        }
        for (final Directory subDirectory : start.getSubDirectories()) {
            result.addAll(getDirectoriesWithMaxSize(subDirectory, size));
        }
        return result;
    }

    private List<Directory> getDirectoriesWithMinSize(final Directory start, final int size) {
        final List<Directory> result = new ArrayList<>();
        if (start.getSize() >= size) {
            result.add(start);
        }
        for (final Directory subDirectory : start.getSubDirectories()) {
            result.addAll(getDirectoriesWithMinSize(subDirectory, size));
        }
        return result;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final Directory root = buildDirectoryTree(input);
        final int minSizeToFree = 30000000 - (70000000 - root.getSize());
        return String.valueOf(
                getDirectoriesWithMinSize(root, minSizeToFree).stream().mapToInt(Directory::getSize).min().getAsInt());
    }

    @Override
    protected String runPart1(final List<String> input) {
        final Directory root = buildDirectoryTree(input);
        return String.valueOf(getDirectoriesWithMaxSize(root, 100000).stream().mapToInt(Directory::getSize).sum());
    }

    public static void main(final String... args) {
        new Day07("day07.txt");
    }
}
