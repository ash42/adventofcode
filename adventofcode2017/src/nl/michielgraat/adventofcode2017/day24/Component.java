package nl.michielgraat.adventofcode2017.day24;

public class Component {
    int port1;
    int port2;
    boolean visited = false;

    public Component(int port1, int port2) {
        this.port1 = port1;
        this.port2 = port2;
    }

    public boolean connects(int nr) {
        return this.port1 == nr || this.port2 == nr;
    }

    public int getOther(int i) {
        return i == port1 ? port2 : port1;
    }

    public int getStrength() {
        return port1 + port2;
    }

    @Override
    public String toString() {
        return port1 + "/" + port2;
    }
}