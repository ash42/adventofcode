package nl.michielgraat.adventofcode2019.day12;

public class PosVel {
    private int pos;
    private int vel;

    public PosVel(final int pos, final int vel) {
        this.pos = pos;
        this.vel = vel;
    }

    public PosVel(final PosVel p) {
        this.pos = p.pos;
        this.vel = p.vel;
    }

    public void applyGravity(final PosVel p) {
        if (pos < p.getPos()) {
            vel++;
        } else if (pos > p.getPos()) {
            vel--;
        }
    }

    public void applyVelocity() {
        pos += vel;
    }

    public void increaseVelocity() {
        vel++;
    }

    public int getPos() {
        return pos;
    }

    public int getVel() {
        return vel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + pos;
        result = prime * result + vel;
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
        final PosVel other = (PosVel) obj;
        if (pos != other.pos)
            return false;
        if (vel != other.vel)
            return false;
        return true;
    }

}
