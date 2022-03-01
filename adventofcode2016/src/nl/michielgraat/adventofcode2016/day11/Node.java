package nl.michielgraat.adventofcode2016.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node implements Comparable<Node> {
    int elevator;
    int steps;
    boolean visited;
    Floor[] floors = new Floor[4];

    Node() {
        this.steps = 0;
        this.elevator = 0;
        floors[0] = new Floor();
        floors[1] = new Floor();
        floors[2] = new Floor();
        floors[3] = new Floor();
    }

    Node(final int elevator, final int steps) {
        this.steps = steps;
        this.elevator = elevator;
        floors[0] = new Floor();
        floors[1] = new Floor();
        floors[2] = new Floor();
        floors[3] = new Floor();
    }

    Node getCopy(final int next) {
        final Node newNode = new Node(next, steps + 1);
        final Floor f1 = new Floor();
        final Floor f2 = new Floor();
        final Floor f3 = new Floor();
        final Floor f4 = new Floor();
        f1.getChips().addAll(floors[0].getChips());
        f1.getGenerators().addAll(floors[0].getGenerators());
        f2.getChips().addAll(floors[1].getChips());
        f2.getGenerators().addAll(floors[1].getGenerators());
        f3.getChips().addAll(floors[2].getChips());
        f3.getGenerators().addAll(floors[2].getGenerators());
        f4.getChips().addAll(floors[3].getChips());
        f4.getGenerators().addAll(floors[3].getGenerators());
        newNode.floors[0] = f1;
        newNode.floors[1] = f2;
        newNode.floors[2] = f3;
        newNode.floors[3] = f4;
        return newNode;
    }

    List<Node> getChipGeneratorCombos(final int current, final int next) {
        final List<Node> neighbours = new ArrayList<>();
        final List<String> chips = floors[current].getChips();
        for (final String chip : chips) {
            // Chip/generator combo
            if (floors[current].containsGenerator(chip)) {
                final Node newNode = getCopy(next);
                newNode.floors[current].getChips().remove(chip);
                newNode.floors[current].getGenerators().remove(chip);
                newNode.floors[next].getChips().add(chip);
                newNode.floors[next].getGenerators().add(chip);
                neighbours.add(newNode);
                return neighbours;
            }
        }
        return neighbours;
    }

    List<Node> getChipCombos(final int current, final int next) {
        final List<Node> neighbours = new ArrayList<>();
        final List<String> chips = floors[current].getChips();
        if (chips.size() > 1) {
            for (int i = 0; i < chips.size() - 1; i++) {
                for (int j = i + 1; j < chips.size(); j++) {
                    final String chip1 = floors[current].getChips().get(i);
                    final String chip2 = floors[current].getChips().get(j);
                    final Node newNode = getCopy(next);
                    newNode.floors[current].getChips().remove(chip1);
                    newNode.floors[current].getChips().remove(chip2);
                    newNode.floors[next].getChips().add(chip1);
                    newNode.floors[next].getChips().add(chip2);
                    if (newNode.floors[current].isValid() && newNode.floors[next].isValid()) {
                        neighbours.add(newNode);
                        return neighbours;
                    }
                }
            }
        }

        return neighbours;
    }

    List<Node> getGeneratorCombos(final int current, final int next) {
        final List<Node> neighbours = new ArrayList<>();
        final List<String> generators = floors[current].getGenerators();
        if (generators.size() > 1) {
            for (int i = 0; i < generators.size() - 1; i++) {
                for (int j = i + 1; j < generators.size(); j++) {
                    final String gen1 = floors[current].getGenerators().get(i);
                    final String gen2 = floors[current].getGenerators().get(j);
                    final Node newNode = getCopy(next);
                    newNode.floors[current].getGenerators().remove(gen1);
                    newNode.floors[current].getGenerators().remove(gen2);
                    newNode.floors[next].getGenerators().add(gen1);
                    newNode.floors[next].getGenerators().add(gen2);
                    if (newNode.floors[current].isValid() && newNode.floors[next].isValid()) {
                        neighbours.add(newNode);
                        return neighbours;
                    }
                }
            }
        }

        return neighbours;
    }

    List<Node> getDouble(final int current, final int next) {
        final List<Node> neighbours = getChipGeneratorCombos(current, next);
        neighbours.addAll(getChipCombos(current, next));
        neighbours.addAll(getGeneratorCombos(current, next));
        return neighbours;
    }

    List<Node> getSingle(final int current, final int next, final boolean chip) {
        final List<Node> neighbours = new ArrayList<>();
        final List<String> elements = chip ? floors[current].getChips() : floors[current].getGenerators();
        for (final String element : elements) {
            final Node newNode = getCopy(next);
            if (chip) {
                newNode.floors[current].getChips().remove(element);
                newNode.floors[next].getChips().add(element);
            } else {
                newNode.floors[current].getGenerators().remove(element);
                newNode.floors[next].getGenerators().add(element);
            }
            if (newNode.floors[current].isValid() && newNode.floors[next].isValid()) {
                neighbours.add(newNode);
                return neighbours;
            }
        }
        return neighbours;
    }

    List<Node> getNeighbours() {
        List<Node> neighbours = new ArrayList<>();
        if (elevator >= 0 && elevator < 3) {
            neighbours = getDouble(elevator, elevator + 1);
            if (neighbours.isEmpty()) {
                neighbours.addAll(getSingle(elevator, elevator + 1, true));
                if (neighbours.isEmpty()) {
                    neighbours.addAll(getSingle(elevator, elevator + 1, false));
                }
            }
        }
        if (elevator > 0 && elevator <= 3) {
            neighbours.addAll(getSingle(elevator, elevator - 1, true));
            if (neighbours.isEmpty()) {
                neighbours.addAll(getSingle(elevator, elevator - 1, false));
            }
        }
        return neighbours;
    }

    private List<String> getEquipment(final String line, final String t) {
        final List<String> equipment = new ArrayList<>();
        final String[] elements = line.split(" ");
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].contains(t)) {
                String type = elements[i - 1];
                if (t.equals("microchip")) {
                    type = type.substring(0, type.indexOf("-"));
                }
                equipment.add(type);
            }
        }
        return equipment;
    }

    Node initialize(final List<String> lines) {
        for (final String line : lines) {
            if (!line.contains("nothing")) {
                final String[] elements = line.split(" ");
                final String floor = elements[1];
                final List<String> chips = getEquipment(line, "microchip");
                final List<String> generators = getEquipment(line, "generator");
                if (floor.equals("first")) {
                    floors[0].setChips(chips);
                    floors[0].setGenerators(generators);
                } else if (floor.equals("second")) {
                    floors[1].setChips(chips);
                    floors[1].setGenerators(generators);
                } else if (floor.equals("third")) {
                    floors[2].setChips(chips);
                    floors[2].setGenerators(generators);
                } else {
                    floors[3].setChips(chips);
                    floors[3].setGenerators(generators);
                }
            }
        }
        elevator = 0;
        steps = 0;
        return this;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(final boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        String output = "Steps: " + steps + " steps\n";
        output += "F1 " + ((elevator == 0) ? "E " : "  ") + floors[0] + "\n";
        output += "F2 " + ((elevator == 1) ? "E " : "  ") + floors[1] + "\n";
        output += "F3 " + ((elevator == 2) ? "E " : "  ") + floors[2] + "\n";
        output += "F4 " + ((elevator == 3) ? "E " : "  ") + floors[3];
        return output;
    }

    @Override
    public int compareTo(final Node o) {
        return Integer.valueOf(this.steps).compareTo(Integer.valueOf(o.steps));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + elevator;
        result = prime * result + Arrays.hashCode(floors);
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
        final Node other = (Node) obj;
        if (elevator != other.elevator)
            return false;
        if (!Arrays.equals(floors, other.floors))
            return false;
        return true;
    }

    // public boolean isEquivalent(Node o) {

    // }
}
