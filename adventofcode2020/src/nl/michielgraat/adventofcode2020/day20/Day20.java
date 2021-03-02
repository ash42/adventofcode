package nl.michielgraat.adventofcode2020.day20;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2020.util.FileReader;

public class Day20 {

	private static final String FILENAME = "day20.txt";

	private List<Tile> getTiles(List<String> lines) {
		List<Tile> tiles = new ArrayList<Tile>();

		for (int i = 0; i < lines.size(); i += 12) {
			List<String> image = new ArrayList<String>();
			String title = lines.get(i);
			int number = Integer.valueOf(title.substring(title.indexOf(' ') + 1, title.length() - 1));
			for (int j = i + 1; j <= i + 10; j++) {
				image.add(lines.get(j));
			}
			tiles.add(new Tile(number, image));
		}
		return tiles;
	}

	public long runPart2(List<String> lines) {
		Tile[][] puzzle = createPuzzle(lines);
		char[][] finalImage = composeFinalImage(puzzle);

		Tile finalTile = new Tile(0, finalImage);
		finalTile.findSeaMonsters();
		finalTile.rotate(1);
		finalTile.findSeaMonsters();
		finalTile.rotate(1);
		finalTile.findSeaMonsters();
		finalTile.rotate(1);
		finalTile.findSeaMonsters();
		finalTile.rotate(1);

		finalTile.flipVertically();

		finalTile.findSeaMonsters();
		finalTile.rotate(1);
		finalTile.findSeaMonsters();
		finalTile.rotate(1);
		finalTile.findSeaMonsters();
		finalTile.rotate(1);
		finalTile.findSeaMonsters();
		finalTile.rotate(1);

		long total = 0;
		char[][] image = finalTile.getImage();
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image.length; j++) {
				if (image[i][j] == '#') {
					total++;
				}
			}
		}

		return total;
	}

	private char[][] composeFinalImage(Tile[][] puzzle) {
		char[][] finalImage = new char[puzzle.length * 8][puzzle.length * 8];
		int imagex = 0;
		int imagey = 0;
		for (int y = 0; y < puzzle.length; y++) {
			for (int x = 0; x < puzzle.length; x++) {
				Tile t = puzzle[x][y];
				imagex = x * 8;
				imagey = y * 8;
				char[][] tileImage = t.getImage();
				for (int tiy = 1; tiy <= tileImage.length - 2; tiy++) {
					for (int tix = 1; tix <= tileImage.length - 2; tix++) {
						finalImage[imagex][imagey] = tileImage[tix][tiy];
						imagex++;
					}
					imagey++;
					imagex = x * 8;
				}

			}
		}
		return finalImage;
		
		
	}

	private Tile[][] createPuzzle(List<String> lines) {
		List<Tile> tiles = getTiles(lines);
		int width = (int) Math.sqrt(tiles.size());
		Tile[][] puzzle = new Tile[width][width];
		List<Tile> corners = getCornerTiles(tiles);
		List<Tile> edges = getEdgeTiles(tiles);
		Tile corner1 = corners.remove(0);
		tiles.remove(corner1);
		rotateFirstCorner(edges, corner1);
		puzzle[0][0] = corner1;
		for (int y = 0; y < puzzle.length; y++) {
			for (int x = 0; x < puzzle.length; x++) {
				if (puzzle[x][y] == null) {
					if (y == 0) { // Top row
						String border = puzzle[x - 1][y].getRightBorder();
						for (Tile tile : tiles) {
							if (border.equals(tile.getTopBorder())) {
								tile.flipVertically();
								tile.rotate(3);
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							} else if (border.equals(tile.getRightBorder())) {
								tile.flipVertically();
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							} else if (border.equals(tile.getBottomBorder())) {
								tile.rotate(1);
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							} else if (border.equals(tile.getLeftBorder())) {
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							} else if (border.equals(tile.getReverseTopBorder())) {
								tile.rotate(3);
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							} else if (border.equals(tile.getReverseRightBorder())) {
								tile.rotate(2);
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							} else if (border.equals(tile.getReverseBottomBorder())) {
								tile.flipVertically();
								tile.rotate(1);
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							} else if (border.equals(tile.getReverseLeftBorder())) {
								tile.flipVertically();
								tile.rotate(2);
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							}
						}
					} else {
						String border = puzzle[x][y - 1].getBottomBorder();
						for (Tile tile : tiles) {
							if (border.equals(tile.getTopBorder())) {
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							} else if (border.equals(tile.getRightBorder())) {
								tile.rotate(3);
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							} else if (border.equals(tile.getBottomBorder())) {
								tile.flipVertically();
								tile.rotate(2);
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							} else if (border.equals(tile.getLeftBorder())) {
								tile.rotate(1);
								tile.flipVertically();
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							} else if (border.equals(tile.getReverseTopBorder())) {
								tile.flipVertically();
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							} else if (border.equals(tile.getReverseRightBorder())) {
								tile.flipVertically();
								tile.rotate(1);
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							} else if (border.equals(tile.getReverseBottomBorder())) {
								tile.rotate(2);
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;
							} else if (border.equals(tile.getReverseLeftBorder())) {
								tile.rotate(1);
								puzzle[x][y] = tile;
								tiles.remove(tile);
								break;

							}
						}
					}
				}
			}
		}
		return puzzle;
	}

	private void rotateFirstCorner(List<Tile> edges, Tile corner1) {
		int top = 0, right = 0, left = 0, bottom = 0;
		for (Tile edge : edges) {
			if (corner1.fitsTopBorder(edge)) {
				top++;
			} else if (corner1.fitsRightBorder(edge)) {
				right++;
			} else if (corner1.fitsBottomBorder(edge)) {
				bottom++;
			} else if (corner1.fitsLeftBorder(edge)) {
				left++;
			}
		}
		if (top == 0 && right == 0) {
			corner1.rotate(3);
		} else if (right == 0 && bottom == 0) {
			corner1.rotate(2);
		} else if (bottom == 0 && left == 0) {
			corner1.rotate(1);
		}
	}

	private List<Tile> getEdgeTiles(List<Tile> tiles) {
		List<Tile> edges = new ArrayList<Tile>();
		for (int i = 0; i < tiles.size(); i++) {
			int nrOfMatchingSides = 0;
			Tile tile = tiles.get(i);
			for (int j = 0; j < tiles.size(); j++) {
				if (i != j) {
					Tile toMatch = tiles.get(j);
					if (tile.anyFits(toMatch.getBorders())) {
						nrOfMatchingSides++;
					}
				}
			}
			if (nrOfMatchingSides == 3) {
				edges.add(tile);
			}
		}
		return edges;
	}

	private List<Tile> getCornerTiles(List<Tile> tiles) {
		List<Tile> corners = new ArrayList<Tile>();
		for (int i = 0; i < tiles.size(); i++) {
			int nrOfMatchingSides = 0;
			Tile tile = tiles.get(i);
			for (int j = 0; j < tiles.size(); j++) {
				if (i != j) {
					Tile toMatch = tiles.get(j);
					if (tile.anyFits(toMatch.getBorders())) {
						nrOfMatchingSides++;
					}
				}
			}
			if (nrOfMatchingSides == 2) {
				corners.add(tile);
			}
		}
		return corners;
	}

	public long runPart1(List<String> lines) {
		List<Tile> tiles = getTiles(lines);
		long total = 1;
		List<Tile> corners = getCornerTiles(tiles);
		for (Tile corner : corners) {
			total *= corner.getNumber();
		}

		return total;
	}

	public static void main(String[] args) {
		final List<String> lines = FileReader.getStringList(FILENAME);
		long start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 1: " + new Day20().runPart1(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
		start = Calendar.getInstance().getTimeInMillis();
		System.out.println("Answer to part 2: " + new Day20().runPart2(lines));
		System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");

	}

}
