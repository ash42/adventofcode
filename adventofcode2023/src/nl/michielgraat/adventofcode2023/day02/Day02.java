package nl.michielgraat.adventofcode2023.day02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day02 extends AocSolver {

    protected Day02(String filename) {
        super(filename);
    }

    @Override
    protected String runPart2(final List<String> input) {
        return String.valueOf(input.stream().map(Game::new).mapToInt(Game::getPower).sum());
    }

    @Override
    protected String runPart1(final List<String> input) {
        return String
                .valueOf(input.stream().map(Game::new).filter(Game::isValid).mapToInt(Game::getGameNr).sum());

    }

    public static void main(String... args) {
        new Day02("day02.txt");
    }
}

class Game {
    int gameNr;
    List<Grab> grabs;

    Game(String input) {
        parseInput(input);
    }

    Game(int gameNr, List<Grab> grabs) {
        this.gameNr = gameNr;
        this.grabs = grabs;
    }

    private void parseInput(String line) {
        this.gameNr = getGameNr(line);
        String[] grabs = line.substring(line.indexOf(":") + 2).split(";");

        List<Grab> lGrabs = new ArrayList<>();
        for (String grab : grabs) {
            String[] subsets = grab.split(",");
            List<Subset> lSubsets = new ArrayList<>();
            for (String subset : subsets) {
                String[] parts = subset.trim().split(" ");
                int nr = Integer.parseInt(parts[0]);
                String color = parts[1];
                lSubsets.add(new Subset(nr, color));
            }
            lGrabs.add(new Grab(lSubsets));
        }
        this.grabs = lGrabs;
    }

    private int getGameNr(String line) {
        Matcher matcher = Pattern.compile("\\d+").matcher(line);
        matcher.find();
        return Integer.parseInt(matcher.group());
    }

    boolean isValid() {
        for (Grab grab : grabs) {
            for (Subset subset : grab.subsets) {
                switch (subset.color) {
                    case "red":
                        if (subset.nr > 12)
                            return false;
                    case "green":
                        if (subset.nr > 13)
                            return false;
                    case "blue":
                        if (subset.nr > 14)
                            return false;
                }
            }
        }
        return true;
    }

    int getPower() {
        Map<String, Integer> colorToMax = new HashMap<>();
        for (Grab grab : grabs) {
            for (Subset subset : grab.subsets) {
                String color = subset.color;
                int nr = subset.nr;
                if (!colorToMax.containsKey(color) || colorToMax.containsKey(color) && nr > colorToMax.get(color)) {
                    colorToMax.put(color, nr);
                }
            }
        }
        return colorToMax.values().stream().reduce(1, (a, b) -> a * b);
    }

    int getGameNr() {
        return this.gameNr;
    }
}

class Grab {
    List<Subset> subsets;

    Grab(List<Subset> subsets) {
        this.subsets = subsets;
    }
}

class Subset {
    int nr;
    String color;

    Subset(int nr, String color) {
        this.nr = nr;
        this.color = color;
    }
}
