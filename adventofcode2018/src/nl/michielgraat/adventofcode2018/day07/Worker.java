package nl.michielgraat.adventofcode2018.day07;

public class Worker {
    private int nr;
    private int secondsLeft;
    private Node node;

    public Worker(int nr) {
        this.nr = nr;
    }

    public void work() {
        if (secondsLeft > 0) {
            secondsLeft--;
        }
    }

    public boolean idle() {
        return node == null;
    }

    public boolean finished() {
        return node != null && secondsLeft == 0;
    }

    public int getNr() {
        return nr;
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public Node getNode() {
        return node;
    }    

    public void setNode(Node node) {
        this.node = node;
        if (node != null) {
            this.secondsLeft = node.getWorkLoad();
        }
    }
}