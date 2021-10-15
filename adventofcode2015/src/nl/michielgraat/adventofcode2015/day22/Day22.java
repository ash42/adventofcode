package nl.michielgraat.adventofcode2015.day22;

import java.util.Calendar;
import java.util.Random;

public class Day22 {

    private int runGame(boolean part2) {
        int minCost = Integer.MAX_VALUE;
        Random r = new Random();
        
        for (int i=0; i < 10000000; i++) {
            int cost = 0;
    
            int playerHp = 50;
            int playerMana = 500;
            int bossHp = 71;
            int bossDmg = 10;
            
            boolean shield = false;
            boolean poison = false;
            boolean recharge = false;
            int shieldTurns = 0;
            int poisonTurns = 0;
            int rechargeTurns = 0;

            for (int j=0; j < 10000000; j++) {
                if (part2) {
                    playerHp--;
                }
                if (playerHp <= 0) {
                    cost = Integer.MAX_VALUE;
                    break;
                }

                if (shield) {
                    shieldTurns--;
                }
                if (poison) {
                    bossHp -= 3;
                    poisonTurns--;
                }
                if (recharge) {
                    playerMana += 101;
                    rechargeTurns--;
                }

                if (shieldTurns <= 0) {
                    shield = false;
                }
                if (poisonTurns <= 0) {
                    poison = false;
                }
                if (rechargeTurns <= 0) {
                    recharge = false;
                }

                if (bossHp <= 0) {
                    break;
                }

                int spell = r.nextInt(5);

                while ((spell == 2 && shieldTurns > 0) || (spell == 3 && poisonTurns > 0) || (spell == 4 && rechargeTurns > 0)) {
                    spell = r.nextInt(5);
                }

                if (spell == 0) {
                    cost += 53;
                    playerMana -= 53;
                    bossHp -= 4;
                } else if (spell == 1) {
                    cost += 73;
                    playerMana -= 73;
                    bossHp -= 2;
                    playerHp += 2;
                } else if (spell == 2) {
                    cost += 113;
                    playerMana -= 113;
                    shield = true;
                    shieldTurns = 6;
                } else if (spell == 3) {
                    cost += 173;
                    playerMana -= 173;
                    poison = true;
                    poisonTurns = 6;
                } else if (spell == 4) {
                    cost += 229;
                    playerMana -= 229;
                    recharge = true;
                    rechargeTurns = 5;
                }

                if (playerMana <= 0) {
                    cost = Integer.MAX_VALUE;
                    break;
                }

                if (bossHp <= 0) {
                    break;
                }

                if (poison) {
                    bossHp -= 3;
                    poisonTurns--;
                }
                if (recharge) {
                    playerMana += 101;
                    rechargeTurns--;
                }

                if (bossHp <= 0) {
                    break;
                }

                if (shield) {
                    int dmg = bossDmg - 7;
                    if (dmg <= 0) {
                        dmg = 1;
                    }
                    playerHp -= dmg;
                    shieldTurns--;
                } else {
                    playerHp -= bossDmg;
                }
                if (playerMana <= 0) {
                    cost = Integer.MAX_VALUE;
                    break;
                }

                if (shieldTurns <= 0) {
                    shield = false;
                }
                if (poisonTurns <= 0) {
                    poison = false;
                }
                if (rechargeTurns <= 0) {
                    recharge = false;
                }

            }
            if (cost < minCost) {
                minCost = cost;
            }
        }
        
        return minCost;
    }

    public int runPart2() {
        return runGame(true);
    }

    public int runPart1() {
        return runGame(false);
    }

    public static void main(String[] args) {
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day22().runPart1());
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day22().runPart2());
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

    }
}
