package nl.michielgraat.adventofcode2019.day13;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import nl.michielgraat.adventofcode2019.intcode.IntcodeComputer;

public class ArcadeCabinet {
    private final IntcodeComputer computer;
    private final List<Tile> tiles;
    private int score;

    public ArcadeCabinet(final List<String> program) {
        computer = new IntcodeComputer(program);
        tiles = new ArrayList<>();
    }

    private void processTiles() {
        final List<Tile> tilesToAdd = new ArrayList<>();
        while (computer.hasOutput()) {
            final int typeNr = (int) computer.readOutput();
            final int y = (int) computer.readOutput();
            final int x = (int) computer.readOutput();
            if (x == -1) {
                score = typeNr;
            } else {
                final Tile tile = new Tile(x, y, TileType.convert(typeNr));
                tilesToAdd.add(tile);
            }
        }
        // Sometimes the tile which contains the ball is returned multiple times, once
        // containing the ball, the other times empty. To prevent not returning a ball,
        // remove all empty tiles which match the balls coordinates.
        final Tile ballTile = tilesToAdd.stream().filter(t -> t.type() == TileType.BALL).findFirst().orElse(null);
        // Ball tile is not send to output in final step (so is null).
        if (ballTile != null) {
            while (tilesToAdd.contains(ballTile)) {
                tilesToAdd.remove(ballTile);
            }
            tilesToAdd.add(ballTile);
        }
        // Replace all the tiles.
        tiles.removeAll(tilesToAdd);
        tiles.addAll(tilesToAdd);
    }

    private int getPaddleX() {
        return tiles.stream().filter(t -> t.type() == TileType.HORIZONTAL_PADDLE).findFirst()
                .orElseThrow(NoSuchElementException::new).x();
    }

    private int getBallX() {
        return tiles.stream().filter(t -> t.type() == TileType.BALL).findFirst()
                .orElseThrow(NoSuchElementException::new).x();
    }

    private int getInput() {
        final int paddleX = getPaddleX();
        final int ballX = getBallX();
        if (paddleX != ballX) {
            return paddleX < ballX ? 1 : -1;
        }
        return 0;
    }

    public void run() {
        computer.run();
        processTiles();
    }

    public void play() {
        computer.setValueAtAddress(0, 2);
        while (true) {
            computer.run();
            processTiles();
            if (getNrOfBlockTiles() == 0) {
                break;
            }
            computer.addInput(getInput());
        }
    }

    public int getNrOfBlockTiles() {
        return (int) tiles.stream().filter(t -> t.type() == TileType.BLOCK).count();
    }

    public int getScore() {
        return score;
    }
}
