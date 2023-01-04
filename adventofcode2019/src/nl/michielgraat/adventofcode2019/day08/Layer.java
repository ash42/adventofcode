package nl.michielgraat.adventofcode2019.day08;

import java.util.List;

public record Layer(List<Integer> pixels, int width, int height) {

    private static final int BLACK = 1;

    private long nr(final int n) {
        return pixels.stream().filter(i -> i == n).count();
    }

    public long nr0s() {
        return nr(0);
    }

    public long nr1s() {
        return nr(1);
    }

    public long nr2s() {
        return nr(2);
    }

    public String getImage() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\n\n");
        for (int row = 0; row < height; row++) {
            for (int pixelNr = (row * width); pixelNr < (row * width) + width; pixelNr++) {
                final int pixel = pixels.get(pixelNr);
                sb.append(pixel == BLACK ? '#' : ' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

}
