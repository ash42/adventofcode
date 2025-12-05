package nl.michielgraat.adventofcode2025.day05;

public class Range {

    long start;
    long end;

    public Range(final long start, final long end) {
        this.start = start;
        this.end = end;
    }

    public boolean contains(final long value) {
        return start <= value && value <= end;
    }

    /**
     * Check whether two ranges overlap. There are four cases (top = this, bottom =
     * other):
     * 
     * |----|
     * |----|
     * 
     * 1. this.end >= other.start && this.end <= other.end
     * 
     * |----|
     * |----|
     * 
     * 2. this.start >= other.start && this.start <= other.end
     * 
     * |---------|
     * |----|
     * 
     * 3. this.start <= other start && this.end >= other.end
     * 
     * |----|
     * |---------|
     * 4. Covered by both 1 and 2, so no extra checking necessary.
     */
    public boolean overlaps(final Range other) {
        return !this.equals(other) && ((this.end >= other.start && this.end <= other.end)
                || (this.start >= other.start && this.start <= other.end) ||
                (this.start <= other.start && this.end >= other.end));
    }
 
    public void adjust(final Range other) {
        if (overlaps(other)) {
            this.start = Math.min(this.start, other.start);
            this.end = Math.max(this.end, other.end);
        }
    }

    @Override
    public String toString() {
        return start + "-" + end;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (start ^ (start >>> 32));
        result = prime * result + (int) (end ^ (end >>> 32));
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
        final Range other = (Range) obj;
        if (start != other.start)
            return false;
        if (end != other.end)
            return false;
        return true;
    }

    
}
