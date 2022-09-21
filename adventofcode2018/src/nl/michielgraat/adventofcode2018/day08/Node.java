package nl.michielgraat.adventofcode2018.day08;

import java.util.ArrayList;
import java.util.List;

public class Node {
    List<Node> children = new ArrayList<>();
    List<Integer> metadata = new ArrayList<>();

    public int getValue() {
        if (children.isEmpty()) {
            return metadata.stream().mapToInt(m -> m).sum();
        } else {
            int sum = 0;
            for (final int m : metadata) {
                if (m != 0 && m <= children.size()) {
                    sum += children.get(m - 1).getValue();
                }
            }
            return sum;
        }
    }
}
