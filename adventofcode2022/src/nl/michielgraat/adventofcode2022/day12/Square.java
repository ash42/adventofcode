package nl.michielgraat.adventofcode2022.day12;

import java.util.ArrayList;
import java.util.List;

public class Square implements Comparable<Square> {
    private final int x;
    private final int y;
    private int height;
    private boolean start;
    private boolean end;

    public Square(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Square(final int x, final int y, final int height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }

    public List<Square> getNeighbours(final List<Square> allSquares) {
        final List<Square> squares = new ArrayList<>();
        Square s = new Square(x - 1, y);
        if (allSquares.contains(s) && allSquares.get(allSquares.indexOf(s)).getHeight() <= (this.height + 1)) {
            squares.add(allSquares.get(allSquares.indexOf(s)));
        }
        s = new Square(x + 1, y);
        if (allSquares.contains(s) && allSquares.get(allSquares.indexOf(s)).getHeight() <= (this.height + 1)) {
            squares.add(allSquares.get(allSquares.indexOf(s)));
        }
        s = new Square(x, y - 1);
        if (allSquares.contains(s) && allSquares.get(allSquares.indexOf(s)).getHeight() <= (this.height + 1)) {
            squares.add(allSquares.get(allSquares.indexOf(s)));
        }
        s = new Square(x, y + 1);
        if (allSquares.contains(s) && allSquares.get(allSquares.indexOf(s)).getHeight() <= (this.height + 1)) {
            squares.add(allSquares.get(allSquares.indexOf(s)));
        }
        return squares;
    }

    @Override
    public String toString() {
        return "Square [x=" + x + ", y=" + y + ", height=" + height + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Square other = (Square) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public void setStart(final boolean start) {
        this.start = start;
    }

    public boolean isStart() {
        return this.start;
    }

    public void setEnd(final boolean end) {
        this.end = end;
    }

    public boolean isEnd() {
        return this.end;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    @Override
    public int compareTo(final Square o) {
        if (this.y != o.getY()) {
            return Integer.compare(this.y, o.getY());
        } else {
            return Integer.compare(this.x, o.getX());
        }
    }
}