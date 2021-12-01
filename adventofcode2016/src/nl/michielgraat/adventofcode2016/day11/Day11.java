package nl.michielgraat.adventofcode2016.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2016.FileReader;

public class Day11 {

    private static final String FILENAME = "day11.txt";

    private List<String> getEquipment(String line, String t) {
        List<String> microchips = new ArrayList<>();
        String[] elements = line.split(" ");
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].contains(t)) {
                String type = elements[i - 1];
                if (t.equals("microchip")) {
                    type = type.substring(0, type.indexOf("-"));
                    type = type.substring(0, 2) + "M";
                } else {
                    type = type.substring(0, 2) + "G";
                }
                microchips.add(type);
            }
        }
        return microchips;
    }

    private State getInitialState(List<String> lines) {
        List<String> floor1 = new ArrayList<>();
        List<String> floor2 = new ArrayList<>();
        List<String> floor3 = new ArrayList<>();
        List<String> floor4 = new ArrayList<>();
        for (String line : lines) {
            if (!line.contains("nothing")) {
                String[] elements = line.split(" ");
                String floor = elements[1];
                List<String> chips = getEquipment(line, "microchip");
                List<String> generators = getEquipment(line, "generator");
                if (floor.equals("first")) {
                    floor1.addAll(chips);
                    floor1.addAll(generators);
                } else if (floor.equals("second")) {
                    floor2.addAll(chips);
                    floor2.addAll(generators);
                } else if (floor.equals("third")) {
                    floor3.addAll(chips);
                    floor3.addAll(generators);
                } else {
                    floor4.addAll(chips);
                    floor4.addAll(generators);
                }
            }
        }
        return new State(0, 0, floor1, floor2, floor3, floor4);
    }

    private List<Integer> getInitialState2(List<String> rawData) {
        List<Integer> initialState = new ArrayList<>();

        Map<String, Integer> indexTable = new HashMap<>();
        int indexIncr = -2;

        for (String line : rawData) {
            System.out.println("FLOOR: " + line);
            int floorState = 0;
            // empty floor
            if (line.contains("nothing")) {
                initialState.add(floorState);
                continue;
            }

            String[] equipment = Arrays
                    .stream(line.substring(line.indexOf("contains") + "contains".length() + 1).split(",|and"))
                    .filter(s -> !s.isBlank()).map(String::trim).toArray(String[]::new);

            for (String equipName : equipment) {
                String equipTmp = equipName.contains("-") ? equipName.substring(0, equipName.indexOf('-'))
                        : equipName.substring(0, equipName.lastIndexOf(' '));

                if (!indexTable.containsKey(equipTmp)) {
                    indexIncr += 2;
                    indexTable.put(equipTmp, indexIncr);
                }

                if (equipName.contains("generator")) {
                    System.out.println("generator");
                    floorState |= (1 << (indexTable.get(equipTmp) + 1));
                } else {
                    System.out.println("microchip");
                    floorState |= (1 << indexTable.get(equipTmp));
                }
                System.out.println("Floor state: " + floorState);
            }
            initialState.add(floorState);
        }

        return initialState;
    }

    private String runPart2(List<String> lines) {
        return null;
    }

    private String runPart1(List<String> lines) {
        State initialState = getInitialState(lines);
        System.out.println(initialState.toString());
        List<Integer> ints = getInitialState2(lines);
        for (int i : ints) {
            System.out.println(Integer.toBinaryString(i));
        }
        int finalBitCount = ints.stream().mapToInt(n -> Integer.bitCount(n)).sum();
        System.out.println(finalBitCount);
        System.out.println(ints);
        return null;
    }

    public static void main(String... args) {
        List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day11().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day11().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }

}

class State {
    int elevator;
    int steps;
    List<String> floor1 = new ArrayList<>();
    List<String> floor2 = new ArrayList<>();
    List<String> floor3 = new ArrayList<>();
    List<String> floor4 = new ArrayList<>();

    public State(int elevator, int steps, List<String> floor1, List<String> floor2, List<String> floor3,
            List<String> floor4) {
        this.elevator = elevator;
        this.steps = steps;
        this.floor1 = floor1;
        this.floor2 = floor2;
        this.floor3 = floor3;
        this.floor4 = floor4;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + elevator;
        result = prime * result + ((floor1 == null) ? 0 : floor1.hashCode());
        result = prime * result + ((floor2 == null) ? 0 : floor2.hashCode());
        result = prime * result + ((floor3 == null) ? 0 : floor3.hashCode());
        result = prime * result + ((floor4 == null) ? 0 : floor4.hashCode());
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
        State other = (State) obj;
        if (elevator != other.elevator)
            return false;

        return this.floor1.size() == other.floor1.size() && this.floor1.containsAll(other.floor1)
                && this.floor2.size() == other.floor2.size() && this.floor2.containsAll(other.floor2)
                && this.floor3.size() == other.floor3.size() && this.floor3.containsAll(other.floor3)
                && this.floor4.size() == other.floor4.size() && this.floor4.containsAll(other.floor4);
    }

    @Override
    public String toString() {
        return "Elevator " + elevator + "\nSteps: " + steps + "\nF1: " + floor1 + "\nF2: " + floor2 + "\nF3: " + floor3
                + "\nF4: " + floor4;
    }
}
