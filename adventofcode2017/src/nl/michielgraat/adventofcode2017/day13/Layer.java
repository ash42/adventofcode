package nl.michielgraat.adventofcode2017.day13;

public class Layer {
    private int name;
    private int depth;

    public Layer(final int name, final int depth) {
        this.name = name;
        this.depth = depth;
    }

    public boolean isAtTop(final int ps) {
        return depth != 0 && (depth == 1 || ps == 0 || ps % (2 * (depth - 1)) == 0);
    }

    public int getName() {
        return name;
    }

    public void setName(final int name) {
        this.name = name;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(final int depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "Layer [name=" + name + ", depth=" + depth + "]";
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
        final Layer other = (Layer) obj;
        if (name != other.name)
            return false;
        return true;
    }
}