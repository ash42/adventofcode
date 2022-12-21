package nl.michielgraat.adventofcode2022.day21;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.michielgraat.adventofcode2022.AocSolver;

public class Day21 extends AocSolver {

    protected Day21(final String filename) {
        super(filename);
    }

    private Map<String, Long> getMonkeyToNumber(final List<String> input) {
        final Map<String, Long> monkeyToNr = new HashMap<>();
        for (final String line : input) {
            final String[] parts = line.split(" ");
            if (parts.length == 2) {
                monkeyToNr.put(parts[0].substring(0, parts[0].length() - 1), Long.parseLong(parts[1]));
            }
        }
        return monkeyToNr;
    }

    private Map<String, Op> getMonkeyToOperation(final List<String> input) {
        final Map<String, Op> monkeyToOperation = new HashMap<>();
        for (final String line : input) {
            final String[] parts = line.split(" ");
            if (parts.length != 2) {
                monkeyToOperation.put(parts[0].substring(0, parts[0].length() - 1),
                        new Op(parts[1], parts[3], parts[2]));
            }
        }
        return monkeyToOperation;
    }

    private long getResult(final long humn, final String outputKey, final List<String> input) {
        final Map<String, Long> monkeyToNr = getMonkeyToNumber(input);
        Map<String, Op> monkeyToOperation = getMonkeyToOperation(input);
        monkeyToNr.put("humn", humn);
        while (!monkeyToNr.containsKey("root")) {
            final Map<String, Op> tmp = new HashMap<>();
            for (final Entry<String, Op> entry : monkeyToOperation.entrySet()) {
                final Op op = entry.getValue();
                if (monkeyToNr.containsKey(op.op1()) && monkeyToNr.containsKey(op.op2())) {
                    monkeyToNr.put(entry.getKey(), op.getResult(monkeyToNr.get(op.op1()), monkeyToNr.get(op.op2())));
                } else {
                    tmp.put(entry.getKey(), entry.getValue());
                }
            }
            monkeyToOperation = tmp;
        }
        return monkeyToNr.get(outputKey);
    }

    @Override
    protected String runPart2(final List<String> input) {
        final Map<String, Op> monkeyToOperation = getMonkeyToOperation(input);
        final String rootOp1 = monkeyToOperation.get("root").op1();
        final String rootOp2 = monkeyToOperation.get("root").op2();
        final long target = getResult(0, rootOp2, input);
        long start = 0L;
        long end = 1000000000000000L;

        while (start < end) {
            final long mid = (start + end) / 2;
            final long result = getResult(mid, rootOp1, input);
            final long score = target - result;

            if (score < 0) {
                start = mid + 1;
            } else if (score == 0) {
                return String.valueOf(mid);
            } else {
                end = mid;
            }
        }
        return "";
    }

    protected String runPart1(final List<String> input) {
        final Map<String, Long> monkeyToNr = getMonkeyToNumber(input);
        Map<String, Op> monkeyToOperation = getMonkeyToOperation(input);

        while (!monkeyToNr.containsKey("root")) {
            final Map<String, Op> tmp = new HashMap<>();
            for (final Entry<String, Op> entry : monkeyToOperation.entrySet()) {
                final Op op = entry.getValue();
                if (monkeyToNr.containsKey(op.op1()) && monkeyToNr.containsKey(op.op2())) {
                    monkeyToNr.put(entry.getKey(), op.getResult(monkeyToNr.get(op.op1()), monkeyToNr.get(op.op2())));
                } else {
                    tmp.put(entry.getKey(), entry.getValue());
                }
            }
            monkeyToOperation = tmp;
        }
        return String.valueOf(monkeyToNr.get("root"));
    }

    public static void main(final String... args) {
        new Day21("day21.txt");
    }
}
