package nl.michielgraat.adventofcode2023.day22;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day22 extends AocSolver {

    protected Day22(String filename) {
        super(filename);
    }

    private List<Brick> getSortedSnapshot(List<String> input) {
        List<Brick> bricks = new ArrayList<>();
        Pattern brickPattern = Pattern.compile("(\\d+),(\\d+),(\\d+)~(\\d+),(\\d+),(\\d+)");
        for (String line : input) {
            Matcher matcher = brickPattern.matcher(line);
            matcher.find();
            bricks.add(new Brick(new Coordinate(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)),
                    Integer.valueOf(matcher.group(3))),
                    new Coordinate(Integer.valueOf(matcher.group(4)),
                            Integer.valueOf(matcher.group(5)), Integer.valueOf(matcher.group(6)))));
        }
        Collections.sort(bricks);
        return bricks;
    }

    private Map<Integer, List<Brick>> fallDown(List<Brick> bricks) {
        // We want to create a map of z-levels to the bricks which are at those levels.
        Map<Integer, List<Brick>> fallenDownBricks = new TreeMap<>();
        // To keep track of the upper edges of all bricks, use a utility map which has
        // for every z-level the bricks which have their top edges there (especially
        // important for vertical bricks, that is, bricks who have differing start- and
        // end-z-coordinates).
        Map<Integer, List<Brick>> brickEdges = new TreeMap<>();
        // We made sure the bricks are sorted, so we start with the lowest
        // (z-coordinate) brick and work our way up.
        for (Brick brick : bricks) {
            // Now we will try to lower this brick, starting at its starting z and going
            // down until the bottom (z=1), unless we have a collision with a brick below.
            for (int z = brick.start().z(); z >= 1; z--) {
                // Calculate the difference between start and ending z's. This is needed to set
                // the correct start and end z's at lower leve.s
                int zDiff = Math.abs(brick.start().z() - brick.end().z());
                if (z > 1 && brickEdges.containsKey(z - 1)) {
                    // We are not at the bottom yet and there are bricks at z-1.
                    Brick current = new Brick(
                            new Coordinate(brick.start().x(), brick.start().y(), z),
                            new Coordinate(brick.end().x(), brick.end().y(), z + zDiff));
                    if ((brickEdges.getOrDefault(z - 1, new ArrayList<>()).stream()
                            .filter(v -> v.intersects(current)).count() > 0)) {
                        // We have bricks at the level below which collide with the current brick, we
                        // can not go down any further, so store the brick at the current level.
                        fallenDownBricks.putIfAbsent(z, new ArrayList<>());
                        fallenDownBricks.get(z).add(current);
                        // Also keep track of the upper edge of the brick so we can use it for collision
                        // detection with the next bricks.
                        brickEdges.putIfAbsent(z + zDiff, new ArrayList<>());
                        brickEdges.get(z + zDiff).add(current);
                        break;
                    }
                } else if (z == 1) {
                    // We are at the bottom, so store the brick here.
                    fallenDownBricks.putIfAbsent(z, new ArrayList<>());
                    fallenDownBricks.get(z)
                            .add(new Brick(
                                    new Coordinate(brick.start().x(), brick.start().y(), 1),
                                    new Coordinate(brick.end().x(), brick.end().y(), 1 + zDiff)));
                    // Also keep track of the upper edge of the brick so we can use it for collision
                    // detection with the next bricks.
                    brickEdges.putIfAbsent(z + zDiff, new ArrayList<>());
                    brickEdges.get(z + zDiff).add(new Brick(
                            new Coordinate(brick.start().x(), brick.start().y(), 1),
                            new Coordinate(brick.end().x(), brick.end().y(), 1 + zDiff)));

                    break;
                }
            }
        }
        return fallenDownBricks;
    }

    private Map<Brick, List<Brick>> brickToSupportingBricks(Map<Integer, List<Brick>> fallenDownBricks) {
        // We want to create a map for every brick to their supporting bricks.
        Map<Brick, List<Brick>> map = new HashMap<>();
        int maxZ = fallenDownBricks.keySet().stream().mapToInt(z -> z).max().getAsInt();
        // Start from the bottom and work our way up.
        for (int z = 1; z <= maxZ; z++) {
            List<Brick> currentLevel = fallenDownBricks.getOrDefault(z, new ArrayList<>());
            // For every brick on this level we are going to find which brick they support.
            for (Brick current : currentLevel) {
                // To find out which bricks are supported by this brick, get all bricks which
                // are one up of its upper z-edge.
                List<Brick> potentialSupported = fallenDownBricks.getOrDefault(current.end().z() + 1,
                        new ArrayList<>());
                for (Brick ps : potentialSupported) {
                    if (current.intersects(ps)) {
                        // This brick support a brick at a higher level, store this connection.
                        map.putIfAbsent(ps, new ArrayList<>());
                        map.get(ps).add(current);
                    } else {
                    }
                }
            }
        }
        return map;
    }

    private Map<Brick, List<Brick>> brickToSupportedBricks(Map<Integer, List<Brick>> fallenDown,
            Map<Brick, List<Brick>> brickToSupportingBricks) {
        // We want to create a map for every brick to the bricks it supports.
        Map<Brick, List<Brick>> brickToSupported = new HashMap<>();
        List<Brick> bricks = fallenDown.values().stream().flatMap(List::stream).distinct().toList();
        for (Brick brick : bricks) {
            // Of course, all bricks which are supported by this brick are those which have
            // it in the list of their supporting bricks.
            brickToSupported.put(brick, brickToSupportingBricks.entrySet().stream()
                    .filter(e -> e.getValue().contains(brick)).map(e -> e.getKey()).distinct().toList());
        }

        return brickToSupported;
    }

    private int countDisintegratableBricks(Map<Integer, List<Brick>> fallenDown,
            Map<Brick, List<Brick>> brickToSupportingBricks) {
        // We want to count every brick which can be disintegrated. That is, every brick
        // which does not support any other brick, and brick which supported bricks are
        // all supported by at minimum one other brick.

        // First, collect all bricks which support other bricks.
        List<Brick> supportingBricks = brickToSupportingBricks.values().stream().flatMap(List::stream).distinct()
                .toList();
        int nr = 0;
        // Now loop over all bricks.
        List<Brick> bricks = fallenDown.values().stream().flatMap(List::stream).distinct().toList();
        for (Brick brick : bricks) {
            if (!supportingBricks.contains(brick)) {
                // This brick is not part of the supporting bricks list, and so does not support
                // any other brick. So, this brick can be disintegrated.
                nr++;
            } else {
                // This brick does support other bricks. For each of these bricks, check if they
                // are supported by at least one other brick. That is, the number of its
                // supporting bricks should be at least 2 (> 1).
                if (brickToSupportingBricks.entrySet().stream().filter(e -> e.getValue().contains(brick))
                        .allMatch(e -> e.getValue().size() > 1)) {
                    nr++;
                }
            }
        }
        return nr;
    }

    private void getRemovedBricks(Brick brick, Map<Brick, List<Brick>> brickToSupportedBricks,
            Map<Brick, List<Brick>> brickToSupportingBricks, Set<Brick> removed) {
        // All the removed bricks will be stored in the 'removed' set

        // First we want a list of all bricks which are supported by the current brick
        // (and so can possibly disintegrate)
        List<Brick> supportedBricks = brickToSupportedBricks.get(brick);
        if (!supportedBricks.isEmpty()) {
            removed.add(brick);
            List<Brick> nextBricksToRemove = new ArrayList<>();
            // Now get a list of all supported bricks which *will* disintegrate by remove
            // this brick. We do this by checking if all the bricks which are supported by
            // this brick only have supporting bricks which have been removed.
            for (Brick supported : supportedBricks) {
                if (removed.containsAll(brickToSupportingBricks.get(supported))) {
                    nextBricksToRemove.add(supported);
                }
            }

            // Rinse and repeat for the next-to-be-removed bricks
            if (nextBricksToRemove.size() > 0) {
                removed.addAll(nextBricksToRemove);
                for (Brick next : nextBricksToRemove) {
                    getRemovedBricks(next, brickToSupportedBricks, brickToSupportingBricks, removed);
                }
            }
        }
    }

    private int getNrOfFallenBricks(Brick brick, Map<Brick, List<Brick>> brickToSupportedBricks,
            Map<Brick, List<Brick>> brickToSupportingBricks) {
        Set<Brick> removed = new HashSet<>();
        getRemovedBricks(brick, brickToSupportedBricks, brickToSupportingBricks, removed);
        // Remove the current brick of the as, per the rules, we do not count it.
        removed.remove(brick);
        return removed.size();
    }

    @Override
    protected String runPart2(final List<String> input) {
        Map<Integer, List<Brick>> fallenDownBricks = fallDown(getSortedSnapshot(input));
        Map<Brick, List<Brick>> brickToSupportingBricks = brickToSupportingBricks(fallenDownBricks);
        Map<Brick, List<Brick>> brickToSupportedBricks = brickToSupportedBricks(fallenDownBricks,
                brickToSupportingBricks);
        List<Brick> allBricks = fallenDownBricks.values().stream().flatMap(List::stream).distinct().toList();

        return String.valueOf(allBricks.stream()
                .mapToInt(b -> getNrOfFallenBricks(b, brickToSupportedBricks, brickToSupportingBricks)).sum());
    }

    @Override
    protected String runPart1(final List<String> input) {
        Map<Integer, List<Brick>> fallenDownBricks = fallDown(getSortedSnapshot(input));
        Map<Brick, List<Brick>> brickToSupportingBricks = brickToSupportingBricks(fallenDownBricks);
        return String.valueOf(countDisintegratableBricks(fallenDownBricks, brickToSupportingBricks));
    }

    public static void main(String... args) {
        new Day22("day22.txt");
    }
}
