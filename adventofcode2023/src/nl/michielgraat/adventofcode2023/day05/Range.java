package nl.michielgraat.adventofcode2023.day05;

import java.util.ArrayList;
import java.util.List;

public class Range {
    private long start;
    private long end;

    public Range(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public boolean inRange(long n) {
        return n >= start && n <= end;
    }

    public long getOffSet(long n) {
        if (inRange(n)) {
            return n - start;
        }
        throw new IllegalArgumentException(n + " is not in range [" + start + " - " + end + "]");
    }

    public List<Long> getAsList() {
        List<Long> list = new ArrayList<>();
        for (long i = start; i <= end; i++) {
            list.add(i);
        }
        return list;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Range [start=" + start + ", end=" + end + "]";
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Range other = (Range) obj;
        if (start != other.start)
            return false;
        if (end != other.end)
            return false;
        return true;
    }

}
