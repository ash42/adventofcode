package nl.michielgraat.adventofcode2023.day24;

public record Line (long x, long y, long z, long velX, long velY, long velZ) {
    
    //Line is defined as y = mx + c

    public double getM() {
        long x2 = x + velX;
        long y2 = y + velY;
        return (double) (y-y2)/(x-x2);
    }

    public double getC() {
        //-c = mx - y
        return -(getM()*x - y);
    }

    public Coordinate crossesAt(Line o) {
        //O - coordinate of intersection

        //a1x + b1y + c1 = O
        //a2x + b2y + c2 = O
        //-> a = m, b = -1, c = c

        double oX = (-o.getC() + getC()) / (-getM() + 1*o.getM());
        double oY = (getC()*o.getM() - o.getC()*getM()) / (-getM() + o.getM());
        return new Coordinate(oX, oY);
    }
}
