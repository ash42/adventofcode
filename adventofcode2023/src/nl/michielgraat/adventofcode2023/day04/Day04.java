package nl.michielgraat.adventofcode2023.day04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day04 extends AocSolver {

    protected Day04(String filename) {
        super(filename);
    }

    private int getTotalNrCards(List<Card> cards) {
        Map<Integer, Integer> cardToAmount = new HashMap<>();
        cards.forEach(c -> cardToAmount.put(c.cardNr, 1));

        for (Card c : cards) {
            int nrMatching = c.getNrOfMatching();
            int cardNr = c.cardNr;
            int curAmount = cardToAmount.get(cardNr);
            for (int i = cardNr + 1; i <= cardNr + nrMatching; i++) {
                if (cardToAmount.containsKey(i)) {
                    cardToAmount.put(i, cardToAmount.get(i) + curAmount);
                }
            }
        }
        return cardToAmount.values().stream().mapToInt(i -> i).sum();
    }

    @Override
    protected String runPart2(final List<String> input) {
        List<Card> cards = input.stream().map(Card::new).collect(Collectors.toList());
        return String.valueOf(getTotalNrCards(cards));
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String.valueOf(input.stream().map(Card::new).mapToInt(Card::getScore).sum());
    }

    public static void main(String... args) {
        new Day04("day04.txt");
    }
}

class Card {
    int cardNr;
    List<Integer> winningNrs;
    List<Integer> ownNrs;

    Card(String line) {
        parse(line);
    }

    private int getCardNr(String line) {
        Matcher matcher = Pattern.compile("\\d+").matcher(line);
        matcher.find();
        return Integer.valueOf(matcher.group());
    }

    private List<Integer> getAllNrs(String line) {
        List<Integer> nrs = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\d+").matcher(line);
        while (matcher.find()) {
            nrs.add(Integer.valueOf(matcher.group()));
        }
        return nrs;
    }

    private void parse(String line) {
        this.cardNr = getCardNr(line);
        line = line.substring(line.indexOf(":") + 1);
        String[] parts = line.split("\\|");
        String winning = parts[0];
        String have = parts[1];

        this.winningNrs = getAllNrs(winning);
        this.ownNrs = getAllNrs(have);
    }

    int getScore() {
        int total = 0;
        for (int nr : winningNrs) {
            if (ownNrs.contains(nr)) {
                total = total == 0 ? 1 : total * 2;
            }
        }
        return total;
    }

    int getNrOfMatching() {
        int nrMatching = 0;
        for (int nr : winningNrs) {
            if (ownNrs.contains(nr)) {
                nrMatching++;
            }
        }
        return nrMatching;
    }
}
