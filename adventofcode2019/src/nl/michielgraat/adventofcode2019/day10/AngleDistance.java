package nl.michielgraat.adventofcode2019.day10;

public class AngleDistance implements Comparable<AngleDistance> {

    private final Asteroid asteroid;
    private final double angle;
    private final double distance;
    private boolean destroyed;

    public AngleDistance(final Asteroid asteroid, final double angle, final double distance, final boolean destroyed) {
        this.asteroid = asteroid;
        this.angle = angle;
        this.distance = distance;
        this.destroyed = destroyed;
    }

    public Asteroid asteroid() {
        return asteroid;
    }

    public double angle() {
        return angle;
    }

    public double distance() {
        return distance;
    }

    public boolean destroyed() {
        return destroyed;
    }

    public void setDestroyed(final boolean destroyed) {
        this.destroyed = destroyed;
    }

    @Override
    public int compareTo(final AngleDistance o) {
        if (this.angle != o.angle) {
            return Double.compare(this.angle, o.angle);
        } else {
            return Double.compare(this.distance, o.distance);
        }
    }

    @Override
    public String toString() {
        return "AngleDistance [asteroid=" + asteroid + ", angle=" + angle + ", distance=" + distance + ", destroyed="
                + destroyed + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((asteroid == null) ? 0 : asteroid.hashCode());
        long temp;
        temp = Double.doubleToLongBits(angle);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(distance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (destroyed ? 1231 : 1237);
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
        final AngleDistance other = (AngleDistance) obj;
        if (asteroid == null) {
            if (other.asteroid != null)
                return false;
        } else if (!asteroid.equals(other.asteroid))
            return false;
        if (Double.doubleToLongBits(angle) != Double.doubleToLongBits(other.angle))
            return false;
        if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
            return false;
        if (destroyed != other.destroyed)
            return false;
        return true;
    }

}
