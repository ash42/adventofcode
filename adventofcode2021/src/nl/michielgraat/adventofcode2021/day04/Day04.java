package nl.michielgraat.adventofcode2021.day04;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day04 {
    private static final String FILENAME = "day04.txt";
    
    private List<BingoCard> getBingoCards(final List<String> lines) {
        final List<BingoCard> cards = new ArrayList<>();
        for (int i = 2; i < lines.size(); i+=6) {
            final List<String> input = new ArrayList<>();
            input.add(lines.get(i));
            input.add(lines.get(i+1));
            input.add(lines.get(i+2));
            input.add(lines.get(i+3));
            input.add(lines.get(i+4));
            cards.add(new BingoCard(input));
        }
        return cards;
    }

    public int runPart2(final List<String> lines) {
        final List<Integer> draw = Stream.of(lines.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
        final List<BingoCard> cards = getBingoCards(lines);
        final List<BingoCard> completedCards = new ArrayList<>();

        for (final int nr : draw) {
            for (final BingoCard card : cards) {
                if (!completedCards.contains(card)) {
                    card.mark(nr);
                    if (card.thatsaBingo()) {
                        completedCards.add(card);
                        if (completedCards.size() == cards.size()) {
                            return card.getSumOfUnmarkedNrs() * nr;
                        }
                    }
                }
            }
        }

        return 0;
    }

    public int runPart1(final List<String> lines) {
        final List<Integer> draw = Stream.of(lines.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
        final List<BingoCard> cards = getBingoCards(lines);
        
        for (final int nr : draw) {
            for (final BingoCard card : cards) {
                card.mark(nr);
                if (card.thatsaBingo()) {
                    return card.getSumOfUnmarkedNrs() * nr;
                }
            }
        }

        return 0;
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day04().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day04().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

    }

}

class BingoCard {

    private final int[] nrs = new int[25];
    private final boolean[] marked = new boolean[25];

    BingoCard(final List<String> lines) {
        init(lines);
    }

    private void init(final List<String> lines) {
        int i = 0;
        for (final String line : lines) {
            final String[] sNrs = line.trim().split("\\s+");
            for (final String sNr : sNrs) {
                final int nr = Integer.parseInt(sNr);
                nrs[i] = nr;
                i++;
            }
        }
    }

    void mark(final int nr) {
        for (int i=0; i<25; i++) {
            if (nrs[i] == nr) {
                marked[i] = true;
            }
        }
    }

    int getSumOfUnmarkedNrs() {
        int total = 0;
        for (int i = 0; i < nrs.length; i++) {
            if (!marked[i]) {
                total += nrs[i];
            }
        }
        return total;
    }

    boolean thatsaBingo() {
        boolean bingo = false;

        for (int i = 0; i < 5; i++) {
            bingo = marked[i] && marked[i+5] && marked[i+10] && marked[i+15] && marked[i+20];
            if (bingo) {
                return bingo;
            }
        }

        for (int i = 0; i < 25; i += 5) {
            bingo = marked[i] && marked[i+1] && marked[i+2] && marked[i+3] && marked[i+4];
            if (bingo) {
                return bingo;
            }
        }

        return bingo;
    }
}
