package nl.michielgraat.adventofcode2023.day19;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Rule(String logic) {
    private static final Pattern OPERATOR_PATTERN = Pattern.compile("(\\S+)(<|>)(\\S+):(\\S+)");

    public Optional<String> apply(Part part) {
        Matcher matcher = OPERATOR_PATTERN.matcher(logic);
        if (matcher.matches()) {
            String property = getProperty().get();
            int partVal = property.equals("x") ? part.x()
                    : property.equals("m") ? part.m() : property.equals("a") ? part.a() : part.s();
            String operator = getOperator().get();
            int operand = getOperand().get();
            if (applyOperator(partVal, operand, operator)) {
                return getDestination();
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.of(logic);
        }
    }

    public boolean isOperation() {
        Matcher matcher = OPERATOR_PATTERN.matcher(logic);
        return matcher.matches();
    }

    private boolean applyOperator(int val, int operand, String operator) {
        if (operator.equals("<")) {
            return val < operand;
        } else if (operator.equals(">")) {
            return val > operand;
        }
        throw new IllegalArgumentException("Unknown operator '" + operator + "'");
    }

    public Optional<String> getProperty() {
        Matcher matcher = OPERATOR_PATTERN.matcher(logic);
        if (matcher.matches()) {
            return Optional.of(matcher.group(1));
        }
        return Optional.empty();
    }

    public Optional<Integer> getOperand() {
        Matcher matcher = OPERATOR_PATTERN.matcher(logic);
        if (matcher.matches()) {
            return Optional.of(Integer.valueOf(matcher.group(3)));
        }
        return Optional.empty();
    }

    public Optional<String> getDestination() {
        Matcher matcher = OPERATOR_PATTERN.matcher(logic);
        if (matcher.matches()) {
            return Optional.of(matcher.group(4));
        }
        return Optional.empty();
    }

    public Optional<String> getOperator() {
        Matcher matcher = OPERATOR_PATTERN.matcher(logic);
        if (matcher.matches()) {
            return Optional.of(matcher.group(2));
        }
        return Optional.empty();
    }
}
