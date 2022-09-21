package nl.michielgraat.adventofcode2018.day12;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Generation {
    List<String> input;
    List<String> output;
    Map<String, String> notes;
    int zeroIdx;
    int score;

    public Generation(final List<String> input, final Map<String, String> notes, final int zeroIdx) {
        this.input = input;
        this.notes = notes;
        this.zeroIdx = zeroIdx;
    }

    private void calculateScore() {
        score = 0;
        for (int i = 0; i < output.size(); i++) {
            if (output.get(i).equals("#")) {
                score += (i - zeroIdx);
            }
        }
    }

    public void grow() {
        output = new ArrayList<>();
        for (int i = -2; i < input.size() + 2; i++) {
            final StringBuilder noteSb = new StringBuilder();
            for (int j = i - 2; j <= i + 2; j++) {
                if (j < 0 || j >= input.size()) {
                    noteSb.append(".");
                } else {
                    noteSb.append(input.get(j));
                }
            }
            final String note = noteSb.toString();
            final String result = notes.get(note);

            if (i < 0 && (result.equals("#") || !output.isEmpty())) {
                zeroIdx++;
                output.add(result);
            } else if (i >= 0) {
                output.add(result);
            }
        }
        calculateScore();
    }

    @Override
    public String toString() {
        return "Zero index: " + zeroIdx + ", score: " + score + " - " + String.join("", output);
    }

    public List<String> getOutput() {
        return output;
    }

    public int getZeroIdx() {
        return zeroIdx;
    }

    public int getScore() {
        return score;
    }
}
