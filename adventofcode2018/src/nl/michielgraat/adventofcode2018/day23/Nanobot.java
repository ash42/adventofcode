package nl.michielgraat.adventofcode2018.day23;

public class Nanobot {
    private int x;
    private int y;
    private int z;
    private int r;

    public Nanobot(final int x, final int y, final int z, final int r) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
    }

    public Nanobot(final String s) {
        final String[] parts = s.split(",");
        this.x = Integer.parseInt(parts[0].substring(parts[0].indexOf("<") + 1));
        this.y = Integer.parseInt(parts[1]);
        this.z = Integer.parseInt(parts[2].substring(0, parts[2].indexOf(">")));
        this.r = Integer.parseInt(parts[3].substring(parts[3].indexOf("=") + 1));
    }

    private int getManhattanDistance(final Nanobot o) {
        return Math.abs(this.x - o.x) + Math.abs(this.y - o.y) + Math.abs(this.z - o.z);
    }

    public boolean inRange(final Nanobot o) {
        return getManhattanDistance(o) <= r;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        result = prime * result + r;
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
        final Nanobot other = (Nanobot) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        if (z != other.z)
            return false;
        if (r != other.r)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "pos=<" + x + "," + y + "," + z + ">, r=" + r;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(final int z) {
        this.z = z;
    }

    public int getR() {
        return r;
    }

    public void setR(final int r) {
        this.r = r;
    }
}
