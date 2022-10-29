package nl.michielgraat.adventofcode2018.day23;

import java.util.ArrayList;
import java.util.List;

public class Cube implements Comparable<Cube> {
    int minX;
    int minY;
    int minZ;
    int maxX;
    int maxY;
    int maxZ;
    int sideLength;
    int nrInRange;
    List<Nanobot> bots;

    public Cube(final int minX, final int minY, final int minZ, final int sideLength, final List<Nanobot> bots) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.sideLength = sideLength;
        this.maxX = minX + sideLength - 1;
        this.maxY = minY + sideLength - 1;
        this.maxZ = minZ + sideLength - 1;
        this.bots = bots;
        setNrInRange();
    }

    public List<Cube> split() {
        final List<Cube> cubes = new ArrayList<>();
        final int newSideLength = sideLength / 2;
        cubes.add(new Cube(minX, minY, minZ, newSideLength, bots));
        cubes.add(new Cube(minX + newSideLength, minY, minZ, newSideLength, bots));
        cubes.add(new Cube(minX, minY + newSideLength, minZ, newSideLength, bots));
        cubes.add(new Cube(minX, minY, minZ + newSideLength, newSideLength, bots));
        cubes.add(new Cube(minX + newSideLength, minY + newSideLength, minZ, newSideLength, bots));
        cubes.add(new Cube(minX + newSideLength, minY, minZ + newSideLength, newSideLength, bots));
        cubes.add(new Cube(minX, minY + newSideLength, minZ + newSideLength, newSideLength, bots));
        cubes.add(new Cube(minX + newSideLength, minY + newSideLength, minZ + newSideLength, newSideLength, bots));
        return cubes;
    }

    public int getSideLength() {
        return sideLength;
    }

    public int getDistanceToOrigin() {
        return Math.abs(minX) + Math.abs(minY) + Math.abs(minZ);
    }

    private int getDistance(final int botC, final int minC, final int maxC) {
        if (botC < minC)
            return minC - botC;
        if (botC > maxC)
            return botC - maxC;
        return 0;
    }

    private void setNrInRange() {
        int total = 0;
        for (final Nanobot bot : bots) {
            final int dist = getDistance(bot.getX(), minX, maxX) + getDistance(bot.getY(), minY, maxY)
                    + getDistance(bot.getZ(), minZ, maxZ);
            if (dist <= bot.getR())
                total++;
        }
        this.nrInRange = total;
    }

    public int getNrInRange() {
        return this.nrInRange;
    }

    @Override
    public int compareTo(final Cube o) {
        if (this.nrInRange != o.getNrInRange()) {
            return Integer.compare(o.getNrInRange(), this.getNrInRange());
        } else if (this.getDistanceToOrigin() != o.getDistanceToOrigin()) {
            return Integer.compare(this.getDistanceToOrigin(), o.getDistanceToOrigin());
        } else {
            return Integer.compare(this.getSideLength(), o.getSideLength());
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + minX;
        result = prime * result + minY;
        result = prime * result + minZ;
        result = prime * result + sideLength;
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
        final Cube other = (Cube) obj;
        if (minX != other.minX)
            return false;
        if (minY != other.minY)
            return false;
        if (minZ != other.minZ)
            return false;
        if (sideLength != other.sideLength)
            return false;
        return true;
    }
}