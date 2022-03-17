package nl.michielgraat.adventofcode2017.day20;

public class Particle {
    int id;

    int x;
    int y;
    int z;
    
    int velX;
    int velY;
    int velZ;

    int accX;
    int accY;
    int accZ;

    public Particle(int id, int x, int y, int z, int velX, int velY, int velZ, int accX, int accY, int accZ) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
    }

    public void update() {
        velX += accX;
        velY += accY;
        velZ += accZ;
        x += velX;
        y += velY;
        z += velZ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
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
        Particle other = (Particle) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        if (z != other.z)
            return false;
        return true;
    }

    
}
