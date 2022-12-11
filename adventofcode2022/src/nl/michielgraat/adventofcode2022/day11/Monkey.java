package nl.michielgraat.adventofcode2022.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Monkey {
    private int nr;
    private List<Long> items = new ArrayList<>();
    private String operator;
    private String operand;
    private int divisor;
    private int trueMonkey;
    private int falseMonkey;
    private int nrInspections;
    private long lcm;

    public Monkey(final List<String> definition) {
        parse(definition);
    }

    private void parse(final List<String> definition) {
        final String monkeyLine = definition.get(0);
        nr = Integer.parseInt(monkeyLine.split(" ")[1].substring(0, monkeyLine.split(" ")[1].indexOf(":")));
        final String startingItems = definition.get(1).split(": ")[1];
        items = Arrays.stream(startingItems.split(", ")).map(Long::parseLong).collect(Collectors.toList());
        operator = definition.get(2).split(" = ")[1].split(" ")[1];
        operand = definition.get(2).split(" = ")[1].split(" ")[2];
        divisor = Integer.parseInt(definition.get(3).trim().split(" ")[3]);
        trueMonkey = Integer.parseInt(definition.get(4).trim().split(" ")[5]);
        falseMonkey = Integer.parseInt(definition.get(5).trim().split(" ")[5]);
    }

    private long calcWorryLevel(final long item) {
        final long op = operand.equals("old") ? item : Long.parseLong(operand);
        switch (operator) {
            case "*":
                return item * op;
            case "+":
                return item + op;
            default:
                throw new IllegalArgumentException("Unknown operator '" + operator + "'");
        }
    }

    private int test(final long worryLevel) {
        return (worryLevel % divisor == 0) ? trueMonkey : falseMonkey;
    }

    public List<Throw> takeTurn(final boolean part1) {
        final List<Throw> monkeyThrows = new ArrayList<>();
        final List<Long> itemsToRemove = new ArrayList<>();
        for (final long item : items) {
            long worryLevel = calcWorryLevel(item);
            if (part1) {
                worryLevel = (int) Math.floor(worryLevel / 3.0);
            } else {
                worryLevel %= lcm;
            }
            final int nextMonkey = test(worryLevel);
            itemsToRemove.add(item);
            monkeyThrows.add(new Throw(worryLevel, nextMonkey));
            nrInspections++;
        }
        items.removeAll(itemsToRemove);
        return monkeyThrows;
    }

    public List<Long> getItems() {
        return items;
    }

    public int getNrInspections() {
        return nrInspections;
    }

    public int getNr() {
        return nr;
    }

    public long getLcm() {
        return lcm;
    }

    public void setLcm(final long lcm) {
        this.lcm = lcm;
    }

    public int getDivisor() {
        return divisor;
    }

    public void setDivisor(final int divisor) {
        this.divisor = divisor;
    }
}
