package nl.michielgraat.adventofcode2025.day10;

import java.util.Arrays;

public record Node(Boolean[] lights, int steps) {

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(lights);
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
        Node other = (Node) obj;
        if (!Arrays.equals(lights, other.lights))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Arrays.stream(this.lights()).forEach(l -> sb.append(l ? '#' : '.'));
        sb.append("] ");
        sb.append(this.steps());
        return sb.toString();
    }
    
}
