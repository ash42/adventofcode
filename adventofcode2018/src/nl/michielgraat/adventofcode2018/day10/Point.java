package nl.michielgraat.adventofcode2018.day10;

public class Point {
    int x;
    int y;
    int velX;
    int velY;

    public Point(final int x, final int y, final int velX, final int velY) {
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
    }

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        x += velX;
        y += velY;
    }

    public void moveBack() {
        x -= velX;
        y -= velY;
    }

    @Override
    public String toString() {
        return "position=<" + x + "," + y + "> velocity=<" + velX + "," + velY + ">";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelX() {
        return velX;
    }

    public int getVelY() {
        return velY;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
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
        final Point other = (Point) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

}