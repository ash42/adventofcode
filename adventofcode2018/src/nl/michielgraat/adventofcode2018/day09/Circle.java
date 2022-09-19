package nl.michielgraat.adventofcode2018.day09;

import java.util.ArrayDeque;

public class Circle<E> extends ArrayDeque<E> {
    
    public void rotate (int steps) {
        if (steps < 0) {
            for (int i = 0; i < Math.abs(steps) - 1 ; i++) {
                E e = this.remove();
                this.addLast(e);
            }
        } else if (steps > 0) {
            for (int i = 0; i < steps; i++) {
                E e = this.removeLast();
                this.addFirst(e);
            }
        }
    }
}
