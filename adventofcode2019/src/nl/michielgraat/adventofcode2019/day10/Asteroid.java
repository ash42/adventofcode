package nl.michielgraat.adventofcode2019.day10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record Asteroid(int x, int y) {

    public double angle(final Asteroid o) {
        return Math.toDegrees(Math.atan2((double) this.y - o.y, (double) this.x - o.x));
    }

    public double distance(final Asteroid o) {
        return Math.hypot(Math.abs(this.x - o.x), Math.abs(this.y - o.y));
    }

    public List<AngleDistance> getAngleDistances(final List<Asteroid> asteroids) {
        final List<AngleDistance> angleDistances = new ArrayList<>();
        for (final Asteroid asteroid : asteroids) {
            if (!this.equals(asteroid)) {
                double angle = angle(asteroid) - 90;
                if (angle < 0.0) {
                    angle += 360.0;
                }
                final double distance = distance(asteroid);
                angleDistances.add(new AngleDistance(asteroid, angle, distance, false));
            }
        }
        Collections.sort(angleDistances);
        return angleDistances;
    }

    public Asteroid getNthDestroyedAsteroid(final int n, final List<Asteroid> asteroids) {
        final List<AngleDistance> angleDistances = this.getAngleDistances(asteroids);

        int nrDestroyed = 0;
        int idx = 0;
        while (true) {
            final AngleDistance ad = angleDistances.get(idx);
            if (ad.destroyed()) {
                // This asteroid is already destroyed, so try the next one.
                idx++;
                // We are shooting in a circle, so after reaching the end of the list, start at
                // the beginning again.
                idx %= angleDistances.size();
            } else {
                ad.setDestroyed(true);
                nrDestroyed++;
                if (nrDestroyed == n) {
                    // Found the nth destroyed, so break.
                    break;
                } else {
                    // Just destroyed an asteroid, so try the next one
                    idx++;
                    idx %= angleDistances.size();
                    // If the angle to the next asteroid we try is the same as the angle to the one
                    // we just destroyed, try the next one, because we can not shoot two asteroids
                    // which are in line (we can only destroy the second one once we have completed
                    // a rotation).
                    final double angle = ad.angle();
                    while (angleDistances.get(idx).angle() == angle) {
                        idx++;
                        idx %= angleDistances.size();
                    }
                }
            }
        }
        return angleDistances.get(idx).asteroid();
    }

    public int getNrInSight(final List<Asteroid> asteroids) {
        // For the number of asteroids in sight we just need to count the number of
        // unique angles to these asteroids. If there is more than 1 asteroid with
        // the same angle, only 1 angle gets recorded in the Set.
        final Set<Double> angles = new HashSet<>();
        for (final Asteroid asteroid : asteroids) {
            if (!this.equals(asteroid)) {
                angles.add(angle(asteroid));
            }
        }
        return angles.size();
    }
}
