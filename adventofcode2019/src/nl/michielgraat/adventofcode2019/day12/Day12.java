package nl.michielgraat.adventofcode2019.day12;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2019.AocSolver;

public class Day12 extends AocSolver {

    protected Day12(final String filename) {
        super(filename);
    }

    private List<Moon> getMoons(final List<String> input) {
        final List<Moon> moons = new ArrayList<>();
        for (final String moon : input) {
            final String[] parts = moon.split(", ");
            final int x = Integer.parseInt(parts[0].split("=")[1]);
            final int y = Integer.parseInt(parts[1].split("=")[1]);
            final int z = Integer.parseInt(parts[2].split("=")[1].substring(0, parts[2].split("=")[1].length() - 1));
            moons.add(new Moon(x, y, z));
        }
        return moons;
    }

    private void applyGravityZ(final Moon m1, final Moon m2) {
        if (m1.getZ() > m2.getZ()) {
            m1.decreaseVelZ();
            m2.increaseVelZ();
        } else if (m1.getZ() < m2.getZ()) {
            m1.increaseVelZ();
            m2.decreaseVelZ();
        }
    }

    private void applyGravityY(final Moon m1, final Moon m2) {
        if (m1.getY() > m2.getY()) {
            m1.decreaseVelY();
            m2.increaseVelY();
        } else if (m1.getY() < m2.getY()) {
            m1.increaseVelY();
            m2.decreaseVelY();
        }
    }

    private void applyGravityX(final Moon m1, final Moon m2) {
        if (m1.getX() > m2.getX()) {
            m1.decreaseVelX();
            m2.increaseVelX();
        } else if (m1.getX() < m2.getX()) {
            m1.increaseVelX();
            m2.decreaseVelX();
        }
    }

    private void applyGravity(final Moon m1, final Moon m2) {
        applyGravityX(m1, m2);
        applyGravityY(m1, m2);
        applyGravityZ(m1, m2);
    }

    private void doTimeStep(final List<Moon> moons) {
        for (int i = 0; i < moons.size() - 1; i++) {
            for (int j = i + 1; j < moons.size(); j++) {
                applyGravity(moons.get(i), moons.get(j));
            }
        }
        moons.forEach(Moon::applyVelocity);
    }

    private void doTimeStepPosVel(final List<PosVel> ps) {
        for (final PosVel p : ps) {
            for (final PosVel p2 : ps) {
                if (!p.equals(p2)) {
                    p.applyGravity(p2);
                }
            }
        }
        ps.forEach(PosVel::applyVelocity);
    }

    private boolean same(final List<PosVel> p1, final List<PosVel> p2) {
        for (int i = 0; i < p1.size(); i++) {
            if (!p1.get(i).equals(p2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private long getStepsTillRepeat(final List<PosVel> ps) {
        final List<PosVel> original = ps.stream().map(PosVel::new).toList();
        long steps = 0;
        while (true) {
            doTimeStepPosVel(ps);
            steps++;
            if (same(ps, original)) {
                return steps;
            }
        }
    }

    private long gcd(final long n1, final long n2) {
        if (n1 == 0 || n2 == 0) {
            return n1 + n2;
        } else {
            final long absN1 = Math.abs(n1);
            final long absN2 = Math.abs(n2);
            final long biggerValue = Math.max(absN1, absN2);
            final long smallerValue = Math.min(absN1, absN2);
            return gcd(biggerValue % smallerValue, smallerValue);
        }
    }

    private long lcm(final long n1, final long n2) {
        if (n1 == 0 || n2 == 0)
            return 0;
        else {
            final long gcd = gcd(n1, n2);
            return Math.abs(n1 * n2) / gcd;
        }
    }

    @Override
    protected String runPart2(final List<String> input) {
        final List<Moon> moons = getMoons(input);

        final List<PosVel> x = new ArrayList<>();
        final List<PosVel> y = new ArrayList<>();
        final List<PosVel> z = new ArrayList<>();
        for (final Moon moon : moons) {
            x.add(new PosVel(moon.getX(), moon.getVelX()));
            y.add(new PosVel(moon.getY(), moon.getVelY()));
            z.add(new PosVel(moon.getZ(), moon.getVelZ()));
        }

        final long repeatX = getStepsTillRepeat(x);
        final long repeatY = getStepsTillRepeat(y);
        final long repeatZ = getStepsTillRepeat(z);

        return String.valueOf(lcm(repeatX, lcm(repeatY, repeatZ)));
    }

    @Override
    protected String runPart1(final List<String> input) {
        final List<Moon> moons = getMoons(input);
        for (int i = 1; i <= 1000; i++) {
            doTimeStep(moons);
        }
        final int total = moons.stream().mapToInt(Moon::getTotalEnery).sum();
        return String.valueOf(total);
    }

    public static void main(final String... args) {
        new Day12("day12.txt");
    }
}
