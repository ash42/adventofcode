package nl.michielgraat.adventofcode2018.day03;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day03 {

    private static final String FILENAME = "day03.txt";

    private static final int UNCLAIMED = 0;
    private static final int CLAIMED = 1;
    private static final int DOUBLE_CLAIMED = 2;

    private final int[][] fabric = new int[1000][1000];
    private final String[][] claims = new String[1000][1000];

    public Day03() {
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                fabric[i][j] = UNCLAIMED;
                claims[i][j] = "unclaimed";
            }
        }
    }

    private void claim(final int startx, final int starty, final int width, final int height, final String claimant) {
        for (int x = startx; x < startx + width; x++) {
            for (int y = starty; y < starty + height; y++) {
                if (fabric[x][y] == UNCLAIMED) {
                    fabric[x][y] = CLAIMED;
                    claims[x][y] = claimant;
                } else {
                    fabric[x][y] = DOUBLE_CLAIMED;
                    claims[x][y] += "," + claimant;
                }
            }
        }
    }

    private int doubleClaimedInches() {
        int total = 0;
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                if (fabric[x][y] == DOUBLE_CLAIMED) {
                    total++;
                }
            }
        }
        return total;
    }

    private String getNonOverlappingClaim(final List<String> claimants) {
        for (final String claimant : claimants) {
            boolean overlapping = false;
            for (int x = 0; x < 1000; x++) {
                for (int y = 0; y < 1000; y++) {
                    if (!claims[x][y].equals("unclaimed") && claims[x][y].contains(",")) {
                        final String[] cs = claims[x][y].split(",");
                        for (final String c : cs) {
                            if (c.equals(claimant)) {
                                overlapping = true;
                                break;
                            }
                        }
                    }
                }
                if (overlapping) {
                    break;
                }
            }
            if (!overlapping) {
                return claimant;
            }
        }
        return "none";
    }

    private String getClaimant(final String s) {
        return s.substring(1, s.indexOf("@") - 1);
    }

    private int getStartx(final String s) {
        return Integer.parseInt(s.substring(s.indexOf("@") + 2, s.indexOf(",")));
    }

    private int getStarty(final String s) {
        return Integer.parseInt(s.substring(s.indexOf(",") + 1, s.indexOf(":")));
    }

    private int getWidth(final String s) {
        return Integer.parseInt(s.substring(s.indexOf(":") + 2, s.indexOf("x")));
    }

    private int getHeight(final String s) {
        return Integer.parseInt(s.substring(s.indexOf("x") + 1).trim());
    }

    public String runPart2(final List<String> lines) {
        final List<String> claimants = new ArrayList<>();
        for (final String line : lines) {
            final String claimant = getClaimant(line);
            claimants.add(claimant);
            claim(getStartx(line), getStarty(line), getWidth(line), getHeight(line), claimant);
        }
        return getNonOverlappingClaim(claimants);
    }

    public int runPart1(final List<String> lines) {
        for (final String line : lines) {
            final String claimant = getClaimant(line);
            claim(getStartx(line), getStarty(line), getWidth(line), getHeight(line), claimant);
        }
        return doubleClaimedInches();
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day03().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day03().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
