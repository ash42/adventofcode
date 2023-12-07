package nl.michielgraat.adventofcode2023.day07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hand implements Comparable<Hand> {
    
    private static final int FIVE_OF_A_KIND = 7;
    private static final int FOUR_OF_A_KIND = 6;
    private static final int FULL_HOUSE = 5;
    private static final int THREE_OF_A_KIND = 4;
    private static final int TWO_PAIR = 3;
    private static final int ONE_PAIR = 2;
    private static final int HIGH_CARD = 1;

    private String hand;
    private int bid;
    private int type;
    private boolean jokerIsWildcard;

    public Hand(String hand, int bid) {
        this(hand, bid, false);
    }

    public Hand(String hand, int bid, boolean jokerIsWildcard) {
        this.hand = hand;
        this.bid = bid;
        this.jokerIsWildcard = jokerIsWildcard;
        if (jokerIsWildcard) {
            type = getHighestPossibleType();
        } else {
            type = determineType(this.hand);
        }
    }

    private int getHighestPossibleType() {
        Map<Integer,Integer> cardToAmount = getCardToAmountMap();
        List<Integer> values = new ArrayList<>(cardToAmount.values());
        Collections.sort(values);

        int nrJs = cardToAmount.containsKey(getCard('J')) ? cardToAmount.get(getCard('J')) : 0;
 
        if (nrJs == 4 || nrJs == 5) {
            return FIVE_OF_A_KIND;
        } else if (nrJs == 3) {
            return values.size() == 2 ? FIVE_OF_A_KIND : FOUR_OF_A_KIND;
        } else if (nrJs == 2) {
            return values.size() == 2 ? FIVE_OF_A_KIND : values.size() == 3 ? FOUR_OF_A_KIND : THREE_OF_A_KIND;
        } else if (nrJs == 1) {
            if (values.size() == 2) {
                return FIVE_OF_A_KIND;
            } else if (values.size() == 3) {
                if (values.get(1) == 2) {
                    return FULL_HOUSE;
                } else {
                    return FOUR_OF_A_KIND;
                }
            } else if (values.size() == 4) {
                return THREE_OF_A_KIND;
            } else {
                return ONE_PAIR;
            }
        } else {
            return determineType(hand);
        }
            
    }

    private int determineType(String hand) {
        Map<Integer,Integer> cardToAmount = getCardToAmountMap();
        List<Integer> values = new ArrayList<>(cardToAmount.values());
        Collections.sort(values);
        if (values.size() == 1) {
            return FIVE_OF_A_KIND;
        } else if (values.size() == 2) {
            if (values.get(0) == 1) {
                return FOUR_OF_A_KIND;
            } else {
                return FULL_HOUSE;
            }
        } else if (values.size() == 3) {
            if (values.get(2) == 3) {
                return THREE_OF_A_KIND;
            } else {
                return TWO_PAIR;
            }
        } else if (values.size() == 4) {
            return ONE_PAIR;
        } else {
            return HIGH_CARD;
        }
    }

    private Map<Integer,Integer> getCardToAmountMap() {
        Map<Integer,Integer> cardToAmount = new HashMap<>();

        for (char c : hand.toCharArray()) {
            int card = getCard(c);
            cardToAmount.putIfAbsent(card, 0);
            cardToAmount.put(card, cardToAmount.get(card) + 1);
        }

        return cardToAmount;
    }

    private int getCard(char c) {
        if (Character.isDigit(c)) {
            return Character.getNumericValue(c);
        } else {
            switch (c) {
                case 'A': return 14;
                case 'K': return 13;
                case 'Q': return 12;
                case 'J': return jokerIsWildcard ? 1 : 11;
                case 'T': return 10;
                default: throw new IllegalArgumentException("Unknown card '" + c + "'");
            }
        }
    }

    public String getTypeAsString() {
        switch (type) {
            case FIVE_OF_A_KIND: return "five of a kind";
            case FOUR_OF_A_KIND: return "four of a kind";
            case FULL_HOUSE: return "full house";
            case THREE_OF_A_KIND: return "three of a kind";
            case TWO_PAIR: return "two pair";
            case ONE_PAIR: return "one pair";
            case HIGH_CARD: return "high card";
            default: throw new IllegalArgumentException("Unknown hand type " + type);
        }
    }

    public int getType() {
        return type;
    }

    public String getHand() {
        return hand;
    }

    public int getBid() {
        return bid;
    }

    @Override
    public int compareTo(Hand o) {
        int typeComparison = Integer.compare(this.getType(), o.getType());
        if (typeComparison != 0) {
            return typeComparison;
        } else {
            for (int i = 0; i < 5; i++) {
                char thisCard = hand.toCharArray()[i];
                char oCard = o.getHand().toCharArray()[i];
                int comparison = Integer.compare(getCard(thisCard), getCard(oCard));
                if (comparison != 0) {
                    return comparison;
                }
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return hand + " (" + bid + "): " + getTypeAsString();
    }


}
