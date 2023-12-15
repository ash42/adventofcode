package nl.michielgraat.adventofcode2023.day15;

import java.util.ArrayList;
import java.util.List;

public class Box {
    private int nr;
    private List<Lens> lenses;

    public Box(int nr) {
        this.nr = nr;
        lenses = new ArrayList<>();
    }

    public void removeLens(String lens) {
        if (lenses.contains(new Lens(lens, 0))) {
            lenses.remove(lenses.indexOf(new Lens(lens, 0)));
        }
    }

    public void addOrReplaceLens(Lens lens) {
        if (lenses.contains(lens)) {
            lenses.set(lenses.indexOf(lens), lens);
        } else {
            lenses.add(lens);
        }
    }

    public long getFocusingPower() {
        long total = 0;
        for (int slotNr = 0; slotNr < lenses.size(); slotNr++) {
            total += (nr + 1) * (slotNr + 1) * lenses.get(slotNr).focalLength();
        }
        return total;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + nr;
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
        Box other = (Box) obj;
        if (nr != other.nr)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Box [nr=" + nr + ", lenses=" + lenses + "]";
    }

}
