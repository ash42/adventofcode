package nl.michielgraat.adventofcode2018.day18;

public class Value {
    int min;
    int val;

    public Value(final int min, final int val) {
        this.min = min;
        this.val = val;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + val;
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
        final Value other = (Value) obj;
        if (val != other.val)
            return false;
        return true;
    }

}
