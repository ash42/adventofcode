package nl.michielgraat.adventofcode2020.day20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tile {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(image);
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (!Arrays.deepEquals(image, other.image))
			return false;
		if (number != other.number)
			return false;
		return true;
	}

	final int number;
	char[][] image;

	public Tile(int number, List<String> tile) {
		this.number = number;
		image = new char[tile.size()][tile.size()];
		for (int y = 0; y < tile.size(); y++) {
			String line = tile.get(y);
			for (int x = 0; x < line.length(); x++) {
				char c = line.charAt(x);
				image[x][y] = c;
			}
		}
	}

	public Tile(int number, char[][] image) {
		this.number = number;
		this.image = image;
	}

	public void rotate(int times) {
		times = times % 4;

		for (int i = 0; i < times; i++) {
			char[][] newimage = new char[image.length][image.length];
			for (int y = 0; y < image.length; y++) {
				for (int x = 0; x < image.length; x++) {
					newimage[image.length - 1 - y][x] = image[x][y];
				}
			}
			image = newimage;
		}
	}

	public void flipHorizontally() {
		char[][] newimage = new char[image.length][image.length];
		for (int y = 0; y < image.length; y++) {
			for (int x = 0; x < image.length; x++) {
				newimage[x][image.length - 1 - y] = image[x][y];
			}
		}
		image = newimage;
	}

	public void flipVertically() {
		char[][] newimage = new char[image.length][image.length];
		for (int y = 0; y < image.length; y++) {
			for (int x = 0; x < image.length; x++) {
				newimage[image.length - 1 - x][y] = image[x][y];
			}
		}
		image = newimage;
	}

	public String getTopBorder() {
		String s = new String();
		for (int x = 0; x < image.length; x++) {
			s += image[x][0];
		}
		return s;
	}

	public String getReverseTopBorder() {
		return reverse(getTopBorder());
	}

	public String getBottomBorder() {
		String s = new String();
		for (int x = 0; x < image.length; x++) {
			s += image[x][image.length - 1];
		}
		return s;
	}

	public String getReverseBottomBorder() {
		return reverse(getBottomBorder());
	}

	public String getLeftBorder() {
		String s = new String();
		for (int y = 0; y < image.length; y++) {
			s += image[0][y];
		}
		return s;
	}

	public String getReverseLeftBorder() {
		return reverse(getLeftBorder());
	}

	public String getRightBorder() {
		String s = new String();
		for (int y = 0; y < image.length; y++) {
			s += image[image.length - 1][y];
		}
		return s;
	}

	public String getReverseRightBorder() {
		return reverse(getRightBorder());
	}

	public List<String> getBorders() {
		List<String> borders = new ArrayList<String>();
		borders.add(getTopBorder());
		borders.add(getBottomBorder());
		borders.add(getLeftBorder());
		borders.add(getRightBorder());
		return borders;
	}

	private String reverse(String s) {
		StringBuilder sb = new StringBuilder();
		sb.append(s);
		sb.reverse();
		return sb.toString();
	}

	public boolean fits(String border) {
		return getTopBorder().equals(border) || getBottomBorder().equals(border) || getLeftBorder().equals(border)
				|| getRightBorder().equals(border) || reverse(getTopBorder()).equals(border)
				|| reverse(getBottomBorder()).equals(border) || reverse(getLeftBorder()).equals(border)
				|| reverse(getRightBorder()).equals(border);
	}

	public boolean anyFits(List<String> borders) {
		for (String border : borders) {
			if (fits(border)) {
				return true;
			}
		}
		return false;
	}

	public boolean fitsTopBorder(String border) {
		return getTopBorder().equals(border);
	}

	public boolean fitsTopBorder(Tile t) {
		String b = getTopBorder();
		return b.equals(t.getTopBorder()) || b.equals(t.getRightBorder()) || b.equals(t.getBottomBorder())
				|| b.equals(t.getLeftBorder()) || b.equals(t.getReverseTopBorder())
				|| b.equals(t.getReverseRightBorder()) || b.equals(t.getReverseBottomBorder())
				|| b.equals(t.getReverseLeftBorder());
	}

	public boolean fitsBottomBorder(String border) {
		return getBottomBorder().equals(border);
	}

	public boolean fitsBottomBorder(Tile t) {
		String b = getBottomBorder();
		return b.equals(t.getTopBorder()) || b.equals(t.getRightBorder()) || b.equals(t.getBottomBorder())
				|| b.equals(t.getLeftBorder()) || b.equals(t.getReverseTopBorder())
				|| b.equals(t.getReverseRightBorder()) || b.equals(t.getReverseBottomBorder())
				|| b.equals(t.getReverseLeftBorder());
	}

	public boolean fitsLeftBorder(String border) {
		return getLeftBorder().equals(border);
	}

	public boolean fitsLeftBorder(Tile t) {
		String b = getLeftBorder();
		return b.equals(t.getTopBorder()) || b.equals(t.getRightBorder()) || b.equals(t.getBottomBorder())
				|| b.equals(t.getLeftBorder()) || b.equals(t.getReverseTopBorder())
				|| b.equals(t.getReverseRightBorder()) || b.equals(t.getReverseBottomBorder())
				|| b.equals(t.getReverseLeftBorder());
	}

	public boolean fitsRightBorder(String border) {
		return getRightBorder().equals(border);
	}

	public boolean fitsRightBorder(Tile t) {
		String b = getRightBorder();
		return b.equals(t.getTopBorder()) || b.equals(t.getRightBorder()) || b.equals(t.getBottomBorder())
				|| b.equals(t.getLeftBorder()) || b.equals(t.getReverseTopBorder())
				|| b.equals(t.getReverseRightBorder()) || b.equals(t.getReverseBottomBorder())
				|| b.equals(t.getReverseLeftBorder());
	}

	public int getNumber() {
		return number;
	}

	char[][] getImage() {
		return image;
	}

	public String toString() {
		String s = new String();
		s += "Tile " + number + ":\r\n";
		for (int y = 0; y < image.length; y++) {
			for (int x = 0; x < image.length; x++) {
				s += image[x][y];
			}
			s += "\r\n";
		}
		return s;
	}

	public void findSeaMonsters() {
		for (int x = 0; x < image.length; x++) {
			for (int y = 0; y < image[0].length; y++) {
				if (y + 19 < image[0].length && x + 2 < image.length)
					if (image[x][y + 18] == '#' && image[x + 1][y] == '#' && image[x + 1][y + 5] == '#'
							&& image[x + 1][y + 6] == '#' && image[x + 1][y + 11] == '#' && image[x + 1][y + 12] == '#'
							&& image[x + 1][y + 17] == '#' && image[x + 1][y + 18] == '#' && image[x + 1][y + 19] == '#'
							&& image[x + 2][y + 1] == '#' && image[x + 2][y + 4] == '#' && image[x + 2][y + 7] == '#'
							&& image[x + 2][y + 10] == '#' && image[x + 2][y + 13] == '#'
							&& image[x + 2][y + 16] == '#') {
						image[x][y + 18] = 'O';
						image[x + 1][y] = 'O';
						image[x + 1][y + 5] = 'O';
						image[x + 1][y + 6] = 'O';
						image[x + 1][y + 11] = 'O';
						image[x + 1][y + 12] = 'O';
						image[x + 1][y + 17] = 'O';
						image[x + 1][y + 18] = 'O';
						image[x + 1][y + 19] = 'O';
						image[x + 2][y + 1] = 'O';
						image[x + 2][y + 4] = 'O';
						image[x + 2][y + 7] = 'O';
						image[x + 2][y + 10] = 'O';
						image[x + 2][y + 13] = 'O';
						image[x + 2][y + 16] = 'O';
					}
			}
		}
	}
}
