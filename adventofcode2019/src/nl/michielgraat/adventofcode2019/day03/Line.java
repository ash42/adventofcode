package nl.michielgraat.adventofcode2019.day03;

import java.util.Optional;

public record Line(Position start, Position end) {

    public Optional<Position> getIntersection(final Line o) {
        if (start.x() == end.x()) {
            // This line is vertical
            if (o.start().x() != o.end().x()) {
                // Other line is horizontal
                final int minThisY = Math.min(start.y(), end.y());
                final int maxThisY = Math.max(start.y(), end.y());
                final int minOtherX = Math.min(o.start().x(), o.end().x());
                final int maxOtherX = Math.max(o.start().x(), o.end().x());
                if (start.x() >= minOtherX && start.x() <= maxOtherX && o.start().y() >= minThisY
                        && o.start().y() <= maxThisY) {
                    return Optional.of(new Position(start.x(), o.start().y(),
                            start.steps() + (Math.abs(start.y() - o.start().y()))));
                }
            }
        } else {
            // This line in horizontal
            if (o.start().y() != o.end().y()) {
                // Other line is vertical
                final int minThisX = Math.min(start.x(), end.x());
                final int maxThisX = Math.max(start.x(), end.x());
                final int minOtherY = Math.min(o.start().y(), o.end().y());
                final int maxOtherY = Math.max(o.start().y(), o.end().y());
                if (o.start.x() >= minThisX && o.start().x() <= maxThisX && start.y() >= minOtherY
                        && start.y() <= maxOtherY) {
                    return Optional.of(new Position(o.start().x(), start().y(),
                            start().steps() + (Math.abs(start().x() - o.start().x()))));
                }
            }
        }
        return Optional.empty();
    }
}
