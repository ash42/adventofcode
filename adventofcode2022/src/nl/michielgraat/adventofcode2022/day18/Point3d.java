package nl.michielgraat.adventofcode2022.day18;

import java.util.ArrayList;
import java.util.List;

public record Point3d(int x, int y, int z) implements Comparable<Point3d> {

    public boolean adjacent(Point3d o) {
        return Math.abs(this.x - o.x) + Math.abs(this.y - o.y) + Math.abs(this.z - o.z) == 1;
    }

    public List<Point3d> getNeighbours() {
        List<Point3d> neighbours = new ArrayList<>();
        neighbours.add(new Point3d(x+1,y,z));
        neighbours.add(new Point3d(x-1,y,z));
        neighbours.add(new Point3d(x,y+1,z));
        neighbours.add(new Point3d(x,y-1,z));
        neighbours.add(new Point3d(x,y,z+1));
        neighbours.add(new Point3d(x,y,z-1));
        return neighbours;
    }

    @Override
    public int compareTo(Point3d o) {
        if (this.z != o.z) {
            return Integer.compare(this.z, o.z);
        } else if (this.y != o.y) {
            return Integer.compare(this.y, o.y);
        } else {
            return Integer.compare(this.x, o.x);
        }
    }
}
