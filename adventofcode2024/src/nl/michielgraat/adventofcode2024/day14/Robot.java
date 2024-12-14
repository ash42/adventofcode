package nl.michielgraat.adventofcode2024.day14;

public class Robot {

    private int x;
    private int y;
    private final int velX;
    private final int velY;

    public Robot(final int x, final int y, final int velX, final int velY) {
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
    }

    public void move(final int sizeX, final int sizeY) {
        final int newX = this.x + this.velX;
        final int newY = this.y + this.velY;
        this.x = newX >= sizeX ? newX % sizeX : newX < 0 ? sizeX + newX : newX;
        this.y = newY >= sizeY ? newY % sizeY : newY < 0 ? sizeY + newY : newY;
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

    @Override
    public String toString() {
        return "Robot [x=" + x + ", y=" + y + ", velX=" + velX + ", velY=" + velY + "]";
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
        final Robot other = (Robot) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

}