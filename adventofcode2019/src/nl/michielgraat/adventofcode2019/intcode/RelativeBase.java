package nl.michielgraat.adventofcode2019.intcode;

public class RelativeBase {
    private long value = 0L;

    public void increaseBy(final long incr) {
        this.value += incr;
    }

    public long getValue() {
        return this.value;
    }

    public int intValue() {
        return (int) this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
