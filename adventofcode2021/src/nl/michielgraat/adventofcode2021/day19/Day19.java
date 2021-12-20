package nl.michielgraat.adventofcode2021.day19;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day19 {

    private static final String FILENAME = "day19.txt";

    private List<Scanner> parseScanners(final List<String> lines) {
        final List<Scanner> scanners = new ArrayList<>();
        List<String> beacons = new ArrayList<>();
        int nr = -1;
        for (final String line : lines) {
            if (line.startsWith("---")) {
                nr = Integer.parseInt(line.substring(line.indexOf("r") + 2, line.lastIndexOf(" ")));
            } else if (line.isBlank()) {
                scanners.add(new Scanner(nr, beacons));
                beacons = new ArrayList<>();
            } else {
                beacons.add(line);
            }
        }
        scanners.add(new Scanner(nr, beacons));
        return scanners;
    }

    private int[][] getAllRotations(final int[] point) {
        final int[][] rotations = new int[24][3];
        // x > 0
        rotations[0] = new int[] { point[0], point[1], point[2] };
        rotations[1] = new int[] { point[0], -point[2], point[1] };
        rotations[2] = new int[] { point[0], -point[1], -point[2] };
        rotations[3] = new int[] { point[0], point[2], -point[1] };
        // x < 0
        rotations[4] = new int[] { -point[0], -point[1], point[2] };
        rotations[5] = new int[] { -point[0], point[2], point[1] };
        rotations[6] = new int[] { -point[0], point[1], -point[2] };
        rotations[7] = new int[] { -point[0], -point[2], -point[1] };
        // y > 0
        rotations[8] = new int[] { point[1], point[2], point[0] };
        rotations[9] = new int[] { point[1], -point[0], point[2] };
        rotations[10] = new int[] { point[1], -point[2], -point[0] };
        rotations[11] = new int[] { point[1], point[0], -point[2] };
        // y < 0
        rotations[12] = new int[] { -point[1], -point[2], point[0] };
        rotations[13] = new int[] { -point[1], point[0], point[2] };
        rotations[14] = new int[] { -point[1], point[2], -point[0] };
        rotations[15] = new int[] { -point[1], -point[0], -point[2] };
        // z > 0
        rotations[16] = new int[] { point[2], point[0], point[1] };
        rotations[17] = new int[] { point[2], -point[1], point[0] };
        rotations[18] = new int[] { point[2], -point[0], -point[1] };
        rotations[19] = new int[] { point[2], point[1], -point[0] };
        // z < 0
        rotations[20] = new int[] { -point[2], -point[0], point[1] };
        rotations[21] = new int[] { -point[2], point[1], point[0] };
        rotations[22] = new int[] { -point[2], point[0], -point[1] };
        rotations[23] = new int[] { -point[2], -point[1], -point[0] };
        return rotations;
    }

    private void getActualPositions(final Scanner s1, final Scanner s2) {
        final int[][] s1beacons = s1.beacons;
        final int[][] s2beacons = s2.beacons;
        List<Integer[]> result = new ArrayList<>();

        final int[][][] beaconsToRotations = new int[s2beacons.length][24][3]; // Beacons 1..n, rotation 1-24, point
        for (int i = 0; i < s2beacons.length; i++) {
            beaconsToRotations[i] = getAllRotations(s2beacons[i]);
        }
        // Algorithm:
        // 1. Find a vector for beacon i, rotation j
        // 2. Count for that beacon all other beacons in the list with same rotation j
        // which also fit
        // 3. If that nr >= 12 -> match, otherwise try the next rotation and go back to
        // 1.
        // 4. If all rotations are done -> no match
        for (int beacon = 0; beacon < beaconsToRotations.length; beacon++) {
            for (int rotation = 0; rotation < beaconsToRotations[0].length; rotation++) {
                final int[] s2point = beaconsToRotations[beacon][rotation];
                final int origX = s2point[0];
                final int origY = s2point[1];
                final int origZ = s2point[2];
                for (int k = 0; k < s1beacons.length; k++) {
                    final int[] s1point = s1beacons[k];
                    final int vectorX = s1point[0] - origX;
                    final int vectorY = s1point[1] - origY;
                    final int vectorZ = s1point[2] - origZ;
                    int total = 1;
                    result = new ArrayList<>();
                    for (int i = 0; i < beaconsToRotations.length; i++) {
                        if (i != beacon) {
                            final int newX = beaconsToRotations[i][rotation][0] + vectorX;
                            final int newY = beaconsToRotations[i][rotation][1] + vectorY;
                            final int newZ = beaconsToRotations[i][rotation][2] + vectorZ;
                            final Integer[] rp = { newX, newY, newZ };
                            result.add(rp);
                            for (int l = 0; l < s1beacons.length; l++) {
                                if (s1beacons[l][0] == newX && s1beacons[l][1] == newY && s1beacons[l][2] == newZ) {
                                    total++;
                                    // Remove matching points, we're only interested in points not already present
                                    // in s1
                                    result.remove(rp);
                                }
                            }

                        }
                    }
                    if (total >= 12) {
                        s2.actualPosition[0] = vectorX;
                        s2.actualPosition[1] = vectorY;
                        s2.actualPosition[2] = vectorZ;
                        s2.actualBeaconPositions = result;
                        return;
                    }
                }
            }
        }
    }

    private void addNewPoints(final Scanner s, final List<Integer[]> points) {
        final int[][] beacons = s.beacons;
        final int[][] newBeacons = new int[beacons.length + points.size()][3];
        int i = 0;
        for (; i < beacons.length; i++) {
            newBeacons[i][0] = beacons[i][0];
            newBeacons[i][1] = beacons[i][1];
            newBeacons[i][2] = beacons[i][2];
        }
        for (; i < beacons.length + points.size(); i++) {
            newBeacons[i][0] = points.get(i - beacons.length)[0];
            newBeacons[i][1] = points.get(i - beacons.length)[1];
            newBeacons[i][2] = points.get(i - beacons.length)[2];
        }
        s.beacons = newBeacons;
    }

    private int getManhattanDistance(final int[] p1, final int[] p2) {
        return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]) + Math.abs(p1[2] - p2[2]);
    }

    private void buildSpace(final List<Scanner> scanners, final Scanner initial) {
        final List<Scanner> done = new ArrayList<>();
        done.add(initial);
        while (done.size() != scanners.size()) {
            for (int i = 0; i < scanners.size(); i++) {
                final Scanner other = scanners.get(i);
                if (!done.contains(other)) {
                    getActualPositions(initial, other);
                    if (!other.actualBeaconPositions.isEmpty()) {
                        addNewPoints(initial, other.actualBeaconPositions);
                        done.add(other);
                    }
                }
            }
        }
    }

    private int getGreatestDistance(final List<String> lines) {
        final List<Scanner> scanners = parseScanners(lines);
        final Scanner initial = scanners.get(0);
        initial.actualPosition[0] = 0;
        initial.actualPosition[1] = 0;
        initial.actualPosition[2] = 0;
        buildSpace(scanners, initial);
        int max = 0;
        for (int i = 0; i < scanners.size(); i++) {
            for (int j = 0; j < scanners.size(); j++) {
                final int distance = getManhattanDistance(scanners.get(i).actualPosition, scanners.get(j).actualPosition);
                if (distance > max) {
                    max = distance;
                }
            }
        }
        return max;
    }

    private int getNrOfBeacons(final List<String> lines) {
        final List<Scanner> scanners = parseScanners(lines);
        final Scanner initial = scanners.get(0);
        buildSpace(scanners, initial);
        return initial.beacons.length;
    }

    private int runPart2(final List<String> lines) {
        return getGreatestDistance(lines);
    }

    private int runPart1(final List<String> lines) {
        return getNrOfBeacons(lines);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day19().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day19().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}

class Scanner {
    int nr;
    int[][] beacons;
    List<Integer[]> actualBeaconPositions = new ArrayList<>();
    int[] actualPosition = new int[3];

    Scanner(final int nr, final List<String> beacons) {
        this.nr = nr;
        this.beacons = new int[beacons.size()][3];
        parseBeacons(beacons);
    }

    void parseBeacons(final List<String> beacons) {
        for (int i = 0; i < beacons.size(); i++) {
            final String current = beacons.get(i);
            this.beacons[i][0] = Integer.parseInt(current.substring(0, current.indexOf(",")));
            this.beacons[i][1] = Integer
                    .parseInt(current.substring(current.indexOf(",") + 1, current.lastIndexOf(",")));
            this.beacons[i][2] = Integer.parseInt(current.substring(current.lastIndexOf(",") + 1));
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("--- scanner " + nr + " ---\n");
        for (int i = 0; i < beacons.length; i++) {
            sb.append(beacons[i][0] + "," + beacons[i][1] + "," + beacons[i][2] + "\n");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + nr;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Scanner other = (Scanner) obj;
        if (nr != other.nr)
            return false;
        return true;
    }

}