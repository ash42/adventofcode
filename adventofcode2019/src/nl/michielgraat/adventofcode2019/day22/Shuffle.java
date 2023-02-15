package nl.michielgraat.adventofcode2019.day22;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * After lots of reading and trying I finally settled on adapting the solution
 * below. I guess I am
 * just not smart enough for this ;-)
 * 
 * https://github.com/SimonBaars/AdventOfCode-Java/blob/master/src/main/java/com/sbaars/adventofcode/year19/days/Day22.java
 */
public class Shuffle {
    private final Technique technique;
    int n;

    public Shuffle(final String input) {
        technique = Technique.actionByText(input);
        if (technique == Technique.CUT) {
            n = Integer.parseInt(input.substring(4));
        } else if (technique == Technique.DEAL_WITH_INCREMENT) {
            n = Integer.parseInt(input.substring("deal with increment ".length()));
        } else {
            n = 0;
        }
    }

    public void run(final BigInteger[] input, final BigInteger deckSize) {
        switch (technique) {
            case DEAL_NEW_STACK: {
                input[0] = input[0].multiply(getAsBigInt(-1));
                input[1] = input[1].add(getAsBigInt(1)).multiply(getAsBigInt(-1));
            }
                break;
            case CUT:
                input[1] = input[1].add(getAsBigInt(n));
                break;
            case DEAL_WITH_INCREMENT: {
                final BigInteger p = getAsBigInt(n).modPow(deckSize.subtract(getAsBigInt(2)), deckSize);
                for (int i = 0; i < input.length; i++)
                    input[i] = input[i].multiply(p);
            }
                break;
        }
    }

    public List<Integer> run(final List<Integer> cards) {
        switch (technique) {
            case DEAL_NEW_STACK:
                Collections.reverse(cards);
                break;
            case CUT: {
                final int n2 = n > 0 ? n : cards.size() + n;
                final List<Integer> sub = new ArrayList<>(cards.subList(n2, cards.size()));
                sub.addAll(cards.subList(0, n2));
                return sub;
            }
            case DEAL_WITH_INCREMENT: {
                final Integer[] deck = new Integer[cards.size()];
                for (int i = 0, card = 0; i < cards.size(); i++) {
                    deck[card] = cards.get(i);
                    card = (card + n) % deck.length;
                }
                return Arrays.asList(deck);
            }
        }
        return cards;
    }

    public static BigInteger getAsBigInt(final long n) {
        return new BigInteger(Long.toString(n));
    }
}
