package nl.michielgraat.adventofcode2023.day17;

import java.util.ArrayList;
import java.util.List;

public class Element implements Comparable<Element> {

    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    private Node node;
    private int heatLoss;

    public Element(Node node, int heatLoss) {
        this.node = node;
        this.heatLoss = heatLoss;
    }

    public List<Element> getNeighboursForPart2(int[][] grid) {
        List<Element> neighbours = new ArrayList<>();
        if (node.blocks() >= 4) {
            Element left = getNextElement(Math.floorMod(node.direction() - 1, 4), grid, 1);
            if (left != null) {
                neighbours.add(left);
            }

            Element right = getNextElement((node.direction() + 1) % 4, grid, 1);
            if (right != null) {
                neighbours.add(right);
            }
        }
        if (node.blocks() < 10) {
            Element straight = getNextElement(node.direction(), grid, node.blocks() + 1);
            if (straight != null) {
                neighbours.add(straight);
            }
        }
        return neighbours;
    }

    public List<Element> getNeighbours(int[][] grid) {
        List<Element> neighbours = new ArrayList<>();

        Element left = getNextElement(Math.floorMod(node.direction() - 1, 4), grid, 1);
        if (left != null) {
            neighbours.add(left);
        }

        Element right = getNextElement((node.direction() + 1) % 4, grid, 1);
        if (right != null) {
            neighbours.add(right);
        }

        if (node.blocks() < 3) {
            Element straight = getNextElement(node.direction(), grid, node.blocks() + 1);
            if (straight != null) {
                neighbours.add(straight);
            }
        }

        return neighbours;
    }

    private Element getNextElement(int direction, int[][] grid, int blocks) {
        int x = getNextX(direction);
        int y = getNextY(direction);
        if (x >= 0 && x < grid[0].length && y >= 0 && y < grid.length) {
            Node nextNode = new Node(x, y, blocks, direction);
            return new Element(nextNode, heatLoss + grid[nextNode.y()][nextNode.x()]);
        }
        return null;
    }

    private int getNextX(int newDirection) {
        int nextX = newDirection == NORTH || newDirection == SOUTH ? node.x()
                : newDirection == EAST ? node.x() + 1 : node.x() - 1;
        return nextX;
    }

    private int getNextY(int newDirection) {
        int nextY = newDirection == EAST || newDirection == WEST ? node.y()
                : newDirection == NORTH ? node.y() - 1 : node.y() + 1;
        return nextY;
    }

    @Override
    public int compareTo(Element o) {
        if (this.heatLoss != o.getHeatLoss()) {
            return Integer.compare(this.heatLoss, o.getHeatLoss());
        } else if (this.node.direction() == o.getNode().direction() && this.node.blocks() != o.getNode().blocks()) {
            return Integer.compare(this.node.blocks(), o.getNode().blocks());
        } else if (this.node.y() != o.getNode().y()) {
            return Integer.compare(this.node.y(), o.getNode().y());
        } else {
            return Integer.compare(this.node.x(), o.getNode().x());
        }
    }

    public Node getNode() {
        return this.node;
    }

    public int getHeatLoss() {
        return this.heatLoss;
    }
}
