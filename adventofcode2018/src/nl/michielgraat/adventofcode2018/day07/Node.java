package nl.michielgraat.adventofcode2018.day07;

public class Node implements Comparable<Node> {
    private final char label;

    Node(final char label) {
        this.label = label;
    }

    char getLabel() {
        return this.label;
    }

    public int getWorkLoad() {
        return label - 64 + 60;
    }

    @Override
    public int compareTo(final Node o) {
        return Character.compare(this.getLabel(), o.getLabel());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + label;
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
        final Node other = (Node) obj;
        if (label != other.label)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return Character.toString(label);
    }
}
