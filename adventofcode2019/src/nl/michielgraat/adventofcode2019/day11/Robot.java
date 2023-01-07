package nl.michielgraat.adventofcode2019.day11;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import nl.michielgraat.adventofcode2019.intcode.IntcodeComputer;

public class Robot {
    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;

    private int direction = UP;

    private final List<Panel> visitedPanels;
    private Panel currentPanel;

    private final IntcodeComputer computer;

    public Robot(final Panel initial, final List<String> program) {
        this.direction = UP;
        this.currentPanel = initial;
        this.visitedPanels = new ArrayList<>();
        this.visitedPanels.add(currentPanel);
        this.computer = new IntcodeComputer(program);
    }

    private void turn(final int turn) {
        switch (turn) {
            case 0:
                this.direction--;
                break;
            case 1:
                this.direction++;
                break;
            default:
                throw new IllegalArgumentException("Unknown direction to turn to [" + turn + "]");
        }
        this.direction = Math.floorMod(this.direction, 4);
    }

    private void move() {
        Panel newPanel = new Panel(currentPanel.x(), currentPanel.y(), Color.BLACK);
        switch (direction) {
            case UP:
                newPanel.setY(currentPanel.y() - 1);
                break;
            case RIGHT:
                newPanel.setX(currentPanel.x() + 1);
                break;
            case DOWN:
                newPanel.setY(currentPanel.y() + 1);
                break;
            case LEFT:
                newPanel.setX(currentPanel.x() - 1);
                break;
            default:
                throw new IllegalArgumentException("Unknown direction to move in [" + this.direction + "]");
        }
        if (visitedPanels.contains(newPanel)) {
            newPanel = visitedPanels.remove(visitedPanels.indexOf(newPanel));
        }
        visitedPanels.add(newPanel);
        currentPanel = newPanel;
    }

    public void paintTurnAndMove(final Color color, final int direction) {
        currentPanel.setColor(color);
        turn(direction);
        move();
    }

    public void run() {
        while (!computer.isHalted()) {
            final Color currentColor = currentPanel.color();
            final long input = currentColor == Color.BLACK ? 0L : 1L;
            computer.addInput(input);
            computer.run();
            final int newDirection = (int) computer.readOutput();
            final Color newColor = computer.readOutput() == 0 ? Color.BLACK : Color.WHITE;
            paintTurnAndMove(newColor, newDirection);
        }
    }

    public int getNrOfPaintedPanels() {
        return visitedPanels.size() - 1;
    }

    public String getIdentifier() {
        final int minX = visitedPanels.stream().mapToInt(Panel::x).min().orElseThrow(NoSuchElementException::new);
        final int maxX = visitedPanels.stream().mapToInt(Panel::x).max().orElseThrow(NoSuchElementException::new);
        final int minY = visitedPanels.stream().mapToInt(Panel::y).min().orElseThrow(NoSuchElementException::new);
        final int maxY = visitedPanels.stream().mapToInt(Panel::y).max().orElseThrow(NoSuchElementException::new);
        final StringBuilder sb = new StringBuilder();
        sb.append("\n\n");
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Panel p = new Panel(x, y, Color.BLACK);
                if (visitedPanels.contains(p)) {
                    p = visitedPanels.get(visitedPanels.indexOf(p));
                }
                sb.append(p.color() == Color.BLACK ? ' ' : '#');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
