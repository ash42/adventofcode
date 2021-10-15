package nl.michielgraat.adventofcode2015.day21;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2015.FileReader;

public class Day21 {

    private static final String FILENAME = "day21.txt";

    private final List<Weapon> weapons = new ArrayList<>();
    private final List<Armor> armor = new ArrayList<>();
    private final List<Ring> rings = new ArrayList<>();

    private int bossHitPoints = 109;
    private final int bossDamage = 8;
    private final int bossArmor = 2;

    public Day21() {
        weapons.add(new Weapon("Dagger", 8, 4));
        weapons.add(new Weapon("Shortsword", 10, 5));
        weapons.add(new Weapon("Warhammer", 25, 6));
        weapons.add(new Weapon("Longsword", 40, 7));
        weapons.add(new Weapon("Greataxe", 74, 8));

        armor.add(new Armor("No armor", 0, 0));
        armor.add(new Armor("Leather", 13, 1));
        armor.add(new Armor("Chainmail", 31, 2));
        armor.add(new Armor("Splintmail", 53, 3));
        armor.add(new Armor("Bandedmail", 75, 4));
        armor.add(new Armor("Platemail", 102, 5));

        rings.add(new Ring("No ring 1", 0, 0, 0));
        rings.add(new Ring("No ring 2", 0, 0, 0));
        rings.add(new Ring("Damage +1", 25, 1, 0));
        rings.add(new Ring("Damage +2", 50, 2, 0));
        rings.add(new Ring("Damage +3", 100, 3, 0));
        rings.add(new Ring("Defense +1", 20, 0, 1));
        rings.add(new Ring("Defense +2", 40, 0, 2));
        rings.add(new Ring("Defense +3", 80, 0, 3));
    }

    private boolean playerWins(Player p) {
        while (p.getHitPoints() > 0 && bossHitPoints > 0) {
            int dmgToBoss = p.getTotalDamage() - bossArmor;
            if (dmgToBoss <= 0) {
                dmgToBoss = 1;
            }
            bossHitPoints -= dmgToBoss;
            if (bossHitPoints <= 0) {
                return true;
            }

            int dmgToPlayer = bossDamage - p.getTotalArmor();
            if (dmgToPlayer <= 0) {
                dmgToPlayer = 1;
            }
            p.setHitPoints(p.getHitPoints() - dmgToPlayer);
        }
        return p.getHitPoints() > 0;
    }

    public int runPart2(List<String> lines) {
        int maxCost = Integer.MIN_VALUE;
        int initialBossHp = bossHitPoints;
        for (Weapon w : weapons) {
            for (Armor a : armor) {
                for (int i = 0; i < rings.size(); i++) {
                    for (int j=i+1; j < rings.size(); j++) {
                        bossHitPoints = initialBossHp;
                        Player p = new Player(w, a, rings.get(i), rings.get(j));
                        if (!playerWins(p) && p.getTotalCost() > maxCost) {
                            //System.out.println("New maximum cost for " + p + ": " + p.getTotalCost());
                            maxCost = p.getTotalCost();
                        }
                    }
                }
            }
        }
        return maxCost;
    }

    public int runPart1(List<String> lines) {
        int minCost = Integer.MAX_VALUE;
        int initialBossHp = bossHitPoints;
        for (Weapon w : weapons) {
            for (Armor a : armor) {
                for (int i = 0; i < rings.size(); i++) {
                    for (int j=i+1; j < rings.size(); j++) {
                        bossHitPoints = initialBossHp;
                        Player p = new Player(w, a, rings.get(i), rings.get(j));
                        if (playerWins(p) && p.getTotalCost() < minCost) {
                            //System.out.println("New minimum cost for " + p + ": " + p.getTotalCost());
                            minCost = p.getTotalCost();
                        }
                    }
                }
            }
        }
        return minCost;
    }

    public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day21().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day21().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}
}

class Player {
    private int hitPoints = 100;
    private Weapon weapon;
    private Armor armor;
    private Ring ring1;
    private Ring ring2;
    
    public Player(Weapon weapon, Armor armor, Ring ring1, Ring ring2) {
        this.weapon = weapon;
        this.armor = armor;
        this.ring1 = ring1;
        this.ring2 = ring2;
    }

    public int getTotalDamage() {
        int damage = weapon.getDamage();
        damage += (ring1 != null) ? ring1.getDamage() : 0;
        damage += (ring2 != null) ? ring2.getDamage() : 0;
        return damage;
    }

    public int getTotalArmor() {
        int a = armor.getArmor();
        a += (ring1 != null) ? ring1.getArmor() : 0;
        a += (ring2 != null) ? ring2.getArmor() : 0;
        return a;
    }

    public int getTotalCost() {
        int cost = weapon.getCost() + armor.getCost();
        cost += (ring1 != null) ? ring1.getCost() : 0;
        cost += (ring2 != null) ? ring2.getCost() : 0;
        return cost;
    }

    public int getHitPoints() {
        return this.hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public Ring getRing1() {
        return ring1;
    }

    public void setRing1(Ring ring1) {
        this.ring1 = ring1;
    }

    public Ring getRing2() {
        return ring2;
    }

    public void setRing2(Ring ring2) {
        this.ring2 = ring2;
    }

    @Override
    public String toString() {
        String a = armor.getName();
        String w = weapon.getName();
        String r1 = (ring1 != null) ? ring1.getName() : "";
        String r2 = (ring2 != null) ? ring2.getName() : "";
        return "[weapon=" + w + ", armor=" + a + ", ring1=" + r1 + ", ring2=" + r2 + "]";
    }
}

abstract class Item implements Comparable<Item> {
    private String name;
    private int cost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(Item o) {
        Integer thisCost = Integer.valueOf(this.getCost());
        Integer oCost = Integer.valueOf(o.getCost());
        return thisCost.compareTo(oCost);
    }
}

class Weapon extends Item {
    private int damage;

    public Weapon(String name, int cost, int damage) {
        super.setName(name);
        super.setCost(cost);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }    
}

class Armor extends Item {
    private int armor;

    public Armor(String name, int cost, int armor) {
        super.setName(name);
        super.setCost(cost);
        this.armor = armor;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    
}

class Ring extends Item {
    private int damage;
    private int armor;
    
    public Ring(String name, int cost, int damage, int armor) {
        super.setName(name);
        super.setCost(cost);
        this.damage = damage;
        this.armor = armor;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public int getArmor() {
        return armor;
    }
    public void setArmor(int armor) {
        this.armor = armor;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ring other = (Ring) obj;
        return this.getName().equals(other.getName());
    }
    @Override
    public String toString() {
        return getName();
    }

    
}