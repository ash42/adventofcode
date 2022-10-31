package nl.michielgraat.adventofcode2018.day24;

import java.util.ArrayList;
import java.util.List;

public class Group implements Comparable<Group> {

    public static final String IMMUNE_SYSTEM = "Immune System";
    public static final String INFECTION = "Infection";

    private final String type;
    private final int nr;
    private int nrUnits;
    private int hitPoints;
    private int attackDmg;
    private String attackType;
    private int initiative;
    private final List<String> weaknesses = new ArrayList<>();
    private final List<String> immunities = new ArrayList<>();

    public Group(final String type, final int nr, final String input, final int boost) {
        this.type = type;
        this.nr = nr;
        parseInput(input, boost);
    }

    public int getEffectivePower() {
        return nrUnits * attackDmg;
    }

    private List<String> parseWkIm(final String type, final String input) {
        final List<String> result = new ArrayList<>();
        if (input.contains(type)) {
            String part = input.substring(input.indexOf(type + " to") + type.length() + 3).trim();
            if (part.contains(";")) {
                part = part.substring(0, part.indexOf(";"));
            }
            final String[] parts = part.split(",");
            for (String p : parts) {
                p = p.trim();
                result.add(p);
            }
        }
        return result;
    }

    private void parseInput(final String input, final int boost) {
        final String[] parts = input.split(" ");
        nrUnits = Integer.parseInt(parts[0]);
        hitPoints = Integer.parseInt(parts[4]);
        initiative = Integer.parseInt(parts[parts.length - 1]);
        final String damagePart = input.substring(input.indexOf("does"), input.indexOf("damage"));
        final String[] dmgParts = damagePart.split(" ");
        attackDmg = Integer.parseInt(dmgParts[1]);
        if (type.equals(IMMUNE_SYSTEM)) {
            attackDmg += boost;
        }
        attackType = dmgParts[2];
        if (input.contains("(")) {
            final String wkIm = input.substring(input.indexOf("("), input.indexOf(")"));
            weaknesses.addAll(parseWkIm("weak", wkIm));
            immunities.addAll(parseWkIm("immune", wkIm));
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(nrUnits);
        sb.append(" units each with ");
        sb.append(hitPoints);
        sb.append(" hit points ");
        if (weaknesses.size() > 0 || immunities.size() > 0) {
            sb.append("(");
            if (weaknesses.size() > 0) {
                sb.append("weak to ");
                for (int i = 0; i < weaknesses.size(); i++) {
                    sb.append(weaknesses.get(i));
                    if (i < weaknesses.size() - 1) {
                        sb.append(", ");
                    }
                }
                if (immunities.size() > 0) {
                    sb.append("; ");
                }
            }
            if (immunities.size() > 0) {
                sb.append("immune to ");
                for (int i = 0; i < immunities.size(); i++) {
                    sb.append(immunities.get(i));
                    if (i < immunities.size() - 1) {
                        sb.append(", ");
                    }
                }
            }
            sb.append(") ");
        }
        sb.append("with an attack that does ");
        sb.append(attackDmg);
        sb.append(" ");
        sb.append(attackType);
        sb.append(" damage at initiative ");
        sb.append(initiative);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + nr;
        result = prime * result + attackDmg;
        result = prime * result + ((attackType == null) ? 0 : attackType.hashCode());
        result = prime * result + initiative;
        result = prime * result + ((weaknesses == null) ? 0 : weaknesses.hashCode());
        result = prime * result + ((immunities == null) ? 0 : immunities.hashCode());
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
        final Group other = (Group) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (nr != other.nr)
            return false;
        if (attackDmg != other.attackDmg)
            return false;
        if (attackType == null) {
            if (other.attackType != null)
                return false;
        } else if (!attackType.equals(other.attackType))
            return false;
        if (initiative != other.initiative)
            return false;
        if (weaknesses == null) {
            if (other.weaknesses != null)
                return false;
        } else if (!weaknesses.equals(other.weaknesses))
            return false;
        if (immunities == null) {
            if (other.immunities != null)
                return false;
        } else if (!immunities.equals(other.immunities))
            return false;
        return true;
    }

    @Override
    public int compareTo(final Group o) {
        if (this.getEffectivePower() != o.getEffectivePower()) {
            return Integer.compare(o.getEffectivePower(), this.getEffectivePower());
        } else {
            return Integer.compare(o.getInitiative(), this.getInitiative());
        }
    }

    public int getInitiative() {
        return initiative;
    }

    public int getNrUnits() {
        return nrUnits;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getAttackDmg() {
        return attackDmg;
    }

    public String getAttackType() {
        return attackType;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public List<String> getImmunities() {
        return immunities;
    }

    public int getNr() {
        return nr;
    }

    public String getType() {
        return type;
    }

    public void setNrUnits(final int nrUnits) {
        this.nrUnits = nrUnits;
    }
}
