package nl.michielgraat.adventofcode2024.day23;

import java.util.HashSet;
import java.util.Set;

public class Node implements Comparable<Node> {
    private String name;
    private Set<Node> adjacentNodes = new HashSet<>();

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addAdjacentNode(Node node) {
        this.adjacentNodes.add(node);
    }

    public Set<Node> getAdjacentNodes() {
        return adjacentNodes;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String adjacent = new String();
        for (Node node : adjacentNodes) {
            adjacent += node.getName() + " ";
        }
        return "Node [name=" + name + ", adjacentNodes=" + adjacent + "]";
    }

    @Override
    public int compareTo(Node o) {
        return name.compareTo(o.getName());
    }   

}
