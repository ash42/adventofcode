package nl.michielgraat.adventofcode2018.day24;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day24 {

    private static final String FILENAME = "day24.txt";
    private static final boolean DEBUG = false;

    private List<Group> getGroups(final List<String> lines, int boost) {
        final List<Group> groups = new ArrayList<>();
        String type = Group.IMMUNE_SYSTEM;
        int nr = 1;
        for (int i = 1; i < lines.size(); i++) {
            final String line = lines.get(i);
            if (line.startsWith(Group.INFECTION)) {
                type = Group.INFECTION;
                nr = 1;
                boost = 0;
            } else {
                if (line.trim().length() != 0) {
                    groups.add(new Group(type, nr++, line, boost));
                }
            }
        }
        return groups;
    }

    private void printGroups(final List<Group> groups) {
        final List<Group> imGroups = groups.stream().filter(g -> g.getType().equals(Group.IMMUNE_SYSTEM))
                .collect(Collectors.toList());
        final List<Group> infGroups = groups.stream().filter(g -> g.getType().equals(Group.INFECTION))
                .collect(Collectors.toList());
        System.out.println("----------------------------------------");
        System.out.println("Immune System:");
        if (imGroups.size() > 0) {
            imGroups.forEach(i -> System.out.println("Group " + i.getNr() + " contains " + i.getNrUnits() + " units"));
        } else {
            System.out.println("No groups remain.");
        }
        System.out.println("Infection:");
        if (infGroups.size() > 0) {
            infGroups.forEach(i -> System.out.println("Group " + i.getNr() + " contains " + i.getNrUnits() + " units"));
        } else {
            System.out.println("No groups remain.");
        }
        System.out.println();
    }

    private int calcDamage(final Group attack, final Group defense) {
        final String attackType = attack.getAttackType();
        if (defense.getImmunities().contains(attackType)) {
            return 0;
        } else if (defense.getWeaknesses().contains(attackType)) {
            return attack.getEffectivePower() * 2;
        } else {
            return attack.getEffectivePower();
        }
    }

    private Map<Group, Group> getTargetSelection(final List<Group> groups) {
        final Map<Group, Group> targetSelection = new HashMap<>();
        // Splitting up isn't strictly necessary, but it is easier to get the
        // debug information grouped by group type this way.
        final List<Group> infGroups = groups.stream().filter(g -> g.getType().equals(Group.INFECTION))
                .collect(Collectors.toList());
        final List<Group> imGroups = groups.stream().filter(g -> g.getType().equals(Group.IMMUNE_SYSTEM))
                .collect(Collectors.toList());
        Collections.sort(infGroups);
        Collections.sort(imGroups);
        final List<Group> groupedSortedGroups = new ArrayList<>();
        groupedSortedGroups.addAll(infGroups);
        groupedSortedGroups.addAll(imGroups);
        for (final Group g : groupedSortedGroups) {
            final String gType = g.getType();
            int maxDmg = 0;
            Group maxDmgGroup = null;
            for (final Group g2 : groups) {
                final String g2Type = g2.getType();
                if (!gType.equals(g2Type) && !targetSelection.values().contains(g2)) {
                    final int dmg = calcDamage(g, g2);
                    if (DEBUG) {
                        System.out.println(g.getType() + " group " + g.getNr() + "(" + g.getEffectivePower() + ", "
                                + g.getInitiative() + ") would deal defending group " + g2.getNr()
                                + "(" + g2.getEffectivePower() + ", " + g2.getInitiative() + ") " + dmg + " damage");
                    }
                    if ((dmg == 0 && maxDmg == 0) || dmg > maxDmg
                            || (dmg == maxDmg && g2.getEffectivePower() > maxDmgGroup.getEffectivePower())
                            || (dmg == maxDmg && g2.getEffectivePower() == maxDmgGroup.getEffectivePower()
                                    && g2.getInitiative() > maxDmgGroup.getInitiative())) {
                        maxDmg = dmg;
                        maxDmgGroup = g2;
                    }
                }
            }
            if (maxDmgGroup != null && maxDmg > 0) {
                targetSelection.put(g, maxDmgGroup);
            }
        }
        if (DEBUG)
            System.out.println();

        return targetSelection;
    }

    private int attack(final Map<Group, Group> targetSelection, final List<Group> groups) {
        final List<Group> attackers = new ArrayList<>();
        attackers.addAll(targetSelection.keySet());
        Collections.sort(attackers, (a, b) -> Integer.compare(b.getInitiative(), a.getInitiative()));
        int nrKilled = 0;
        for (final Group attacker : attackers) {
            final Group defender = targetSelection.get(attacker);
            final String attackType = attacker.getAttackType();
            int dmg = attacker.getEffectivePower();
            if (defender.getWeaknesses().contains(attackType)) {
                dmg *= 2;
            } else if (defender.getImmunities().contains(attackType)) {
                dmg = 0;
            }
            int unitsKilled = (int) Math.floor(dmg / defender.getHitPoints());
            if (unitsKilled > defender.getNrUnits()) {
                unitsKilled = defender.getNrUnits();
            }
            nrKilled += unitsKilled;
            defender.setNrUnits(defender.getNrUnits() - unitsKilled);
            if (DEBUG) {
                System.out.println(attacker.getType() + " group " + attacker.getNr() + " attacks defending group "
                        + defender.getNr() + ", killing " + unitsKilled + " units");
            }
            if (defender.getNrUnits() == 0) {
                groups.remove(defender);
            }
        }
        return nrKilled;
    }

    private int runGame(final List<Group> groups, final boolean part1) {
        List<Group> infGroups = groups.stream().filter(g -> g.getType().equals(Group.INFECTION))
                .collect(Collectors.toList());
        List<Group> imGroups = groups.stream().filter(g -> g.getType().equals(Group.IMMUNE_SYSTEM))
                .collect(Collectors.toList());
        boolean deadlock = false;
        while (!infGroups.isEmpty() && !imGroups.isEmpty()) {
            if (DEBUG)
                printGroups(groups);
            final Map<Group, Group> targetSelection = getTargetSelection(groups);
            final int nrKilled = attack(targetSelection, groups);
            if (nrKilled == 0) {
                // There were no units killed, so nothing is happening -> deadlock.
                deadlock = true;
                break;
            }
            infGroups = groups.stream().filter(g -> g.getType().equals(Group.INFECTION))
                    .collect(Collectors.toList());
            imGroups = groups.stream().filter(g -> g.getType().equals(Group.IMMUNE_SYSTEM))
                    .collect(Collectors.toList());
        }
        if (DEBUG)
            printGroups(groups);
        if (part1) {
            return groups.stream().mapToInt(Group::getNrUnits).sum();
        } else {
            if (!deadlock) {
                return imGroups.stream().mapToInt(Group::getNrUnits).sum();
            } else {
                return 0;
            }
        }
    }

    public int runPart2(final List<String> lines) {
        int boost = 1;
        int total = 0;
        while ((total = runGame(getGroups(lines, boost), false)) == 0) {
            boost++;
        }
        return total;
    }

    public int runPart1(final List<String> lines) {
        return runGame(getGroups(lines, 0), true);
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day24().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day24().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }

}
