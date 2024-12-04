package nl.michielgraat.adventofcode2024.day04;

import java.util.List;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day04 extends AocSolver {

    protected Day04(final String filename) {
        super(filename);
    }

    private char[][] getWordSearch(final List<String> input) {
        final char[][] wordSearch = new char[input.get(0).length()][input.size()];
        for (int y = 0; y < input.size(); y++) {
            final String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                wordSearch[y][x] = line.charAt(x);
            }
        }
        return wordSearch;
    }

    private boolean westOk(final int x, final int y, final char[][] wordSearch) {
        return x >= 3 && wordSearch[y][x - 1] == 'M' && wordSearch[y][x - 2] == 'A'
                && wordSearch[y][x - 3] == 'S';
    }

    private boolean nwOk(final int x, final int y, final char[][] wordSearch) {
        return x >= 3 && y >= 3 && wordSearch[y - 1][x - 1] == 'M' && wordSearch[y - 2][x - 2] == 'A'
                && wordSearch[y - 3][x - 3] == 'S';
    }

    private boolean northOk(final int x, final int y, final char[][] wordSearch) {
        return y >= 3 && wordSearch[y - 1][x] == 'M' && wordSearch[y - 2][x] == 'A'
                && wordSearch[y - 3][x] == 'S';
    }

    private boolean neOk(final int x, final int y, final char[][] wordSearch) {
        return x < wordSearch[0].length - 3 && y >= 3 && wordSearch[y - 1][x + 1] == 'M'
                && wordSearch[y - 2][x + 2] == 'A' && wordSearch[y - 3][x + 3] == 'S';
    }

    private boolean eastOk(final int x, final int y, final char[][] wordSearch) {
        return x < wordSearch[0].length - 3 && wordSearch[y][x + 1] == 'M' && wordSearch[y][x + 2] == 'A'
                && wordSearch[y][x + 3] == 'S';
    }

    private boolean seOk(final int x, final int y, final char[][] wordSearch) {
        return x < wordSearch[0].length - 3 && y < wordSearch.length - 3 && wordSearch[y + 1][x + 1] == 'M'
                && wordSearch[y + 2][x + 2] == 'A' && wordSearch[y + 3][x + 3] == 'S';
    }

    private boolean southOk(final int x, final int y, final char[][] wordSearch) {
        return y < wordSearch.length - 3 && wordSearch[y + 1][x] == 'M' && wordSearch[y + 2][x] == 'A'
                && wordSearch[y + 3][x] == 'S';
    }

    private boolean swOk(final int x, final int y, final char[][] wordSearch) {
        return x >= 3 && y < wordSearch.length - 3 && wordSearch[y + 1][x - 1] == 'M'
                && wordSearch[y + 2][x - 2] == 'A' && wordSearch[y + 3][x - 3] == 'S';
    }

    private int findNrXmas(final char[][] wordSearch) {
        int nrXmas = 0;
        for (int y = 0; y < wordSearch.length; y++) {
            for (int x = 0; x < wordSearch[y].length; x++) {
                if (wordSearch[y][x] == 'X') {
                    if (westOk(x, y, wordSearch))
                        nrXmas++;
                    if (nwOk(x, y, wordSearch))
                        nrXmas++;
                    if (northOk(x, y, wordSearch))
                        nrXmas++;
                    if (neOk(x, y, wordSearch))
                        nrXmas++;
                    if (eastOk(x, y, wordSearch))
                        nrXmas++;
                    if (seOk(x, y, wordSearch))
                        nrXmas++;
                    if (southOk(x, y, wordSearch))
                        nrXmas++;
                    if (swOk(x, y, wordSearch))
                        nrXmas++;
                }
            }
        }
        return nrXmas;
    }

    private int findNrXmasShape(final char[][] wordSearch) {
        int nrXmas = 0;
        for (int y = 1; y < wordSearch.length - 1; y++) {
            for (int x = 1; x < wordSearch[y].length - 1; x++) {
                if (wordSearch[y][x] == 'A' && wordSearch[y - 1][x - 1] == 'M' && wordSearch[y - 1][x + 1] == 'S'
                        && wordSearch[y + 1][x - 1] == 'M' && wordSearch[y + 1][x + 1] == 'S') {
                    nrXmas++;
                }
            }
        }
        return nrXmas;
    }

    private char[][] rotate(final char[][] input) {
        final char[][] output = new char[input.length][input[0].length];
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length; x++) {
                output[input.length - 1 - y][x] = input[x][y];
            }
        }
        return output;
    }

    @Override
    protected String runPart2(final List<String> input) {
        char[][] wordSearch = getWordSearch(input);

        int total = findNrXmasShape(wordSearch);
        wordSearch = rotate(wordSearch);
        total += findNrXmasShape(wordSearch);
        wordSearch = rotate(wordSearch);
        total += findNrXmasShape(wordSearch);
        wordSearch = rotate(wordSearch);
        total += findNrXmasShape(wordSearch);

        return String.valueOf(total);
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(findNrXmas(getWordSearch(input)));
    }

    public static void main(final String... args) {
        new Day04("day04.txt");
    }
}
