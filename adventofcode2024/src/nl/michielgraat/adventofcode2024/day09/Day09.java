package nl.michielgraat.adventofcode2024.day09;

import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day09 extends AocSolver {

    protected Day09(final String filename) {
        super(filename);
    }

    private int[] getSizeAndEmptyBlocks(final String line) {
        final int[] result = new int[2];
        int size = 0;
        int nrEmptyBlocks = 0;
        for (int i = 0; i < line.length(); i++) {
            final int current = line.charAt(i) - '0';
            size += current;
            if (i % 2 != 0) {
                nrEmptyBlocks += current;
            }
        }
        result[0] = size;
        result[1] = nrEmptyBlocks;
        return result;
    }

    private int[] buildBlocks(final String line, final int size) {
        final int[] blocks = new int[size];
        int processed = 0;
        int curNr = 0;
        for (int i = 0; i < line.length(); i++) {
            final int current = line.charAt(i) - '0';
            int nr = 0;
            for (int j = processed; j < processed + current; j++) {
                if (i % 2 == 0) {
                    blocks[j] = curNr;
                } else {
                    blocks[j] = -1;
                }
                nr++;
            }
            processed += nr;
            if (i % 2 == 0) {
                curNr++;
            }

        }
        return blocks;
    }

    private void moveBlocks(final int nrEmptyBlocks, final int[] blocks) {
        int endIdx = blocks.length - 1;
        int nrProcessed = 0;
        for (int j = 0; endIdx > j && nrProcessed < nrEmptyBlocks; j++) {
            if (blocks[j] == -1) {
                while (blocks[endIdx] == -1) {
                    endIdx--;
                }
                if (endIdx < j) {
                    break;
                } else {
                    blocks[j] = blocks[endIdx];
                    blocks[endIdx] = -1;
                    endIdx--;
                    nrProcessed++;
                }
            }
        }
    }

    private void moveFiles(final int[] blocks, final String line, final TreeMap<Integer, Integer> fileSizes,
            TreeMap<Integer, Integer> emptyBlockSizes, final TreeMap<Integer, Integer> fileLocations,
            TreeMap<Integer, Integer> emptySpaceLocations) {
        for (int fileIdx = fileSizes.firstKey(); fileIdx >= 0; fileIdx--) {
            int emptySpaceIdx = 0;
            final int curFileSize = fileSizes.get(fileIdx);
            int curEmptyBlockSize = emptyBlockSizes.get(emptySpaceIdx);

            boolean stop = false;
            while (curFileSize > curEmptyBlockSize) {
                emptySpaceIdx++;
                if (emptySpaceIdx >= emptyBlockSizes.size()) {
                    stop = true;
                    break;
                } else {
                    curEmptyBlockSize = emptyBlockSizes.get(emptySpaceIdx);
                }
            }
            if (!stop) {
                final int fileLocation = fileLocations.get(fileIdx);
                final int emptySpaceLocation = emptySpaceLocations.get(emptySpaceIdx);
                if (curFileSize <= curEmptyBlockSize && emptySpaceLocation < fileLocation) {
                    for (int i = fileLocation; i < fileLocation + curFileSize; i++) {
                        blocks[i] = -1;
                    }
                    for (int i = emptySpaceLocation; i < emptySpaceLocation + curFileSize; i++) {
                        blocks[i] = fileIdx;
                    }
                    emptyBlockSizes = getEmptyBlockSizes(blocks);
                    emptySpaceLocations = getEmptySpaceLocations(blocks);
                }
            }
        }
    }

    private TreeMap<Integer, Integer> getEmptySpaceLocations(final int[] blocks) {
        final TreeMap<Integer, Integer> map = new TreeMap<>();
        int nr = 0;
        for (int i = 0; i < blocks.length; i++) {
            int current = blocks[i];
            if (current == -1) {
                map.put(nr++, i);
                while (current == -1) {
                    i++;
                    if (i >= blocks.length) {
                        break;
                    }
                    current = blocks[i];
                }
            }
        }
        return map;
    }

    private TreeMap<Integer, Integer> getFileLocations(final int[] blocks) {
        final TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        int nr = 0;
        for (int i = 0; i < blocks.length; i++) {
            int current = blocks[i];
            final int prev = current;
            if (current != -1) {
                map.put(nr++, i);
                while (current != -1 && current == prev) {
                    i++;
                    if (i >= blocks.length) {
                        break;
                    }
                    current = blocks[i];
                }
                if (current != prev) {
                    i--;
                }
            }
        }
        return map;
    }

    private TreeMap<Integer, Integer> getFileSizes(final String line) {
        final TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        int nr = 0;
        for (int i = 0; i < line.length(); i++) {
            if (i % 2 == 0) {
                map.put(nr++, line.charAt(i) - '0');
            }
        }
        return map;
    }

    private TreeMap<Integer, Integer> getEmptyBlockSizes(final int[] blocks) {
        final TreeMap<Integer, Integer> map = new TreeMap<>();
        int nr = 0;
        for (int i = 0; i < blocks.length; i++) {
            int current = blocks[i];
            if (current == -1) {
                int size = 1;
                while (current == -1) {
                    i++;
                    size++;
                    if (i >= blocks.length) {
                        map.put(nr, size - 1);
                        break;
                    }
                    current = blocks[i];
                }
                map.put(nr++, size - 1);
            }
        }
        return map;
    }

    private long calculateChecksum(final int[] blocks) {
        long checksum = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != -1) {
                checksum += blocks[i] * i;
            }
        }
        return checksum;
    }

    @Override
    protected String runPart2(final List<String> input) {
        final String line = input.get(0);
        final int[] sizeAndEmptyBlocks = getSizeAndEmptyBlocks(line);
        final int[] blocks = buildBlocks(line, sizeAndEmptyBlocks[0]);
        moveFiles(blocks, line, getFileSizes(line), getEmptyBlockSizes(blocks), getFileLocations(blocks),
                getEmptySpaceLocations(blocks));
        final long checksum = calculateChecksum(blocks);
        return String.valueOf(checksum);
    }

    @Override
    protected String runPart1(final List<String> input) {
        final String line = input.get(0);
        final int[] sizeAndEmptyBlocks = getSizeAndEmptyBlocks(line);
        final int[] blocks = buildBlocks(line, sizeAndEmptyBlocks[0]);
        moveBlocks(sizeAndEmptyBlocks[1], blocks);
        final long checksum = calculateChecksum(blocks);
        return String.valueOf(checksum);
    }

    public static void main(final String... args) {
        new Day09("day09.txt");
    }
}
