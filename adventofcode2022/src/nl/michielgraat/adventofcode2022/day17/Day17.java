package nl.michielgraat.adventofcode2022.day17;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day17 extends AocSolver {

    private static final boolean DEBUG = false;

    private static final char ROCK = '#';
    private static final char AIR = '.';

    protected Day17(final String filename) {
        super(filename);
    }

    private void printCave(final List<Position> cave) {
        final long maxY = cave.stream().mapToLong(Position::y).max().getAsLong();
        for (long y = maxY; y >= 0; y--) {
            System.out.print(y == 0 ? "+" : "|");
            final long y2 = y;
            if (y == 0) {
                System.out.print("-------");
            } else {
                cave.stream().filter(p -> p.y() == y2).sorted().forEach(p -> System.out.print(p.type()));
            }
            System.out.println(y == 0 ? "+" : "|");
        }
    }

    private void addFloor(final List<Position> cave) {
        for (int x = 0; x < 7; x++) {
            cave.add(new Position(x, 0, '#'));
        }
    }

    private List<Position> addShapeFive(final List<Position> cave, final long height) {
        final List<Position> rock = new ArrayList<>();
        cave.add(new Position(0, height + 5, AIR));
        cave.add(new Position(1, height + 5, AIR));
        cave.add(new Position(2, height + 5, ROCK));
        cave.add(new Position(3, height + 5, ROCK));
        rock.add(new Position(2, height + 5, ROCK));
        rock.add(new Position(3, height + 5, ROCK));
        cave.add(new Position(4, height + 5, AIR));
        cave.add(new Position(5, height + 5, AIR));
        cave.add(new Position(6, height + 5, AIR));
        cave.add(new Position(0, height + 4, AIR));
        cave.add(new Position(1, height + 4, AIR));
        cave.add(new Position(2, height + 4, ROCK));
        cave.add(new Position(3, height + 4, ROCK));
        rock.add(new Position(2, height + 4, ROCK));
        rock.add(new Position(3, height + 4, ROCK));
        cave.add(new Position(4, height + 4, AIR));
        cave.add(new Position(5, height + 4, AIR));
        cave.add(new Position(6, height + 4, AIR));
        fillEmptySpace(cave, height);
        return rock;
    }

    private List<Position> addShapeFour(final List<Position> cave, final long height) {
        final List<Position> rock = new ArrayList<>();
        cave.add(new Position(0, height + 7, AIR));
        cave.add(new Position(1, height + 7, AIR));
        cave.add(new Position(2, height + 7, ROCK));
        rock.add(new Position(2, height + 7, ROCK));
        cave.add(new Position(3, height + 7, AIR));
        cave.add(new Position(4, height + 7, AIR));
        cave.add(new Position(5, height + 7, AIR));
        cave.add(new Position(6, height + 7, AIR));
        cave.add(new Position(0, height + 6, AIR));
        cave.add(new Position(1, height + 6, AIR));
        cave.add(new Position(2, height + 6, ROCK));
        rock.add(new Position(2, height + 6, ROCK));
        cave.add(new Position(3, height + 6, AIR));
        cave.add(new Position(4, height + 6, AIR));
        cave.add(new Position(5, height + 6, AIR));
        cave.add(new Position(6, height + 6, AIR));
        cave.add(new Position(0, height + 5, AIR));
        cave.add(new Position(1, height + 5, AIR));
        cave.add(new Position(2, height + 5, ROCK));
        rock.add(new Position(2, height + 5, ROCK));
        cave.add(new Position(3, height + 5, AIR));
        cave.add(new Position(4, height + 5, AIR));
        cave.add(new Position(5, height + 5, AIR));
        cave.add(new Position(6, height + 5, AIR));
        cave.add(new Position(0, height + 4, AIR));
        cave.add(new Position(1, height + 4, AIR));
        cave.add(new Position(2, height + 4, ROCK));
        rock.add(new Position(2, height + 4, ROCK));
        cave.add(new Position(3, height + 4, AIR));
        cave.add(new Position(4, height + 4, AIR));
        cave.add(new Position(5, height + 4, AIR));
        cave.add(new Position(6, height + 4, AIR));
        fillEmptySpace(cave, height);
        return rock;
    }

    private List<Position> addShapeThree(final List<Position> cave, final long height) {
        final List<Position> rock = new ArrayList<>();
        cave.add(new Position(0, height + 6, AIR));
        cave.add(new Position(1, height + 6, AIR));
        cave.add(new Position(2, height + 6, AIR));
        cave.add(new Position(3, height + 6, AIR));
        cave.add(new Position(4, height + 6, ROCK));
        rock.add(new Position(4, height + 6, ROCK));
        cave.add(new Position(5, height + 6, AIR));
        cave.add(new Position(6, height + 6, AIR));
        cave.add(new Position(0, height + 5, AIR));
        cave.add(new Position(1, height + 5, AIR));
        cave.add(new Position(2, height + 5, AIR));
        cave.add(new Position(3, height + 5, AIR));
        cave.add(new Position(4, height + 5, ROCK));
        rock.add(new Position(4, height + 5, ROCK));
        cave.add(new Position(5, height + 5, AIR));
        cave.add(new Position(6, height + 5, AIR));
        cave.add(new Position(0, height + 4, AIR));
        cave.add(new Position(1, height + 4, AIR));
        cave.add(new Position(2, height + 4, ROCK));
        cave.add(new Position(3, height + 4, ROCK));
        cave.add(new Position(4, height + 4, ROCK));
        rock.add(new Position(2, height + 4, ROCK));
        rock.add(new Position(3, height + 4, ROCK));
        rock.add(new Position(4, height + 4, ROCK));
        cave.add(new Position(5, height + 4, AIR));
        cave.add(new Position(6, height + 4, AIR));
        fillEmptySpace(cave, height);
        return rock;
    }

    private List<Position> addShapeTwo(final List<Position> cave, final long height) {
        final List<Position> rock = new ArrayList<>();
        cave.add(new Position(0, height + 6, AIR));
        cave.add(new Position(1, height + 6, AIR));
        cave.add(new Position(2, height + 6, AIR));
        cave.add(new Position(3, height + 6, ROCK));
        rock.add(new Position(3, height + 6, ROCK));
        cave.add(new Position(4, height + 6, AIR));
        cave.add(new Position(5, height + 6, AIR));
        cave.add(new Position(6, height + 6, AIR));
        cave.add(new Position(0, height + 5, AIR));
        cave.add(new Position(1, height + 5, AIR));
        cave.add(new Position(2, height + 5, ROCK));
        cave.add(new Position(3, height + 5, ROCK));
        cave.add(new Position(4, height + 5, ROCK));
        rock.add(new Position(2, height + 5, ROCK));
        rock.add(new Position(3, height + 5, ROCK));
        rock.add(new Position(4, height + 5, ROCK));
        cave.add(new Position(5, height + 5, AIR));
        cave.add(new Position(6, height + 5, AIR));
        cave.add(new Position(0, height + 4, AIR));
        cave.add(new Position(1, height + 4, AIR));
        cave.add(new Position(2, height + 4, AIR));
        cave.add(new Position(3, height + 4, ROCK));
        rock.add(new Position(3, height + 4, ROCK));
        cave.add(new Position(4, height + 4, AIR));
        cave.add(new Position(5, height + 4, AIR));
        cave.add(new Position(6, height + 4, AIR));
        fillEmptySpace(cave, height);
        return rock;
    }

    private List<Position> addShapeOne(final List<Position> cave, final long height) {
        final List<Position> rock = new ArrayList<>();
        cave.add(new Position(0, height + 4, AIR));
        cave.add(new Position(1, height + 4, AIR));
        cave.add(new Position(2, height + 4, ROCK));
        cave.add(new Position(3, height + 4, ROCK));
        cave.add(new Position(4, height + 4, ROCK));
        cave.add(new Position(5, height + 4, ROCK));
        rock.add(new Position(2, height + 4, ROCK));
        rock.add(new Position(3, height + 4, ROCK));
        rock.add(new Position(4, height + 4, ROCK));
        rock.add(new Position(5, height + 4, ROCK));
        cave.add(new Position(6, height + 4, AIR));
        fillEmptySpace(cave, height);
        return rock;
    }

    private void fillEmptySpace(final List<Position> cave, final long height) {
        for (long y = height + 1; y < height + 4; y++) {
            for (int x = 0; x < 7; x++) {
                final Position air = new Position(x, y, AIR);
                cave.remove(air);
                cave.add(air);
            }
        }
    }

    private List<Position> addNextShape(final long shapeNr, final List<Position> cave, final long height) {
        switch ((int) (shapeNr % 5)) {
            case 0:
                return addShapeOne(cave, height);
            case 1:
                return addShapeTwo(cave, height);
            case 2:
                return addShapeThree(cave, height);
            case 3:
                return addShapeFour(cave, height);
            default:
                return addShapeFive(cave, height);
        }
    }

    private List<Position> moveDown(final List<Position> rock, final List<Position> cave, final long height) {
        final List<Position> newRockPosition = new ArrayList<>();

        // Collect all distinct X coordinates of the rock
        final List<Integer> rockXCoordinates = rock.stream().filter(p -> p.type() == ROCK).mapToInt(Position::x).distinct()
                .boxed().toList();
        // For all these X-coordinates find the positions with minimum Y-coordinates.
        // These mark the bottom edges of the rock.
        final List<Position> rockEdges = new ArrayList<>();
        for (final int rockX : rockXCoordinates) {
            final long y = rock.stream().filter(p -> p.type() == ROCK).filter(p -> p.x() == rockX).mapToLong(Position::y)
                    .min().getAsLong();
            rockEdges.addAll(rock.stream().filter(p -> p.type() == ROCK).filter(p -> p.x() == rockX)
                    .filter(p -> p.y() == y).collect(Collectors.toList()));
        }
        if (DEBUG) {
            System.out.println("Rock edges: " + rockEdges);
        }

        final long rockBottom = rock.stream().mapToLong(Position::y).min().getAsLong();
        if (DEBUG) {
            System.out.println("Rock bottom: " + rockBottom);
            System.out.println("Rock edges: " + rockEdges.toString());
        }
        // Only check if we are at the top of the stack of below it.
        if (rockBottom <= height + 1) {
            final List<Position> restingRockEdges = new ArrayList<>();
            boolean goingToTouch = false;
            final List<Position> caveRocks = cave.stream().filter(p -> p.type() == ROCK).filter(p -> !rock.contains(p))
                    .toList();
            for (final Position rockEdge : rockEdges) {
                final Position potentialClash = new Position(rockEdge.x(), rockEdge.y() - 1, ROCK);
                if (caveRocks.contains(potentialClash)) {
                    if (DEBUG) {
                        System.out.println(
                                "There is a potential clash at " + potentialClash.toString() + " so stop moving rock");
                    }
                    goingToTouch = true;
                    break;
                }
            }

            if (goingToTouch) {
                if (DEBUG) {
                    System.out.println("Rock falls 1 unit, causing it to come to rest");
                }
                return newRockPosition;
            } else {
                if (DEBUG) {
                    System.out.println("Rock can continue falling");
                }
            }
        }
        for (final Position pos : rock) {
            if (pos.type() == ROCK) {
                final Position newRockPos = new Position(pos.x(), pos.y() - 1, ROCK);
                final Position oldPos = new Position(pos.x(), pos.y(), AIR);
                cave.remove(oldPos);
                cave.add(oldPos);
                cave.remove(newRockPos);
                cave.add(newRockPos);
                newRockPosition.add(newRockPos);
            }
        }
        final long maxY = Math.max(newRockPosition.stream().mapToLong(Position::y).max().getAsLong(),
                cave.stream().filter(p -> p.type() == ROCK).mapToLong(Position::y).max().getAsLong());
        final List<Position> posToRemove = cave.stream().filter(p -> p.y() > maxY).toList();
        cave.removeAll(posToRemove);
        if (DEBUG) {
            System.out.println("Rock falls 1 unit");
        }
        return newRockPosition;
    }

    private List<Position> shiftSideways(final int modifier, final List<Position> rock, final List<Position> cave) {
        final List<Position> newRockPosition = new ArrayList<>();
        for (final Position pos : rock) {
            if (pos.type() == ROCK) {
                final Position newRockPos = new Position(pos.x() + modifier, pos.y(), ROCK);
                final Position oldPos = new Position(pos.x(), pos.y(), AIR);
                cave.remove(oldPos);
                cave.add(oldPos);
                cave.remove(newRockPos);
                cave.add(newRockPos);
                newRockPosition.add(newRockPos);
            }
        }
        Collections.sort(newRockPosition);
        return newRockPosition;
    }

    private List<Position> handleWind(final char direction, final List<Position> rock, final List<Position> cave, final long height) {
        if (DEBUG) {
            System.out.println("Jet of gas pushes rock " + (direction == '<' ? "LEFT" : "RIGHT"));
        }
        final int modifier = direction == '<' ? -1 : 1;
        final long bottomRock = rock.stream().filter(p -> p.type() == ROCK).mapToLong(Position::y).min().getAsLong();
        final long topStack = cave.stream().filter(p -> !rock.contains(p)).filter(p -> p.type() == ROCK)
                .mapToLong(Position::y).max().getAsLong();
        final boolean rockAtCriticalHeight = bottomRock - topStack <= 0;
        if (DEBUG) {
            System.out.println("Bottom rock: " + bottomRock);
            System.out.println("Top stack: " + topStack);
            System.out.println(
                    "Rock at critical height: " + rockAtCriticalHeight + ", because: " + (bottomRock - topStack));
        }
        // Collect all distinct Y coordinates of the rock
        final List<Long> rockYCoordinates = rock.stream().filter(p -> p.type() == ROCK).mapToLong(Position::y).distinct()
                .boxed().toList();
        // For all these Y-coordinates find the positions with minimum (going left) or
        // maximum (going right) X-coordinates. These mark the left or right edges of
        // the rock.
        final List<Position> rockEdges = new ArrayList<>();
        for (final long rockY : rockYCoordinates) {
            final int x = direction == '<'
                    ? rock.stream().filter(p -> p.type() == ROCK).filter(p -> p.y() == rockY).mapToInt(Position::x)
                            .min().getAsInt()
                    : rock.stream().filter(p -> p.type() == ROCK).filter(p -> p.y() == rockY).mapToInt(Position::x)
                            .max().getAsInt();
            rockEdges.addAll(rock.stream().filter(p -> p.type() == ROCK).filter(p -> p.y() == rockY)
                    .filter(p -> p.x() == x).collect(Collectors.toList()));
        }
        if (DEBUG) {
            System.out.println("Rock edges: " + rockEdges);
        }

        final int edgeRock = direction == '<'
                ? rockEdges.stream().mapToInt(Position::x).min().getAsInt()
                : rockEdges.stream().mapToInt(Position::x).max().getAsInt();

        boolean goingToTouch = false;
        if (rockAtCriticalHeight) {
            for (final Position rockEdge : rockEdges) {
                goingToTouch = cave.stream().filter(p -> !rock.contains(p)).filter(p -> p.type() == ROCK)
                        .filter(p -> p.y() == rockEdge.y()).filter(p -> p.x() == rockEdge.x() + modifier).count() > 0;
                if (goingToTouch) {
                    if (DEBUG) {
                        System.out.println("Rock edge " + rockEdge.toString() + " is going to collide");
                    }
                    break;
                }
            }
        }

        if (DEBUG) {
            System.out.println("X of rock edge: " + edgeRock);
            System.out.println("Rock edges: " + rockEdges.toString());
            System.out.println("Going to touch: " + goingToTouch);
        }

        if (direction == '<' && edgeRock > 0) {
            if (!goingToTouch) {
                if (DEBUG) {
                    System.out.println("Shifting left");
                }
                Collections.sort(rock);
                return shiftSideways(modifier, rock, cave);
            }
        } else if (direction == '>' && edgeRock < 6) {
            if (!goingToTouch) {
                if (DEBUG) {
                    System.out.println("Shifting right");
                }
                Collections.sort(rock, Collections.reverseOrder());
                return shiftSideways(modifier, rock, cave);
            }
        }
        Collections.sort(rock);
        return rock;
    }

    private long getResult(final List<String> input, final boolean part2) {
        final char[] wind = input.get(0).toCharArray();
        int windIdx = 0;
        long height = 0;
        final List<Position> cave = new ArrayList<>();
        addFloor(cave);

        final Map<CacheKey, CacheValue> cache = new HashMap<>();
        long incrHeight = 0L;

        final long nrBlocks = part2 ? 1000000000000L : 2022;
        for (long shapeNr = 0; shapeNr < nrBlocks; shapeNr++) {
            if (DEBUG) {
                System.out.println("Shape " + (shapeNr + 1));
                System.out.println("A new rock begins falling");
            }
            List<Position> rock = addNextShape(shapeNr, cave, height);
            if (DEBUG) {
                printCave(cave);
            }
            boolean atRest = false;
            while (!atRest) {
                rock = handleWind(wind[windIdx++ % wind.length], rock, cave, height);
                if (DEBUG) {
                    printCave(cave);
                }
                rock = moveDown(rock, cave, height);
                atRest = rock.isEmpty();
                if (DEBUG) {
                    printCave(cave);
                }
            }
            height = cave.stream().filter(p -> p.type() == ROCK).mapToLong(Position::y).max().getAsLong();

            if (DEBUG) {
                System.out.println((shapeNr + 1) + ": " + height);
            }

            if (part2) {
                final long startY = height;
                final List<Position> fWindow = cave.stream().filter(p -> p.type() == ROCK).filter(p -> startY - p.y() <= 20)
                        .sorted().collect(Collectors.toList());
                final List<Position> window = new ArrayList<>();
                for (final Position p : fWindow) {
                    window.add(new Position(p.x(), p.y() - startY, p.type()));
                }
                Collections.sort(window);
                final CacheKey k = new CacheKey(wind[(windIdx - 1) % wind.length], (int) (shapeNr % 5), window);
                if (cache.containsKey(k)) {
                    final CacheValue prev = cache.get(k);
                    final long dy = height - prev.height();
                    final long dt = shapeNr - prev.shapeNr();
                    final long amount = (1000000000000L - shapeNr) / dt;
                    incrHeight += amount * dy;
                    shapeNr += dt * amount;
                }
                final long vHeight = window.stream().filter(p -> p.type() == ROCK).mapToLong(Position::y).max().getAsLong()
                        - window.stream().filter(p -> p.type() == ROCK).mapToLong(Position::y).min().getAsLong();
                final CacheValue v = new CacheValue(shapeNr, height);
                cache.put(k, v);
            }
        }
        if (DEBUG) {
            printCave(cave);
        }
        return height + incrHeight;
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(getResult(input, true));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(getResult(input, false));
    }

    public static void main(final String... args) {
        new Day17("day17.txt");
    }
}

record CacheKey(char windDir, int shapeNr, List<Position> window) {
};

record CacheValue(long shapeNr, long height) {
};