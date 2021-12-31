package nl.michielgraat.adventofcode2021.day24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import nl.michielgraat.adventofcode2021.FileReader;

/*
    So basically the input is a program which consists of 14 (once for each input) times the same subprogram 
    with only a few differences. 
    
    Sometimes, at index 4, the Z-value is truncated by dividing by 26. In that case the last value of z is 
    increased by the value by which x is increased at index 5. Otherwise z becomes z + input_value + y_incr. 

    To keep track of previous values of z a stack is used. So basically there are two cases:

    1. z is not truncated -> push input digit + y incr to stack
    2. z is truncated -> pop the stack and the current input should be any digit for which popped_value + x_incr 
       is >= 1 and <= 9

    Based on this we can define at set of rules by which we can generate the correct number.

    So, the whole program becomes:

    1. Create a list of Inputs which for each subprogram defines if z is truncated and what the values for 
       increasing x and increasing y are.

    2. Based upon this create a set of Rules. Each rule defines the input digit for which it holds (nr), the 
       related other input (other) and the incr for this input (y incr).

       For example (and in case of the input in day24.txt): 
       
       Rule: 3 2 0 
       
       Because the value for input 2 is defined as input 2 + 8 (xIncr of input 2) 
       And the value for input 3 as input 2 + 8 (top of queue) + -8 (yIncr of input 3)

    3. Based upon these rules in getNr the correct number is generated by getting the maximum (part 1) or 
       minimum (part 2) value for which every rule holds.
*/
public class Day24 {

    private static final String FILENAME = "day24.txt";

    private static final int IDX_TRUNC = 4;
    private static final int IDX_X_INCR = 5;
    private static final int IDX_Y_INCR = 15;
    private static final int NR_INPUTS = 14;
    private static final List<Integer> VALID_DIGITS = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    private List<Input> getInputs(final List<String> lines) {
        final int size = lines.size() / NR_INPUTS;
        final List<Input> inputs = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += size) {
            final boolean trunc = lines.get(i + IDX_TRUNC).endsWith("26");
            final int xIncr = Integer.parseInt(lines.get(i + IDX_X_INCR).split(" ")[2]);
            final int yIncr = Integer.parseInt(lines.get(i + IDX_Y_INCR).split(" ")[2]);
            inputs.add(new Input(trunc, xIncr, yIncr));
        }
        return inputs;
    }

    private List<Rule> getRules(final List<Input> inputs) {
        final Deque<Rule> stack = new LinkedList<>();
        int i = 0;
        final List<Rule> result = new ArrayList<>();
        for (final Input input : inputs) {
            if (!input.truncateZ) {
                stack.push(new Rule(i, input.yIncr));
            } else {
                final Rule top = stack.pop();
                result.add(new Rule(i, top.nr, top.incr + input.xIncr));
            }
            i++;
        }
        return result;
    }

    private boolean isValid(final int i) {
        return i >= 1 && i <= 9;
    }

    private String getNr(final List<Rule> rules, final boolean min) {
        final int[] nr = new int[NR_INPUTS];
        for (final Rule r : rules) {
            final int i1 = r.nr;
            final int i2 = r.other;
            final int incr = r.incr;
            final IntStream is = VALID_DIGITS.stream().filter(d -> isValid(incr + d)).mapToInt(i -> i);
            final int i2val = (min) ? is.min().orElseThrow(NoSuchElementException::new)
                    : is.max().orElseThrow(NoSuchElementException::new);
            final int i1val = i2val + r.incr;
            nr[i1] = i1val;
            nr[i2] = i2val;
        }
        return Arrays.stream(nr).mapToObj(String::valueOf).collect(Collectors.joining());
    }

    private String runPart2(final List<String> lines) {
        return getNr(getRules(getInputs(lines)), true);
    }

    private String runPart1(final List<String> lines) {
        return getNr(getRules(getInputs(lines)), false);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day24().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day24().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}

class Rule {
    int nr;
    int other;
    int incr;

    Rule(final int nr, final int incr) {
        this.nr = nr;
        this.incr = incr;
    }

    Rule(final int nr, final int other, final int incr) {
        this.nr = nr;
        this.other = other;
        this.incr = incr;
    }
}

class Input {
    boolean truncateZ;
    int xIncr;
    int yIncr;

    Input(final boolean truncateZ, final int xIncr, final int yIncr) {
        this.truncateZ = truncateZ;
        this.xIncr = xIncr;
        this.yIncr = yIncr;
    }

    int getValue() {
        return truncateZ ? xIncr : yIncr;
    }
}