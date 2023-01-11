package nl.michielgraat.adventofcode2019.day14;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Chemical {

    private final String name;
    private long quantity;

    public Chemical(final String name, final long quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String name() {
        return this.name;
    }

    public long quantity() {
        return this.quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public long getOreNeeded(final Map<Chemical, List<Chemical>> reactions) {
        final List<Chemical> wanted = new ArrayList<>();
        wanted.add(this);
        final List<Chemical> surplus = new ArrayList<>();
        long ore = 0;
        while (!wanted.isEmpty()) {
            final Chemical wantedChemical = wanted.get(0);
            long neededAmt = wantedChemical.quantity();
            if (surplus.contains(wantedChemical)) {
                final Chemical surplusChemical = surplus.get(surplus.indexOf(wantedChemical));
                if (wantedChemical.quantity() <= surplusChemical.quantity()) {
                    surplusChemical.setQuantity(surplusChemical.quantity() - wantedChemical.quantity());
                    wanted.remove(wantedChemical);
                    continue;
                } else {
                    neededAmt -= surplusChemical.quantity();
                    surplus.remove(surplusChemical);
                }
            }
            wanted.remove(wantedChemical);
            final long producedAmt = reactions.keySet().stream().filter(wantedChemical::equals).findFirst()
                    .orElseThrow(NoSuchElementException::new).quantity();
            final long nrReactions = (long) Math.ceil(neededAmt / (double) producedAmt);

            surplus.add(new Chemical(wantedChemical.name(), nrReactions * producedAmt - neededAmt));
            for (final Chemical input : reactions.get(wantedChemical)) {
                if (input.name().equals("ORE")) {
                    ore += input.quantity() * nrReactions;
                } else {
                    wanted.add(new Chemical(input.name(), input.quantity() * nrReactions));
                }
            }
        }
        return ore;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        final Chemical other = (Chemical) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Chemical[name=" + name + ", quantity=" + quantity + "]";
    }

}
