package nl.michielgraat.adventofcode2022.day16;

import java.util.HashSet;
import java.util.Set;

public class Valve implements Comparable<Valve> {
    private final String name;
    private int flowRate;

    private final Set<String> neighbourNames = new HashSet<>();

    public Valve(final String name, final int flowRate) {
        this.name = name;
        this.flowRate = flowRate;
    }

    public void addNeighbourName(final String neighbourName) {
        neighbourNames.add(neighbourName);
    }

    public String getName() {
        return this.name;
    }

    public int getFlowRate() {
        return this.flowRate;
    }

    public void setFlowRate(final int flowRate) {
        this.flowRate = flowRate;
    }

    public Set<String> getNeighbourNames() {
        return this.neighbourNames;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        final Valve other = (Valve) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(final Valve o) {
        return Integer.compare(this.flowRate, o.flowRate);
    }
}
