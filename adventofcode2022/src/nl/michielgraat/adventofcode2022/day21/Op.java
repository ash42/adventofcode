package nl.michielgraat.adventofcode2022.day21;

public record Op(String op1, String op2, String operator) {

    long getResult(final long o1, final long o2) {
        switch (operator) {
            case "+":
                return o1 + o2;
            case "-":
                return o1 - o2;
            case "*":
                return o1 * o2;
            default:
                return o1 / o2;
        }
    }
}