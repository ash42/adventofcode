package nl.michielgraat.adventofcode2024.day24;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2024.AocSolver;

public class Day24 extends AocSolver {

    protected Day24(final String filename) {
        super(filename);
    }

    private List<Gate> parseGates(final List<String> input) {
        final List<Gate> gates = new ArrayList<>();
        boolean startParsing = false;
        for (final String line : input) {
            if (startParsing) {
                final String wire = line.split("-> ")[1];
                final String gate = line.split(" ->")[0];
                gates.add(new Gate(gate, wire));
            }
            if (line.isBlank()) {
                startParsing = true;
            }
        }
        return gates;
    }

    private Map<String, Integer> parseValues(final List<String> input) {
        final Map<String, Integer> map = new TreeMap<>();
        for (final String line : input) {
            if (line.isBlank()) {
                break;
            }
            final String wire = line.split(":")[0];
            final int value = Integer.parseInt(line.split(": ")[1]);
            map.put(wire, value);
        }
        return map;
    }

    private void calculateFinalValues(final Map<String, Integer> values, final List<Gate> gates) {
        final List<String> wires = gates.stream().map(Gate::getOutputWire).collect(Collectors.toList());
        while (!values.keySet().containsAll(wires)) {
            for (final Gate c : gates) {
                final Optional<Integer> result = c.getResult(values);
                if (result.isPresent()) {
                    values.put(c.getOutputWire(), result.get());
                }
            }
        }
    }

    private long getNumber(final Map<String, Integer> values) {
        final List<String> zWires = values.keySet().stream().filter(k -> k.startsWith("z"))
                .collect(Collectors.toList());
        Collections.sort(zWires, Collections.reverseOrder());
        final StringBuilder sb = new StringBuilder();
        for (final String zWire : zWires) {
            sb.append(values.get(zWire));
        }
        return Long.parseLong(sb.toString(), 2);
    }

    private List<Gate> findFaultyGates(final List<Gate> gates) {
        final List<Gate> faultyGates = new ArrayList<>();
        /*
         * There are 4 cases in which is faulty:
         * 
         * 1. If there is output to a z-wire, the operator should always be XOR (unless
         * it is the final bit). If not -> faulty.
         * 2. If the output is not a z-wire and the inputs are not x and y, the operator
         * should always be AND or OR. If not -> faulty.
         * 3. If the inputs are x and y (but not the first bit) and the operator is XOR,
         * the output wire should be the input of another XOR-gate. If not -> faulty.
         * 4. If the inputs are x and y (but not the first bit) and the operator is AND,
         * the output wire should be the input of an OR-gate. If not -> faulty.
         */
        for (final Gate c : gates) {
            if (c.getOutputWire().startsWith("z") && !c.getOutputWire().equals("z45")) {
                if (!c.getOperator().equals("XOR")) {
                    faultyGates.add(c);
                }
            } else if (!c.getOutputWire().startsWith("z")
                    && !(c.getOperand1().startsWith("x") || c.getOperand1().startsWith("y"))
                    && !(c.getOperand2().startsWith("x") || c.getOperand2().startsWith("y"))) {
                if (c.getOperator().equals("XOR")) {
                    faultyGates.add(c);
                }
            } else if (c.getOperator().equals("XOR")
                    && (c.getOperand1().startsWith("x") || c.getOperand1().startsWith("y"))
                    && (c.getOperand2().startsWith("x") || c.getOperand2().startsWith("y"))) {
                if (!(c.getOperand1().endsWith("00") && c.getOperand2().endsWith("00"))) {
                    final String output = c.getOutputWire();
                    boolean anotherFound = false;
                    for (final Gate c2 : gates) {
                        if (!c2.equals(c)) {
                            if ((c2.getOperand1().equals(output) || c2.getOperand2().equals(output))
                                    && c2.getOperator().equals("XOR")) {
                                anotherFound = true;
                                break;
                            }
                        }
                    }
                    if (!anotherFound) {
                        faultyGates.add(c);
                    }
                }
            } else if (c.getOperator().equals("AND")
                    && (c.getOperand1().startsWith("x") || c.getOperand1().startsWith("y"))
                    && (c.getOperand2().startsWith("x") || c.getOperand2().startsWith("y"))) {
                if (!(c.getOperand1().endsWith("00") && c.getOperand2().endsWith("00"))) {
                    final String output = c.getOutputWire();
                    boolean anotherFound = false;
                    for (final Gate c2 : gates) {
                        if (!c2.equals(c)) {
                            if ((c2.getOperand1().equals(output) || c2.getOperand2().equals(output))
                                    && c2.getOperator().equals("OR")) {
                                anotherFound = true;
                                break;
                            }
                        }
                    }
                    if (!anotherFound) {
                        faultyGates.add(c);
                    }
                }
            }
        }
        return faultyGates;
    }

    private String getOutput(final List<Gate> faultyGates) {
        Collections.sort(faultyGates);
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < faultyGates.size(); i++) {
            sb.append(faultyGates.get(i).getOutputWire());
            if (i < faultyGates.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Gate> gates = parseGates(input);
        final List<Gate> faultyGates = findFaultyGates(gates);
        return getOutput(faultyGates);
    }

    @Override
    protected String runPart1(final List<String> input) {
        final Map<String, Integer> values = parseValues(input);
        final List<Gate> gates = parseGates(input);
        calculateFinalValues(values, gates);
        return String.valueOf(getNumber(values));
    }

    public static void main(final String... args) {
        new Day24("day24.txt");
    }
}
