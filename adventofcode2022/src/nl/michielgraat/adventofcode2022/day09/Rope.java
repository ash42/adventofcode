package nl.michielgraat.adventofcode2022.day09;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rope {
    private final List<Knot> knots = new ArrayList<>();
    Set<Position> tailPositions = new HashSet<>();

    public Rope(final int size) {
        initRope(size);
        tailPositions.add(new Position(0, 0));
    }

    private void initRope(final int size) {
        for (int i = 0; i < size; i++) {
            knots.add(new Knot(i, new Position(0, 0)));
        }
    }

    private void moveHead(final String direction) {
        final Knot head = knots.get(0);
        switch (direction) {
            case "L":
                head.setX(head.getX() - 1);
                break;
            case "R":
                head.setX(head.getX() + 1);
                break;
            case "U":
                head.setY(head.getY() - 1);
                break;
            case "D":
                head.setY(head.getY() + 1);
                break;
            default:
                throw new IllegalArgumentException("Unknown direction '" + direction + "'");
        }
    }

    private void moveAlongXAxis(final Knot current, final Knot previous) {
        if (previous.getX() != current.getX()) {
            current.setPosition(
                    new Position((previous.getX() > current.getX())
                            ? current.getX() + 1
                            : current.getX() - 1, current.getY()));
        }
    }

    private void moveAlongYAxis(final Knot current, final Knot previous) {
        if (previous.getY() != current.getY()) {
            current.setPosition(new Position(current.getX(),
                    (previous.getY() > current.getY())
                            ? current.getY() + 1
                            : current.getY() - 1));
        }
    }

    private void addTailPosition(final Knot current) {
        if (current.getNr() == knots.size() - 1) {
            tailPositions.add(current.getPosition());
        }
    }

    private void moveRestOfRope() {
        for (int i = 1; i < knots.size(); i++) {
            final Knot previous = knots.get(i - 1);
            final Knot current = knots.get(i);
            if (!previous.touches(current)) {
                moveAlongXAxis(current, previous);
                moveAlongYAxis(current, previous);
                addTailPosition(current);
            }
        }
    }

    public void doMotions(final List<String> motions) {
        for (final String line : motions) {
            final String[] parts = line.split(" ");
            final String direction = parts[0];
            final int steps = Integer.parseInt(parts[1]);
            for (int step = 0; step < steps; step++) {
                moveHead(direction);
                moveRestOfRope();
            }
        }
    }

    public int getNrOfTailPositions() {
        return tailPositions.size();
    }
}
