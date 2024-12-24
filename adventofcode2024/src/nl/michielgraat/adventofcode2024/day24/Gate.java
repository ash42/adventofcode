package nl.michielgraat.adventofcode2024.day24;

import java.util.Map;
import java.util.Optional;

public class Gate implements Comparable<Gate> {

    private String outputWire;
    private final String operand1;
    private final String operand2;
    private final String operator;

    public Gate(final String gate, final String outputWire) {
        this.outputWire = outputWire;
        operand1 = gate.split(" ")[0];
        operand2 = gate.split(" ")[2];
        operator = gate.split(" ")[1];
    }

    public Gate(final String outputWire, final String operand1, final String operand2, final String operator) {
        this.outputWire = outputWire;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    public Optional<Integer> getResult(final Map<String, Integer> values) {
        if (values.containsKey(operand1) && values.containsKey(operand2)) {
            switch (operator) {
                case "AND":
                    return Optional.of(values.get(operand1) & values.get(operand2));
                case "OR":
                    return Optional.of(values.get(operand1) | values.get(operand2));
                case "XOR":
                    return Optional.of(values.get(operand1) ^ values.get(operand2));
                default:
                    throw new IllegalArgumentException("Unknown operator: " + operator);
            }
        }
        return Optional.empty();
    }

    public String getOutputWire() {
        return outputWire;
    }

    public void setOutputWire(final String wire) {
        this.outputWire = wire;
    }

    public String getOperator() {
        return operator;
    }

    public String getOperand1() {
        return operand1;
    }

    public String getOperand2() {
        return operand2;
    }

    @Override
    public String toString() {
        return operand1 + " " + operator + " " + operand2 + " -> " + outputWire;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((outputWire == null) ? 0 : outputWire.hashCode());
        result = prime * result + ((operand1 == null) ? 0 : operand1.hashCode());
        result = prime * result + ((operand2 == null) ? 0 : operand2.hashCode());
        result = prime * result + ((operator == null) ? 0 : operator.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Gate other = (Gate) obj;
        if (outputWire == null) {
            if (other.outputWire != null)
                return false;
        } else if (!outputWire.equals(other.outputWire))
            return false;
        if (operand1 == null) {
            if (other.operand1 != null)
                return false;
        } else if (!operand1.equals(other.operand1))
            return false;
        if (operand2 == null) {
            if (other.operand2 != null)
                return false;
        } else if (!operand2.equals(other.operand2))
            return false;
        if (operator == null) {
            if (other.operator != null)
                return false;
        } else if (!operator.equals(other.operator))
            return false;
        return true;
    }

    @Override
    public int compareTo(final Gate o) {
        return this.getOutputWire().compareTo(o.getOutputWire());
    }

}
