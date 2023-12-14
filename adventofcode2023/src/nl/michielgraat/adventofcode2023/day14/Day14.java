package nl.michielgraat.adventofcode2023.day14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day14 extends AocSolver {

    protected Day14(String filename) {
        super(filename);
    }

    private List<Rock> readRocks(final List<String> input) {
        List<Rock> rocks = new ArrayList<>();

        for (int y = 0; y < input.size(); y++) {
            String row = input.get(y);
            for (int x = 0; x < row.length(); x++) {
                char type = row.charAt(x);
                if (type == 'O' || type == '#') {
                    rocks.add(new Rock(x, y, type));
                }
            }
        }
        return rocks;
    }

    private List<Rock> tiltNorth(List<Rock> rocks) {
        Collections.sort(rocks);
        List<Rock> result = new ArrayList<>();
        Map<Integer,Integer> columnToMax = new HashMap<>();
        for (Rock rock : rocks) {
            int max = -1;
            if (!columnToMax.containsKey(rock.x())) {
                max = result.stream().filter(r -> r.x() == rock.x()).mapToInt(Rock::y).max().orElse(-1);
            } else {
                max = columnToMax.get(rock.x());
            }
            if (rock.type() == 'O') {
                result.add(new Rock(rock.x(), rock.y() == 0 ? 0 : max + 1, rock.type()));
                columnToMax.put(rock.x(),max+1);
            } else {
                result.add(rock);
                columnToMax.put(rock.x(), rock.y());
            }
        }

        return result;
    }

    private List<Rock> tiltEast(List<Rock> rocks, int maxX) {
        Collections.sort(rocks);
        Collections.reverse(rocks);
        List<Rock> result = new ArrayList<>();
        Map<Integer,Integer> rowToMin = new HashMap<>();
        for (Rock rock : rocks) {
            int min = maxX+1;
            if (!rowToMin.containsKey(rock.y())) {
                min = result.stream().filter(r -> r.y() == rock.y()).mapToInt(Rock::x).min().orElse(maxX + 1);
            } else {
                min = rowToMin.get(rock.y());
            }
            if (rock.type() == 'O') {
                Rock r = new Rock(rock.x() == maxX ? maxX : min - 1, rock.y(), rock.type());
                result.add(r);
                rowToMin.put(rock.y(), min - 1);
            } else {
                result.add(rock);
                rowToMin.put(rock.y(), rock.x());
            }
        }

        return result;
    }

    private List<Rock> tiltSouth(List<Rock> rocks, int maxY) {
        Collections.sort(rocks);
        Collections.reverse(rocks);
        List<Rock> result = new ArrayList<>();
        Map<Integer,Integer> columnToMin = new HashMap<>();
        for (Rock rock : rocks) {
            int min = maxY;
            if (!columnToMin.containsKey(rock.x())) {
                min = result.stream().filter(r -> r.x() == rock.x()).mapToInt(Rock::y).min().orElse(maxY);
            } else {
                min = columnToMin.get(rock.x());
            }
            if (rock.type() == 'O') {
                Rock r = new Rock(rock.x(), rock.y() == maxY ? maxY : min - 1, rock.type());
                result.add(r);
                columnToMin.put(rock.x(), min - 1);
            } else {
                result.add(rock);
                columnToMin.put(rock.x(), rock.y());
            }
        }
        return result;
    }

    private List<Rock> tiltWest(List<Rock> rocks) {
        Collections.sort(rocks);
        List<Rock> result = new ArrayList<>();
        Map<Integer,Integer> rowToMax = new HashMap<>();
        for (Rock rock : rocks) {
            int max = -1;
            if (!rowToMax.containsKey(rock.y())) {
                max = result.stream().filter(r -> r.y() == rock.y()).mapToInt(Rock::x).max().orElse(-1);
            } else {
                max = rowToMax.get(rock.y());
            }
            if (rock.type() == 'O') {
                Rock r = new Rock(rock.x() == 0 ? 0 : max + 1, rock.y(), rock.type());
                result.add(r);
                rowToMax.put(rock.y(), max+1);
            } else {
                result.add(rock);
                rowToMax.put(rock.y(),rock.x());
            }
        }

        return result;
    }

    private List<Rock> cycle(List<Rock> rocks, int maxX, int maxY) {
        return tiltEast(tiltSouth(tiltWest(tiltNorth(rocks)), maxY), maxX);
    }

    private int calculateWeight(List<Rock> rocks, int size) {
        return rocks.stream().filter(r -> r.type() == 'O').mapToInt(r -> size - r.y()).sum();
    }

    private List<Integer> findPattern(final List<Integer> values) {
        int slowIdx = 0;
        int slow = values.get(slowIdx);
        int fastIdx = 0;
        int fast;
        boolean met = false;
        while (fastIdx + 5 < values.size()) {
            slowIdx++;
            slow = values.get(slowIdx);
            fastIdx += 2;
            fast = values.get(fastIdx);
            if (slow == fast) {
                met = true;
                break;
            }
        }
        if (!met) {
            throw new IllegalArgumentException("No cycle found");
        }
        final int startIdx = slowIdx;
        slowIdx++;
        int slow2 = values.get(slowIdx);
        while (slow2 != slow) {
            slowIdx++;
            slow2 = values.get(slowIdx);
        }
        final List<Integer> pattern = new ArrayList<>();
        for (int i = startIdx; i < slowIdx; i++) {
            pattern.add(values.get(i));
        }
        return pattern;
    }

    private List<Integer> getFirstNWeights(int n, List<String> input) {
        List<Rock> rocks = readRocks(input);
        List<Integer> weights = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            rocks = cycle(rocks, input.get(0).length() - 1, input.size());
            weights.add(calculateWeight(rocks, input.size()));
        }
        return weights;
    }

    private int getValueFor(int n, List<Integer> weights) {
        List<Integer> pattern = findPattern(weights);

        int valToFetch = (n % pattern.size()) - 1;
        if (valToFetch < 0) {
            valToFetch = pattern.size() - 1;
        }
        return pattern.get(valToFetch);
    }

    @Override
    protected String runPart2(final List<String> input) {
        List<Integer> weights = getFirstNWeights(300, input);
        return String.valueOf(getValueFor(1000000000, weights));
    }

    @Override
    protected String runPart1(final List<String> input) {
        List<Rock> rocks = tiltNorth(readRocks(input));
        return String.valueOf(calculateWeight(rocks, input.size()));
    }

    public static void main(String... args) {
        new Day14("day14.txt");
    }
}
