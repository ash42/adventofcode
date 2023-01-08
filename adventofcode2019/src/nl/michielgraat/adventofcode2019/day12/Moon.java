package nl.michielgraat.adventofcode2019.day12;

public class Moon {
    private int x;
    private int y;
    private int z;
    private int velX;
    private int velY;
    private int velZ;

    public Moon(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void applyVelocity() {
        x += velX;
        y += velY;
        z += velZ;
    }

    public int getPotentialEnergy() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    public int getKineticEnery() {
        return Math.abs(velX) + Math.abs(velY) + Math.abs(velZ);
    }

    public int getTotalEnery() {
        return getPotentialEnergy() * getKineticEnery();
    }

    public void increaseVelX() {
        this.velX++;
    }

    public void increaseVelY() {
        this.velY++;
    }

    public void increaseVelZ() {
        this.velZ++;
    }

    public void decreaseVelX() {
        this.velX--;
    }

    public void decreaseVelY() {
        this.velY--;
    }

    public void decreaseVelZ() {
        this.velZ--;
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

    public int getVelX() {
        return velX;
    }

    public void setVelX(final int velocity) {
        this.velX = velocity;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(final int velY) {
        this.velY = velY;
    }

    public int getVelZ() {
        return velZ;
    }

    public void setVelZ(final int velZ) {
        this.velZ = velZ;
    }

    @Override
    public String toString() {
        return "pos=<x=" + x + ", y=" + y + ", z= " + z + ">, vel=<x=" + velX + ", y=" + velY + ", z=" + velZ + ">";
    }

}
