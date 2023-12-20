package nl.michielgraat.adventofcode2023.day19;

public class Range {
    long minX = 1;
    long maxX = 4000;
    long minM = 1;
    long maxM = 4000;
    long minA = 1;
    long maxA = 4000;
    long minS = 1;
    long maxS = 4000;

    public Range() {
    }

    public Range(long minX, long maxX, long minM, long maxM, long minA, long maxA, long minS, long maxS) {
        this.minX = minX;
        this.maxX = maxX;
        this.minM = minM;
        this.maxM = maxM;
        this.minA = minA;
        this.maxA = maxA;
        this.minS = minS;
        this.maxS = maxS;
    }

    public long getCombinations() {
        return (maxX - minX + 1) * (maxM - minM + 1) * (maxA - minA + 1) * (maxS - minS + 1);
    }

    /**
     * Split a range according to a rule. An array of size 2 is returned with at the
     * first index the range which complies with the rule, and at the second index
     * the range which does not.
     * 
     * For instance:
     * 
     * x<10:a
     * 
     * Then at index 0 the range will have 1-9 for x and at index 1 the range will
     * have 10-4000 for x.
     * 
     * @param rule The rule to use
     * @return The array of size 2 as described above.
     */
    public Range[] split(Rule rule) {
        Range[] splitRanges = new Range[2];
        String property = rule.getProperty().get();
        String operator = rule.getOperator().get();
        int operand = rule.getOperand().get();
        if (property.equals("x")) {
            if (operator.equals("<")) {
                splitRanges[0] = new Range(Math.max(1, minX), Math.min(operand - 1, maxX), minM, maxM, minA, maxA, minS,
                        maxS);
                splitRanges[1] = new Range(Math.max(operand, minX), Math.min(4000, maxX), minM, maxM, minA, maxA, minS,
                        maxS);
            } else {
                splitRanges[0] = new Range(Math.max(operand + 1, minX), Math.min(4000, maxX), minM, maxM, minA, maxA,
                        minS, maxS);
                splitRanges[1] = new Range(Math.max(1, minX), Math.min(operand, maxX), minM, maxM, minA, maxA, minS,
                        maxS);
            }
        } else if (property.equals("m")) {
            if (operator.equals("<")) {
                splitRanges[0] = new Range(minX, maxX, Math.max(1, minM), Math.min(operand - 1, maxM), minA, maxA, minS,
                        maxS);
                splitRanges[1] = new Range(minX, maxX, Math.max(operand, minM), Math.min(4000, maxM), minA, maxA, minS,
                        maxS);
            } else {
                splitRanges[0] = new Range(minX, maxX, Math.max(operand + 1, minM), Math.min(4000, maxM), minA, maxA,
                        minS, maxS);
                splitRanges[1] = new Range(minX, maxX, Math.max(1, minM), Math.min(operand, maxM), minA, maxA, minS,
                        maxS);
            }
        } else if (property.equals("a")) {
            if (operator.equals("<")) {
                splitRanges[0] = new Range(minX, maxX, minM, maxM, Math.max(1, minA), Math.min(operand - 1, maxA), minS,
                        maxS);
                splitRanges[1] = new Range(minX, maxX, minM, maxM, Math.max(operand, minA), Math.min(4000, maxA), minS,
                        maxS);
            } else {
                splitRanges[0] = new Range(minX, maxX, minM, maxM, Math.max(operand + 1, minA), Math.min(4000, maxA),
                        minS, maxS);
                splitRanges[1] = new Range(minX, maxX, minM, maxM, Math.max(1, minA), Math.min(operand, maxA), minS,
                        maxS);
            }
        } else {
            if (operator.equals("<")) {
                splitRanges[0] = new Range(minX, maxX, minM, maxM, minA, maxA, Math.max(1, minS),
                        Math.min(operand - 1, maxS));
                splitRanges[1] = new Range(minX, maxX, minM, maxM, minA, maxA, Math.max(operand, minS),
                        Math.min(4000, maxS));
            } else {
                splitRanges[0] = new Range(minX, maxX, minM, maxM, minA, maxA, Math.max(operand + 1, minS),
                        Math.min(4000, maxS));
                splitRanges[1] = new Range(minX, maxX, minM, maxM, minA, maxA, Math.max(1, minS),
                        Math.min(operand, maxS));
            }
        }
        return splitRanges;
    }

    @Override
    public String toString() {
        return "[x (" + minX + " - " + maxX + "), m (" + minM + " - " + maxM + "), a (" + minA
                + " - " + maxA + "), s (" + minS + " - " + maxS + ")]";
    }

}
