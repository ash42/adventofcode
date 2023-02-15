package nl.michielgraat.adventofcode2019.day22;

import java.util.Arrays;

public enum Technique {
    DEAL_WITH_INCREMENT("deal with increment "),
    DEAL_NEW_STACK("deal into new stack"),
    CUT("cut ");

    String name;

    private Technique(final String name) {
        this.name = name;
    }

    public static Technique actionByText(final String text) {
        return Arrays.stream(values()).filter(t -> text.startsWith(t.name)).findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}