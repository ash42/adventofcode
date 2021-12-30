package nl.michielgraat.adventofcode2021.day23;

public class Move {
    int sX;
    int sY;
    int eX;
    int eY;
    char pod;
    int nr;
    long energy;

    public Move(int sX, int sY, int eX, int eY, char pod, int nr) {
        this.sX = sX;
        this.sY = sY;
        this.eX = eX;
        this.eY = eY;
        this.pod = pod;
        this.nr = nr;
        switch (pod) {
            case 'A':
                this.energy = nr;
                break;
            case 'B':
                this.energy = nr * 10L;
                break;
            case 'C':
                this.energy = nr * 100L;
                break;
            case 'D':
                this.energy = nr * 1000L;
                break;
            default:
                throw new IllegalArgumentException("Unknown pod: '" + pod + "'");
        }
    }

    @Override
    public String toString() {
        return "Move for " + pod + " from (" + sX + "," + sY + ") to (" + eX + "," + eY + "), " + nr
                + " steps, " + energy + " energy.";
    }
}
