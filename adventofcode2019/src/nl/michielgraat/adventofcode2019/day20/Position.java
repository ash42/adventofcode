package nl.michielgraat.adventofcode2019.day20;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public record Position(int x, int y, Portal portal) implements Comparable<Position> {

    public List<Position> getNeighbours(final List<Position> maze) {
        final List<Position> neighbours = new ArrayList<>();
        if (!this.portal().name().isBlank() && !isStart() && !isEnd()) {
            neighbours.add(maze.stream().filter(p -> p.portal().name().equals(this.portal().name()))
                    .filter(p -> p.x() != this.x() || p.y() != this.y()).findAny()
                    .orElseThrow(NoSuchElementException::new));
        }
        final Position left = maze.stream().filter(p -> p.x() == this.x() - 1 && p.y() == this.y()).findAny()
                .orElse(null);
        final Position right = maze.stream().filter(p -> p.x() == this.x() + 1 && p.y() == this.y()).findAny()
                .orElse(null);
        final Position up = maze.stream().filter(p -> p.x() == this.x() && p.y() == this.y() - 1).findAny()
                .orElse(null);
        final Position down = maze.stream().filter(p -> p.x() == this.x() && p.y() == this.y() + 1).findAny()
                .orElse(null);
        if (left != null) {
            neighbours.add(left);
        }
        if (right != null) {
            neighbours.add(right);
        }
        if (up != null) {
            neighbours.add(up);
        }
        if (down != null) {
            neighbours.add(down);
        }

        return neighbours;
    }

    public List<Position> getNeighbours2(final List<Position> maze, final int maxDepth) {
        final List<Position> neighbours = new ArrayList<>();
        if (!this.portal().name().isBlank() && !this.portal().name().equals("AA") && !this.portal().name().equals("ZZ")
                && (this.portal().inner() || !this.portal().inner() && this.portal().level() > 0)
                && this.portal().level() < maxDepth) {
            final int newLevel = this.portal().inner() ? this.portal().level() + 1 : this.portal().level() - 1;
            final Position neighbour = maze.stream().filter(p -> p.portal().name().equals(this.portal().name()))
                    .filter(p -> p.x() != this.x() || p.y() != this.y()).findAny()
                    .orElseThrow(NoSuchElementException::new);
            final Position newNeighbour = new Position(neighbour.x(), neighbour.y(),
                    new Portal(neighbour.portal.name(), !this.portal().inner(), newLevel));
            neighbours.add(newNeighbour);
        }
        final Position left = maze.stream().filter(p -> p.x() == this.x() - 1 && p.y() == this.y()).findAny()
                .orElse(null);
        final Position right = maze.stream().filter(p -> p.x() == this.x() + 1 && p.y() == this.y()).findAny()
                .orElse(null);
        final Position up = maze.stream().filter(p -> p.x() == this.x() && p.y() == this.y() - 1).findAny()
                .orElse(null);
        final Position down = maze.stream().filter(p -> p.x() == this.x() && p.y() == this.y() + 1).findAny()
                .orElse(null);
        if (left != null) {
            if (!left.isStart() && !left.isEnd() || ((left.isStart() && this.portal().level() == 0)
                    || (left.isEnd() && this.portal().level() == 0))) {
                neighbours.add(left);
            }
        }
        if (right != null) {
            if (!right.isStart() && !right.isEnd() || ((right.isStart() && this.portal().level() == 0)
                    || (right.isEnd() && this.portal().level() == 0))) {
                neighbours.add(right);
            }
        }
        if (up != null) {
            if (!up.isStart() && !up.isEnd()
                    || ((up.isStart() && this.portal().level() == 0) || (up.isEnd() && this.portal().level() == 0))) {
                neighbours.add(up);
            }
        }
        if (down != null) {
            if (!down.isStart() && !down.isEnd() || ((down.isStart() && this.portal().level() == 0)
                    || (down.isEnd() && this.portal().level() == 0))) {
                neighbours.add(down);
            }
        }
        return neighbours;
    }

    public boolean isStart() {
        return this.portal().isStart();
    }

    public boolean isEnd() {
        return this.portal().isEnd();
    }

    @Override
    public int compareTo(final Position o) {
        if (this.y() != o.y()) {
            return Integer.compare(this.y(), o.y());
        } else {
            return Integer.compare(this.x(), o.x());
        }
    }

}
