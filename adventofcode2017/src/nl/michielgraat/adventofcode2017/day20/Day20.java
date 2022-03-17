package nl.michielgraat.adventofcode2017.day20;

import java.util.ArrayList;
import java.util.List;

import nl.michielgraat.adventofcode2017.FileReader;

public class Day20 {

    private static final String FILENAME = "day20.txt";

    private List<Particle> getParticleList(final List<String> lines) {
        final List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            final String[] p = line.substring(3, line.indexOf(">")).split(",");
            final String[] v = line.substring(line.indexOf(" ") + 4, line.lastIndexOf(" ") - 2).split(",");
            final String[] a = line.substring(line.indexOf("a=<") + 3, line.length() - 1).split(",");
            final Particle pa = new Particle(i, Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]),
                    Integer.parseInt(v[0]), Integer.parseInt(v[1]), Integer.parseInt(v[2]), Integer.parseInt(a[0]),
                    Integer.parseInt(a[1]), Integer.parseInt(a[2]));
            particles.add(pa);
        }
        return particles;
    }

    private int getSlowestAcc(final List<String> lines) {
        int min = Integer.MAX_VALUE;
        int p = -1;
        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            final String[] a = line.substring(line.indexOf("a=<") + 3, line.length() - 1).split(",");
            final int dist = Math.abs(Integer.parseInt(a[0])) + Math.abs(Integer.parseInt(a[1]))
                    + Math.abs(Integer.parseInt(a[2]));
            if (dist < min) {
                min = dist;
                p = i;
            }
        }
        return p;
    }

    private List<Particle> getCollidingParticles(final List<Particle> particles) {
        final List<Particle> toRemove = new ArrayList<>();
        for (int i = 0; i < particles.size(); i++) {
            final Particle p1 = particles.get(i);
            for (int j = i + 1; j < particles.size(); j++) {
                final Particle p2 = particles.get(j);
                if (p1.equals(p2)) {
                    toRemove.add(p1);
                    toRemove.add(p2);
                }
            }
        }
        return toRemove;
    }

    public int runPart2(final List<String> lines) {
        final List<Particle> particles = getParticleList(lines);
        int cnt = 0;
        boolean same = false;
        // We assume we are finished when the particles list stays the same 20 times.
        while (cnt < 20) {
            cnt = same ? cnt + 1 : 0;
            final int size = particles.size();
            particles.forEach(Particle::update);
            particles.removeAll(getCollidingParticles(particles));
            same = size == particles.size();
        }
        return particles.size();
    }

    public int runPart1(final List<String> lines) {
        return getSlowestAcc(lines);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day20().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day20().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
