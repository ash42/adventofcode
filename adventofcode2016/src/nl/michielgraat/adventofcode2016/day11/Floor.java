package nl.michielgraat.adventofcode2016.day11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Floor {
    List<String> chips = new ArrayList<>();
    List<String> generators = new ArrayList<>();

    boolean isValid() {
        if (!generators.isEmpty()) {
            for (String chip : chips) {
                if (!containsGenerator(chip)) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean isEmpty() {
        return chips.isEmpty() && generators.isEmpty();
    }

    boolean containsGenerator(String element) {
        return generators.contains(element);
    }

    boolean containsChip(String element) {
        return chips.contains(element);
    }

    void addChip(String chip) {
        chips.add(chip);
    }

    void addGenerator(String generator) {
        generators.add(generator);
    }

    void setChips(List<String> chips) {
        this.chips = chips;
    }

    void setGenerators(List<String> generators) {
        this.generators = generators;
    }

    List<String> getChips() {
        return chips;
    }

    List<String> getGenerators() {
        return generators;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String chip : chips) {
            sb.append(chip + " chip ");
        }
        for (String generator : generators) {
            sb.append(generator + " generator ");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        int chipsHashcode = 0;
        if (chips != null) {
            List<String> copy = chips.stream().sorted().collect(Collectors.toList());
            StringBuilder sb = new StringBuilder();
            for (String c : copy) {
                sb.append(c);
            }
            chipsHashcode = sb.toString().hashCode();
        }
        int generatorsHashCode = 0;
        if (generators != null) {
            List<String> copy = generators.stream().sorted().collect(Collectors.toList());
            StringBuilder sb = new StringBuilder();
            for (String c : copy) {
                sb.append(c);
            }
            generatorsHashCode = sb.toString().hashCode();
        }
        result = prime * result + chipsHashcode;
        result = prime * result + generatorsHashCode;
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
        Floor other = (Floor) obj;
        if (chips == null) {
            if (other.chips != null)
                return false;
        } else if (!(chips.size() == other.chips.size() && chips.containsAll(other.chips)
                && other.chips.containsAll(chips)))
            return false;
        if (generators == null) {
            if (other.generators != null)
                return false;
        } else if (!(generators.size() == other.generators.size() && generators.containsAll(other.generators)
                && other.generators.containsAll(generators)))
            return false;
        return true;
    }
}
