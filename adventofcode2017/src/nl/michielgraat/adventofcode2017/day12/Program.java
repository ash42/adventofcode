package nl.michielgraat.adventofcode2017.day12;

import java.util.List;

public class Program {
    
    int name;
    List<Program> neighbours;

    public Program(final int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }

    public void setName(final int name) {
        this.name = name;
    }

    public List<Program> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(final List<Program> neighbours) {
        this.neighbours = neighbours;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name;
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
        final Program other = (Program) obj;
        if (name != other.name)
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(name + ": ");
        for (final Program n : neighbours) {
            sb.append(n.getName() + " ");
        }
        return sb.toString();
    }

}